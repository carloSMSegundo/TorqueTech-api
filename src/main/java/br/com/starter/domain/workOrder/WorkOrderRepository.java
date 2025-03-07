package br.com.starter.domain.workOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
    Optional<WorkOrder> findByStatus(WorkOrderStatus status);

    @Query("""
    SELECT wo FROM WorkOrder wo
    WHERE wo.id = :workOrderId
    AND wo.work.id = :workId
""")
    Optional<WorkOrder> findByIdAndWorkId(
            @Param("workOrderId") UUID workOrderId,
            @Param("workId") UUID workId
    );

}
