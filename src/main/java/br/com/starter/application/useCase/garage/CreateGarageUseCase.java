package br.com.starter.application.useCase.garage;

import br.com.starter.application.api.garage.dtos.CreateGarageDTO;
import br.com.starter.application.api.user.dto.UserRegistrationRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateGarageUseCase {
    private final GarageService garageService;
    private final UserService userService;

    @Transactional
    public Optional<Garage> handler(CreateGarageDTO request) {
        var garage = new Garage();
        garage.setName(request.getName());
        garage.setCnpj(request.getCnpj());

        var createUserRequest = new UserRegistrationRequest();
        createUserRequest.setName(request.getName());
        createUserRequest.setUsername(request.getUsername());
        createUserRequest.setPassword(request.getPassword());
        createUserRequest.setDocument(request.getDocument());
        createUserRequest.setPhone(request.getPhone());
        createUserRequest.setBirthDate(request.getBirthDate());
        createUserRequest.setRole(request.getRole());

        var user = userService.create(createUserRequest);

        garage.setOwner(user);

        return Optional.of(garageService.save(garage));
    };
}
