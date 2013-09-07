/**
 * 
 */
package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Date;
import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * @author Sole
 *
 */
public class GrupoTablePopulator extends TablePopulator {
	
	public void populateGrupo1() {
		Grupo grupo = createGrupoInscripto("grupo1", "Grupo 1", "gruposisop@gmail.com", "grupo1", "pablo");		
		save(grupo);		
		createAndSaveAlumno("Juan", "Perez", "raron@hotmail.com", "113.349-5", grupo);
		createAndSaveAlumno("Daniel", "Gomez", "ddd@hotmail.com", "113.849-5", grupo);
		createAndSaveAlumno("Jose", "Duarte", "adddd@hotmail.com", "123.849-5", grupo);
		createAndSaveAlumno("Jesica", "Santiñez", "jsantiñez@hotmail.com", "112.849-5", grupo);
		createAndSaveAlumno("Jorge", "Ludivico", "jludivico@hotmail.com", "123.749-5", grupo);
	}
		
	public void populateGrupo2() {
		Grupo grupo = createGrupoRegistrado("grupo2", "Grupo 2", "gruposisop2@gmail.com", "grupo2", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Ana", "Ventosa", "anav@hotmail.com", "110.849-1", grupo);
		createAndSaveAlumno("Cristina", "Delins", "ojoscafe_0@hotmail.com", "112.833-6", grupo);				
		createAndSaveAlumno("Carlos", "Castillo", "carlos_castillo@hotmail.com", "122.833-6", grupo);
		createAndSaveAlumno("Gustavo", "Cerati", "gcerati@hotmail.com", "192.833-6", grupo);				
		createAndSaveAlumno("Manuel", "Ñuñez", "mnunez@hotmail.com", "182.833-6", grupo);
	}

	public void populateGrupo3() {		
		Grupo grupo = createGrupoRegistrado("grupo3", "Grupo 3", "grupo3@hotmail.com", "grupo3", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Carolina", "Suarez", "carosuarez@hotmail.com", "113.849-1", grupo);
		createAndSaveAlumno("Pepe", "Soros", "soros@hotmail.com", "112.833-8", grupo);				
		createAndSaveAlumno("Juan", "Lopez", "juan_lopez@hotmail.com", "172.833-6", grupo);
		createAndSaveAlumno("Luciana", "Magnum", "lmagnum@hotmail.com", "112.873-8", grupo);				
		createAndSaveAlumno("Juan", "Sanchez", "jsanchez@hotmail.com", "172.893-6", grupo);
	}
	
	public void populateGrupo4() {		
		Grupo grupo = createGrupoRegistrado("grupo4", "Grupo 4", "grupo4@hotmail.com", "grupo4", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Gonzalo", "Tiro", "gtiro@hotmail.com", "112.849-1", grupo);
		createAndSaveAlumno("Fernanda", "Maciel", "fmaciel@hotmail.com", "192.833-8", grupo);				
		createAndSaveAlumno("Alberto", "Estebez", "aetebez@hotmail.com", "179.833-6", grupo);
		createAndSaveAlumno("Marcos", "Ivañez", "mivanez@hotmail.com", "102.873-8", grupo);				
		createAndSaveAlumno("Daniel", "Huerta", "dhuerta@hotmail.com", "102.893-6", grupo);
	}

	public void populateGrupo5() {		
		Grupo grupo = createGrupoRegistrado("grupo5", "Grupo 5", "grupo5@hotmail.com", "grupo5", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Guillermo", "Fitriani", "gfitriani@hotmail.com", "112.849-1", grupo);
		createAndSaveAlumno("Carlos", "Gutierrez", "cgutierrez@hotmail.com", "182.833-8", grupo);
	}

	public void populateGrupo6() {
		Grupo grupo = createGrupoRegistrado("grupo6", "Grupo 6", "grupo6@gmail.com", "grupo6", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Jose", "Perez", "jperez@hotmail.com", "115.679-4", grupo);	
		createAndSaveAlumno("Maria", "Hernanadez", "mhernanadez@hotmail.com", "173.849-5", grupo);
		createAndSaveAlumno("Juan", "Gomez", "jgomez@hotmail.com", "110.129-1", grupo);
	}

	public void populateGrupo7() {
		Grupo grupo = createGrupoRegistrado("grupo7", "Grupo 7", "grupo7@gmail.com", "grupo7", EstadoGrupo.REGISTRADO);
		save(grupo);
		createAndSaveAlumno("Rodrigo", "Perez", "rperez@hotmail.com", "115.699-4", grupo);
		createAndSaveAlumno("German", "Hernanadez", "ghernanadez@hotmail.com", "173.846-5", grupo);
		createAndSaveAlumno("Pepe", "Gomez", "pgomez@hotmail.com", "170.829-1", grupo);
		createAndSaveAlumno("Cecilia", "Dominguez", "cdominguez@hotmail.com", "173.806-5", grupo);
		createAndSaveAlumno("Silvina", "Meputsky", "smeputsky@hotmail.com", "170.899-1", grupo);
	}

	public void populateGrupo8() {		
		Grupo grupo = createGrupoRegistrado("grupo8", "Grupo 8", "grupo8@hotmail.com", "grupo8", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Carlos", "Gutierrez", "cgutierrez@hotmail.com", "182.2	33-8", grupo);
		createAndSaveAlumno("Betina", "Perez", "bperez@hotmail.com", "182.933-8", grupo);
	}

	public void populateGrupo9() {		
		Grupo grupo = createGrupoRegistrado("grupo9", "Grupo 9", "grupo9@hotmail.com", "grupo9", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Monica", "Sanchez", "msanchez@hotmail.com", "185.699-4", grupo);
		createAndSaveAlumno("Ricardo", "Smith", "rmisth@hotmail.com", "132.846-5", grupo);
		createAndSaveAlumno("Pablo", "Santiaguez", "pgomez@hotmail.com", "170.829-9", grupo);
		createAndSaveAlumno("Julio", "Cofer", "jcofer@hotmail.com", "189.846-5", grupo);
	}
	
	public void populateGrupo10() {		
		Grupo grupo = createGrupoRegistrado("grupo10", "Grupo 10", "grupo10@hotmail.com", "grupo10", EstadoGrupo.REGISTRADO);
		save(grupo);
		createAndSaveAlumno("Maximiliano", "Notagno", "mnotagno@hotmail.com", "182.323-8", grupo);
		createAndSaveAlumno("Ruben", "Elite", "relite@hotmail.com", "182.968-9", grupo);
		createAndSaveAlumno("Graciela", "Polo", "gpolo@hotmail.com", "182.723-8", grupo);
		createAndSaveAlumno("Pedro", "Jalieti", "pjalieti@hotmail.com", "182.968-8", grupo);
		createAndSaveAlumno("Nicolas", "Kloper", "nkloper@hotmail.com", "182.223-8", grupo);		
	}

	public void populateGrupo11() {		
		Grupo grupo = createGrupoRegistrado("grupo11", "Grupo 11", "grupo11@hotmail.com", "grupo11", EstadoGrupo.CONFIRMADO_MANUAL);
		save(grupo);
		createAndSaveAlumno("Maximiliano", "Notagno", "mnotagno@hotmail.com", "182.653-8", grupo);
		createAndSaveAlumno("Gustavo", "Elite", "relite@hotmail.com", "182.943-8", grupo);
		createAndSaveAlumno("Pablo", "Munchis", "pmunchis@hotmail.com", "122.653-8", grupo);
		createAndSaveAlumno("Marcelo", "Clarin", "mclarin@hotmail.com", "172.943-8", grupo);
		createAndSaveAlumno("Cesar", "Estebez", "cestebez@hotmail.com", "152.653-8", grupo);
	}
	
	public void populateGrupo12() {		
		Grupo grupo = createGrupoRegistrado("grupo12", "Grupo 12", "grupo12@hotmail.com", "grupo12", EstadoGrupo.REGISTRADO);
		save(grupo);
		createAndSaveAlumno("Silvia", "Suarez", "ssuarez@hotmail.com", "156.323-8", grupo);
		createAndSaveAlumno("Jose", "Paz", "jpaz@hotmail.com", "182.943-8", grupo);
		createAndSaveAlumno("Raquel", "Lampone", "rlampone@hotmail.com", "156.323-0", grupo);
		createAndSaveAlumno("Carlos", "Garcia", "cgarcia@hotmail.com", "182.933-8", grupo);
		createAndSaveAlumno("John", "Smith", "jsmith@hotmail.com", "146.323-8", grupo);

	}

	private Grupo createGrupoRegistrado(String loginName, String nombre, String mail, String encryptedPass, Long estado) {
		Grupo grupo = new Grupo();		
		grupo.setLoginName(loginName);
		grupo.setNombre(nombre);
		grupo.setEmail(mail);
		grupo.setPassword(encryptedPass);
		grupo.setEstado(estado);
		grupo.setFechaAlta(new Date());
		
		Set<Rol> treeSet = CollectionFactory.createTreeSet(Rol.class);
		treeSet.add(Rol.findMeByDomainCode(Rol.ROL_GRUPO_REGISTRADO));
		grupo.setRoles(treeSet);
		
		return grupo;
	}
	
	private Grupo createGrupoInscripto(String loginName, String nombre, String mail, String encryptedPass, String loginNameAyudante) {
		Grupo grupo = new Grupo();		
		grupo.setLoginName(loginName);
		grupo.setNombre(nombre);
		grupo.setEmail(mail);
		grupo.setPassword(encryptedPass);
		grupo.setEstado(EstadoGrupo.INSCRIPTO);
		grupo.setFechaAlta(new Date());
		grupo.setAyudante(Ayudante.findMeByLoginName(loginNameAyudante));
		
		Set<Rol> treeSet = CollectionFactory.createTreeSet(Rol.class);
		treeSet.add(Rol.findMeByDomainCode(Rol.ROL_GRUPO_INSCRIPTO));
		grupo.setRoles(treeSet);
		
		return grupo;
	}

	private void createAndSaveAlumno(String nombre, String apellido, String email, String legajo, Grupo grupo){
		Alumno alumno = new Alumno();
		alumno.setNombre(nombre);
		alumno.setApellido(apellido);
		alumno.setEmail(email);
		alumno.setLegajo(legajo);
		alumno.setGrupo(grupo);
		alumno.save();
	}

}
