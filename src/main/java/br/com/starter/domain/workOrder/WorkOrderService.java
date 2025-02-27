package br.com.starter.domain.workOrder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkOrderService {
    private final WorkOrderRepository workOrderRepository;

    public WorkOrder save(WorkOrder workOrder) {
        return workOrderRepository.save(workOrder);
    }

    public List<WorkOrder> getAll() {
        return workOrderRepository.findAll();
    }

    public Optional<WorkOrder> getById(UUID id) {
        return workOrderRepository.findById(id);
    }

    public Optional<WorkOrder> getByStatus(WorkOrderStatus status) {
        return workOrderRepository.findByStatus(status);
    }

    // relacionar com
}
