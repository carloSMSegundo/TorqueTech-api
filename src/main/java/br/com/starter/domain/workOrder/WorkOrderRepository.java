package br.com.starter.domain.workOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, UUID> {
    Optional<WorkOrder> findByStatus(WorkOrderStatus status);
}
