package br.com.starter.application.useCase.mechanic;

import br.com.starter.application.api.mechanic.dtos.UpdateMechanicDTO;
import br.com.starter.application.api.user.dto.UpdateUserDTO;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.mechanic.Mechanic;
import br.com.starter.domain.mechanic.MechanicService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateMechanicUseCase {
    private final UserService userService;
    private final MechanicService mechanicService;
    private final GarageService garageService;

    @Transactional
    public Optional<Mechanic> handler(UUID mechanicId, UpdateMechanicDTO request, User user) {
        ModelMapper mapper = new ModelMapper();

        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada"
                )
        );

        var mechanic = mechanicService.getById(mechanicId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Mecânico não encontrado!"
                )
        );

        if (!mechanic.getGarage().equals(garage)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para atualizar este mecânico."
            );
        }

        var updateRequest = mapper.map(request, UpdateUserDTO.class);
        updateRequest.setEmail(request.getUsername());

        var updatedUser = userService.update(mechanic.getUser().getId(), updateRequest);
        mechanic.setUser(updatedUser);

        return Optional.of(mechanicService.save(mechanic));
    }
}
