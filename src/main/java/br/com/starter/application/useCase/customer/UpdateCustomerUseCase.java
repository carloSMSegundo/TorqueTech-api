package br.com.starter.application.useCase.customer;

import br.com.starter.application.api.customer.dtos.UpdateCustomerDTO;
import br.com.starter.domain.address.Address;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.profile.Profile;
import br.com.starter.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerService customerService;
    private final GarageService garageService;

    public Customer handler(User user, UUID customerId, UpdateCustomerDTO request) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "O usuário não possui uma oficina registrada!"
            )
        );

        ModelMapper mapper = new ModelMapper();

        Customer customer = customerService.getByIdAndGarageId(customerId, garage.getId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Cliente não encontrado!"
            ));

        // Atualizando o nome do customer com o nome vindo do request
        customer.setName(request.getName());  // Correção aqui!

        Profile profile = customer.getProfile();

        // Atualizando os dados do profile
        profile.setDocument(request.getDocument());
        profile.setName(request.getName());  // Atualizando o nome do profile também
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());

        if(request.getAddress() != null) {
            Address address = profile.getAddress() != null
                ? profile.getAddress()
                : new Address();

            address.setStreet(request.getAddress().getStreet());
            address.setNumber(request.getAddress().getNumber());
            address.setCep(request.getAddress().getCep());
            address.setCity(request.getAddress().getCity());
            address.setState(request.getAddress().getState());
            address.setProvince(request.getAddress().getProvince());

            profile.setAddress(address);
        }

        // As alterações no profile já estão feitas, não precisa reatribuir o profile ao customer
        // O relacionamento entre customer e profile já deve ser gerenciado automaticamente

        return customerService.save(customer);  // Salvando o customer com as alterações
    }
}

