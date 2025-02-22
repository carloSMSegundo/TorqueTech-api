package br.com.starter.domain.stockTransaction;

import br.com.starter.application.api.stockTransaction.dtos.GetStockTransactionPage;
import br.com.starter.domain.garage.Garage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockTransactionService {
    private final StockTransactionRepository stockTransactionRepository;

    public StockTransaction save(StockTransaction stockTransaction) {
        return stockTransactionRepository.save(stockTransaction);
    }

    public List<StockTransaction> getAll() {
        return stockTransactionRepository.findAll();
    }

    public Page<StockTransaction> getAll(Pageable pageable) {
        return stockTransactionRepository.findAll(pageable);
    }

    public List<StockTransaction> getAllByIds(Set<UUID> ids) {
        return stockTransactionRepository.findAllById(ids);
    }

    public Page<StockTransaction> getAllByIds(Set<UUID> ids, Pageable pageable) {
        return stockTransactionRepository.findAllById(ids, pageable);
    }

    public Set<UUID> getPageFilterIds (
        Garage garage,
        GetStockTransactionPage request
    ) {
        Set<UUID> mapper = null;

        if (request.getIds() != null && !request.getIds().isEmpty()) {
            var ids = request.getIds();
            mapper = new HashSet<>(ids);
        }

        if (request.getItemsIs() != null && !request.getItemsIs().isEmpty()) {
            var ids = stockTransactionRepository.findByItemsFilter(
                garage.getId(),
                request.getItemsIs()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getOwnerId() != null) {
            var ids = stockTransactionRepository.findByOwnerFilter(
                garage.getId(),
                request.getOwnerId()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getTransactionType() != null) {
            var ids = stockTransactionRepository.findBytransactionTypeFilter(
                garage.getId(),
                request.getTransactionType()
            );

            mapper = mountMapper(mapper, ids);
        }

        if (request.getQuery() != null) {
            var ids = stockTransactionRepository.findByItemNameFilters(
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
