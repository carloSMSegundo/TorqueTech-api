package br.com.starter.application.useCase.work;

import br.com.starter.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.starter.application.api.work.dtos.CreateWorkRequestDTO;
import br.com.starter.application.useCase.stockTransaction.OutputStockTransactionUseCase;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.mechanic.MechanicService;
import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.stockTransaction.TransactionCategory;
import br.com.starter.domain.user.User;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleService;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.workOrder.WorkOrder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateWorkRequestUseCase {

    private final WorkService workService;
    private final VehicleService vehicleService;
    private final CustomerService customerService;
    private final MechanicService mechanicService;
    private final GarageService garageService;
    private final OutputStockTransactionUseCase outputStockTransactionUseCase;

    @Transactional
    public Optional<Work> handler(CreateWorkRequestDTO request, User owner) {
        // Verifica se mecânico, veículo, cliente e garagem existem
        Mechanic mechanic = mechanicService.getById(request.getMechanicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mecânico não encontrado!"));

        Vehicle vehicle = vehicleService.getById(request.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado!"));

        Customer customer = customerService.getById(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));

        Garage garage = garageService.getByUser(owner)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário não possui uma oficina registrada"));

        // 🔹 1. Criar e salvar o Work primeiro (SEM WorkOrders ainda)
        Work work = new Work();
        work.setTitle(request.getTitle());
        work.setDescription(request.getDescription());
        work.setStartAt(request.getStartAt());
        work.setExpectedAt(request.getExpectedAt());
        work.setPrice(request.getPrice());
        work.setTotalCost(request.getTotalCost());
        work.setMechanic(mechanic);
        work.setOwner(owner);
        work.setVehicle(vehicle);
        work.setCustomer(customer);
        work.setGarage(garage);

        work = workService.save(work); // 🔹 Agora o Work já tem um ID no banco

        // 🔹 2. Criar e salvar WorkOrders ANTES de associá-las ao Work
        Set<WorkOrder> workOrders = new HashSet<>();
        if (request.getWorkOrders() != null) {
            for (var workOrderRequest : request.getWorkOrders()) {
                WorkOrder workOrder = new WorkOrder();
                workOrder.setTitle(workOrderRequest.getTitle());
                workOrder.setDescription(workOrderRequest.getDescription());
                workOrder.setStartAt(workOrderRequest.getStartAt());
                workOrder.setExpectedAt(workOrderRequest.getExpectedAt());
                workOrder.setCost(workOrderRequest.getCost());
                workOrder.setNote(workOrderRequest.getNote());

                workOrder.setWork(work); // 🔹 Agora associamos ao Work já salvo
                workOrders.add(workOrder);
            }
        }

        work.setOrders(workOrders);

        // 🔹 3. Agora salvamos novamente o Work já com as WorkOrders persistidas
        return Optional.of(workService.save(work));
    }

}

