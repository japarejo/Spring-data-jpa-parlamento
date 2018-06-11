package com.japarejo.springdatajpaexercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Optional;

import javax.smartcardio.ATR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;

import com.japarejo.springdatajpaexercise.model.entities.Organo;
import com.japarejo.springdatajpaexercise.model.entities.Parlamentario;
import com.japarejo.springdatajpaexercise.model.entities.Sala;
import com.japarejo.springdatajpaexercise.model.entities.Sesion;
import com.japarejo.springdatajpaexercise.model.entities.TipoSesion;
import com.japarejo.springdatajpaexercise.model.repositories.OrganoRepository;
import com.japarejo.springdatajpaexercise.model.repositories.ParlamentarioRepository;
import com.japarejo.springdatajpaexercise.model.repositories.SalaRepository;
import com.japarejo.springdatajpaexercise.model.repositories.TipoSesionRepository;
import com.japarejo.springdatajpaexercise.model.services.OrganoService;
import com.japarejo.springdatajpaexercise.model.services.ParlamentarioService;
import com.japarejo.springdatajpaexercise.model.services.SalaService;
import com.japarejo.springdatajpaexercise.model.services.SesionService;
import com.japarejo.springdatajpaexercise.model.services.TipoSesionService;
import com.japarejo.springdatajpaexercise.model.repositories.SesionRepository;
import com.sun.media.sound.ModelStandardTransform;

import jdk.nashorn.internal.parser.Lexer.LexerToken;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringDataJpaExerciseApplication implements CommandLineRunner {
	// COMANDOS:
	public static final String MOSTRARMODIFICIAR = "mostrar/modificar";
	public static final String INSERTAR = "insertar";
	public static final String BORRAR = "borrar";
	public static final String ERROR = "error";
	public static final String ATRAS = "back";
	public static final String SALIR = "exit";
	public static final String[] opciones = { MOSTRARMODIFICIAR, INSERTAR, BORRAR };

	// ENTIDADES:
	public static final String SALA = "Sala";
	public static final String TIPOSESION = "Tipo de Sesión";
	public static final String ORGANO = "Órgano";
	public static final String PARLAMENTARIO = "Parlamentario";
	public static final String SESION = "Sesión";
	public static final String[] entidades = { SALA, TIPOSESION, ORGANO, PARLAMENTARIO, SESION };

	@Autowired
	private SalaService salaService;
	@Autowired
	private TipoSesionService tipoSesionService;
	@Autowired
	private OrganoService organoService;
	@Autowired
	private SesionService sesionService;
	@Autowired
	private ParlamentarioService parlamentarioService;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		// Inicialización de datos:
		tipoSesionService.initializeTipoSesion();
		organoService.initializeOrganos();
		// Menú:
		String command="";
		do {
			mostrarMenuGeneral();
			command=in.readLine();
			command=interpretarComando(command,in);
			if(!command.equals(SALIR) && !command.equals(ERROR))
				mostrarMenuDatos(command,in);
		}while(!command.equals(SALIR) );
		System.out.println("Adiós!");
	}

	private String interpretarComando(String command, BufferedReader in) {
		String resultado = ERROR;
		try {
			int option = Integer.parseInt(command);
			if (option == opciones.length)
				resultado = SALIR;
			else if (option >= 0 && option < opciones.length)
				resultado = opciones[option];
		} catch (NumberFormatException e) {
		}
		if (resultado.equals(ERROR))
			System.out.println("Debe seleccionar una opción valida entre 0 y " + entidades.length);
		return resultado;
	}

	private void mostrarMenuDatos(String accion, BufferedReader in) throws IOException {
		String comando = "";
		do {
			mostrarMenuSeleccionarEntidad(accion);
			comando = in.readLine();
			comando = interpretarComandoSeleccionarEntidad(comando);
			if (!comando.equals(ATRAS))
				ejecutarAccionEntidad(accion, comando, in);
		} while (!comando.equals(ATRAS));

	}

	private String interpretarComandoSeleccionarEntidad(String comando) {
		String resultado = ERROR;
		try {
			int option = Integer.parseInt(comando);
			if (option == entidades.length)
				resultado = ATRAS;
			else if (option >= 0 && option < entidades.length)
				resultado = entidades[option];
		} catch (NumberFormatException e) {
		}
		if (resultado.equals(ERROR))
			System.out.println("Debe seleccionar un comando válido (1,2,3,4 o 5");
		return resultado;
	}

	private void mostrarMenuSeleccionarEntidad(String accion) {
		System.out.println("#============================#");
		System.out.println("# PARLAMENTO COMMANDER v1.0  #");
		System.out.println("#----------------------------#");
		System.out.println("# "+accion+" datos    #");
		System.out.println("#============================#");
		System.out.println("Seleccione la entidad a "+accion+":");
		System.out.println("------------------------------");
		int i=0;
		for(String entidad:entidades) {
			System.out.println("["+i+"] - "+entidad);
			i++;
		}
		System.out.println("["+i+"] - Volver");
	}

	private void mostrarMenuGeneral() {
		System.out.println("#============================#");
		System.out.println("# PARLAMENTO COMMANDER v1.0  #");
		System.out.println("#============================#");
		System.out.println("Seleccione un comando:");
		System.out.println("------------------------------");
		int i = 0;
		for (String accion : opciones) {
			System.out.println("[" + i + "] - " + accion);
			i++;
		}
		System.out.println("[" + i + "] - Salir");
	}

	
	
	private void ejecutarAccionEntidad(String accion, String entidad,BufferedReader in) throws IOException {
		
		switch(entidad) {		
		case SALA:				
			switch(accion) {
				case MOSTRARMODIFICIAR:
					salaService.printSalas(in);
					break;
				case INSERTAR:
					salaService.insertarSalas(in);
					break;
				case BORRAR:
					salaService.printSalas();
					System.out.println("Indique la sala que desea borrar:");
					salaService.borrarSala(in);
					break;
			}
			break;
		case SESION:				
			switch(accion) {
				case MOSTRARMODIFICIAR:
					sesionService.printSesiones(in);
					break;
				case INSERTAR:
					sesionService.insertarSesion(in);
					break;
				case BORRAR:
					sesionService.printSesiones();
					System.out.println("Indique la sala que desea borrar:");
					sesionService.borrarSesion(in);
					break;
			}
			break;
		case TIPOSESION:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				tipoSesionService.printTipoSesion(in);
				break;
			case INSERTAR:
				tipoSesionService.insertarTipoSesion(in);
				break;
			case BORRAR:
				tipoSesionService.printTiposSesion();
				System.out.println("Indique el tipo de sesión que desea borrar:");
				tipoSesionService.borrarTipoSesion(in);
				break;
		}
			break;
		case PARLAMENTARIO:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				parlamentarioService.printParlamentario(in);
				break;
			case INSERTAR:
				parlamentarioService.insertarParlamentario(in);
				break;
			case BORRAR:
				parlamentarioService.printParlamentarios();
				System.out.println("Indique el parlamentario que desea borrar:");
				parlamentarioService.borrarInsertarParlamentario(in);
				break;
			}
			break;
		case ORGANO:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				organoService.printOrganos(in);
				break;
			case INSERTAR:
				organoService.insertarOrgano(in);
				break;
			case BORRAR:
				organoService.printOrganos();
				System.out.println("Indique el parlamentario que desea borrar:");
				organoService.borrarOrgano(in);
				break;
			}
			break;
		 
		}						
		
	}

}
