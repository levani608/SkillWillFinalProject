package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.InvoiceEntity;
import com.study.projects.newphotoproject.model.param.filterparams.InvoiceFilterParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    List<InvoiceEntity> findAllByTransactionEntityTransactionId(Long transactionId);


    /*@Query("select i from invoices i where " +
            "(:invoiceIds is null or i.invoiceId in (:invoiceIds)) and" +
            "(:userServerIds is null or i.userServerEntity.userServerId in (:userServerIds)) and " +
            "(:transactionIds is null or i.transactionEntity.transactionId in (:transactionIds)) and " +
            "i.createdAt between :from and :to")*/
    @Query(value = """
            select i.* from invoices as i 
            inner join user_servers us on us.user_server_id = i.user_server_id 
            inner join wallet_transactions wt on wt.transaction_id = i.transaction_id where
            ((:invoiceIds) is null or i.invoice_id in (:invoiceIds)) and
            ((:userServerIds) is null or i.user_server_id in (:userServerIds)) and
            ((:transactionIds) is null or i.transaction_id in (:transactionIds)) and
            (cast(cast(:createdFrom as text) as date) is null or i.created_at >= cast(cast(:createdFrom as text) as date)) and (cast(cast(:createdTo as text) as date) is null or i.created_at <= cast(cast(:createdTo as text) as date))
""", nativeQuery = true)
    List<InvoiceEntity> findInvoicesByFilter(
            @Param("invoiceIds") List<Long> invoiceIds,
            @Param("userServerIds") List<Long> userServerIds,
            @Param("transactionIds") List<Long> transactionIds,
            @Param("createdFrom") LocalDate createdFrom,
            @Param("createdTo") LocalDate createdTo);
}
