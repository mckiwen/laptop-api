package com.example.API.REST.Ejercicio.repositories;

import com.example.API.REST.Ejercicio.entities.Portatil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortatilRepository extends JpaRepository<Portatil, Long> {
}
