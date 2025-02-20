package br.com.starter.application.useCase.manager;

import br.com.starter.application.api.manager.dtos.UpdateManagerDTO;
import br.com.starter.application.api.user.dto.UpdateUserDTO;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.manager.Manager;
import br.com.starter.domain.manager.ManagerService;
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
public class UpdateManagerUseCase {
    private final UserService userService;
    private final ManagerService managerService;
    private final GarageService garageService;

    @Transactional
    public Optional<Manager> handler(UUID managerId, UpdateManagerDTO request, User user) {
        ModelMapper mapper = new ModelMapper();

        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada"
                )
        );

        var manager = managerService.getById(managerId).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Gerente não encontrado!"
                )
        );

        if (!manager.getGarage().equals(garage)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Você não tem permissão para atualizar este gerente."
            );
        }

        var updateRequest = mapper.map(request, UpdateUserDTO.class);
        updateRequest.setEmail(request.getUsername());

        var updatedUser = userService.update(manager.getUser().getId(), updateRequest);
        manager.setUser(updatedUser);

        return Optional.of(managerService.save(manager));
    }
}
