package br.com.starter.application.useCase.customer;

import br.com.starter.application.api.customer.dtos.CreateCustomerDTO;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.profile.Profile;
import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserService;  // Serviço para buscar o owner
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerService customerService;
    private final VehicleService vehicleService;
    private final UserService userService;

    @Transactional
    public Optional<Customer> handler(CreateCustomerDTO request) {

        Customer customer = new Customer();
        customer.setStatus(request.getStatus());
        customer.setName(request.getName());  // Definindo o nome do cliente

        Profile profile = new Profile();
        profile.setDocument(request.getDocument());
        profile.setName(request.getName());  // Nome do cliente
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());

        if (request.getAddress() != null) {
            profile.setAddress(request.getAddress());
        }

        customer.setProfile(profile);

        Garage garage = new Garage();
        customer.setGarage(garage);

        User owner = userService.getUserById(request.getOwnerId());

        customer.setOwner(owner);

        customer.setCreatedAt(request.getCreatedAt());

        if (request.getVehicleIds() != null && !request.getVehicleIds().isEmpty()) {
            List<Vehicle> vehicles = request.getVehicleIds().stream()
                    .map(vehicleId -> vehicleService.getById(vehicleId)
                            .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + vehicleId)))
                    .collect(Collectors.toList());
            customer.setVehicles(vehicles);
        } else {
            customer.setVehicles(List.of());
        }

        // Salvando o cliente
        return Optional.of(customerService.save(customer));
    }
}
