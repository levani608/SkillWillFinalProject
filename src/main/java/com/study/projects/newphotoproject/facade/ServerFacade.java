package com.study.projects.newphotoproject.facade;

import com.study.projects.newphotoproject.model.domain.database.*;
import com.study.projects.newphotoproject.model.dto.ServerPlanDto;
import com.study.projects.newphotoproject.model.dto.UserServerDto;
import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import com.study.projects.newphotoproject.model.enums.ServerPlanStatus;
import com.study.projects.newphotoproject.model.enums.UserStatus;
import com.study.projects.newphotoproject.model.enums.UserWalletStatus;
import com.study.projects.newphotoproject.model.mapper.ServerPlanMapper;
import com.study.projects.newphotoproject.model.mapper.UserServerMapper;
import com.study.projects.newphotoproject.model.param.PlanBuyParam;
import com.study.projects.newphotoproject.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServerFacade {

    private final ServerPlanService serverPlanService;
    private final UserService userService;
    private final UserWalletService userWalletService;
    private final UserServerService userServerService;
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    private final WalletTransactionService walletTransactionService;
    private final InvoiceService invoiceService;

    public List<ServerPlanDto> getServerPlans() {

        return serverPlanService.getAllServerPlans().stream()
                .filter(sp->sp.getServerPlanStatus()== ServerPlanStatus.VALID)
                .map(ServerPlanMapper::toServerPlanDto).toList();
    }

    @Transactional
    public List<UserServerDto> buyPlan(Long userId, PlanBuyParam planBuyParam) {

        UserEntity user = userService.findByUserId(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        if (user.getUserStatus() == UserStatus.DEACTIVATED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User deactivated!");
        }

        UserWalletEntity userWallet = userWalletService.findUserWalletById(planBuyParam.getUserWalletId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not Found!")); /*userWallets.get(0);*/
        if(userWallet.getUserWalletStatus()==UserWalletStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Wallet deleted!");
        }
        else if(userWallet.getPaymentMethodEntity().getPaymentMethodStatus() == PaymentMethodStatus.BLOCKED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Payment method blocked!");
        }
        else if (!Objects.equals(userWallet.getWalletOwnerEntity().getId(), userId))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This wallet doesn't belong to the user!");

        ServerPlanEntity serverPlan = serverPlanService.findServerPlanById(planBuyParam.getServerPlanId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server plan not found!"));
        if (serverPlan.getServerPlanStatus()==ServerPlanStatus.INVALID) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Server plan invalid!");
        }
        StockEntity stock = stockService.findByServerPlanEntity(serverPlan).orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "A mistake has occurred!"));

        List<String> userServerNames = List.of(planBuyParam.getUserServerName());
        int quantity = userServerNames.size();
        double serverPlanPrice = userWallet.getPaymentMethodEntity().getPaymentRate()*serverPlan.getPrice();
        double price = serverPlanPrice * quantity;

        if (stock.getQuantity() < quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough items in stock!");
        }
        else if (userWallet.getBalance() < serverPlanPrice) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough funds on the wallet!");
        }

        stock.setQuantity(stock.getQuantity()-quantity);
        stockService.saveStock(stock);

        StockHistoryEntity stockHistory = new StockHistoryEntity(stock, -quantity);
        stockHistoryService.saveStockHistory(stockHistory);

        List<UserServerEntity> userServers = new ArrayList<>();
        for (String userServerName : userServerNames) {
            userServers.add(new UserServerEntity(userServerName, user, serverPlan, 0.0));
        }
        userServerService.saveUserServers(userServers);

        userWallet.setBalance(userWallet.getBalance()-price);
        userWalletService.saveUserWallet(userWallet);

        WalletTransactionEntity transaction = new WalletTransactionEntity();
        transaction.setUserWalletEntity(userWallet);
        transaction.setAmount(-price);
        walletTransactionService.saveWalletTransaction(transaction);

        List<InvoiceEntity> invoices = new ArrayList<>();
        for (UserServerEntity userServer : userServers) {
            invoices.add(new InvoiceEntity(userServer, transaction));
        }
        invoiceService.saveInvoices(invoices);

        return userServers.stream()
                .map(UserServerMapper::toUserServerDto).toList();
    }
}
