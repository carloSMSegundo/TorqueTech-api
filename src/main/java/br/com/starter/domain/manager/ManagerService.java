package br.com.starter.domain.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAll() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getById(UUID id) {
        return managerRepository.findById(id);
    }
}
