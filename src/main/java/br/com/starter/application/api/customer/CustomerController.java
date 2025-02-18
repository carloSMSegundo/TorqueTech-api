package br.com.starter.application.api.customer;

import br.com.starter.application.api.customer.dtos.CreateCustomerDTO;
import br.com.starter.application.useCase.customer.CreateCustomerUseCase;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/torque/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody CreateCustomerDTO createCustomerDTO) {
        // Delegando a criação do cliente para o UseCase
        return createCustomerUseCase.handler(createCustomerDTO)
                .map(customer -> ResponseEntity.status(HttpStatus.CREATED).body(customer))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        var customer = customerService.findById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Customer>> listCustomers(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) UserStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Customer> customers = customerService.listCustomers(query, status, page, size);
        return ResponseEntity.ok(customers);
    }
}
