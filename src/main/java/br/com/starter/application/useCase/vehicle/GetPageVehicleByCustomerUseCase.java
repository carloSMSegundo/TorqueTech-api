package br.com.starter.application.useCase.vehicle;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.vehicle.VehicleService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetPageVehicleByCustomerUseCase {

    private final VehicleService vehicleService;
    private final GarageService garageService;
    private final CustomerService customerService;

    public Optional<?> handler(UUID customerId, Integer page, GetPageRequest request, User user) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada"
                )
        );

        var customer = customerService.getById(customerId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Cliente não encontrado"
                )
        );

        if (!customer.getGarage().equals(garage)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para acessar os veículos deste cliente na garagem."
            );
        }

        return Optional.of(
                vehicleService.findByCustomerAndGarage(
                        customerId,
                        garage.getId(),
                        request.getQuery(),
                        PageRequest.of(page, request.getSize())
                )
        );
    }
}
