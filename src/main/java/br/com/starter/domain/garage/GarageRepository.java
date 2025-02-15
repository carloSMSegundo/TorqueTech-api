package br.com.starter.domain.garage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarageRepository extends JpaRepository<Garage, UUID> {
}
