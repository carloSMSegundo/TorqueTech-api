package br.com.starter.application.useCase.mechanic;

import br.com.starter.application.api.mechanic.dtos.GetMechanicRequest;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.mechanic.MechanicService;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetMechanicByRegistrationDateUseCase {
    private final MechanicService mechanicService;
    private final GarageService garageService;

    public List<Mechanic> handler(User user, GetMechanicRequest request) {
        var garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada"
                )
        );

        return mechanicService.getMechanicsByRegistrationDate(
                garage.getId(),
                request.getQuery(),
                request.isSortByCreatedAt()
        );
    }
}