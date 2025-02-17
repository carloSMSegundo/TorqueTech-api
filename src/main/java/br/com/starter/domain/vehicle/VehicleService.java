package br.com.starter.domain.vehicle;

import br.com.starter.domain.vehicleType.VehicleType;
import br.com.starter.domain.vehicleType.VehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final VehicleTypeRepository vehicleTypeRepository;  // Para obter o tipo de veículo

    // Cadastro de veículo
    public Vehicle save(Vehicle vehicle) {
        // Caso o vehicleType esteja nulo ou o id não esteja setado, você pode atribuir um tipo de veículo aqui
        if (vehicle.getVehicleType() != null && vehicle.getVehicleType().getId() != null) {
            // Aqui você pode validar que o tipo de veículo existe no banco antes de salvar o veículo
            Optional<VehicleType> vehicleType = vehicleTypeRepository.findById(vehicle.getVehicleType().getId());
            if (vehicleType.isPresent()) {
                vehicle.setVehicleType(vehicleType.get());  // Atribui o tipo de veículo encontrado
            } else {
                // Se o tipo de veículo não existir, você pode lançar um erro ou criar um novo tipo
                // Exemplo: lançar uma exceção personalizada
                throw new RuntimeException("Tipo de veículo não encontrado.");
            }
        }
        return vehicleRepository.save(vehicle);  // Salva o veículo com o tipo atribuído
    }

    // Listar todos os veículos
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    // Buscar veículo por ID
    public Optional<Vehicle> getById(UUID id) {
        return vehicleRepository.findById(id);
    }

}
