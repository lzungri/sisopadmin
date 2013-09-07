package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.util.Set;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Leandro
 */
public class UsuarioTablePopulator extends TablePopulator {
	
	public void populatePepeGrilloUsuario() {
		save(createUsuario("pepeGrillo", "Pepe Grillo", "pepe@grillo.com", "grillito", Rol.ROL_ADMIN));
	}
	
	public void populateSoledadUsuario() {
		Usuario usuario = createCoordinador("soledad", "Soledad Merlo", "sole@mail.com", "soledad", Rol.ROL_COORDINADOR);
		save(usuario);
	}
	
	public void populateRafaelUsuario() {
		save(createUsuario("rafael", "Rafael", "", "rafael", Rol.ROL_ADMIN));
	}
	
	public void populateEncuestasUsuario() {
		save(createUsuario("encuestas", "encuestas", "", "encuestas", Rol.ROL_ADMIN_ENCUESTAS));
	}
	
	public void populateLeandroUsuario() {
		save(createUsuario("leandro", "Leandro", "", "leandro", Rol.ROL_PEDORRO));
	}
	 
	public void populateDamianUsuario() {
		save(createUsuario("damian", "Damian", "", "damian", Rol.ROL_ADMIN));
	}
	
	public void populatePabloUsuario() {
		save(createUsuario("pablo", "Pablo", "", "pablo", Rol.ROL_ADMIN));
	}
	
	private Usuario createUsuario(String loginName, String nombre, String mail, String encryptedPass, String rolDomainCode) {
		Usuario usuario = new Usuario();
		
		usuario.setLoginName(loginName);
		usuario.setEmail(mail);
		usuario.setPassword(encryptedPass);
		Set<Rol> treeSet = CollectionFactory.createTreeSet(Rol.class);
		treeSet.add(SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Rol.class, rolDomainCode));
		usuario.setRoles(treeSet);
		
		return usuario;
	}
	
	private Usuario createCoordinador(String loginName, String nombre, String mail, String encryptedPass, String rolDomainCode) {
		Coordinador usuario = new Coordinador();
		
		usuario.setLoginName(loginName);
		usuario.setEmail(mail);
		usuario.setPassword(encryptedPass);
		Set<Rol> treeSet = CollectionFactory.createTreeSet(Rol.class);
		treeSet.add(SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Rol.class, rolDomainCode));
		usuario.setRoles(treeSet);
		
		return usuario;
	}

}