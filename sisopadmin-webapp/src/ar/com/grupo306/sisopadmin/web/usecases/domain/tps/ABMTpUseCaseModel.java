/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts.upload.FormFile;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Rol;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * @author Sole
 *
 */
public class ABMTpUseCaseModel extends ABMBaseUseCaseModel {
	private Tp tp = new Tp();
	private String index;
	private Set<Fase> fasesEliminadas = CollectionFactory.createSet(Fase.class);
	private Set<Fase> fasesCreadas = CollectionFactory.createSet(Fase.class);
	private Set<Consigna> consignasCreadas = CollectionFactory.createSet(Consigna.class);
	private Set<Consigna> consignasEliminadas = CollectionFactory.createSet(Consigna.class);
	private FormFile archivoSubido;
	
	private boolean enviarNotificacion;
	
	
	@Secure ({})
	@Description ("Acceder a ABMTPs")
	@Transactional	
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);		
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {			
		this.setTp((Tp) modelObject);
		context.refreshUseCase();
	}	
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la creación de TPs")
	@Transactional
	public void createAccept(UseCaseContext context) {
		if(!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		
		Tp unTp = new Tp();	
		
		//Atributos no modificables
		unTp.setNombre(this.getTp().getNombre());		
		
		saveOrUpdateTp(context, unTp);
		
		if(enviarNotificacion) {
			// Se genera el evento notificable para el grupo.
			new EventoNotificable(DateUtils.addDays(new Date(), 5), 
					"Se ha creado el trabajo práctico '" + tp.getNombre() + "'.", 
					"." + UseCaseUtils.getUseCaseInitPath(new AdministrarTPsUseCase()))
				.addNotificable(Rol.findMeByDomainCode(Rol.ROL_AYUDANTE))
				.save();
		}
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la edición de Tps")
	@Transactional	
	public void editAccept(UseCaseContext context) {
		if(validacionOK(new UseCaseValidationUtils(context))) {
			saveOrUpdateTp(context, this.getTp());
		}
	}
	
	@Secure ({CreateMode.class, EditMode.class, ViewMode.class, RemoveMode.class})
	@Description ("Efectuar la eliminación de TPs")
	@Transactional
	public void removeAccept(UseCaseContext context) {
		this.getTp().remove();		
		context.acceptUseCase();		
	}
	
	private void saveOrUpdateTp(UseCaseContext context, Tp unTp) {
		for(Iterator it = this.getTp().getFases().iterator(); it.hasNext();){
			((Fase)it.next()).saveOrUpdate();		
		}

		if(getMode().equals(new CreateMode()) || (getMode().equals(new EditMode()) && archivoSubido.getFileSize() > 0)) {
			try{
				File archivoAGrabar;
				//Graba en disco el archivo.
				String uploadPath = SisopAdminConfig.getInstance().getProperty(Module.TP, "abmTp.uploadFiles.path");
				archivoAGrabar = new File(uploadPath + archivoSubido.getFileName());
				InputStream archivoIS = archivoSubido.getInputStream();
				OutputStream archivoOS = FileUtils.openOutputStream(archivoAGrabar);
				IOUtils.copy(archivoIS, archivoOS);
				
				archivoIS.close();
				archivoOS.close();				
				unTp.setArchivoEspecificacion(archivoSubido.getFileName());
			}
			catch(Exception excep) {
				throw ExceptionFactory.createProgramException("Error al intentar subir el archivo " + archivoSubido.getFileName(), excep);
			}		
		}
		unTp.setFases(this.getTp().getFases());
		unTp.saveOrUpdate();		
		
		for(Iterator it = this.getTp().getFases().iterator(); it.hasNext();){
			Fase fase =((Fase)it.next());
			
			//Persisto las consignas de c/fase
			for(Iterator itC = fase.getConsignas().iterator(); itC.hasNext();){
				Consigna consigna = ((Consigna)itC.next());			
				consigna.setFase(fase);				
				consigna.saveOrUpdate();
			}
			
			fase.setTp(unTp);
			fase.update();
		}	
		unTp.setFases(this.getTp().getFases());
		unTp.update();
		
		//Borro las consignas que no apuntan a ninguna fase
		for(Iterator it = consignasEliminadas.iterator(); it.hasNext();){
			Consigna consignaEliminable = (Consigna)it.next();			
			if( ! consignasCreadas.contains(consignaEliminable)) // Solo hago Remove si ya está creada en la BD
				consignaEliminable.remove();
		}
		
		//Borro las fases que no apuntan a ninguna tp
		for(Iterator it = fasesEliminadas.iterator(); it.hasNext();){
			Fase faseEliminable = (Fase)it.next(); 
			if( ! fasesCreadas.contains(faseEliminable)) // Solo hago Remove si ya está creada en la BD
				faseEliminable.remove();
		}
		context.addMessage("Operación exitosa.");
		context.acceptUseCase();
	}
	
	public void cargarFase(UseCaseContext context){
		context.goToChildUseCase(ABMFaseUseCase.class);
	}
	
	public void editFase(UseCaseContext context){
		goToABMFaseUseCase(context, new EditMode());
	}
	
	public void viewFase(UseCaseContext context){
		goToABMFaseUseCase(context, new ViewMode());
	}
	@Transactional
	public void removeFase(UseCaseContext context){
		//Controla que no tenga Entregas o EvaluacionFasePorPlantilla antes de borrar
		Boolean tieneEvaluaciones = false;
		Boolean tieneEntregas = false;
		
		Fase fase = selectFase(context);
		
		List<EvaluacionFasePorPlantilla> listaEv = EvaluacionFasePorPlantilla.findBy(null, null, fase.getId(), null, null);
		tieneEvaluaciones = listaEv != null && listaEv.size() > 0 ;
		
		List<Entrega> listaEn = Entrega.findBy(null, fase.getId()); 
		tieneEntregas = listaEn != null && listaEn.size() > 0 ;
				
		
		if(tieneEvaluaciones){
			context.addErrorMessage("No puede eliminarse la fase " + fase.getNombre() + " porque tiene asociada evaluaciones.");
		}
		else{
			if(tieneEntregas){
				context.addErrorMessage("No puede eliminarse la fase " + fase.getNombre() + " porque tiene entregas asociada.");
			}
			else{
				goToABMFaseUseCase(context, new RemoveMode());
			}			
		}		
	}
	
	private void goToABMFaseUseCase(UseCaseContext useCaseContext, UseCaseModelMode mode) {
		Map parameters = CollectionFactory.createMap();		
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, selectFase(useCaseContext));			
		useCaseContext.goToChildUseCase(ABMFaseUseCase.class, mode, parameters);						
	}

	private Fase selectFase(UseCaseContext context){
		if(context.getRequestParameter("useCaseModel.index") == null) 
			return null;
		else{
			try{				
				return (Fase) tp.getFases().toArray()[Integer.parseInt(index)];
			}
			catch(IndexOutOfBoundsException excep) {
				throw ExceptionFactory.createBusinessException("Ha intentado seleccionar un elemento inexistente.");
			}
		}
	}
	
	@Override
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		
		//Setea la parte recién agregada/modificada a la colección
		if(context.getReturnedModel() != null)   { //Por si fue un Cancel
			Fase fase = ((ABMFaseUseCaseModel)context.getReturnedModel()).getFase();			 
			
			if(context.getReturnedModel().getMode().equals(new CreateMode())){
				tp.getFases().add(fase);
				fasesCreadas.add(fase);
				consignasCreadas.addAll((((ABMFaseUseCaseModel)context.getReturnedModel()).getConsignasCreadas()));
				consignasEliminadas.addAll((((ABMFaseUseCaseModel)context.getReturnedModel()).getConsignasEliminadas()));
			}
			if(context.getReturnedModel().getMode().equals(new EditMode())){
				//Busco la fase, y la reemplazo por la nueva
				Boolean encontroFase = false;
				Iterator it = tp.getFases().iterator();
				while(it.hasNext() && !encontroFase ){
					Fase faseAux = (Fase)it.next();
					if(faseAux.getId() == fase.getId()){
						tp.getFases().remove(faseAux);
						tp.getFases().add(fase);
						encontroFase = true;
					}	
				}
				consignasCreadas.addAll((((ABMFaseUseCaseModel)context.getReturnedModel()).getConsignasCreadas()));
				consignasEliminadas.addAll((((ABMFaseUseCaseModel)context.getReturnedModel()).getConsignasEliminadas()));
			}			
			if(context.getReturnedModel().getMode().equals(new RemoveMode())){				
				tp.getFases().remove(fase);			
				fasesEliminadas.add(fase);
			}
		}
		super.returnFromChildDefaultMethod(context);
	}	
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils){
		validationUtils.assertNotEmpty(getTp().getNombre(), "Debe ingresar del TP");
		if(getMode().equals(new CreateMode())){
			validationUtils.assertNull(Tp.findMeByNombre(getTp().getNombre()), "El nombre de usuario ya existe");
			validationUtils.assertIsUploaded(archivoSubido , "Debe adjuntar un archivo de especificación.");
		}
		validationUtils.assertFalse(tp.getFases().isEmpty(), "El Trabajo práctico debe tener al menos una fase.");
		
		//Validar que no se repita el número de consigna
		Boolean repetido = false;
		Object[] fasesArray = tp.getFases().toArray();  
		for(int i = 0; i < (fasesArray.length - 1); i++ ){
			for(int j = i+1; j < fasesArray.length; j++ ){
				if(((Fase)fasesArray[i]).getNumero().equals(((Fase)fasesArray[j]).getNumero())){
					repetido = true;
				}					
			}
		}
		validationUtils.assertFalse(repetido, "Los números de fase no pueden estar repetidos");	
		

		return !validationUtils.hasErrors();
	}

	public Tp getTp() {
		return tp;
	}
	public void setTp(Tp tp) {
		this.tp = tp;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public FormFile getArchivoSubido() {
		return archivoSubido;
	}

	public void setArchivoSubido(FormFile archivoSubido) {
		this.archivoSubido = archivoSubido;
	}

	public boolean getEnviarNotificacion() {
		return enviarNotificacion;
	}

	public void setEnviarNotificacion(boolean enviarNotificacion) {
		this.enviarNotificacion = enviarNotificacion;
	}
}
