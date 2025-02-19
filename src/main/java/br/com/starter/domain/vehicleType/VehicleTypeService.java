package br.com.starter.domain.vehicleType;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service // Anotação que indica que essa classe é um serviço no Spring
@RequiredArgsConstructor // Anotação do Lombok que gera o construtor com parâmetros obrigatórios
public class VehicleTypeService {

    private final VehicleTypeRepository vehicleTypeRepository; // Injeta o repositório de VehicleType

    // Cadastro de um novo tipo de veículo
    public VehicleType save(VehicleType vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }


    // Atualização de um tipo de veículo existente
    public VehicleType update(UUID id, VehicleType vehicleType) {
        // Procura o tipo de veículo no banco de dados pelo ID
        VehicleType existingVehicleType = vehicleTypeRepository.findById(id).orElse(null);

        // Se o tipo de veículo for encontrado, atualiza seus campos
        if (existingVehicleType != null) {
            existingVehicleType.setModel(vehicleType.getModel()); // Atualiza o modelo
            existingVehicleType.setBrand(vehicleType.getBrand()); // Atualiza a marca
            existingVehicleType.setYear(vehicleType.getYear()); // Atualiza o ano

            // Persiste o tipo de veículo atualizado no banco de dados e retorna o objeto atualizado
            return vehicleTypeRepository.save(existingVehicleType);
        }

        // Se o tipo de veículo não for encontrado, retorna null
        return null;
    }

    // Listagem paginada de tipos de veículos
    public Page<VehicleType> listVehicleTypes(int page, int size) {
        // Cria um objeto PageRequest que define as informações de paginação (número da página e tamanho)
        PageRequest pageable = PageRequest.of(page, size);

        // Chama o metodo findAll do repositório para obter uma lista de tipos de veículos paginada
        return vehicleTypeRepository.findAll(pageable);
    }

    // Filtro por marca e modelo com paginação
    public Page<VehicleType> filterByBrandAndModel(String brand, String model, int page, int size) {
        // Cria um objeto PageRequest para paginação
        PageRequest pageable = PageRequest.of(page, size);

        // Chama o metodo findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase no repositório
        // Este metodo vai procurar os tipos de veículos que possuem a marca e o modelo que contenham os valores passados (não sensível a maiúsculas/minúsculas)
        return vehicleTypeRepository.findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(brand, model, pageable);
    }

    public VehicleType findOrCreate(String model, String brand, String year) {
        // Busca um VehicleType pelo modelo, marca e ano
        Optional<VehicleType> existingVehicleType = vehicleTypeRepository
                .findByModelAndBrandAndYear(model, brand, year);

        // Se encontrar, retorna o existente, senão cria um novo
        return existingVehicleType.orElseGet(() -> {
            VehicleType newVehicleType = new VehicleType();
            newVehicleType.setModel(model);
            newVehicleType.setBrand(brand);
            newVehicleType.setYear(year);
            return vehicleTypeRepository.save(newVehicleType);
        });
    }



}
