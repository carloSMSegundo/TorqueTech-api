package br.com.starter.domain.item;

import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.local.Local;
import br.com.starter.domain.local.LocalStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Item> getAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getById(UUID id, Garage garage) {
        return itemRepository.findByIdAndGarage(id, garage.getId());
    }

    public List<Item> findAllByGarageAndQuery(Garage garage) {
        return itemRepository.findAllByGarageAndQuery(
            garage.getId(),
            ItemStatus.ACTIVE
        );
    }

    public Page<Item> findPageAllByGarageAndQuery(Garage garage, String query, Pageable pageable) {
        return itemRepository.findPageAllByGarageAndQuery(
            garage.getId(),
            ItemStatus.ACTIVE,
            query,
            pageable
        );
    }
}
