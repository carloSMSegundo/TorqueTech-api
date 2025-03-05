package br.com.starter.application.api.stockItem;

import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.useCase.stockItem.GetAllStockItemUseCase;
import br.com.starter.domain.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/torque/api/stock-item")
@RequiredArgsConstructor
public class StockItemController {
    private final GetAllStockItemUseCase getAllStockItemUseCase;

    @GetMapping
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ){
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getAllStockItemUseCase.handler(user)
            )
        );
    }
}
