package ar.com.grupo306.sisopadmin.web.usecases.domain.roles;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

import javax.swing.text.StyledEditorKit.BoldAction;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU de ABM de integrantes de la Cátedra.
 * 
 * @author Damian
 */

public class ABMRolesUseCaseModel extends ABMBaseUseCaseModel {
	private String nombre;
	private String nombreOriginal="";
	private int entradas=0;
	private String descripcion;
	private Collection<Permiso> permisos = CollectionFactory.createCollection(Permiso.class);	
	private Collection<Permiso> permisosDisponibles = CollectionFactory
			.createCollection(Permiso.class);
	private String[] permisosDelRol = null;
	private String[] permisosDelRolDisponibles = null;
	private Collection<Usuario> usuarios = CollectionFactory
			.createCollection(Usuario.class);
	private Collection<Usuario> usuariosDisponibles = CollectionFactory
			.createCollection(Usuario.class);
	private String[] usuariosDelRol = null;
	private String[] usuariosDelRolDisponibles = null;
	private Long id = 0L;
	private Rol rolSelected = null;
	private String tam;
	private boolean deSistema;

	@Secure( {})
	@Description("ABM Roles de la Cátedra")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		// Cargo la lista de permisos disponibles
		super.initUseCase(context);
		rolSelected = (Rol) context.getParameter("rolSelected");

		permisosDisponibles = Permiso.findLike("nombre", "");
		usuariosDisponibles = Usuario.findAll();
		if (rolSelected != null) {
			rolSelected = (Rol) Rol.findMeById(rolSelected.getId()).iterator()
					.next();
			populame(rolSelected);
		}
		permisosDisponibles.removeAll(permisos);
		usuariosDisponibles.removeAll(usuarios);
		
		if(entradas==0 && nombre!=null){
			nombreOriginal=nombre;
		}
		entradas++;

	}

	/**
	 * Me popula con los parametros
	 * 
	 * @param rol
	 */
	private void populame(Rol rol) {
		descripcion = rol.getDescripcion();
		nombre = rol.getNombre();
		/*permisos = (Collection<Permiso>) rol.getPermisos();*/
		permisos = (Collection<Permiso>) ordenarPermisos(rol.getPermisos());
		usuarios = (Collection<Usuario>) ordenarUsuarios(rol.getUsuarios());
		deSistema = rol.getDeSistema();
	}

	/**
	 * Se encarga de realizar el ALTA de un Rol
	 * 
	 * @param context
	 * 
	 */
	@Transactional
	public void altaRolCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}

		Rol nuevoRolCatedra = new Rol();
		UserContext userContext = (UserContext) context.getUserContext();
		if (!userContext.isUserLogged()) {
			throw new BusinessException(
					"No se puede crear un rol ya que tiene que estar logueado en el sistema");
		}

		// Obtengo los permisos seleccionados -> permisosDelRol
		permisos.clear();
		permisos.addAll(buscarPermisosSeleccionados(context, "permisosDelRol"));
		nuevoRolCatedra.addPermisos(permisos);

		// Obtengo los usuarios seleccionados -> usuariosDelRol
		usuarios.clear();
		usuarios.addAll(buscarUsuariosSeleccionados(context, "usuariosDelRol"));
		nuevoRolCatedra.addUsuarios(usuarios);

		nuevoRolCatedra.setNombre(nombre);
		nuevoRolCatedra.setDescripcion(descripcion);
		nuevoRolCatedra.save();

		context.addMessage("Rol de la Cátedra creado exitosamente.");
		context.acceptUseCase();
	}

	/**
	 * Se encarga de realizar la BAJA de un Rol de la Cátedra
	 * 
	 * @param context
	 * 
	 */
	@Transactional
	public void bajaRolCatedra(UseCaseContext context) {
		UserContext userContext = (UserContext) context.getUserContext();
		if (!userContext.isUserLogged()) {
			throw new BusinessException(
					"No se puede eliminar el rol ya que tiene que estar logueado en el sistema");
		}

		rolSelected = (Rol) context.getParameter("rolSelected");
		rolSelected = (Rol) Rol.findMeById(rolSelected.getId()).iterator()
				.next();

		rolSelected.remove();

		context.addMessage("Rol de la Cátedra eliminado exitosamente.");
		context.acceptUseCase();
	}

	/**
	 * Se encarga de realizar la MODIFICACION de un Rol de la Cátedra
	 * 
	 * @param context
	 * 
	 */
	@Transactional
	public void modificacionRolCatedra(UseCaseContext context) {
		// permisos
		permisos.clear();
		permisos.addAll(buscarPermisosSeleccionados(context, "permisosDelRol"));
		permisosDisponibles.clear();
		permisosDisponibles.addAll(buscarPermisosSeleccionados(context,
				"permisosDelRolDisponibles"));

		// usuarios
		usuarios.clear();
		usuarios.addAll(buscarUsuariosSeleccionados(context, "usuariosDelRol"));
		usuariosDisponibles.clear();
		usuariosDisponibles.addAll(buscarUsuariosSeleccionados(context,
				"usuariosDelRolDisponibles"));

		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}

		UserContext userContext = (UserContext) context.getUserContext();
		if (!userContext.isUserLogged()) {
			throw new BusinessException(
					"No se puede modificar el rol ya que tiene que estar logueado en el sistema");
		}

		rolSelected = (Rol) context.getParameter("rolSelected");
		rolSelected = (Rol) Rol.findMeById(rolSelected.getId()).iterator()
				.next();
		rolSelected.setNombre(nombre);
		rolSelected.setDescripcion(descripcion);

		rolSelected.removePermisos(permisosDisponibles);
		rolSelected.addPermisos(permisos);

		rolSelected.removeUsuarios(usuariosDisponibles);
		rolSelected.addUsuarios(usuarios);
		rolSelected.saveOrUpdate();

		context.addMessage("Rol de la Cátedra modificado exitosamente.");
		context.acceptUseCase();
	}

	public void cancelarRolCatedra(UseCaseContext context) {
		context.cancelUseCase();
	}

	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertNotEmpty(nombre,
				"Debe ingresar un nombre para el rol.");
		//Si cambia el nombre, validar que se pueda asignar
		if(!nombreOriginal.equalsIgnoreCase(nombre)){
			validationUtils.assertTrue(Rol.findMeBy("nombre", nombre)==0,"El nombre del rol ya existe en el sistema.");
		}
		validationUtils.assertNotEmpty(descripcion,
				"Debe ingresar una descripción para el rol.");
		validationUtils.assertTextoSeguro(nombre, " El nombre contiene caracteres no admitidos por el sistema.");
		validationUtils.assertTextoSeguro(descripcion, " La descripción contiene caracteres no admitidos por el sistema.");
		return !validationUtils.hasErrors();
	}

	@Transactional
	private Collection<Permiso> buscarPermisosSeleccionados(
			UseCaseContext context, String param) {
		Collection<Permiso> permisos = CollectionFactory
				.createCollection(Permiso.class);
		String[] permisosSelected = null;

		try {
			permisosSelected = context.getRequestParameterValues(param);
		} catch (Exception e) {
		}

		if (permisosSelected != null) {
			for (int i = 0; i < permisosSelected.length; i++) {
				permisos.addAll(Permiso.findMeById(Long
						.valueOf(permisosSelected[i])));
			}
		}
		return permisos;
	}

	@Transactional
	private Collection<Usuario> buscarUsuariosSeleccionados(
			UseCaseContext context, String param) {
		Collection<Usuario> usuarios = CollectionFactory
				.createCollection(Usuario.class);
		String[] usuariosSelected = null;

		try {
			usuariosSelected = context.getRequestParameterValues(param);
		} catch (Exception e) {
		}

		if (usuariosSelected != null) {
			for (int i = 0; i < usuariosSelected.length; i++) {
				usuarios.add(Usuario.findMeById(Long
						.valueOf(usuariosSelected[i])));
			}
		}
		return usuarios;
	}

	//Ordenar Permisos
	private Collection ordenarPermisos(Collection colAOrdenar){
		//Para que los permisos aparezcan ordenados
		SortedSet permisosOrdenados = CollectionFactory.createTreeSet(new Comparator() {	
											public int compare(Object o1, Object o2) {
												return((Permiso)o1).getDescription().compareTo(((Permiso)o2).getDescription());				
											}		
										});
		if(colAOrdenar!=null){
		permisosOrdenados.addAll(colAOrdenar);
		}
		return permisosOrdenados;
	}
	

	//Ordenar Usuarios
	private Collection ordenarUsuarios(Collection colAOrdenar){
		//Para que los permisos aparezcan ordenados
		SortedSet usuariosOrdenados = CollectionFactory.createTreeSet(new Comparator() {	
											public int compare(Object o1, Object o2) {
												return((Usuario)o1).getLoginName().compareTo(((Usuario)o2).getLoginName());				
											}		
										});
		if(colAOrdenar!=null){
		usuariosOrdenados.addAll(colAOrdenar);
		}
		return usuariosOrdenados;
	}
	
	// GETTER AND SETTERS

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Collection<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Collection<Permiso> permisos) {
		this.permisos = (SortedSet) permisos;
	}

	public Rol getRolSelected() {
		return rolSelected;
	}

	public void setRolSelected(Rol rolSelected) {
		this.rolSelected = rolSelected;
	}

	public Collection<Permiso> getPermisosDisponibles() {
		return permisosDisponibles;
	}

	public void setPermisosDisponibles(Collection<Permiso> permisosDisponibles) {
		this.permisosDisponibles = permisosDisponibles;
	}

	public String getTam() {
		return tam;
	}

	public void setTam(String tam) {
		this.tam = tam;
	}

	public String[] getPermisosDelRol() {
		return permisosDelRol;
	}

	public void setPermisosDelRol(String[] permisosDelRol) {
		this.permisosDelRol = permisosDelRol;
	}

	public String[] getPermisosDelRolDisponibles() {
		return permisosDelRolDisponibles;
	}

	public void setPermisosDelRolDisponibles(String[] permisosDelRolDisponibles) {
		this.permisosDelRolDisponibles = permisosDelRolDisponibles;
	}

	public Collection<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Collection<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String[] getUsuariosDelRol() {
		return usuariosDelRol;
	}

	public void setUsuariosDelRol(String[] usuariosDelRol) {
		this.usuariosDelRol = usuariosDelRol;
	}

	public String[] getUsuariosDelRolDisponibles() {
		return usuariosDelRolDisponibles;
	}

	public void setUsuariosDelRolDisponibles(String[] usuariosDelRolDisponibles) {
		this.usuariosDelRolDisponibles = usuariosDelRolDisponibles;
	}

	public Collection<Usuario> getUsuariosDisponibles() {
		return usuariosDisponibles;
	}

	public void setUsuariosDisponibles(Collection<Usuario> usuariosDisponibles) {
		this.usuariosDisponibles = usuariosDisponibles;
	}

	public int getEntradas() {
		return entradas;
	}

	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}
}
