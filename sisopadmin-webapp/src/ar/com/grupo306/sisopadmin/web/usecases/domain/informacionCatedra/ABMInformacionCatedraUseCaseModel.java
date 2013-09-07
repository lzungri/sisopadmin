package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;

import java.util.Date;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
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

public class ABMInformacionCatedraUseCaseModel extends ABMBaseUseCaseModel{
	private String contenido;
	private String descripcion;
	private Long estado;
	private String nombre;
	private Date fechaFin;
	private Date fechaInicio;
	private Long id=0L;
	private String nombreOriginal="";
	private int entradas=0;
	private Informacion informacionSelected=null;
	
	@Secure ({})
	@Description ("ABM Información de la Cátedra")
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		informacionSelected = (Informacion) context.getParameter("informacionSelected");
		if(informacionSelected!=null){
			populame(informacionSelected);
		}
		if(entradas==0 && nombre!=null){
			nombreOriginal=nombre;
		}
		entradas++;
	}
	
	/**
	 * Me popula con los parametros =P 
	 * @param informacion
	 */
	private void populame(Informacion informacion){
		contenido = informacion.getContenido();
		descripcion = informacion.getDescripcion();
		estado = informacion.getEstado();
		nombre = informacion.getNombre();
		fechaFin = informacion.getFechaFin();
		fechaInicio = informacion.getFechaInicio();
	}
	
	/**
	 * Se encarga de realizar el ALTA de una información de la Cátedra
	 * @param context
	 * 
	 */
	@Transactional
	public void altaInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		
		Informacion nuevoInformacionCatedra= new Informacion();
		Usuario usuario;
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
		}
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
		//Cuando esté arreglado el mapeo
		nuevoInformacionCatedra.setNombre(nombre);
		nuevoInformacionCatedra.setContenido(contenido);
		nuevoInformacionCatedra.setDescripcion(descripcion);
		nuevoInformacionCatedra.setEstado(estado);
		nuevoInformacionCatedra.setFechaFin(fechaFin);
		nuevoInformacionCatedra.setFechaInicio(fechaInicio);
		nuevoInformacionCatedra.setUsuarioCreador(usuario);
		nuevoInformacionCatedra.save();

		context.addMessage("Información de la Cátedra creada exitosamente.");
		context.acceptUseCase();
	}
	
	//bajaInformacionCatedra
	/**
	 * Se encarga de realizar la BAJA de una información de la Cátedra
	 * @param context
	 * 
	 */
	@Transactional
	public void bajaInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
		}
		//Cuando esté arreglado el mapeo
		informacionSelected = (Informacion) context.getParameter("informacionSelected");
		informacionSelected.remove();
		
		context.addMessage("Información de la Cátedra eliminada exitosamente.");
		context.acceptUseCase();			
	}
	
	//modificacionInformacionCatedra
	/**
	 * Se encarga de realizar la MODIFICACION de una información de la Cátedra
	 * @param context
	 * 
	 */
	@Transactional
	public void modificacionInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
		}
		
		Usuario usuario;
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());

		informacionSelected = (Informacion) context.getParameter("informacionSelected");
		informacionSelected.setNombre(nombre);
		informacionSelected.setContenido(contenido);
		informacionSelected.setDescripcion(descripcion);
		informacionSelected.setEstado(estado);
		informacionSelected.setFechaFin(fechaFin);
		informacionSelected.setFechaInicio(fechaInicio);
		informacionSelected.setUsuarioCreador(usuario);
		informacionSelected.saveOrUpdate();
		
		
		context.addMessage("Información de la Cátedra modificada exitosamente.");
		context.acceptUseCase();			
	}
	
	public void cancelarInformacionCatedra(UseCaseContext context){
		context.cancelUseCase();
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertNotEmpty(nombre,"Debe ingresar un nombre para la información.");
		//Si cambia el nombre, validar que se pueda asignar
		if(!nombreOriginal.equalsIgnoreCase(nombre)){
			validationUtils.assertTrue(Informacion.findMeBy("nombre", nombre)==0,"El nombre de la información ya existe en el sistema.");
		}
		validationUtils.assertNotEmpty(descripcion,"Debe ingresar una descripción para la información.");
		validationUtils.assertNotEmpty(contenido,"Debe ingresar el Contenido de la información.");
		validationUtils.assertValidTextArea(contenido, "Debe ingresar un contenido válido.");
		validationUtils.assertFechaFinValida(fechaFin,"Debe ingresar una fecha de finalización de publicación válida");
		validationUtils.assertNotNull(fechaInicio,"Debe ingresar una fecha de inicio de publicación válida");
		validationUtils.assertFechaDesdeHastaSinNull(fechaInicio, fechaFin, "Debe ingresar una fecha de inicio anterior a la fecha de fin");
		validationUtils.assertTextoSeguro(nombre, "El nombre contiene caracteres no admitidos por el sistema.");
		validationUtils.assertTextoSeguro(descripcion, "La descripción contiene caracteres no admitidos por el sistema.");
		validationUtils.assertTextoHTMLSeguro(contenido, "El contenido incluye caracteres no admitidos por el sistema.");
		return !validationUtils.hasErrors();
	}

	
	// GETTER AND SETTERS 
	
	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int getEntradas() {
		return entradas;
	}

	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}

}