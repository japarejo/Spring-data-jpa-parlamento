package com.japarejo.springdatajpaexercise.model.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japarejo.springdatajpaexercise.model.entities.Sala;
import com.japarejo.springdatajpaexercise.model.repositories.SalaRepository;

@Service
public class SalaService {	
	
	
	@Autowired
	private SalaRepository salaRepo;
	
	public void borrarSala(BufferedReader in) throws NumberFormatException, IOException {
		Long id=Long.parseLong(in.readLine());
		salaRepo.deleteById(id);
		
	}

	public void printSalas() {
		printSalas(salaRepo.findAll());
		
	}

	public void printSalas(Iterable<Sala> findAll) {
		System.out.println("Salas:");
		for(Sala sala:findAll) {
			System.out.println(sala.getId()+" - "+sala.getDescripcion()+", activo:"+sala.getActivo());
		}
		
	}

	public void insertarSalas(BufferedReader in) throws IOException {
		Sala sala=new Sala();
		leerSala(sala,in);
		salaRepo.save(sala);
		
	}

	public void leerSala(Sala sala, BufferedReader in) throws IOException {
		System.out.println("Indique la descripción de la sala:");
		if(sala.getDescripcion()!=null) {
			System.out.println("   - Valor actual: "+sala.getDescripcion());
		}
		String desc=in.readLine();
		sala.setDescripcion(desc);
		
		System.out.println("Indique el estado de la sala (1) activa o (0) inactiva"); 
		if(sala.getActivo()!=null)
			System.out.println("    - Valor actual: "+sala.getActivo());
		String act=in.readLine();
		sala.setActivo(act);		
	}

	public void printSalas(BufferedReader in) throws IOException {
		printSalas();
		System.out.println("Indique el id de la sala a modificar o ENTER para volver.");		
		String sid;		
		sid = in.readLine();
		if(!"".equals(sid)) {
			Long id=Long.parseLong(sid);
			if(id!=null)
				modificarSala(id,in);
		}
		
	}

	public void modificarSala(Long id, BufferedReader in) throws IOException {
		Optional<Sala> salaOpt=salaRepo.findById(id);
		if(salaOpt.isPresent()) {
			Sala sala=salaOpt.get();
			leerSala(sala,in);
			salaRepo.save(sala);
		}
	}

	public Optional<Sala> findById(Long idSala) {
		return salaRepo.findById(idSala);
	}


	
}
