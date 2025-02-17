package br.com.starter.domain.customer;

import br.com.starter.domain.user.User;
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

    // Metodo para listagem paginada com filtros
    public Page<Customer> listCustomers(User owner, String profileName, UserStatus status, int page, int size) {
        // Configura a paginação
        PageRequest pageable = PageRequest.of(page, size);

        // Verifica qual filtro foi passado e chama o metodo adequado no repositório
        if (profileName != null && status != null) {
            return customerRepository.findByProfile_Name(profileName, pageable);
        } else if (status != null) {
            return customerRepository.findByStatus(status, pageable);
        } else if (profileName != null) {
            return customerRepository.findByProfile_Name(profileName, pageable);
        } else {
            return customerRepository.findByOwner(owner, pageable);
        }
    }
}
