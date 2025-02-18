package br.com.starter.application.useCase.manager;

import br.com.starter.application.api.manager.dtos.CreateManagerDTO;
import br.com.starter.application.api.user.dto.UserRegistrationRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.manager.Manager;
import br.com.starter.domain.manager.ManagerService;
import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateManagerUseCase {
    private final UserService userService;
    private final ManagerService managerService;
    private final GarageService garageService;

    @Transactional
    public Optional<Manager> handler(CreateManagerDTO request, User user){
        ModelMapper mapper = new ModelMapper();

        Garage garage = garageService.getByUser(user);

        var manager = new Manager();

        manager.setGarage(garage);

        var createUserRequest = mapper.map(request, UserRegistrationRequest.class);
        createUserRequest.setName(request.getOwnerName());

        var newUser = userService.create(createUserRequest);
        manager.setUser(newUser);

        return Optional.of(managerService.save(manager));
    }
}
