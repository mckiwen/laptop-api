package com.example.API.REST.Ejercicio.controllers;

import com.example.API.REST.Ejercicio.entities.Portatil;
import com.example.API.REST.Ejercicio.repositories.PortatilRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PortatilController {

    // Atributos
    PortatilRepository portatilRepository;

    private final Logger log = LoggerFactory.getLogger(PortatilRepository.class);

    public PortatilController(PortatilRepository portatilRepository){
        this.portatilRepository = portatilRepository;
    }

    /**
     * Get all laptops objects from the database
     * @return ResponseEntity
     */
    @Operation(summary = "Get all laptops objects from the database")
    @ApiResponse(responseCode = "200", description = "Successful operation: Resource found")
    @ApiResponse(responseCode = "204", description = "Successful operation: No resources to show")
    @GetMapping("/api/portatiles")
    public ResponseEntity<List<Portatil>> findAll(){
        List<Portatil> listaPortatil = portatilRepository.findAll();

        if(listaPortatil.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listaPortatil);
        }
    }

    /**
     * Get a laptop by an id given
     * @param id
     * @return
     */
    @Operation(summary = "Get a laptop by its id")
    @ApiResponse(responseCode = "200", description = "Successful operation: Resource found")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @GetMapping("api/portatiles/{id}")
    public ResponseEntity<Optional<Portatil>> findOneById(@Parameter(description = "Id of laptop to be searched") @PathVariable Long id){
        Optional<Portatil> portatil = portatilRepository.findById(id);

        if(portatil.isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(portatil);
        }
    }

    /**
     * Create a laptop given by a POST request
     * @param portatil
     * @param headers
     * @return
     */
    @Operation(summary = "Create a laptop given by a POST request")
    @ApiResponse(responseCode = "200", description = "Successful operation: Resource created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @PostMapping("/api/portatiles")
    public ResponseEntity<Portatil> create(@RequestBody Portatil portatil, @RequestHeader HttpHeaders headers){
        if(portatil.getId() != null){
            log.warn("trying to create a laptop with id");
            return ResponseEntity.badRequest().build();
        }
        Portatil result = portatilRepository.save(portatil);
        return ResponseEntity.ok(result);
    }

    /**
     * Update a laptop by an id given.
     * @param portatil
     * @return
     */
    @Operation(summary = "Update a laptop")
    @ApiResponse(responseCode = "200", description = "Successful operation: Resource created")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "404", description = "Trying to update a non existing laptop")
    @PutMapping("/api/portatiles")
    public ResponseEntity<Portatil> update(@RequestBody Portatil portatil){
        if(portatil.getId() == null) {
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.badRequest().build();
        }
        if(!portatilRepository.existsById(portatil.getId())){
            log.warn("Trying to update a non existent laptop");
            return ResponseEntity.notFound().build();
        }
        Portatil result =  portatilRepository.save(portatil);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete a laptop for an id given
     * @param id
     * @return
     */
    @Operation(summary = "Delete a laptop for an id given")
    @ApiResponse(responseCode = "204", description = "Successful operation: Resource removed")
    @ApiResponse(responseCode = "404", description = "Trying to update a non existing laptop")
    @DeleteMapping("/api/portatiles/{id}")
    public ResponseEntity<Portatil> delete(@PathVariable Long id){
        if(!portatilRepository.existsById(id)){
            log.warn("Trying to delete a non existent laptop");
            return ResponseEntity.notFound().build();
        }
        portatilRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all the laptops
     * @return
     */
    @Operation(summary = "Delete all the laptops")
    @ApiResponse(responseCode = "204", description = "Successful operation: Resources removed")
    @DeleteMapping("/api/portatiles")
    public ResponseEntity<Portatil> deleteAll(){
        log.info("REST Request to delete all laptops");
        portatilRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
