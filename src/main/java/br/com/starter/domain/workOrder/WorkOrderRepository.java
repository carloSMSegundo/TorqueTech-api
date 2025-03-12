package br.com.starter.domain.workOrder;

import br.com.starter.domain.work.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query("""
    SELECT wo FROM WorkOrder wo
    WHERE wo.work = :work
    """)
    List<WorkOrder> findByWork(
            @Param("work") Work work
    );

    @Query("""
    SELECT wo FROM WorkOrder wo
    WHERE wo.work = :work
    """)
    Page<WorkOrder> findByWorkPage(
            @Param("work") Work work,
            Pageable pageable
    );


}
