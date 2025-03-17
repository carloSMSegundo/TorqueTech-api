package br.com.starter.domain.mechanic;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.user.User;
import br.com.starter.domain.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Mechanic> findByGarageId(UUID garageId) {
        return mechanicRepository.findByGarageId(garageId);
    }

    public List<Mechanic> getMechanicsByRegistrationDate(UUID garageId, String query, boolean sortByCreatedAt) {
        Mechanic[] mechanicsArray = mechanicRepository.findAllByGarageId(garageId).toArray(new Mechanic[0]); // convertendo para vetor

        // Busca linear
        if (query != null && !query.isEmpty()) {
            mechanicsArray = searchMechanicsByName(mechanicsArray, query);
        }

        // Insertion Sort
        if (sortByCreatedAt) {
            insertionSortByCreatedAt(mechanicsArray);
        }

        // converte o vetor de volta
        return new ArrayList<>(Arrays.asList(mechanicsArray));
    }

    // Busca linear
    private Mechanic[] searchMechanicsByName(Mechanic[] mechanics, String searchQuery) {
        int count = 0;
        for (Mechanic mechanic : mechanics) {
            if (mechanic.getUser().getProfile().getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                count++;
            }
        }

        Mechanic[] result = new Mechanic[count];
        int index = 0;
        for (Mechanic mechanic : mechanics) {
            if (mechanic.getUser().getProfile().getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                result[index++] = mechanic;
            }
        }

        return result;
    }

    // Insertion sort
    private void insertionSortByCreatedAt(Mechanic[] mechanics) {
        for (int i = 1; i < mechanics.length; i++) {
            Mechanic key = mechanics[i];
            int j = i - 1;

            while (j >= 0 && mechanics[j].getCreatedAt().isAfter(key.getCreatedAt())) {
                mechanics[j + 1] = mechanics[j];
                j--;
            }
            mechanics[j + 1] = key;
        }
    }
}
