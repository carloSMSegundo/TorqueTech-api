package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.GetPageCustomerRequest;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.work.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPageCustomerWorkUseCase {

    private final GarageService garageService;
    private final CustomerService customerService;
    private final WorkService workService;

    public Page<Work> handler(
            User user,
            UUID customerId,
            Integer page,
            GetPageCustomerRequest request
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada!"
                )
        );

        Customer customer = customerService.getById(customerId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado!"
                )
        );

        Set<UUID> ids = workService.getPageFilterIdsByCustomer(request, garage, customer);

        PageRequest pageRequest = PageRequest.of(page, request.getSize());

        return (ids != null && !ids.isEmpty())
                ? workService.getAllByIds(ids, pageRequest)
                : workService.getAllByCustomer(customer, pageRequest);
    }
}
