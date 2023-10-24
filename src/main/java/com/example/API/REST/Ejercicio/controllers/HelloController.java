package com.example.API.REST.Ejercicio.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    /**
     * Get a Hello message as String
     * @return
     */
    @Operation(summary = "Get a Hello message as String")
    @GetMapping("/hola")
    public String hola(){
        return "Hola!! Qué tal todo??";
    }


}
