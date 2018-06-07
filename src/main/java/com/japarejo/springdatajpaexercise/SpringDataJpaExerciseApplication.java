package com.japarejo.springdatajpaexercise;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.japarejo.springdatajpaexercise.model.entities.Organo;
import com.japarejo.springdatajpaexercise.model.entities.TipoSesion;
import com.japarejo.springdatajpaexercise.model.repositories.OrganoRepository;
import com.japarejo.springdatajpaexercise.model.repositories.TipoSesionRepository;

import sun.security.timestamp.TSRequest;


@SpringBootApplication
@EnableAutoConfiguration
public class SpringDataJpaExerciseApplication implements CommandLineRunner {

	@Autowired
	private TipoSesionRepository tipoSesionRepo;
	
	@Autowired
	private OrganoRepository organoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeTipoSesion();
		initializeOrgano();
	}
	
	private void initializeTipoSesion() throws Exception {
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
			String[] args= {};
			run(args);
		}else {
			System.out.println("Tipos de sesión en la BD:");
			for(TipoSesion ts:tiposDeSesion) {
				System.out.println(ts.getId()+" - "+ts.getDescripcion()+", activo:"+ts.getActivo());
			}
		}
	}
	
	private void initializeOrgano() throws Exception {
		Iterable<Organo> organoIter=organoRepo.findAll();
		Iterator<Organo> iterador=organoIter.iterator();
		if(!iterador.hasNext()) {
			// No existen organos, vamos a crearlos:
			System.out.println("No hay organos!, vamos a incializarlos...");
			String[][] organos= {	{"MESA","Mesa del Parlamento","1"},
									{"JP","Junta de Portavoces","2"},
									{"PPA","Pleno del Parlamento","3"},
									{"DIPPER","Diputación Permamente","4"},
									{"GPS","G.P. Socialista","1"},
									{"IULV-CA","G.P. Izquierda Unida Los Verdes-Convocatoria por Andalucía","5"},
									{"GPP","G.P. Popular Andaluz","2"},
									{"GPPD","G.P. Podemos Andalucía","3"},
									{"GPC","G.P. Ciudadanos","4"}};
			Organo organo=null;
			for(int i=0;i<organos.length;i++) {
				organo=new Organo();
				organo.setAbreaviatura(organos[i][0]);
				organo.setDescripcion(organos[i][1]);
				organo.setOrden(Long.valueOf(organos[i][2]));
				organoRepo.save(organo);
			}			
			System.out.println("Done!");
			String []args= {};
			run(args);
		}else {
			System.out.println("Organos en la BD:");
			
			for(Organo ts:organoIter) {
				System.out.println(ts.getId()+" - "+ts.getDescripcion()+", orden:"+ts.getOrden());
			}
		}
	}
	
}
