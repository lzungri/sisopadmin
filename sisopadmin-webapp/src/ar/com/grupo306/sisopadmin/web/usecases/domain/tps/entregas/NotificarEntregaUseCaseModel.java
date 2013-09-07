package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
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
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU por el cual el grupo notifica la realización de una
 * entrega para una fase determinada.
 *
 * @author Leandro
 */
public class NotificarEntregaUseCaseModel extends ABMBaseUseCaseModel {
	private Entrega entrega;
	private Set<Fase> fases;
	
	private FormFile archivoDeEntrega;

	@Secure ({CreateMode.class, ViewMode.class, EditMode.class, RemoveMode.class})
	@Description ("Inicio de caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		if(entrega == null) {
			entrega = new Entrega();
			entrega.setFase(new Fase());
			entrega.getFase().setTp(new Tp());
		}
		fases = CollectionFactory.createSet(Fase.class);
	}
	
	protected void processModelObject(UseCaseContext context, ModelObject modelObject) {
		entrega = (Entrega) modelObject;
	}

	@Transactional
	public void aceptar(UseCaseContext context) {
		UseCaseValidationUtils validationUtils = new UseCaseValidationUtils(context);
		validationUtils.assertNotNull(entrega.getFase().getTp().getId(), "Debe seleccionar un Trabajo práctico");
		validationUtils.assertNotNull(entrega.getFase().getId(), "Debe seleccionar una Fase");
		if(validationUtils.hasErrors())
			return;

		Usuario usuarioLogueado = Usuario.findMeByLoginName(((LoggedUserContext) context.getUserContext()).getUserLoginName());
		if(!(usuarioLogueado instanceof Grupo)) {
			throw ExceptionFactory.createSisopAdminSecurityException("El usuario que realiza una entrega debe ser un grupo");
		}
		Grupo grupoLogueado = (Grupo) usuarioLogueado;
		
		if(!entrega.getDescargarDeCVS()) {
			validationUtils.assertIsUploaded(archivoDeEntrega, "Debe adjuntar el archivo correspondiente a la entrega");
			if(validationUtils.hasErrors())
				return;
			
			try {
				String uploadPath = SisopAdminConfig.getInstance().getProperty(Module.TP, "tp.entrega.uploadPath");
				// Nomenclatura: <grupoLoginName>-<Timestamp>-<nombreDeArchivoSubido>
				File archivoAGrabar = new File(uploadPath + grupoLogueado.getLoginName() + "-" + new Date().getTime() + "-" + archivoDeEntrega.getFileName());
				InputStream archivoIS = archivoDeEntrega.getInputStream();
				OutputStream archivoOS = FileUtils.openOutputStream(archivoAGrabar);
				IOUtils.copy(archivoIS, archivoOS);

				archivoIS.close();
				archivoOS.close();
				
				entrega.setPathArchivo(archivoAGrabar.getAbsolutePath());
			}
			catch(Exception excep) {
				throw ExceptionFactory.createProgramException("Error al intentar subir el archivo " + archivoDeEntrega.getFileName(), excep);
			}
		}
		
		entrega.setFase(Fase.findMeById(entrega.getFase().getId()));
		entrega.setFechaEntrega(new Date());
		entrega.setGrupo(grupoLogueado);
		entrega.save();
		
		// Se notifica a su ayudante acerca de la entrega.
		new EventoNotificable(
				DateUtils.addDays(new Date(), 5),
				"El grupo '" + grupoLogueado.getNombre() + "' ha realizado la entrega de la fase '" + entrega.getFase().getNombre() + "'.",
				"." + UseCaseUtils.getUseCaseInitPath(new AdministrarEntregasUseCase()))
			.addNotificable(grupoLogueado.getAyudante())
			.save();
		
		context.addMessage("La entrega se ha realizado correctamente");
		context.acceptUseCase();
	}
	
	public void seleccionarTP(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionarTP");
	}
	
	public void returnFromSeleccionarTP(UseCaseContext useCaseContext) {
		AdministrarTPsUseCaseModel model = (AdministrarTPsUseCaseModel) useCaseContext.getReturnedModel();
		if(model != null) {
			entrega.getFase().setTp((Tp) model.getSelectedEntities().iterator().next());
			fases = entrega.getFase().getTp().getFases();
		}
		else {
			entrega.getFase().setTp(new Tp());
			fases = CollectionFactory.createSet(Fase.class);
		}
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public Set<Fase> getFases() {
		return fases;
	}

	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}
	
	public FormFile getArchivoDeEntrega() {
		return archivoDeEntrega;
	}
	
	public void setArchivoDeEntrega(FormFile archivoDeEntrega) {
		this.archivoDeEntrega = archivoDeEntrega;
	}

}