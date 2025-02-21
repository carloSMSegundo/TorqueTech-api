package br.com.starter.domain.vehicleType;

public enum VehicleTypeCategory {
    // Veículos de passeio
    PASSENGER_CAR,         // Carros de passeio (Hatch, Sedan, SUV, etc.)
    ELECTRIC_CAR,          // Veículos elétricos e híbridos
    PICKUP_TRUCK,          // Caminhonetes e picapes
    VAN,                   // Vans e furgões

    // Veículos comerciais e pesados
    LIGHT_TRUCK,           // Caminhões leves
    HEAVY_TRUCK,           // Caminhões pesados e carretas
    BUS,                   // Ônibus e micro-ônibus

    // Motocicletas e similares
    MOTORCYCLE,            // Motocicletas
    SCOOTER,               // Scooters e ciclomotores
    ATV,                   // Quadriciclos
    UTV,                   // Veículos utilitários todo-terreno

    // Veículos agrícolas e especiais
    AGRICULTURAL,          // Veículos agrícolas (tratores, colheitadeiras, etc.)
    CONSTRUCTION,          // Máquinas de construção (retroescavadeiras, etc.)
    MARINE,                // Veículos náuticos (barcos, lanchas, jet skis)

    // Veículos de uso específico
    EMERGENCY,             // Veículos de emergência (ambulâncias, viaturas)
    MILITARY               // Veículos militares
}
