package br.com.starter.domain.item;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.local.Local;
import br.com.starter.domain.local.LocalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> getById(UUID id) {
        return itemRepository.findById(id);
    }

    public List<Item> findAllByGarageAndQuery(Garage garage, String query) {
        return itemRepository.findAllByGarageAndQuery(
            garage.getId(),
            ItemStatus.ACTIVE,
            query
        );
    }
}
