package br.com.starter.domain.mechanic;

import lombok.RequiredArgsConstructor;
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
}
