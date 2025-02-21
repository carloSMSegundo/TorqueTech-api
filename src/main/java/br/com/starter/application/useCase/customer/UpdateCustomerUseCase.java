package br.com.starter.application.useCase.customer;

import br.com.starter.application.api.customer.dtos.UpdateCustomerDTO;
import br.com.starter.domain.address.Address;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.profile.Profile;
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

    @Transactional
    public Customer handler(UUID customerId, UpdateCustomerDTO request) {

        ModelMapper mapper = new ModelMapper();

        Customer customer = customerService.getById(customerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado com o ID: " + customerId
                ));

        // Atualizando o nome do customer com o nome vindo do request
        customer.setName(request.getName());  // Correção aqui!

        Profile profile = customer.getProfile();

        // Atualizando os dados do profile
        profile.setDocument(request.getDocument());
        profile.setName(request.getName());  // Atualizando o nome do profile também
        profile.setPhone(request.getPhone());
        profile.setBirthDate(request.getBirthDate());

        // Se o endereço foi fornecido, atualiza o endereço do profile
        if (request.getAddress() != null) {
            var address = mapper.map(request.getAddress(), Address.class);
            profile.setAddress(address);
        }

        // As alterações no profile já estão feitas, não precisa reatribuir o profile ao customer
        // O relacionamento entre customer e profile já deve ser gerenciado automaticamente

        return customerService.save(customer);  // Salvando o customer com as alterações
    }
}

