package com.japarejo.springdatajpaexercise.model.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.japarejo.springdatajpaexercise.model.entities.Organo;
import com.japarejo.springdatajpaexercise.model.entities.Parlamentario;
import com.japarejo.springdatajpaexercise.model.repositories.ParlamentarioRepository;

@Service
@Transactional
public class AlternanciaService {
	
	double PROBEXCEPTION=0.02;
	int NPARLAMENTARIOS=5;
	String[] sustantivos= {"El milagro","Trueno","Vigilante","Capitán","Chico","Aguijón","Maestro","Halcón","Tornado","Pecador","Fistro"};
	String[] adjetivos= {"elástico","espacial","mágico","biónico","radiactivo","maravilla","letal","mutante","amoroso","carmesí","diodenarr","de la pradera"};
	
	@Autowired
	ParlamentarioService parlamentarioService;
	
	@Autowired
	OrganoService organoService;
	
	public void alternancia() {
		parlamentarioService.resetOrganos();
		List<Parlamentario> parlamentarios=crearUObtenerParlamentarios();
		modificarOrganos(parlamentarios);
	}
	
	private void modificarOrganos(List<Parlamentario> parlamentarios) {
		
		Organo mesa=organoService.findByAbreviatura("MESA");		
		Organo juntaPortavoces=organoService.findByAbreviatura("JP");
		Organo gobierno=organoService.findByAbreviatura("GOBIERNO");
		for(Parlamentario parlamentario:parlamentarios) {
			double random=Math.random();
			if(random<PROBEXCEPTION) {
				throw new CutreMasterException();
			}else if(random<2*PROBEXCEPTION) {
				throw new CasoplonException();
			}
			parlamentario.getOrganos().add(mesa);
			parlamentario.getOrganos().add(juntaPortavoces);
			parlamentario.getOrganos().add(gobierno);
			parlamentarioService.save(parlamentario);		
		}
	}

	private List<Parlamentario> crearUObtenerParlamentarios() {
		List<Parlamentario> result=new ArrayList<>();
		Parlamentario parlamentario=null;
		String nombre=null;
		for(int i=0;i<NPARLAMENTARIOS;i++) {
			nombre=generarNombreAleatorio();
			parlamentario=parlamentarioService.findByNombre(nombre);
			if(parlamentario==null) {
				parlamentario=new Parlamentario();
				parlamentario.setNombre(nombre);		
				parlamentarioService.save(parlamentario);
			}
			result.add(parlamentario);
		}					
		return result;
	}
	
	private String generarNombreAleatorio() {
		String sustantivo=sustantivos[(int)(Math.random()*sustantivos.length)];
		String adjetivo=adjetivos[(int)(Math.random()*adjetivos.length)];
		return sustantivo+" "+adjetivo;
	}

	class CutreMasterException extends RuntimeException {	}
	
	class CasoplonException extends RuntimeException {  }

}
