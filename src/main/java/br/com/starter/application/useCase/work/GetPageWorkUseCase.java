package br.com.starter.application.useCase.work;

import br.com.starter.application.api.work.dtos.GetPageWorkRequest;
import br.com.starter.domain.garage.Garage;
import br.com.starter.domain.garage.GarageService;
import br.com.starter.domain.work.WorkService;
import br.com.starter.domain.work.Work;
import br.com.starter.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Page;

@Component
@RequiredArgsConstructor
public class GetPageWorkUseCase {

    private final WorkService workService;
    private final GarageService garageService;

    public Page<Work> handler(
            User user,
            GetPageWorkRequest request,
            Integer page
    ) {
        Garage garage = garageService.getByUser(user).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "O usuário não possui uma oficina registrada!"
                )
        );

        var ids = workService.getPageFilterIds(request, garage);

        var pageRequest = PageRequest.of(page, request.getSize());

        return ids != null && !ids.isEmpty()
                ? workService.getAllByIds(ids, pageRequest)
                : workService.getAll(pageRequest);
    }
}
