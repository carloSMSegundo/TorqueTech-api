package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.GetPageCustomerRequest;
import br.com.starter.application.api.work.dtos.GetPageMechanicRequest;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.mechanic.MechanicService;
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
public class GetPageMechanicWorkUseCase {

    private final GarageService garageService;
    private final WorkService workService;
    private final MechanicService mechanicService;

    public Page<Work> handler(
            User user,
            UUID mechanicId,
            Integer page,
            GetPageMechanicRequest request
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada!"
                )
        );

        Mechanic mechanic = mechanicService.getById(mechanicId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado!"
                )
        );

        Set<UUID> ids = workService.getPageFilterIdsByMechanic(request, garage, mechanic);

        PageRequest pageRequest = PageRequest.of(page, request.getSize());

        return (ids != null && !ids.isEmpty())
                ? workService.getAllByIds(ids, pageRequest)
                : workService.getAllByMechanic(mechanic, pageRequest);
    }
}
