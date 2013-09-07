package ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes;


import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators.ModelObjectComparator;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors.EncuestaParaAyudanteFilter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.functors.EncuestadaFilter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.RolDomainCodes;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU de ABM de integrantes de la Cátedra.
 *
 * @author Sole
 */

public class ABMIntegranteUseCaseModel extends ABMBaseUseCaseModel{
	private String nombre = "";
	private String apellido = "";
	private String tipoIntegrante= "";
	private String correoElectronico = "";
	private String nombreUsuario = "";
	private String password = " ";
	private String passwordBis = " ";
	private Date fechaIngresoCatedra = null;
	private Long cantidadGrupos = 0l;
	private Boolean notificacionesEmail = false;
	private Boolean resumenConsultas = false;
	private Date fechaAlta = null;	
	Ayudante ayu;
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		ayu = (Ayudante) modelObject;
		this.setNombre(ayu.getNombre());
		this.setApellido(ayu.getApellido());
		this.setCorreoElectronico(ayu.getEmail());
		this.setNombreUsuario(ayu.getLoginName());
		this.setPassword(ayu.getPassword());
		this.setPasswordBis(ayu.getPassword());
		this.setCantidadGrupos(ayu.getMaximaCantidadGrupos());			
		this.setNotificacionesEmail(ayu.getNotificacionesPorMail());
		this.setResumenConsultas(ayu.getResumenDeConsultas());	
		this.setFechaIngresoCatedra(ayu.getFechaAltaCatedra());
		this.setFechaAlta(ayu.getFechaAlta());	
		this.setTipoIntegrante(ayu.getTipoIntegrante());
	}
	
	/**
	 * Acepta la operación en CreateMode.
	 * Es segura para todos los modos, pero sólo se asociará el permiso de CreateMode al
	 * rol adecuado, de esta manera nunca será posible ejecutarla en los demás modos.
	 * 
	 * @param context
	 */
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la creación")
	@Transactional
	public void createAccept(UseCaseContext context) {
		if(!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		
		Ayudante unAyudante = null;
		
		if("Ayudante".equals(tipoIntegrante)){
			unAyudante = new Ayudante();
			unAyudante.addRol(Rol.findMeByDomainCode(RolDomainCodes.ROL_AYUDANTE));
		}
		else {
			unAyudante = new Coordinador();
			unAyudante.addRol(Rol.findMeByDomainCode(RolDomainCodes.ROL_COORDINADOR));
		}

		//Atributos no modificables
		unAyudante.setFechaAlta(new Date());
		unAyudante.setLoginName(nombreUsuario);
		unAyudante.setPassword(password);
		
		
		PlantillaEncuesta encuestaFinder = new PlantillaEncuesta();
		Collection encuestas = encuestaFinder.findAll(PlantillaEncuesta.class);
		CollectionUtils.filter(encuestas,new EncuestadaFilter());
		CollectionUtils.filter(encuestas,new EncuestaParaAyudanteFilter());
		Iterator iEncuestas = encuestas.iterator();
		while(iEncuestas.hasNext()){
			PlantillaEncuesta encuesta = (PlantillaEncuesta)iEncuestas.next();
			if(unAyudante.getEncuestasSinLlenar() == null)
				unAyudante.setEncuestasSinLlenar(CollectionFactory.createSet());
			unAyudante.getEncuestasSinLlenar().add(encuesta);
			if(encuesta.getUsuariosSinEncuestar() == null || encuesta.getUsuariosSinEncuestar().isEmpty())
				encuesta.setUsuariosSinEncuestar(CollectionFactory.createTreeSet(new ModelObjectComparator()));
			else{
				Set set = CollectionFactory.createTreeSet(new ModelObjectComparator());
				set.addAll(encuesta.getUsuariosSinEncuestar());
				encuesta.setUsuariosSinEncuestar(set);
			}	
			encuesta.getUsuariosSinEncuestar().add((Usuario)unAyudante);
			//encuesta.saveOrUpdate();
		}
		
		saveOrUpdateAyudante(context, unAyudante);
	}

	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la edición")
	@Transactional
	public void editAccept(UseCaseContext context) {
		if(validacionOK(new UseCaseValidationUtils(context))) {
			saveOrUpdateAyudante(context, this.ayu);
		}
	}

	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la remoción")
	@Transactional
	public void removeAccept(UseCaseContext context) {		
		Ayudante.findMeById(ayu.getId()).remove();
		context.addMessage("Integrante eliminado exitosamente.");
		context.acceptUseCase();		
	}
	
	private void saveOrUpdateAyudante(UseCaseContext context, Ayudante unAyudante) {
		unAyudante.setNombre(this.getNombre());
		unAyudante.setApellido(this.getApellido());
		unAyudante.setEmail(this.getCorreoElectronico());			
		unAyudante.setMaximaCantidadGrupos(this.getCantidadGrupos());			
//		unAyudante.setNotificacionesPorMail(this.getNotificacionesEmail());
//		unAyudante.setResumenDeConsultas(this.getResumenConsultas());	
		unAyudante.setFechaAltaCatedra(this.getFechaIngresoCatedra());
		
		unAyudante.saveOrUpdate();
		
		context.addMessage("Operación exitosa.");
		context.acceptUseCase();
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils){
		validationUtils.assertNotEmpty(nombreUsuario, "Debe ingresar el nombre de usuario");
		validationUtils.assertTrue(!nombreUsuario.contains(" "), "El nombre de usuario no debe contener espacios en blanco");
		if(getMode().equals(new CreateMode())){
			validationUtils.assertNull(Usuario.findMeByLoginName(nombreUsuario), "El nombre de usuario ya existe");			
		}		
		validationUtils.assertNotEmpty(password, "Debe ingresar una contraseña");
		if (password != null && !password.trim().equals("") )
			validationUtils.assertPasswordLength(password, "La contraseña ingresada debe tener al menos 5 caracteres");
		validationUtils.assertNotEmpty(correoElectronico, "Debe ingresar el correo electrónico");
		validationUtils.assertTrue(password.trim().equals(passwordBis), "Las contraseñas no coinciden");			
		validationUtils.assertValidMail(correoElectronico, "Debe ingresar una dirección de correo electrónico válida");
		validationUtils.assertNotNull(cantidadGrupos, "Debe ingresar una cantidad de grupos válida");
		if(cantidadGrupos != null)
			validationUtils.assertNotNegativeNumber(cantidadGrupos, "Debe ingresar una cantidad de grupos positiva o mayor que cero");

		return !validationUtils.hasErrors();
	}
		
	// GETTER AND SETTERS
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Long getCantidadGrupos() {
		return cantidadGrupos;
	}
	public void setCantidadGrupos(Long cantidadGrupo) {
		this.cantidadGrupos = cantidadGrupo;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Date getFechaIngresoCatedra() {
		return fechaIngresoCatedra;
	}
	public void setFechaIngresoCatedra(Date fechaIngresoCatedra) {
		this.fechaIngresoCatedra = fechaIngresoCatedra;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Boolean getNotificacionesEmail() {
		return notificacionesEmail;
	}
	public void setNotificacionesEmail(Boolean notificacionesEmail) {
		this.notificacionesEmail = notificacionesEmail;
	}
	public Boolean getResumenConsultas() {
		return resumenConsultas;
	}
	public void setResumenConsultas(Boolean resumenConsultas) {
		this.resumenConsultas = resumenConsultas;
	}
	public String getTipoIntegrante() {
		return tipoIntegrante;
	}
	public void setTipoIntegrante(String tipoIntegrante) {
		this.tipoIntegrante = tipoIntegrante;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordBis() {
		return passwordBis;
	}
	public void setPasswordBis(String passwordBis) {
		this.passwordBis = passwordBis;
	}	
}
