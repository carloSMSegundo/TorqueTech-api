package br.com.starter.domain.mechanic;

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
public class MechanicService {
    private final MechanicRepository mechanicRepository;

    public Mechanic save(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAll() {
        return mechanicRepository.findAll();
    }

    public Optional<Mechanic> getById(UUID id) {
        return mechanicRepository.findById(id);
    }

    public Page<Mechanic> findAllPage(Pageable pageable) {
        return mechanicRepository.findAll(pageable);
    }

    public Page<Mechanic> getPageByStatusAndName(
            Garage garage,
            String query,
            UserStatus userStatus,
            Pageable pageable
    ) {
        return mechanicRepository.findPageByStatusAndNames(
                garage.getId(),
                query,
                userStatus,
                pageable
        );
    }

    public Optional<Mechanic> getByUser(User user) {
        return mechanicRepository.findByUser(user);
    }

    public Optional<Mechanic> getByIdAndGarageId(UUID mechanicId, UUID garageId) {
        return mechanicRepository.findByIdAndGarageId(mechanicId, garageId);
    }
}
