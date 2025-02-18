package br.com.starter.application.useCase.garage;

import br.com.starter.application.api.garage.dtos.CreateGarageDTO;
import br.com.starter.application.api.user.dto.UserRegistrationRequest;
import br.com.starter.domain.address.Address;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreateGarageUseCase {
    private final GarageService garageService;
    private final UserService userService;

    @Transactional
    public Optional<Garage> handler(CreateGarageDTO request) {

        ModelMapper mapper = new ModelMapper();

        var garage = new Garage();
        garage.setName(request.getName());
        garage.setCnpj(request.getCnpj());

        if (request.getAddress() != null) {
            // Clona informações de endereço
            var address = mapper.map(request.getAddress(), Address.class);
            garage.setAddress(address);
        }

        // Clona informações de criação de usuário
        var createUserRequest = mapper.map(request, UserRegistrationRequest.class);
        createUserRequest.setName(request.getOwnerName()); // seta o nome certo pois a clonagem vai pegar o getName que é o nome da oficina

        var user = userService.create(createUserRequest);
        garage.setOwner(user);

        return Optional.of(garageService.save(garage));
    };
}
