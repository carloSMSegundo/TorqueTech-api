package br.com.starter.application.useCase.customer;

import br.com.starter.application.api.customer.dtos.UpdateCustomerDTO;
import br.com.starter.domain.address.Address;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.profile.Profile;
import br.com.starter.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class UpdateCustomerUseCase {

    private final CustomerService customerService;
    private final GarageService garageService;

    public UpdateCustomerUseCase(CustomerService customerService, GarageService garageService) {
        this.customerService = customerService;
        this.garageService = garageService;
    }

    public Customer handler(User owner, UUID customerId, UpdateCustomerDTO request) {
        Garage garage = garageService.getByUser(owner).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada!"
                )
        );

        ModelMapper mapper = new ModelMapper();

        Customer customer = customerService.getByIdAndGarageId(customerId, garage.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Cliente não encontrado!"
                ));

        Profile profile = customer.getProfile();

        mapper.map(request, profile);

        if (request.getAddress() != null) {
            Address address = profile.getAddress();

            if (address == null) {
                address = new Address();
                profile.setAddress(address);
            }

            mapper.map(request.getAddress(), address);
        }



        return customerService.save(customer);
    }
}
