package com.study.projects.newphotoproject.repository;

import com.study.projects.newphotoproject.model.domain.database.PaymentMethodEntity;
import com.study.projects.newphotoproject.model.enums.PaymentMethodStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {

    Optional<PaymentMethodEntity> findByPaymentMethodName(String name);
    Optional<PaymentMethodEntity> findByPaymentMethodId(Long id);


/*    @Query("""
            select p from payment_methods p where
            (:paymentMethodIds is null or p.paymentMethodId in (:paymentMethodIds)) and 
            (:paymentMethodName is null or upper(p.paymentMethodName) like %:paymetnMethodName% ) and
            (:paymentRates is null or p.paymentRate in (:paymentRates)) and
            (:paymentMethodStatuses is null or p.paymentMethodStatus in (:paymentMethodStatuses)) and
            p.createdAt between :createdFrom and :createdTo and
            p.updatedAt between :updatedFrom and :updatedTo""")*/
    @Query( value = """
            select p.* from payment_methods as p where
            ((:paymentMethodIds is null) or p.payment_method_id in (:paymentMethodIds)) and 
            (cast(:paymentMethodName as text) is null or upper(p.payment_method_name) like cast(cast(:paymentMethodName as text) as varchar )) and
            ((:paymentRates is null) or p.payment_rate in (:paymentRates)) and
            ((:paymentMethodStatuses) is null or p.payment_method_status in ( cast((:paymentMethodStatuses) as text))) and
            (cast(cast(:createdFrom as text) as date) is null or p.created_at >= cast(cast(:createdFrom as text) as date)) and (cast(cast(:createdTo as text) as date) is null or p.created_at <= cast(cast(:createdTo as text) as date)) and
              (cast(cast(:updatedFrom as text) as date) is null or p.updated_at >= cast(cast(:updatedFrom as text) as date)) and (cast(cast(:updatedTo as text) as date) is null or p.updated_at <= cast(cast(:updatedTo as text) as date))
""", nativeQuery = true)
    List<PaymentMethodEntity> findFilteredPaymentMethods(
            @Param("paymentMethodIds") List<Long> paymentMethodIds,
            @Param("paymentMethodName") String paymentMethodName,
            @Param("paymentRates") List<Double> paymentRates,
            @Param("paymentMethodStatuses") List<String> paymentMethodStatuses,
            @Param("createdFrom") LocalDate createdFrom,
            @Param("createdTo") LocalDate createdTo,
            @Param("updatedFrom") LocalDate updatedFrom,
            @Param("updatedTo") LocalDate updatedTo
    );
}
