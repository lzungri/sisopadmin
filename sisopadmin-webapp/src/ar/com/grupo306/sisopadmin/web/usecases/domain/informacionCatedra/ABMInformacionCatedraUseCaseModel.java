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
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;

/**
 * Modelo para el CU de ABM de integrantes de la C�tedra.
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
	private Informacion informacionSelected=null;
	
	@Secure ({CreateMode.class,EditMode.class,RemoveMode.class})
	@Description ("ABM Informaci�n de la C�tedra")
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		informacionSelected = (Informacion) context.getParameter("informacionSelected");
		if(informacionSelected!=null){
			populame(informacionSelected);
		}
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
	 * Se encarga de realizar el ALTA de una informaci�n de la C�tedra
	 * @param context
	 * 
	 */
	@Secure ({CreateMode.class})
	@Description ("Efectuar el Alta de Informaci�n de la C�tedra")
	@Transactional
	public void altaInformacionCatedra(UseCaseContext context) {
		try{
			if (! validar(context)) return;
			
			Informacion nuevoInformacionCatedra= new Informacion();
			Usuario usuario;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
			}
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			//Cuando est� arreglado el mapeo
			nuevoInformacionCatedra.setNombre(nombre);
			nuevoInformacionCatedra.setContenido(contenido);
			nuevoInformacionCatedra.setDescripcion(descripcion);
			nuevoInformacionCatedra.setEstado(estado);
			nuevoInformacionCatedra.setFechaFin(fechaFin);
			nuevoInformacionCatedra.setFechaInicio(fechaInicio);
			nuevoInformacionCatedra.setUsuarioCreador(usuario);
			nuevoInformacionCatedra.save();

			context.addMessage("Informaci�n de la C�tedra creada exitosamente.");
			context.acceptUseCase();			
		}catch(Exception e){
			context.addElement("login.message", "No se pudo cargar la informaci�n de la c�tedra");
			context.refreshUseCase();
		}
	}
	
	//bajaInformacionCatedra
	/**
	 * Se encarga de realizar la BAJA de una informaci�n de la C�tedra
	 * @param context
	 * 
	 */
	@Secure ({RemoveMode.class})
	@Description ("Efectuar la Baja de Informaci�n de la C�tedra")
	@Transactional
	public void bajaInformacionCatedra(UseCaseContext context) {
		try{
			if (! validar(context)) return;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
			}
			//Cuando est� arreglado el mapeo
			informacionSelected = (Informacion) context.getParameter("informacionSelected");
			informacionSelected.remove();
			
			context.addMessage("Informaci�n de la C�tedra eliminada exitosamente.");
			context.acceptUseCase();			
		}catch(Exception e){
			context.addElement("login.message", "No se pudo cargar la informaci�n de la c�tedra");
			context.refreshUseCase();
		}
	}
	
	//modificacionInformacionCatedra
	/**
	 * Se encarga de realizar la MODIFICACION de una informaci�n de la C�tedra
	 * @param context
	 * 
	 */
	@Secure ({EditMode.class})
	@Description ("Efectuar la Modificaci�n de Informaci�n de la C�tedra")
	@Transactional
	public void modificacionInformacionCatedra(UseCaseContext context) {
		try{
			if (! validar(context)) return;
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
			//informacionSelected.setFechaFin(DateHelper.textoComoFecha(fechaFin));
			//informacionSelected.setFechaInicio(DateHelper.textoComoFecha(fechaInicio));
			informacionSelected.setFechaFin(fechaFin);
			informacionSelected.setFechaInicio(fechaInicio);
			informacionSelected.setUsuarioCreador(usuario);
			informacionSelected.saveOrUpdate();
			
			
			context.addMessage("Informaci�n de la C�tedra modificada exitosamente.");
			context.acceptUseCase();			
		}catch(Exception e){
			context.addElement("login.message", "No se pudo cargar la informaci�n de la c�tedra");
			context.refreshUseCase();
		}
	}
	
	public void cancelarInformacionCatedra(UseCaseContext context){
		context.cancelUseCase();
	}
	
	private Boolean validar(UseCaseContext context){
		Boolean valida = true;
		if(contenido.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar el Contenido de la informaci�n");
			valida = false;
		}
		if(nombre.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar el Nombre de la informaci�n");
			valida = false;
		}
		if(descripcion.compareTo("") == 0){
			context.addErrorMessage("Debe ingresar la Descripci�n de la informaci�n");
			valida = false;
		}
		
		/*TODO Validar fechas inicio, fin*/ 
		/*
		if(fechaFin==null){
			context.addErrorMessage("Debe ingresar una fecha de finalizaci�n de publicaci�n v�lida");
			valida = false;
		}
		if(fechaInicio==null){
			context.addErrorMessage("Debe ingresar una fecha de inicio de publicaci�n v�lida");
			valida = false;
		}
		*/
		return valida;
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

}
