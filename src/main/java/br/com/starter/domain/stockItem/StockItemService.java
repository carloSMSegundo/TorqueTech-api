package br.com.starter.domain.stockItem;

import br.com.starter.application.api.stockItem.dto.GetPageStockItemRequest;
import br.com.starter.application.api.stockTransaction.dtos.GetPageStockTransactionRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.stockTransaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StockItemService {
    private final StockItemRepository stockItemRepository;

    public StockItem save(StockItem stockItem) {
        return stockItemRepository.save(stockItem);
    }

    public List<StockItem> getAllByGarage(UUID garageId) {
        return stockItemRepository.findAllWithItems(garageId);
    }

    public Optional<StockItem> findByItemAndPrice(UUID itemId, UUID garageId, UUID localId , Long price) {
        return stockItemRepository.findByItemAndAcquisitionPriceAndStatus(itemId, garageId, localId, price);
    }

    public Optional<StockItem> findById(UUID id, Garage garage) {
        return stockItemRepository.findByIdAndGarageId(garage.getId(), id);
    }

    public Page<StockItem> findAllByGarageId(UUID garageId, Pageable pageable) {
        return stockItemRepository.findAllByGarageId(garageId, pageable);
    }

    public Page<StockItem> getAllByIds(UUID garageId, Set<UUID> ids, Pageable pageable) {
        return stockItemRepository.findAllByIds(garageId, ids, pageable);
    }

    public Set<UUID> getPageFilterIds (
        Garage garage,
        GetPageStockItemRequest request
    ) {
        Set<UUID> mapper = null;

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            var ids = request.getIds();
            mapper = new HashSet<>(ids);
        }

        if (request.getItemsIs() != null && !request.getItemsIs().isEmpty()) {
            var ids = stockItemRepository.findByItemsFilter(
                garage.getId(),
                request.getItemsIs()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            var ids = stockItemRepository.findByItemNameFilters(
                garage.getId(),
                request.getQuery()
            );

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
