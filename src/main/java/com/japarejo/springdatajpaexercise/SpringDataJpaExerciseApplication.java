package com.japarejo.springdatajpaexercise;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.japarejo.springdatajpaexercise.model.entities.TipoSesion;
import com.japarejo.springdatajpaexercise.model.repositories.TipoSesionRepository;

import sun.security.timestamp.TSRequest;


@SpringBootApplication
@EnableAutoConfiguration
public class SpringDataJpaExerciseApplication implements CommandLineRunner {

	@Autowired
	private TipoSesionRepository tipoSesionRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<TipoSesion> tiposDeSesion=tipoSesionRepo.findAll();
		Iterator<TipoSesion> iterador=tiposDeSesion.iterator();
		if(!iterador.hasNext()) {
			// No existen tipos de sesión, vamos a crearlos:
			System.out.println("No hay tipos de sesión!, vamos a incializarlos...");
			String[] tipos= {"Legislativa",
					"Informativa, de control y de adopción de acuerdos",
					"Mesa de Comisión",
					"Ponencia Legislativa",
					"Institucional",
					"Visita"};
			TipoSesion ts=null;
			for(String tipo:tipos) {
				ts=new TipoSesion();
				ts.setDescripcion(tipo);
				ts.setActivo("1");
				tipoSesionRepo.save(ts);
			}			
			System.out.println("Done!");
			run(args);
		}else {
			System.out.println("Tipos de sesión en la BD:");
			for(TipoSesion ts:tiposDeSesion) {
				System.out.println(ts.getDescripcion()+", activo:"+ts.getActivo());
			}
		}
	}
}
