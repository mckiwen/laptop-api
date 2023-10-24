package com.example.API.REST.Ejercicio.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "portatil")
@Data
public class Portatil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float pulgadas;
    private String marca;
    private String so;
    private LocalDate releaseDate;




}
