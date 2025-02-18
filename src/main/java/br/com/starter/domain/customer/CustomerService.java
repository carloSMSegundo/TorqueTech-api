package br.com.starter.domain.customer;

import br.com.starter.domain.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> listCustomers(String query, UserStatus status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        // Busca de clientes com filtros de status e nome do perfil
        return customerRepository.findPageByStatusAndProfileName(query, status, pageable);
    }

    public Optional<Customer> findByIdWithVehicles(UUID id) {
        Optional<Customer> customer = customerRepository.findById(id);
        customer.ifPresent(c -> {
            // Aqui, caso seja necessário, você pode carregar os veículos do cliente de forma explícita
            // Dependendo da configuração do relacionamento (fetch = FetchType.LAZY ou FetchType.EAGER)
        });
        return customer;
    }

    public Customer saveWithVehicles(Customer customer) {
        // Se o cliente já existir, atualizamos os veículos
        if (customer.getId() != null) {
            Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
            if (existingCustomer.isPresent()) {
                Customer updatedCustomer = existingCustomer.get();
                updatedCustomer.setVehicles(customer.getVehicles()); // Atualizando veículos
                return customerRepository.save(updatedCustomer);
            }
        }
        return customerRepository.save(customer); // Caso contrário, salva um novo cliente
    }
}
