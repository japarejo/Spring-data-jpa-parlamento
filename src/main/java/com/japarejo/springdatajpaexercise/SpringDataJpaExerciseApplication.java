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
	private SalaRepository salaRepo;
	@Autowired
	private TipoSesionRepository tipoSesionRepo;
	@Autowired
	private OrganoRepository organoRepo;
	@Autowired
	private SesionRepository sesionRepo;
	@Autowired
	private ParlamentarioRepository parlamentarioRepo;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaExerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		// Inicialización de datos:
		initializeTipoSesion();
		initializeOrganos();
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
					printSalas(in);
					break;
				case INSERTAR:
					insertarSalas(in);
					break;
				case BORRAR:
					printSalas();
					System.out.println("Indique la sala que desea borrar:");
					borrarSala(in);
					break;
			}
			break;
		case SESION:				
			switch(accion) {
				case MOSTRARMODIFICIAR:
					printSesiones(in);
					break;
				case INSERTAR:
					insertarSesion(in);
					break;
				case BORRAR:
					printSesiones();
					System.out.println("Indique la sala que desea borrar:");
					borrarSesion(in);
					break;
			}
			break;
		case TIPOSESION:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				printTipoSesion(in);
				break;
			case INSERTAR:
				insertarTipoSesion(in);
				break;
			case BORRAR:
				printTiposSesion();
				System.out.println("Indique el tipo de sesión que desea borrar:");
				borrarTipoSesion(in);
				break;
		}
			break;
		case PARLAMENTARIO:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				printParlamentario(in);
				break;
			case INSERTAR:
				insertarParlamentario(in);
				break;
			case BORRAR:
				printParlamentarios();
				System.out.println("Indique el parlamentario que desea borrar:");
				borrarInsertarParlamentario(in);
				break;
			}
			break;
		case ORGANO:
			switch(accion) {
			case MOSTRARMODIFICIAR:
				printOrganos(in);
				break;
			case INSERTAR:
				insertarOrgano(in);
				break;
			case BORRAR:
				printOrganos();
				System.out.println("Indique el parlamentario que desea borrar:");
				borrarOrgano(in);
				break;
			}
			break;
		 
		}						
		
	}
	
	
	private void borrarSala(BufferedReader in) throws NumberFormatException, IOException {
		Long id=Long.parseLong(in.readLine());
		salaRepo.deleteById(id);
		
	}

	private void printSalas() {
		printSalas(salaRepo.findAll());
		
	}

	private void printSalas(Iterable<Sala> findAll) {
		System.out.println("Salas:");
		for(Sala sala:findAll) {
			System.out.println(sala.getId()+" - "+sala.getDescripcion()+", activo:"+sala.getActivo());
		}
		
	}

	private void insertarSalas(BufferedReader in) throws IOException {
		Sala sala=new Sala();
		leerSala(sala,in);
		salaRepo.save(sala);
		
	}

	private void leerSala(Sala sala, BufferedReader in) throws IOException {
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

	private void printSalas(BufferedReader in) throws IOException {
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

	private void modificarSala(Long id, BufferedReader in) throws IOException {
		Optional<Sala> salaOpt=salaRepo.findById(id);
		if(salaOpt.isPresent()) {
			Sala sala=salaOpt.get();
			leerSala(sala,in);
			salaRepo.save(sala);
		}
	}

	private void insertarParlamentario(BufferedReader in) throws IOException {
		Parlamentario parlamentario=new Parlamentario();
		leerParlamentario(parlamentario, in);
		parlamentarioRepo.save(parlamentario);
		
	}

	private void borrarInsertarParlamentario(BufferedReader in) throws NumberFormatException, IOException {		
		Long id=Long.parseLong(in.readLine());
		parlamentarioRepo.deleteById(id);
		
	}

	private void printParlamentarios() {
		printParlamentarios(parlamentarioRepo.findAll());
		
	}

	private void printParlamentarios(Iterable<Parlamentario> findAll) {
		for(Parlamentario parlamentario:findAll) {
			System.out.print("   -> Organos: ");
			for(Organo organo:parlamentario.getOrganos())
				System.out.println(organo.getId()+" ("+organo.getDescripcion()+ "),");
		}
		
	}

	private void printParlamentario(BufferedReader in) throws IOException {
		printParlamentarios();
		System.out.println("Indique el id del parlamentario a modificar o ENTER para volver.");		
		String sid;		
		sid = in.readLine();
		if(!"".equals(sid)) {
			Long id=Long.parseLong(sid);
			if(id!=null)
				modificarParlamentario(id,in);
		}							

		
	}

	private void modificarParlamentario(Long id, BufferedReader in) throws IOException {
		Optional<Parlamentario> parlamentarioOpt=parlamentarioRepo.findById(id);
		if(parlamentarioOpt.isPresent()) {
			Parlamentario parlamentario=parlamentarioOpt.get();
			leerParlamentario(parlamentario,in);
			parlamentarioRepo.save(parlamentario);
		}else
			System.out.println("Unable to load tipo de sesion con id "+id);				
	}

	private void leerParlamentario(Parlamentario parlamentario, BufferedReader in) throws IOException {
		System.out.println("Indique el nuevo nombre:");
		if(parlamentario.getNombre()!=null)
			System.out.println("(valor actual "+parlamentario.getNombre()+")");
		String nombre=in.readLine();
		parlamentario.setNombre(nombre);
		System.out.println("Indique los órganos a los que pertenece el parlamentario (por Id separados por comas):");
		if(!parlamentario.getOrganos().isEmpty()) {
			System.out.print("   -> Organos actuales: ");
			for(Organo organo:parlamentario.getOrganos())
				System.out.println(organo.getId()+" ("+organo.getDescripcion()+ "),");
		}
		printOrganos();
		String organos=in.readLine();
		String[] arrayOrganos=organos.split(",");
		parlamentario.getOrganos().clear();
		for(String organoId:arrayOrganos) {
			if(!"".equals(organoId)) {				
				Optional<Organo> organo=organoRepo.findById(Long.parseLong(organoId));
				if(organo.isPresent())
					parlamentario.getOrganos().add(organo.get());
			}
		}
		
	}

	private void borrarTipoSesion(BufferedReader in) throws NumberFormatException, IOException {
		Long id=Long.parseLong(in.readLine());
		tipoSesionRepo.deleteById(id);				
	}

	private void insertarTipoSesion(BufferedReader in) throws IOException {
		TipoSesion tipoSesion=new TipoSesion();
		leerTipoSesion(tipoSesion, in);
		tipoSesionRepo.save(tipoSesion);		
	}

	private void printTipoSesion(BufferedReader in) throws IOException {
		printTiposSesion();
		System.out.println("Indique el id del tipo de sesion a modificar o ENTER para volver.");
		String sid;		
			sid = in.readLine();
			if(!"".equals(sid)) {
				Long id=Long.parseLong(sid);
				if(id!=null)
					modificarTipoSesion(id,in);
			}									
	}

	private void modificarTipoSesion(Long id, BufferedReader in) throws IOException {
		Optional<TipoSesion> tipoSesionOpt=tipoSesionRepo.findById(id);
		if(tipoSesionOpt.isPresent()) {
			TipoSesion tipoSesion=tipoSesionOpt.get();
			leerTipoSesion(tipoSesion,in);
			tipoSesionRepo.save(tipoSesion);
		}else
			System.out.println("Unable to load tipo de sesion con id "+id);
	}

	private void leerTipoSesion(TipoSesion tipoSesion2, BufferedReader in) throws IOException {
		System.out.println("Proporcione la descripción del tipo de sesión");
		if(tipoSesion2.getDescripcion()!=null)
			System.out.print(tipoSesion2.getDescripcion());
		tipoSesion2.setDescripcion(in.readLine());

		System.out.println("Indique el si el tipo de sesión está activo (1) o no (0):");
		if(tipoSesion2.getActivo()!=null)
			System.out.print(tipoSesion2.getActivo());
		String orden=in.readLine();
		tipoSesion2.setActivo(orden);
		
	}

	private void borrarSesion(BufferedReader in) throws NumberFormatException, IOException {
		Long id=Long.parseLong(in.readLine());
		sesionRepo.deleteById(id);		
	}


	private void printSesiones() {		
		printSesiones(sesionRepo.findAll());
	}
	
	
	private void printSesiones(Iterable<Sesion> sesiones) {
		System.out.println("Sesiones:");
		for (Sesion s : sesiones) {
			System.out.print(s.getId() + " - " +s.getFecha()+ "("+s.getNumero()+" de la "+s.getLegislatura()+" legislatura)");
			System.out.println(" desde las "+s.getHoraInicio() + "a las "+s.getHoraFin()+" en la sala "+s.getSala().getDescripcion() );
			System.out.println("    - "+s.getOrgano() +" tipo de sesión: "+	s.getTipoSesion().getDescripcion());

		}
	}

	private void insertarSesion(BufferedReader in) throws IOException {
		Sesion s=new Sesion();
		leerSesion(s,in);
		sesionRepo.save(s);
	}

	private void printSesiones(BufferedReader in) throws IOException {
		printSesiones();
		System.out.println("Indique el id de la sesión que quiere modificar o [ENTER] var volver:");
		String sid=in.readLine();
		if(!"".equals(sid)) {
			Long id=Long.parseLong(sid);
			if(id!=null)
				modificarSesion(id,in);
		}				
		
	}

	private void modificarSesion(Long id, BufferedReader in) throws IOException {		
		Optional<Sesion> sesionOpt=sesionRepo.findById(id);
		if(sesionOpt.isPresent()) {
			Sesion sesion=sesionOpt.get();
			leerSesion(sesion,in);
			sesionRepo.save(sesion);
			System.out.println("Sesion actualizado con éxito!");
			printOrganos();
		}else
			System.out.println("Sesion con Id '"+id+" no encontrada.");
	}

	
	
	private void leerSesion(Sesion s, BufferedReader in) throws IOException {
		System.out.print("Indique la fecha de la sesión:");
		if(s.getFecha()!=null)
			System.out.println("    -Valor actual: "+s.getFecha());		
		String sfecha=in.readLine();
		long ldate=Date.parse(sfecha);
		Date date=new Date(ldate);
		s.setFecha(date);
		
		System.out.println("Indique el número de la sesión:");
		if(s.getNumero()!=null)
			System.out.println("   - Valor actual: "+s.getNumero() );
		String snumero=in.readLine();
		Long numero=Long.parseLong(snumero);
		s.setNumero(numero);
		
		System.out.println("Indique la legislatura de la sesión:");
		if(s.getLegislatura()!=null)
			System.out.println("   - Valor actual: "+s.getLegislatura());
		String slegislatura=in.readLine();
		Long legislatura=Long.parseLong(slegislatura);
		s.setLegislatura(legislatura);
		
		System.out.println("Indique la hora de inicio de la sesión:");
		if(s.getHoraInicio()!=null)
			System.out.println("    - Valor actual: "+s.getHoraInicio());
		String shoraInicio=in.readLine();
		Timestamp horaInicio=Timestamp.valueOf(shoraInicio);
		s.setHoraInicio(horaInicio);
		
		System.out.println("Indique la hora de fin de la sesión:");
		if(s.getHoraFin()!=null)
			System.out.println("    - Valor actual: "+s.getHoraFin());
		String shoraFin=in.readLine();
		Timestamp horaFin=Timestamp.valueOf(shoraFin);
		s.setHoraFin(horaFin);

		System.out.println("Indique el Id de la sala donde se realiza la  sesión:");
		if(s.getSala()!=null)
			System.out.println("    - Valor actual: "+s.getSala().getId()+" ("+s.getSala().getDescripcion()+")");
		printSalas();
		String salaId=in.readLine();
		Long idSala=Long.parseLong(salaId);
		Optional<Sala> sala=salaRepo.findById(idSala);
		if(sala.isPresent())
			s.setSala(sala.get());
		
		System.out.println("Indique el Id del órgano que realiza la  sesión:");
		if(s.getOrgano()!=null)
			System.out.println("    - Valor actual: "+s.getOrgano().getId()+" ("+s.getOrgano().getDescripcion()+")");
		printOrganos();
		String organoId=in.readLine();
		Long idOrgano=Long.parseLong(organoId);
		Optional<Organo> organo=organoRepo.findById(idOrgano);
		if(organo.isPresent())
			s.setOrgano(organo.get());

		
		System.out.println("Indique el Id del tipo de sesion de la  sesión:");
		if(s.getTipoSesion()!=null)
			System.out.println("    - Valor actual: "+s.getTipoSesion().getId()+" ("+s.getTipoSesion().getDescripcion()+")");
		printTiposSesion();
		String tiposesionId=in.readLine();
		Long idTipoSesion=Long.parseLong(tiposesionId);
		Optional<TipoSesion> ts=tipoSesionRepo.findById(idTipoSesion);
		if(ts.isPresent())
			s.setTipoSesion(ts.get());
		
	}

	private void insertarOrgano(BufferedReader in) throws IOException {
		
		Organo newOrgano=new Organo();
		leerOrgano(newOrgano, in);
		organoRepo.save(newOrgano);
		
	}

	private void printOrganos(BufferedReader in) throws IOException{
		printOrganos();
		System.out.println("Indique el id del organo a modificar o ENTER para volver.");
		String sid;		
			sid = in.readLine();
			if(!"".equals(sid)) {
				Long id=Long.parseLong(sid);
				if(id!=null)
					modificarOrgano(id,in);
			}							
		
	}

	private void modificarOrgano(Long id, BufferedReader in) throws IOException {
		Optional<Organo> organoOpt=organoRepo.findById(id);
		if(organoOpt.isPresent()) {
			Organo organo=organoOpt.get();
			leerOrgano(organo,in);
			organoRepo.save(organo);
			System.out.println("Organo actualizado con éxito!");
			printOrganos();
		}else		
			System.out.println("Organo con Id '"+id+" no encontrado.");
		
	}
	
	private void leerOrgano(Organo organo, BufferedReader in) throws IOException {
		System.out.println("Indique la nueva descripción:");
		if(organo.getDescripcion()!=null)
			System.out.print(organo.getDescripcion());
		String descripcion=in.readLine();
		organo.setDescripcion(descripcion);
		System.out.println("Indique la nueva abreviatura");
		if(organo.getAbreaviatura()!=null)
			System.out.print(organo.getAbreaviatura());
		String abreviatura=in.readLine();
		organo.setAbreaviatura(abreviatura);
		System.out.println("Indique el nuevo orden:");
		if(organo.getOrden()!=null)
			System.out.print(organo.getOrden());
		String orden=in.readLine();
		organo.setOrden(Long.parseLong(orden));
		
	}

	private void borrarOrgano(BufferedReader in) {
		Long id;
		try {
			id = Long.parseLong(in.readLine());
			organoRepo.deleteById(id);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}

	private void initializeTipoSesion() throws Exception {
		Iterable<TipoSesion> tiposDeSesion = tipoSesionRepo.findAll();
		Iterator<TipoSesion> iterador = tiposDeSesion.iterator();
		if (!iterador.hasNext()) {
			// No existen tipos de sesión, vamos a crearlos:
			System.out.println("No hay tipos de sesión!, vamos a incializarlos...");
			String[] tipos = { "Legislativa", "Informativa, de control y de adopción de acuerdos", "Mesa de Comisión",
					"Ponencia Legislativa", "Institucional", "Visita" };
			TipoSesion ts = null;
			for (String tipo : tipos) {
				ts = new TipoSesion();
				ts.setDescripcion(tipo);
				ts.setActivo("1");
				tipoSesionRepo.save(ts);
			}
			System.out.println("Done!");

		}
	}

	private void printTiposSesion() {
		printTiposSesion(tipoSesionRepo.findAll());
	}

	private void printTiposSesion(Iterable<TipoSesion> tiposDeSesion) {
		System.out.println("Tipos de sesión en la BD:");
		for (TipoSesion ts : tiposDeSesion) {
			System.out.println(ts.getId() + " - " + ts.getDescripcion() + ", activo:" + ts.getActivo());
		}
	}

	private void initializeOrganos() throws Exception {
		Iterable<Organo> organoIter = organoRepo.findAll();
		Iterator<Organo> iterador = organoIter.iterator();
		if (!iterador.hasNext()) {
			// No existen organos, vamos a crearlos:
			System.out.println("No hay organos!, vamos a incializarlos...");
			String[][] organos = { 
					{ "MESA", "Mesa del Parlamento", "1" }, { "JP", "Junta de Portavoces", "2" },
					{ "PPA", "Pleno del Parlamento", "3" }, { "DIPPER", "Diputación Permamente", "4" },
					{ "GPS", "G.P. Socialista", "1" },
					{ "IULV-CA", "G.P. Izquierda Unida Los Verdes-Convocatoria por Andalucía", "5" },
					{ "GPP", "G.P. Popular Andaluz", "2" }, { "GPPD", "G.P. Podemos Andalucía", "3" },
					{ "GPC", "G.P. Ciudadanos", "4" } };
			Organo organo = null;
			for (int i = 0; i < organos.length; i++) {
				organo = new Organo();
				organo.setAbreaviatura(organos[i][0]);
				organo.setDescripcion(organos[i][1]);
				organo.setOrden(Long.valueOf(organos[i][2]));
				organoRepo.save(organo);
			}
			System.out.println("Done!");
			String[] args = {};
			run(args);
		}
	}

	public void printOrganos() {
		printOrganos(organoRepo.findAll());
	}

	public void printOrganos(Iterable<Organo> organoIter) {
		System.out.println("Organos en la BD:");
		for (Organo ts : organoIter) {
			System.out.println(ts.getId() + " - " + ts.getDescripcion() + ", orden:" + ts.getOrden());
			if(!ts.getMiembros().isEmpty()) {
				System.out.println("  \\-> Miembros: ");
				for(Parlamentario miembro:ts.getMiembros()) {
					System.out.println("    - "+miembro.getNombre());
				}
			}
		}
	}

}
