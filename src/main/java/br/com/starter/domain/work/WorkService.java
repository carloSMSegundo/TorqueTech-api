package br.com.starter.domain.work;

import br.com.starter.application.api.work.dtos.GetPageWorkRequest;
import br.com.starter.domain.garage.Garage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;

    public Work save(Work work) {
        return workRepository.save(work);
    }

    public Page<Work> getAll(Pageable pageable) {
        return workRepository.findAll(pageable);
    }

    public Optional<Work> getById(UUID id) {
        return workRepository.findById(id);
    }

    public Page<Work> getAllByIds(Set<UUID> ids, Pageable pageable) {
        return workRepository.findAllByIds(ids, pageable);
    }

    public Set<UUID> getPageFilterIds(GetPageWorkRequest request, Garage garage) {
        Set<UUID> mapper = null;

        if (request.getLicensePlate() != null && !request.getLicensePlate().isEmpty()) {
            var ids = workRepository.findByLicensePlates(request.getLicensePlate(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            var ids = workRepository.findByTitleFilter(request.getTitle(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            WorkStatus status = WorkStatus.valueOf(request.getStatus());
            var ids = workRepository.findByStatusFilter(status, garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getExpectedAt() != null) {
            var ids = workRepository.findByExpectedAt(request.getExpectedAt(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getCustomerId() != null) {
            var ids = workRepository.findByCustomerId(request.getCustomerId(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        if (request.getMechanicId() != null) {
            var ids = workRepository.findByMechanicId(request.getMechanicId(), garage.getId());
            mapper = mountMapper(mapper, ids);
        }

        return mapper;
    }


    private static Set<UUID> mountMapper(Set<UUID> mapper, Set<UUID> ids) {
        if (mapper == null)
            mapper = new HashSet<>();

        if (mapper.isEmpty()) mapper.addAll(ids);
        else mapper.retainAll(ids);

        return mapper;
    }

}

