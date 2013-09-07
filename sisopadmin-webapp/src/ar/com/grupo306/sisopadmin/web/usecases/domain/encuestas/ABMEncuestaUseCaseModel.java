package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM.ABMContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM.EstrategiaAceptacionABM;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM.EstrategiaProcesamientoFactory;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM.EstrategiaProcesamientoModelObject;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM.EstrategiasABMFactory;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;

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
public class ABMEncuestaUseCaseModel extends ABMBaseUseCaseModel {
	String OPERACION = "operacion";
	private String nombrePlantilla = "";
	private Date fechaInicio = null;
	private Date fechaFin = null;
	private boolean obligatoria = false;
	private Collection puntos= CollectionFactory.createSet();
	private Collection puntosAEliminar = CollectionFactory.createSet();
	private String descripcionPunto = "";
	private String nombrePunto="";
	private Long numeroPunto = new Long(0);
	private String index;
	private String destinatario;
	private Collection destinatarios;
	private PlantillaEncuesta encuesta;
	private boolean edicionPuntoAvailable = true;
	private PuntoAEncuestar puntoAModificar = null;
	static String ID_ENCUESTA = "idEncuesta";
	private String[] puntaje = new  String[200] ;
	private boolean hasResult = false;
	
	
	


	public boolean getHasResult() {
		return hasResult;
	}


	public void setHasResult(boolean hasResult) {
		this.hasResult = hasResult;
	}

	@Description("Inicio del abm de encuestas")
	@Secure ({EditMode.class})
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		String AYUDANTE = "AYUDANTE";
		String GRUPO = "GRUPO";
		EstrategiasABMFactory factory = EstrategiasABMFactory.getABMEncuestasInstance();
		EstrategiaAceptacionABM  estrategia = factory.getEstrategiaEncuesta(context.getParameters().get(OPERACION));
		edicionPuntoAvailable = estrategia.initializeEdicionPunto();
		ArrayList destinatarios = new ArrayList();
			this.setDestinatarios(destinatarios);
			this.getDestinatarios().add(AYUDANTE);
			this.getDestinatarios().add(GRUPO);
			if(GRUPO.equalsIgnoreCase(this.getDestinatario())){
				this.setDestinatarios(new ArrayList());
				this.getDestinatarios().add(GRUPO);
				this.getDestinatarios().add(AYUDANTE);
			}
				
		
		context.refreshUseCase();
		
	}
	
	
	public void cargarPuntaje(UseCaseContext context){
		String PUNTOS_MAP = "PUNTOS_MAP";
		String numeroPunto = this.getIndex();
		String puntajeEncuesta = this.getPuntaje()[new Integer(numeroPunto).intValue()];
		Map puntosMap=(Map)context.getParameters().get(PUNTOS_MAP);
        Set setPuntosMap = puntosMap.entrySet();
        Iterator iPuntosMap = setPuntosMap.iterator();
        while(iPuntosMap.hasNext()){
        	Entry entry = (Entry)iPuntosMap.next();
        	PuntoEncuestado puntoEncuestado = (PuntoEncuestado)puntosMap.get(entry.getKey());
        	PuntoAEncuestar puntoAEncuestar = (PuntoAEncuestar)entry.getKey();
        	if(puntoAEncuestar.getNumero().equals(new Long(numeroPunto))){
        		puntoEncuestado.setPuntuacion(new Long(puntajeEncuesta));
        		break;
        	}
        		
        }
        
        	
		
	}
	/**
	 * Procesa de acuerdo a la estrategia de operacion o de carga de puntaje de encuesta
	 */
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		EstrategiaProcesamientoFactory factory = EstrategiaProcesamientoFactory.getInstance();
		ABMContext aBMContext = new ABMContext();
		aBMContext.setContext(context);
		aBMContext.setModel(this);
		aBMContext.setModelObject(modelObject);
		EstrategiaProcesamientoModelObject estrategia = factory.getEstrategy((String)context.getParameters().get(OPERACION));
		estrategia.procesarModelObject(aBMContext);
		return;
	
	}
	
	
	
	
	/**
	 * Carga la plantilla con los puntos elegidos
	 * @param context
	 */
	@Description("Alta de una plantilla de encuestas")
	@Transactional
	@Secure({})
	public void aceptar(UseCaseContext context) {
		
		EstrategiasABMFactory factory = EstrategiasABMFactory.getABMEncuestasInstance();
		EstrategiaAceptacionABM estrategia = factory.getEstrategiaEncuesta(context.getParameters().get(OPERACION).toString());
		ABMContext contextABM = new ABMContext();
		contextABM.setContext(context);
		contextABM.setModel(this);
		estrategia.ejecutar(contextABM);
	}
	
	public void resecuenciarEncuesta(UseCaseContext context){
		Iterator iPuntos = this.getPuntos().iterator();
		Long cont = new Long(0);
		while(iPuntos.hasNext()){
			PuntoAEncuestar punto = (PuntoAEncuestar)iPuntos.next();
			punto.setNumero(cont + 1);
			cont = cont + 1;
			
		}
		if(this.getPuntos().isEmpty())
			context.addErrorMessage("No hay puntos cargados para resecuenciar");
		context.refreshUseCase();
	}
	

	
	/**
	 * Vacia el caso de uso para que se refresque el jsp listo para cargar una nueva encuesta.
	 *
	 */
	
	public void vaciarCU(){
		this.setNombrePlantilla("");
		this.setFechaInicio(null);
		this.setFechaFin(null);
		this.setObligatoria(false);
		puntos= CollectionFactory.createSet();
		this.setDescripcionPunto("");
		this.setNombrePunto("");
		this.setNumeroPunto(new Long(0));
		this.setIndex("0");
		this.setDestinatario("");
		this.puntosAEliminar = CollectionFactory.createSet();
		return;
	}
	
	
	/**
	 * Recupera el punto de la encuesta y lo guarda en el cu para que en el momento de
	 * aceptar la encuesta pueda persistirlo.
	 * @param context
	 */
	
	@Secure({OperationMode.class})
	@Description("Carga un punto a encuestar")
	@Transactional
	
	
	public void cargarPuntoAEncuestar(UseCaseContext context){
		String AYUDANTE = "AYUDANTE";
		String GRUPO = "GRUPO";
		ArrayList destinatarios = new ArrayList();
		this.setDestinatarios(destinatarios);
		this.getDestinatarios().add(AYUDANTE);
		this.getDestinatarios().add(GRUPO);
		if(GRUPO.equalsIgnoreCase(this.getDestinatario())){
			this.setDestinatarios(new ArrayList());
			this.getDestinatarios().add(GRUPO);
			this.getDestinatarios().add(AYUDANTE);
		}
		if(this.validarCargarPuntoAEncuestar(context)){
			context.refreshUseCase();
			return;
		}
			
		if(this.getPuntoAModificar()== null){
			PuntoAEncuestar punto = new PuntoAEncuestar();
			punto.setDescripcion(descripcionPunto);
			punto.setNombre(nombrePunto);
			numeroPunto=getNumeroMax(puntos);
			punto.setNumero(numeroPunto);
			punto.setPeso(new Integer(0));
			puntos.add(punto);
			this.setPuntoAModificar(null);
			
		}else{
			PuntoAEncuestar puntoModificado = this.getPuntoAModificar();
			puntoModificado.setNombre(this.nombrePunto);
			puntoModificado.setDescripcion(this.descripcionPunto);
			//puntoModificado.saveOrUpdate();
			this.setPuntoAModificar(null);
		}
		this.setNombrePunto("");
		this.setDescripcionPunto("");
		context.refreshUseCase();
	}
	
	private Long getNumeroMax(Collection puntosAEncuestar){
		Iterator iPuntosAEncuestar = puntosAEncuestar.iterator();
		Long max = null;
		while(iPuntosAEncuestar.hasNext()){
			PuntoAEncuestar puntoAEncuestar = (PuntoAEncuestar)iPuntosAEncuestar.next();
			if(max == null){
				max = puntoAEncuestar.getNumero();
			}
			if(max < puntoAEncuestar.getNumero())
				max = puntoAEncuestar.getNumero();
			
		}
		if(max == null)
			return new Long(1);
		return max + 1;
	}
	
	public boolean validarCargarPuntoAEncuestar(UseCaseContext context){
		boolean error = false;
		if(this.getNombrePunto() == null || "".equalsIgnoreCase(this.getNombrePunto())){
			context.addErrorMessage("Debe cargar el nombre del punto");
			error = true;
		}
			
		if(this.getDescripcionPunto() == null || "".equalsIgnoreCase(this.getDescripcionPunto())){
			context.addErrorMessage("Debe cargar la descripcion del punto a aplicar");
			error = true;
			
		}
		return error;
			
	}
	
	
	
	@Description("Carga un punto a encuestar")
	@Transactional
	public void cancelarCargaModificacion(UseCaseContext context){
		this.setPuntoAModificar(null);
		this.setNumeroPunto(null);
		this.setNombrePunto("");
		this.setDescripcionPunto("");
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
		puntosAEliminar.add(punto);
		
		context.refreshUseCase();
		
	}
	
	public void modificarPunto(UseCaseContext context){
		PuntoAEncuestar punto = (PuntoAEncuestar)CollectionUtils.find(puntos,new NumeroPuntoPredicate(index));
		this.setDescripcionPunto(punto.getDescripcion());
		this.setNombrePunto(punto.getNombre());
		this.setNumeroPunto(punto.getNumero());
		this.setPuntoAModificar(punto);
		context.refreshUseCase();
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

	public PlantillaEncuesta getEncuesta() {
		return encuesta;
	}

	public void setEncuesta(PlantillaEncuesta encuesta) {
		this.encuesta = encuesta;
	}

	public boolean isEdicionPuntoAvailable() {
		return edicionPuntoAvailable;
	}

	public void setEdicionPuntoAvailable(boolean edicionPuntoAvailable) {
		this.edicionPuntoAvailable = edicionPuntoAvailable;
	}

	public PuntoAEncuestar getPuntoAModificar() {
		return puntoAModificar;
	}

	public void setPuntoAModificar(PuntoAEncuestar puntoAModificar) {
		this.puntoAModificar = puntoAModificar;
	}

	public Collection getPuntosAEliminar() {
		return puntosAEliminar;
	}

	public void setPuntosAEliminar(Collection puntosAEliminar) {
		this.puntosAEliminar = puntosAEliminar;
	}

	public String[] getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(String[] puntaje) {
		this.puntaje = puntaje;
	}


	public Collection getDestinatarios() {
		return destinatarios;
	}


	public void setDestinatarios(Collection destinatarios) {
		this.destinatarios = destinatarios;
	}






	
	
	
}