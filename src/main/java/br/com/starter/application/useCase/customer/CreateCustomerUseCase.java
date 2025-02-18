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

        // Criando o cliente
        Customer customer = new Customer();
        customer.setStatus(request.getStatus());
        customer.setName(request.getName());  // Definindo o nome do cliente

        // Criando o perfil do cliente e associando dados
        Profile profile = new Profile();
        profile.setDocument(request.getDocument());
        profile.setName(request.getName());  // Nome do cliente
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());

        // Se o endereço for fornecido, associamos ao perfil
        if (request.getAddress() != null) {
            profile.setAddress(request.getAddress());
        }

        // Associando o perfil ao cliente
        customer.setProfile(profile);

        // Criando a garagem e associando ao cliente
        Garage garage = new Garage();
        customer.setGarage(garage);

        // Buscando o usuário (owner) que está criando o cliente
        User owner = userService.getUserById(request.getOwnerId());

        // Associando o owner ao cliente
        customer.setOwner(owner);

        customer.setCreatedAt(request.getCreatedAt());

        // Verificando e associando veículos ao cliente
        if (request.getVehicleIds() != null && !request.getVehicleIds().isEmpty()) {
            List<Vehicle> vehicles = request.getVehicleIds().stream()
                    .map(vehicleId -> vehicleService.getById(vehicleId)
                            .orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + vehicleId)))
                    .collect(Collectors.toList());
            customer.setVehicles(vehicles);
        } else {
            customer.setVehicles(List.of());  // Se não houver veículos, definimos como lista vazia
        }

        // Salvando o cliente
        return Optional.of(customerService.save(customer));
    }
}
