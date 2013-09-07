package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Leandro
 */
public class UsuarioTablePopulator extends TablePopulator {
	
//	public void populatePepeGrilloUsuario() {
//		save(createUsuario("pepeGrillo", "Pepe Grillo", "pepe@grillo.com", "grillito", new String[] {Rol.ROL_ADMIN_ENCUESTAS, Rol.ROL_COORDINADOR, Rol.ROL_USUARIO_SISTEMA, Rol.ROL_ADMIN_RECURSOS}));
//	}
	
	public void populateNeetzelUsuario() {		
		Usuario usuario = createCoordinador("admin", "Carlos", "Neetzel", "cneetzel@mail.com", "admin", new String[] {Rol.ROL_ADMIN}, 0L);
		save(usuario);
	}
	
	public void populateSoledadUsuario() {		
		Usuario usuario = createCoordinador("soledad", "Soledad", "Merlo", "sole@mail.com", "soledad", new String[] {Rol.ROL_COORDINADOR}, 1L);
		save(usuario);
	}
	
	public void populateRafaelUsuario() {
		Usuario usuario = createAyudante("rafael", "Rafael", "Aragón", "rafa@mail.com", "rafael", new String[] {Rol.ROL_AYUDANTE }, 1L, "soledad");
		save(usuario);
	}
	
	public void populateLeandroUsuario() {
		Usuario usuario = createAyudante("leandro", "Leandro", "Zungri", "lea@mail.com", "leandro",new String[] {Rol.ROL_AYUDANTE}, 1L, "soledad");
		save(usuario);
	}
	
	public void populateDamianUsuario() {
		Usuario usuario = createAyudante("damian", "Damian", "Zammar", "dam@mail.com", "damian", new String[]{Rol.ROL_AYUDANTE}, 1L ,  "soledad");
		save(usuario);
	}
	
	public void populatePabloUsuario() {
		Usuario usuario = createAyudante("pablo", "Pablo", "Schulklapper", "pablo@mail.com", "pablo", new String[] {Rol.ROL_AYUDANTE}, 2L, "soledad");
		save(usuario);
	}
	
	private Usuario createCoordinador(String loginName, String nombre, String apellido, String mail, String encryptedPass, String[] rolDomainCode, Long maximaCantidadGrupos) {
		Coordinador usuario = new Coordinador();
		
		usuario.setLoginName(loginName);
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(mail);
		usuario.setPassword(encryptedPass);
		usuario.setMaximaCantidadGrupos(maximaCantidadGrupos);
		for(int i=0; i<rolDomainCode.length; i++ ){
			Rol rol = SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Rol.class, rolDomainCode[i]);
			if (rol != null)
				usuario.addRol(rol);			
		}
		return usuario;
	}
	
	private Usuario createAyudante(String loginName, String nombre, String apellido, String mail, String encryptedPass, String[] rolDomainCode, Long maximaCantidadGrupos,String loginNameCoordinador) {
		Ayudante usuario = new Ayudante();
		
		usuario.setLoginName(loginName);
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(mail);
		usuario.setPassword(encryptedPass);
		usuario.setMaximaCantidadGrupos(maximaCantidadGrupos);
		for(int i=0; i<rolDomainCode.length; i++ ){
			Rol rol = SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Rol.class, rolDomainCode[i]);
			if (rol != null)
				usuario.addRol(rol);			
		}
		usuario.setCoordinador((Coordinador)Usuario.findMeByLoginName(loginNameCoordinador));
		return usuario;
	}	
}