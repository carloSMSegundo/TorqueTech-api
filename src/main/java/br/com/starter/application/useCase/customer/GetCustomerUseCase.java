package br.com.starter.application.useCase.customer;

import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetCustomerUseCase {

    private final CustomerService customerService;
    private final GarageService garageService;

    public Optional<?> handler(User user, UUID customerId) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        Customer customer = customerService.getByIdAndGarageId(customerId, garage.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cliente não encontrado!"
            ));

        return Optional.of(customer);
    }
}

