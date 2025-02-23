package br.com.starter.domain.stockItem;

import br.com.starter.domain.garage.Garage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockItemService {
    private final StockItemRepository stockItemRepository;

    public StockItem save(StockItem stockItem) {
        return stockItemRepository.save(stockItem);
    }

    public List<StockItem> findAll() {
        return stockItemRepository.findAllWithItems();
    }

    public Optional<StockItem> findByItemAndPrice(UUID itemId, UUID garageId, UUID localId , Long price) {
        return stockItemRepository.findByItemAndAcquisitionPriceAndStatus(itemId, garageId, localId, price);
    }

    public Optional<StockItem> findById(UUID id, Garage garage) {
        return stockItemRepository.findByIdAndGarageId(id, garage.getId());
    }
}
