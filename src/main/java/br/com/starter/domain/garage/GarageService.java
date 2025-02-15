package br.com.starter.domain.garage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GarageService {
    private final GarageRepository garageRepository;

    public Garage save(Garage garage) {
        return garageRepository.save(garage);
    }

    public List<Garage> getAll() {
        return garageRepository.findAll();
    }

    public Optional<Garage> getById(UUID id) {
        return garageRepository.findById(id);
    }
}
