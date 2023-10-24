package com.example.API.REST.Ejercicio;

import com.example.API.REST.Ejercicio.entities.Portatil;
import com.example.API.REST.Ejercicio.repositories.PortatilRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class ApiRestEjercicioApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ApiRestEjercicioApplication.class, args);
		PortatilRepository repository = context.getBean(PortatilRepository.class);

		Portatil portatil1 = new Portatil();
		portatil1.setId(null);
		portatil1.setPulgadas(15.6F);
		portatil1.setMarca("Lenovo");
		portatil1.setSo("Windows");
		portatil1.setReleaseDate(LocalDate.of(2020, 10, 7));
		Portatil portatil2 = new Portatil();
		portatil2.setId(null);
		portatil2.setPulgadas(17.0F);
		portatil2.setMarca("Asus");
		portatil2.setSo("Linux");
		portatil2.setReleaseDate(LocalDate.of(2023, 5, 1));

		repository.save(portatil1);
		repository.save(portatil2);
	}

}
