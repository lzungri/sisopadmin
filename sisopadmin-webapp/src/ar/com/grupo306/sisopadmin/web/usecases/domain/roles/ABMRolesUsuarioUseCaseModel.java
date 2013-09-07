package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU de ABM de Roles del Usuario.
 * 
 * @author Damian
 */

public class ABMRolesUsuarioUseCaseModel extends ABMBaseUseCaseModel {
	private Collection<Rol> roles = CollectionFactory.createCollection(Rol.class);
	private Collection<Rol> rolesDisponibles = CollectionFactory.createCollection(Rol.class);
	private String[] rolesDelUsuario = null;
	private String[] rolesDelUsuarioDisponibles = null;
	private Long id = 0L;
	private Usuario usuarioSelected = null;
	private int entradas=0;
	private String tam;

	@Secure( {})
	@Description("ABM Roles del Usuario")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		// Cargo la lista de permisos disponibles
		super.initUseCase(context);
		Long userId = (Long) context.getParameter("usuarioById");
		rolesDisponibles = Rol.findLike("nombre", "");
		if (userId != null) {
			usuarioSelected = (Usuario) Usuario.findMeById(userId);
			roles = ordenarRoles(usuarioSelected.getRoles());
		}
		rolesDisponibles.removeAll(roles);
		//roles = (Collection<Rol>) ordenarRoles(roles); 
		entradas++;
	}
	/*
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		usuarioSelected = (Usuario) modelObject;
		this.setRoles(usuarioSelected.getRoles());
		rolesDisponibles.removeAll(roles);
	}
*/

	/**
	 * Se encarga de realizar la MODIFICACION de los Roles del usuario
	 * 
	 * @param context
	 * 
	 */
	@Transactional
	public void modificacionRoldeUsuario(UseCaseContext context) {
		// usuarios
		roles = buscarRolesSeleccionados(context, "rolesDelUsuario");
		rolesDisponibles = buscarRolesSeleccionados(context,"rolesDelUsuarioDisponibles");

		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}

		UserContext userContext = (UserContext) context.getUserContext();
		if (!userContext.isUserLogged()) {
			throw new BusinessException(
					"No se puede modificar el rol ya que tiene que estar logueado en el sistema");
		}

		//usuarioSelected = (Usuario) context.getParameter("usuarioSelected");
		usuarioSelected = (Usuario) Usuario.findMeById(usuarioSelected.getId());
		usuarioSelected.removeRoles(rolesDisponibles);
		usuarioSelected.addRoles(roles);
		usuarioSelected.saveOrUpdate();

		context.addMessage("Roles del usuario modificados exitosamente.");
		context.acceptUseCase();
	}

	public void cancelarRoldeUsuario(UseCaseContext context) {
		context.cancelUseCase();
	}

	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		return !validationUtils.hasErrors();
	}


	@Transactional
	private Collection<Rol> buscarRolesSeleccionados(UseCaseContext context, String param) {
		Collection<Rol> roles = CollectionFactory.createCollection(Rol.class);
		String[] rolesSelected = null;

		try {
			rolesSelected = context.getRequestParameterValues(param);
		} catch (Exception e) {
		}

		if (rolesSelected != null) {
			for (int i = 0; i < rolesSelected.length; i++) {
				roles.add((Rol) Rol.findMeById(Long.valueOf(rolesSelected[i])).iterator().next());
			}
		}
		return ordenarRoles(roles);
	}


	//Ordenar Roles
	private Collection ordenarRoles(Collection colAOrdenar){
		//Para que los roles aparezcan ordenados
		SortedSet rolesOrdenados = CollectionFactory.createTreeSet(new Comparator() {	
											public int compare(Object o1, Object o2) {
												return((Rol)o1).getNombre().compareTo(((Rol)o2).getNombre());				
											}		
										});
		if(colAOrdenar!=null){
			rolesOrdenados.addAll(colAOrdenar);
		}
		return rolesOrdenados;
	}
	
	// GETTER AND SETTERS


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public int getEntradas() {
		return entradas;
	}


	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}


	public Collection<Rol> getRoles() {
		return roles;
	}


	public void setRoles(Collection<Rol> roles) {
		this.roles = roles;
	}


	public String[] getRolesDelUsuario() {
		return rolesDelUsuario;
	}


	public void setRolesDelUsuario(String[] rolesDelUsuario) {
		this.rolesDelUsuario = rolesDelUsuario;
	}


	public String[] getRolesDelUsuarioDisponibles() {
		return rolesDelUsuarioDisponibles;
	}


	public void setRolesDelUsuarioDisponibles(String[] rolesDelUsuarioDisponibles) {
		this.rolesDelUsuarioDisponibles = rolesDelUsuarioDisponibles;
	}


	public Collection<Rol> getRolesDisponibles() {
		return rolesDisponibles;
	}


	public void setRolesDisponibles(Collection<Rol> rolesDisponibles) {
		this.rolesDisponibles = rolesDisponibles;
	}


	public String getTam() {
		return tam;
	}


	public void setTam(String tam) {
		this.tam = tam;
	}


	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}


	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}


	
}
