package br.com.starter.application.api.stockTransaction;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.stockTransaction.dtos.InputStockTransactionRequest;
import br.com.starter.application.api.stockTransaction.dtos.GetPageStockTransactionRequest;
import br.com.starter.application.api.stockTransaction.dtos.OutputStockTransactionRequest;
import br.com.starter.application.useCase.stockTransaction.*;
import br.com.starter.domain.stockItem.StockItem;
import br.com.starter.domain.stockTransaction.StockTransaction;
import br.com.starter.domain.transactionItem.TransactionItem;
import br.com.starter.domain.user.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/torque/api/stock-transaction")
@RequiredArgsConstructor
public class StockTransactionController {

    private final GetPageStockTransactionUseCase getPageStockTransactionUseCase;
    private final InputStockTransactionUseCase createStockTransactionUseCase;
    private final OutputStockTransactionUseCase outputStockTransactionUseCase;
    private final CancelStockTransactionUseCase cancelStockTransactionUseCase;
    private final GetStockTransactionUseCase getStockTransactionUseCase;
    private final UpdateOutStockTransactionUseCase updateOutStockTransactionUseCase;
    private final UpdateInpStockTransactionUseCase updateInpStockTransactionUseCase;

    private final ObjectMapper objectMapper;

    @PostMapping("/input")
    public ResponseEntity<?> input(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @Valid @RequestBody InputStockTransactionRequest request
    ) {
        var user = userAuthentication.getUser();
        var savedTransaction = createStockTransactionUseCase.handler(user, request).orElseThrow();

        return ResponseEntity.ok(
            new ResponseDTO<>(savedTransaction)
        );
    }

    @PutMapping("/input/{stockTransactionId}")
    public ResponseEntity<?> updateInput(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID stockTransactionId,
        @Valid @RequestBody InputStockTransactionRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateInpStockTransactionUseCase.handler(user, stockTransactionId, request)
            )
        );
    }

    @PostMapping("/output")
    public ResponseEntity<?> output(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody OutputStockTransactionRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                outputStockTransactionUseCase.handler(user, request)
            )
        );
    }

    @PutMapping("/output/{stockTransactionId}")
    public ResponseEntity<?> updateOutput(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID stockTransactionId,
        @Valid @RequestBody OutputStockTransactionRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateOutStockTransactionUseCase.handler(user, stockTransactionId, request)
            )
        );
    }

    @PutMapping("/{stockTransactionId}/cancel")
    public ResponseEntity<?> cancel(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID stockTransactionId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        cancelStockTransactionUseCase.handler(user, stockTransactionId)
                )
        );
    }

    @GetMapping("/{stockTransactionId}")
    public ResponseEntity<?> get(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable UUID stockTransactionId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getStockTransactionUseCase.handler(user, stockTransactionId)
                )
        );
    }

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
            @AuthenticationPrincipal CustomUserDetails userAuthentication,
            @PathVariable Integer page,
            @RequestBody GetPageStockTransactionRequest request
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
                new ResponseDTO<>(
                        getPageStockTransactionUseCase.handler(
                                user,
                                request,
                                page
                        )
                )
        );
    }


    @GetMapping("/debug-stock-transaction")
    public ResponseEntity<String> debugStockTransaction(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        StockTransaction transaction = new StockTransaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setOwner(userAuthentication.getUser());
        transaction.setGarage(userAuthentication.getGarage().get());

        var stockItem = new StockItem();
        stockItem.setQuantity(5);
        stockItem.setPrice(50L);

        var transactionItem = new TransactionItem();
        transactionItem.setTransaction(transaction);
        transactionItem.setStockItem(stockItem);
        transactionItem.setQuantity(5);
        transaction.setItems(List.of(transactionItem));

        try {
            String json = objectMapper.writeValueAsString(transaction);
            return ResponseEntity.ok(json);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
