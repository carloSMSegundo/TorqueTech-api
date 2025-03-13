package br.com.starter.domain.work;

import br.com.starter.application.api.work.dtos.CreateWorkRequestDTO;
import br.com.starter.application.api.work.dtos.GetPageCustomerRequest;
import br.com.starter.application.api.work.dtos.GetPageMechanicRequest;
import br.com.starter.application.api.work.dtos.GetPageWorkRequest;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.workOrder.WorkOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public Page<Work> getAll(Pageable pageable) {
        return workRepository.findAll(pageable);
    }

    public Optional<Work> getById(UUID id) {
        return workRepository.findById(id);
    }

    public Page<Work> getAllByIds(Set<UUID> ids, Pageable pageable) {
        return workRepository.findAllByIds(ids, pageable);
    }

    public Page<Work> getAllByCustomer(Customer customer, Pageable pageable) {
        return workRepository.getAllByCustomer(customer.getId(), pageable);
    }

    public Page<Work> getAllByMechanic(Mechanic mechanic, Pageable pageable) {
        return workRepository.getAllByMechanic(mechanic.getId(), pageable);
    }

    public Set<UUID> getPageFilterIds(GetPageWorkRequest request, Garage garage) {
        Set<UUID> mapper = null;

        if (request.getLicensePlate() != null && !request.getLicensePlate().isEmpty()) {
            var ids = workRepository.findByLicensePlates(request.getLicensePlate(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            var ids = workRepository.findByQueryFilter(request.getQuery(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getStatus() != null) {
            var ids = workRepository.findByStatusFilter(request.getStatus(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getExpectedAt() != null) {
            var ids = workRepository.findByExpectedAt(request.getExpectedAt(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getCustomerId() != null) {
            var ids = workRepository.findByCustomerId(request.getCustomerId(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getMechanicId() != null) {
            var ids = workRepository.findByMechanicId(request.getMechanicId(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        return mapper;
    }

    private static Set<UUID> mountMapper(Set<UUID> mapper, Set<UUID> ids) {
        if (mapper == null)
            mapper = new HashSet<>();

        if (mapper.isEmpty()) mapper.addAll(ids);
        else mapper.retainAll(ids);

        return mapper;
    }

    public Optional<Work> getByIdAndGarageId(UUID workId, UUID garageId) {
        return workRepository.findByIdAndGarageId(workId, garageId);
    }
}