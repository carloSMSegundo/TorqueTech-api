package br.com.starter.domain.work;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public List<Work> getAll() {
        return workRepository.findAll();
    }

    public Optional<Work> getById(UUID id) {
        return workRepository.findById(id);
    }
}
