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

    // Método atualizado para listagem paginada com filtros
    public Page<Customer> listCustomers(String query, UserStatus status, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return customerRepository.findPageByStatusAndProfileName(query, status, pageable);
    }
}
