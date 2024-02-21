package com.study.projects.newphotoproject.model.mapper;

import com.study.projects.newphotoproject.model.domain.database.InvoiceEntity;
import com.study.projects.newphotoproject.model.dto.InvoiceDto;

public class InvoiceMapper {

    public static InvoiceDto toInvoiceDto(InvoiceEntity invoiceEntity) {
        return new InvoiceDto(invoiceEntity.getInvoiceId(), invoiceEntity.getCreatedAt(), invoiceEntity.getTransactionEntity().getTransactionId(),
                invoiceEntity.getUserServerEntity().getUserServerId(), WalletTransactionMapper.toWalletTransactionDto(invoiceEntity.getTransactionEntity()));
    }

}
