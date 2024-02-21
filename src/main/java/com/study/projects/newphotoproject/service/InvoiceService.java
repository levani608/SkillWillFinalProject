package com.study.projects.newphotoproject.service;

import com.study.projects.newphotoproject.model.domain.database.InvoiceEntity;
import com.study.projects.newphotoproject.model.param.filterparams.InvoiceFilterParam;
import com.study.projects.newphotoproject.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<InvoiceEntity> saveInvoices(List<InvoiceEntity> invoices) {
        return invoiceRepository.saveAllAndFlush(invoices);
    }

    public List<InvoiceEntity> findAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<InvoiceEntity> findInvoicesByTransactionId(Long transactionId) {
        return invoiceRepository.findAllByTransactionEntityTransactionId(transactionId);
    }

    public List<InvoiceEntity> getFilteredInvoices(InvoiceFilterParam invoiceFilterParam) {

        List<Long> invoiceIds = new ArrayList<>();
        List<Long> userServerIds = new ArrayList<>();
        List<Long> transactionIds = new ArrayList<>();

        if (invoiceFilterParam.getInvoiceIds() != null)
            invoiceIds = invoiceFilterParam.getInvoiceIds();
        if (invoiceFilterParam.getUserServerIds() != null)
            userServerIds = invoiceFilterParam.getUserServerIds();
        if (invoiceFilterParam.getTransactionIds() != null)
            transactionIds = invoiceFilterParam.getTransactionIds();

        return invoiceRepository.findInvoicesByFilter(
                invoiceIds,/*invoiceFilterParam.getInvoiceIds(),*/
                userServerIds,/*invoiceFilterParam.getUserServerIds(),*/
                transactionIds,/*invoiceFilterParam.getTransactionIds(),*/
                invoiceFilterParam.getFrom(),
                invoiceFilterParam.getTo());

    }
}
