package br.com.starter.application.useCase.vehicleType;

import br.com.starter.application.api.common.GetPageRequest;
import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPageVehicleTypeUseCase {

    private final VehicleTypeService vehicleTypeService;

    @Transactional
    public Page<VehicleType> handler(
        Integer page,
        GetPageRequest request
    ) {
        return vehicleTypeService.findPageByQuery(
            request.getQuery(),
            PageRequest.of(page, request.getSize())
        );
    }
}
