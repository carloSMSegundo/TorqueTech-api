package br.com.starter.domain.customer;

import br.com.starter.application.api.customer.dtos.GetPageCustomerRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.manager.Manager;
import br.com.starter.domain.user.UserStatus;
import br.com.starter.domain.vehicle.Vehicle;
import br.com.starter.domain.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public Customer save(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Page<Customer> getPage(
        Garage garage,
        String query,
        UserStatus status,
        Pageable pageable
    ) {
        return customerRepository.findPageByStatusAndProfileName(garage.getId(), query, status, pageable);
    }

    public List<Customer> listCustomers(Garage garage) {
        return customerRepository.findAllByGarageIdAndStatus(garage.getId(),  UserStatus.ACTIVE);
    }

    public Optional<Customer> getById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getByIdAndGarageId(UUID id, UUID garageId) {
        return customerRepository.findAllByGarageId(garageId, id);
    }

    public Customer saveWithVehicles(Customer customer) {
        if (customer.getId() != null) {
            Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
            if (existingCustomer.isPresent()) {
                Customer updatedCustomer = existingCustomer.get();
                updatedCustomer.setVehicles(customer.getVehicles());
                return customerRepository.save(updatedCustomer);
            }
        }
        return customerRepository.save(customer);
    }
}
