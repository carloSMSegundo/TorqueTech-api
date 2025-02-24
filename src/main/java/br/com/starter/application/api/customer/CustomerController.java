package br.com.starter.application.api.customer;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.application.api.common.ResponseDTO;
import br.com.starter.application.api.customer.dtos.CreateCustomerDTO;
import br.com.starter.application.api.customer.dtos.UpdateCustomerDTO;
import br.com.starter.application.useCase.customer.*;
import br.com.starter.domain.customer.Customer;
import br.com.starter.domain.customer.CustomerService;
import br.com.starter.domain.user.CustomUserDetails;
import br.com.starter.domain.user.UserStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/torque/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetPageCustomerUseCase getPageCustomerUseCase;
    private final GetAllCustomerUseCase getAllCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    @PostMapping
    public ResponseEntity<?> create(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody CreateCustomerDTO createCustomerDTO
    ) {
        return ResponseEntity.ok(
            new ResponseDTO<>(
                createCustomerUseCase.handler(createCustomerDTO, userAuthentication.getUser())
            )
        );
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> update(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @Valid @RequestBody UpdateCustomerDTO request,
        @PathVariable UUID customerId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                updateCustomerUseCase.handler(user, customerId, request)
            )
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> get(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable UUID customerId
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            getCustomerUseCase.handler(user, customerId)
        );
    }

    @GetMapping
    public ResponseEntity<?> get(
        @AuthenticationPrincipal CustomUserDetails userAuthentication
    ) {
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            getAllCustomerUseCase.handler(user)
        );
    }

    @PostMapping("/page/{page}")
    public ResponseEntity<?> page(
        @AuthenticationPrincipal CustomUserDetails userAuthentication,
        @PathVariable Integer page,
        @RequestBody GetPageRequest request
    ){
        var user = userAuthentication.getUser();
        return ResponseEntity.ok(
            new ResponseDTO<>(
                getPageCustomerUseCase.handler(user, page, request)
            )
        );
    }
}
