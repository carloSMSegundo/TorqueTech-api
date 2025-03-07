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

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            var ids = workRepository.findByTitleFilter(request.getTitle(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            WorkStatus status = WorkStatus.valueOf(request.getStatus());
            var ids = workRepository.findByStatusFilter(status, garage.getId());
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

    public Set<UUID> getPageFilterIdsByCustomer(GetPageCustomerRequest request, Garage garage, Customer customer) {
        Set<UUID> mapper = null;

        if (request.getLicensePlate() != null && !request.getLicensePlate().isEmpty()) {
            var ids = workRepository.findCustomerByLicensePlates(request.getLicensePlate(), garage.getId(), customer.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            var ids = workRepository.findCustomerByTitleFilter(request.getTitle(), garage.getId(), customer.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            WorkStatus status = WorkStatus.valueOf(request.getStatus());
            var ids = workRepository.findCustomerByStatusFilter(status, garage.getId(), customer.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getExpectedAt() != null) {
            var ids = workRepository.findCustomerByExpectedAt(request.getExpectedAt(), garage.getId(), customer.getId());
            mapper = mountMapper(mapper, ids);
        }

        return mapper;
    }

    public Set<UUID> getPageFilterIdsByMechanic(GetPageMechanicRequest request, Garage garage, Mechanic mechanic) {
        Set<UUID> mapper = null;

        if (request.getLicensePlate() != null && !request.getLicensePlate().isEmpty()) {
            var ids = workRepository.findMechanicByLicensePlates(request.getLicensePlate(), garage.getId(), mechanic.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            var ids = workRepository.findMechanicByTitleFilter(request.getTitle(), garage.getId(), mechanic.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            WorkStatus status = WorkStatus.valueOf(request.getStatus());
            var ids = workRepository.findMechanicByStatusFilter(status, garage.getId(), mechanic.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getExpectedAt() != null) {
            var ids = workRepository.findMechanicByExpectedAt(request.getExpectedAt(), garage.getId(), mechanic.getId());
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

    // TODO talvez outra lógica, mas por enquanto essa
    public boolean existsSimilarWork(CreateWorkRequestDTO request, Garage garage) {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
        return workRepository.existsByTitleAndGarageAndCreatedAtAfter(request.getTitle(), garage, oneMinuteAgo);
    }

    // TODO fazer mais testes para cobrir todos os casos possíveis
    public LocalDateTime calculateWorkExpectedAt(Set<WorkOrder> workOrders, Work work) {
        if (workOrders == null || workOrders.isEmpty()) {
            throw new IllegalStateException("A Work precisa ter pelo menos uma WorkOrder.");
        }
        if (work.getStartAt() == null) {
            throw new IllegalStateException("A Work precisa ter um startAt definido.");
        }

        // Ordena as workOrders pelo startAt para calcular de forma sequencial
        List<WorkOrder> sortedOrders = workOrders.stream()
                .sorted(Comparator.comparing(WorkOrder::getStartAt))
                .collect(Collectors.toList());

        LocalDateTime current = work.getStartAt();

        for (WorkOrder order : sortedOrders) {
            // Valida as datas da ordem
            validateWorkOrderDates(order, work);

            // Se houver um gap (a ordem começa depois do horário atual acumulado), avança para o startAt da ordem
            if (order.getStartAt().isAfter(current)) {
                current = order.getStartAt();
            }

            // Calcula a duração da workOrder
            Duration orderDuration = Duration.between(order.getStartAt(), order.getExpectedAt());

            // Adiciona essa duração, respeitando os horários de funcionamento
            current = addDurationWithinWorkingHours(current, orderDuration);
        }

        work.setExpectedAt(current);
        return current;
    }

    private LocalDateTime addDurationWithinWorkingHours(LocalDateTime start, Duration duration) {
        LocalTime workStartTime = LocalTime.of(8, 0);
        LocalTime workEndTime = LocalTime.of(18, 0);
        LocalDateTime current = start;
        Duration remaining = duration;

        while (!remaining.isZero() && !remaining.isNegative()) {
            // Se o horário atual estiver antes do expediente, ajusta para o início do dia útil
            if (current.toLocalTime().isBefore(workStartTime)) {
                current = current.withHour(workStartTime.getHour())
                        .withMinute(workStartTime.getMinute())
                        .withSecond(0)
                        .withNano(0);
            }

            // Se o horário atual já passou do expediente, avança para o próximo dia útil
            if (current.toLocalTime().isAfter(workEndTime) || current.toLocalTime().equals(workEndTime)) {
                current = current.plusDays(1)
                        .withHour(workStartTime.getHour())
                        .withMinute(workStartTime.getMinute())
                        .withSecond(0)
                        .withNano(0);
                continue;
            }

            // Calcula quanto tempo resta no dia de trabalho atual
            Duration timeLeftToday = Duration.between(current.toLocalTime(), workEndTime);

            if (remaining.compareTo(timeLeftToday) <= 0) {
                // Se o tempo restante couber no dia atual, basta somá-lo
                current = current.plus(remaining);
                remaining = Duration.ZERO;
            } else {
                // Se ultrapassar, soma o tempo restante do dia e diminui da duração
                current = current.plus(timeLeftToday);
                remaining = remaining.minus(timeLeftToday);
                // Inicia no próximo dia útil
                current = current.plusDays(1)
                        .withHour(workStartTime.getHour())
                        .withMinute(workStartTime.getMinute())
                        .withSecond(0)
                        .withNano(0);
            }
        }
        return current;
    }

    private void validateWorkOrderDates(WorkOrder workOrder, Work work) {
        if (workOrder.getStartAt() == null || workOrder.getExpectedAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StartAt ou ExpectedAt da WorkOrder são nulos.");
        }

        if (workOrder.getStartAt().isAfter(workOrder.getExpectedAt())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O StartAt de WorkOrder não pode ser após o ExpectedAt.");
        }

        if (workOrder.getStartAt().isBefore(work.getStartAt())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O StartAt da WorkOrder não pode ser antes do início da Work.");
        }
        for (WorkOrder existingOrder : work.getOrders()) {
            if (workOrderOverlap(workOrder, existingOrder)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "O horário da WorkOrder se sobrepõe a outra já existente.");
            }
        }
    }

    public void updateTotalCost(Work work) {
        Long totalCost = work.getOrders().stream()
                .mapToLong(WorkOrder::getCost)
                .sum();

        work.setTotalCost(totalCost);
    }

    private boolean workOrderOverlap(WorkOrder newOrder, WorkOrder existingOrder) {
        return !newOrder.equals(existingOrder) &&
                !newOrder.getExpectedAt().isBefore(existingOrder.getStartAt()) &&
                !newOrder.getStartAt().isAfter(existingOrder.getExpectedAt());
    }
}