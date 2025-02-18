package br.com.starter.application.useCase.manager;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.manager.ManagerService;
import br.com.starter.domain.user.CustomUserDetails;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GetPageManagerUseCase {
    private final ManagerService managerService;

    public Optional<?> handler(Integer page, GetPageRequest request, CustomUserDetails userAuthentication) {
        User user = userAuthentication.getUser();
        return Optional.of(
                managerService.getPageByStatusAndName(
                        user,
                        request.getQuery(),
                        request.getStatus(),
                        PageRequest.of(page, request.getSize())
                )
        );
    }
}
