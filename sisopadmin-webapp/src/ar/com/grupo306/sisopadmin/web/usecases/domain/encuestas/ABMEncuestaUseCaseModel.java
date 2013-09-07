package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.AsignadorDeEncuestas;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * UseCase que maneja el ABM de las plantillas de encuesta
 * CU 2.3.2 - Cargar plantilla de encuesta
 * CU 2.3.3 - Visualizar plantilla de encuesta
 * CU 2.3.4 - Modificar plantilla de encuesta
 * CU 2.3.5 - Eliminar plantilla de encuesta
 * CU 2.3.6 - Llenar plantilla de encuesta
 * @author Rafa
 *
 */
public class ABMEncuestaUseCaseModel extends BaseUseCaseModel {
	private String nombrePlantilla = "";
	private String fechaInicio = "";
	private String fechaFin = "";
	private boolean obligatoria = false;
	private Collection<PuntoAEncuestar> puntos= CollectionFactory.createCollection(PuntoAEncuestar.class);
	private String descripcionPunto = "";
	private String nombrePunto="";
	private Long numeroPunto = new Long(0);
	private String index;
	private String destinatario;
	
	
	
	@Secure ({})
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		
	}
	
	/**
	 * Carga la plantilla con los puntos elegidos
	 * @param context
	 */
	@Transactional
	public void cargarPlantillaEncuesta(UseCaseContext context) {
		try{
			
			Usuario usuario;
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				context.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
			}
			if (! validar(context)) return;
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
			plantilla.setNombre(nombrePlantilla);
			plantilla.setFechaAlta(DateUtils.getDateFromString(fechaInicio,SisopAdminConstants.DATE_FORMAT_PATTERN));
			plantilla.setFechaFin(DateUtils.getDateFromString(fechaFin,SisopAdminConstants.DATE_FORMAT_PATTERN));
			plantilla.setObligatoriedad(obligatoria?new Long(0):new Long(1));
			plantilla.setUsuario(usuario);
			plantilla.setPuntosAEncuestar(puntos);
			Iterator iPuntosAEncuestar = puntos.iterator();
			plantilla.setEstado(new Long(1));
			
			while(iPuntosAEncuestar.hasNext()){
				PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
				punto.setPlantillaEncuesta(plantilla);
				punto.saveOrUpdate();
			}
			plantilla.saveOrUpdate();
			AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance(destinatario);
			asignador.asignar(plantilla);
			context.addMessage("Encuesta cargada exitosamente");
			
			this.vaciarCU();
			context.refreshUseCase();
		}catch(Exception e){
			throw new BusinessException("No se pudo generar la encuesta. Consulte al administrador");
		}
	}
	
	/**
	 * Vacia el caso de uso para que se refresque el jsp listo para cargar una nueva encuesta.
	 *
	 */
	
	public void vaciarCU(){
		this.setNombrePlantilla("");
		this.setFechaInicio("");
		this.setFechaFin("");
		this.setObligatoria(false);
		puntos= CollectionFactory.createCollection(PuntoAEncuestar.class);
		this.setDescripcionPunto("");
		this.setNombrePunto("");
		this.setNumeroPunto(new Long(0));
		this.setIndex("0");
		this.setDestinatario("");
		return;
	}
	
	
	/**
	 * Recupera el punto de la encuesta y lo guarda en el cu para que en el momento de
	 * aceptar la encuesta pueda persistirlo.
	 * @param context
	 */
	public void cargarPuntoAEncuestar(UseCaseContext context){
		PuntoAEncuestar punto = new PuntoAEncuestar();
		punto.setDescripcion(descripcionPunto);
		punto.setNombre(nombrePunto);
		numeroPunto=new Long(numeroPunto.longValue() + 1);
		punto.setNumero(numeroPunto);
		punto.setPeso(new Integer(0));
		puntos.add(punto);
		context.refreshUseCase();
	}
	/**
	 * Elimina los puntos de la encuesta que existen en el CU
	 * @param context
	 */
	public void eliminarPunto(UseCaseContext context){
		/** TODO-  resecuenciar en la eliminación*/
		PuntoAEncuestar punto = (PuntoAEncuestar)CollectionUtils.find(puntos,new NumeroPuntoPredicate(index));
		puntos.remove(punto);
		context.refreshUseCase();
		
	}
	
	public void modificarPunto(UseCaseContext context){
		//debería cambiar el modo de la columna para modificar
	}
	
	/**
	 * Se ejecuta ante la acción de cancelar. Vuelve al CU anterior.
	 * @param context
	 */
	public void volver(UseCaseContext context){
		context.acceptUseCase();
	}

	
		
	/****************/
	/** PREDICATES**/
	/***************/
	
	private static class NumeroPuntoPredicate implements Predicate{
		Long numero;
		
		NumeroPuntoPredicate(String numero){
			this.numero = new Long(numero);
		}
			
		public boolean evaluate(Object arg0) {
			PuntoAEncuestar punto = (PuntoAEncuestar)arg0;
			return punto.getNumero().equals(numero);
			
		}
		
	}
	/**
	 * GETTERS-SETTERS
	 * 
	 */
	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public boolean isObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}

	public Collection getPuntos() {
		return puntos;
	}

	public void setPuntos(Collection puntos) {
		this.puntos = puntos;
	}

	public String getNombrePunto() {
		return nombrePunto;
	}

	public void setNombrePunto(String nombrePunto) {
		this.nombrePunto = nombrePunto;
	}

	public String getDescripcionPunto() {
		return descripcionPunto;
	}

	public void setDescripcionPunto(String descripcionPunto) {
		this.descripcionPunto = descripcionPunto;
	}

	public Long getNumeroPunto() {
		return numeroPunto;
	}

	public void setNumeroPunto(Long numeroPunto) {
		this.numeroPunto = numeroPunto;
	}

	
	/****************
	 * VALIDACIONES
	 */
	
	private Boolean validar(UseCaseContext context){
		Boolean valida = true;
		if("".compareToIgnoreCase(nombrePlantilla) == 0){
			context.addErrorMessage("Debe ingresar el nombre de la plantilla");
			valida = false;
		}
		if(puntos.isEmpty()){
			context.addErrorMessage("Debe ingresar puntos en la encuesta");
			valida = false;
		}
		if("".equalsIgnoreCase(fechaInicio)){
			context.addErrorMessage("Debe ingresar la fecha de inicio");
			valida = false;
		}
		if("".equalsIgnoreCase(fechaFin)){
			context.addErrorMessage("Debe ingresar la fecha de finalización");
			valida = false;
		}

		return valida;
	}
}