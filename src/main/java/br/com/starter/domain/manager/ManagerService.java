package br.com.starter.domain.manager;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Manager> findAllPage(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

    public Page<Manager> getPageByStatusAndName(
            Garage garage,
            String query,
            UserStatus userStatus,
            Pageable pageable
    ) {
        return managerRepository.findPageByStatusAndNames(
                garage.getId(),
                query,
                userStatus,
                pageable
        );
    }

    public Optional<Manager> getByUser(User user) {
        return  managerRepository.findByUser(user);
    }

    public Optional<Manager> getByIdAndGarageId(UUID managerId, UUID garageId) {
        return managerRepository.findByIdAndGarageId(managerId, garageId);
    }

}
