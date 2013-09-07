package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FileUtils;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFaseConsolidada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.RelacionPlantillaEvaluacion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.AdministrarEntregasUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Model para administrar las plantillas a evaluar de una fase.
 *
 * @author Leandro
 */
public class AdministrarPlantillasAEvaluarUseCaseModel extends BaseUseCaseModel {
	private EvaluacionFaseConsolidada evConsolidada;
	private Tp tp;
	private Grupo grupo;
	private Long faseId;
	private Set<Fase> fases;
	private Ayudante ayudanteActual;
	private Integer plantillaIndex;
	
	private Boolean faseAprobada;
	private Entrega entrega;
	
	
	

	@Secure ({})
	@Description ("Inicio de caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);

		evConsolidada = new EvaluacionFaseConsolidada();
		tp = new Tp();
		fases = CollectionFactory.createSet(Fase.class);
		grupo = new Grupo();
		ayudanteActual = Ayudante.findMeByLoginName(((LoggedUserContext) context.getUserContext()).getUserLoginName());
	}
	
	@Transactional
	public void find(UseCaseContext context) {
		if(validacionCamposOK(new UseCaseValidationUtils(context))) {
			Fase fase = Fase.findMeById(faseId);
			evConsolidada = fase.getEvaluacionFaseConsolidada(ayudanteActual, grupo);
		}
	}
	
	public void view(UseCaseContext context) {
		RelacionPlantillaEvaluacion relacionSeleccionada = findEntity(evConsolidada.getRelaciones(), plantillaIndex);
		if(relacionSeleccionada.getEvaluacion() == null) {
			context.addErrorMessage("Debe existir una evaluación para la plantilla");
			return;
		}
		
		Map parameters = CollectionFactory.createMap();
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, relacionSeleccionada.getEvaluacion());		
		
		context.goToChildUseCase(ABMEvaluacionFasePorPlantillaUseCase.class, new ViewMode(), parameters);
	}
	
	@Transactional
	public void evaluar(UseCaseContext context) {
		RelacionPlantillaEvaluacion relacionSeleccionada = findEntity(evConsolidada.getRelaciones(), plantillaIndex);
		EvaluacionFasePorPlantilla evaluacion = relacionSeleccionada.getEvaluacion();
		
		// Si aún no fue evaluada => se la crea.
		if(evaluacion == null) {
			if(validacionCamposOK(new UseCaseValidationUtils(context))) {
				evaluacion = new EvaluacionFasePorPlantilla();
				evaluacion.setPlantilla(relacionSeleccionada.getPlantilla());
				evaluacion.setAyudanteEvaluador(ayudanteActual);
				evaluacion.setFase(Fase.findMeById(faseId));
				evaluacion.setGrupo(grupo);
				
				Map parameters = CollectionFactory.createMap();
				parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, evaluacion);		
				context.goToChildUseCase(ABMEvaluacionFasePorPlantillaUseCase.class, new CreateMode(), parameters, "returnFromABMEvaluacionFase");
			}
		}
		else {
			Map parameters = CollectionFactory.createMap();
			parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, evaluacion);		
			context.goToChildUseCase(ABMEvaluacionFasePorPlantillaUseCase.class, new EditMode(), parameters, "returnFromABMEvaluacionFase");
		}
	}
	
	@Transactional
	public void returnFromABMEvaluacionFase(UseCaseContext context) {
		if(context.getReturnedModel() != null) {
			// Se refresca la lista de relaciones plantilla/evaluación.
			find(context);
		}
	}
	
	public void remove(UseCaseContext context) {
		RelacionPlantillaEvaluacion relacionSeleccionada = findEntity(evConsolidada.getRelaciones(), plantillaIndex);
		EvaluacionFasePorPlantilla evaluacion = relacionSeleccionada.getEvaluacion();
		
		Map parameters = CollectionFactory.createMap();
		parameters.put(ABMBaseUseCaseModel.ENTITY_KEY, evaluacion);		
		context.goToChildUseCase(ABMEvaluacionFasePorPlantillaUseCase.class, new RemoveMode(), parameters, "returnFromABMEvaluacionFase");
	}
	
	/**
	 * Generar el informe de la evaluación de la fase.
	 * 
	 * @param context
	 */
	@Transactional
	public void generarInforme(UseCaseContext context) {
		UseCaseValidationUtils validationUtils = new UseCaseValidationUtils(context); 
		if(validacionCamposOK(validationUtils)) {
			evConsolidada.procesarEvaluaciones();
			
			try {
				validationUtils.assertNotNull(entrega, "Debe seleccionar la entrega del grupo que ha sido evaluada.");
				if(validationUtils.hasErrors()) {
					return;
				}
				
				// Creación de la entidad informe.
				InformeEvaluacionFase informe = new InformeEvaluacionFase();
				informe.setFase(Fase.findMeById(faseId));
				informe.setAyudanteEvaluador(ayudanteActual);
				informe.setGrupoEvaluado(grupo);
				informe.setFaseAprobada(faseAprobada);
				informe.setPorcentajeEvaluado(evConsolidada.getPorcentajeEvaluado());
				informe.setPorcentajeCumplimientoFase(evConsolidada.getPorcentajeCumplimientoFase());
				informe.setFechaAlta(new Date());
				informe.setEstado(Estado.findMeByDomainCode(InformeEvaluacionFase.ESTADO_CODE_NOENVIADA));
				informe.setEntregaEvaluada(entrega);
				
				
				// Se genera el reporte y se almacena en disco.
				Map parameters = CollectionFactory.createMap();
				parameters.put("informe", informe);
				
				// En primer lugar se asegura que exista el compilado del sub reporte.
				SisopAdminServiceProvider.getReportsGeneratorService().getCompiledReport("informeEvaluacion_itemsEvaluacion");
				ByteArrayOutputStream reportStream = SisopAdminServiceProvider.getReportsGeneratorService().generateReport(
						"informeEvaluacion", parameters, new JRBeanCollectionDataSource(evConsolidada.getRelaciones()), "pdf");
				
				String filePath = SisopAdminConfig.getInstance().getProperty(Module.TP, "evaluacion.informes.path") + "informeGrupo" + grupo.getId() + "-" + new Date().getTime() + ".pdf";
				FileOutputStream outputStream = FileUtils.openOutputStream(new File(filePath));
				outputStream.write(reportStream.toByteArray());
				outputStream.close();
				reportStream.close();
				
				
				informe.setPathInforme(filePath);
				informe.save();
				context.addMessage("Se ha generado el informe de evaluación del grupo " + grupo.getNombre() + ". <a href='." + UseCaseUtils.getUseCaseInitPath(new AdministrarInformesUseCase())+ "'>Ver informes</a>");
			}
			catch(IOException excep) {
				throw ExceptionFactory.createProgramException("Error al intentar generar el informe.", excep);
			}
		}
	}
	
	private boolean validacionCamposOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertNotNull(tp.getId(), "Debe seleccionar un Trabajo práctico");
		validationUtils.assertNotNull(faseId, "Debe seleccionar una Fase");
		validationUtils.assertNotNull(grupo.getId(), "Debe seleccionar un Grupo");
		validationUtils.assertNotNull(ayudanteActual, "El usuario actual debe ser ayudante");
		
		return !validationUtils.hasErrors();
	}

	public void seleccionarTP(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarTPsUseCase.class, new SelectionMode(), "returnFromSeleccionarTP");
		useCaseContext.addMessage("Seleccione el trabajo práctico a evaluar.");
	}
	
	public void returnFromSeleccionarTP(UseCaseContext useCaseContext) {
		AdministrarTPsUseCaseModel model = (AdministrarTPsUseCaseModel) useCaseContext.getReturnedModel();
		if(model != null) {
			tp = (Tp) model.getSelectedEntities().iterator().next();
			fases = tp.getFases();
		}
		else {
			tp = new Tp();
			fases = CollectionFactory.createSet(Fase.class);
		}
	}
	
	@Transactional
	public void seleccionarGrupo(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarGruposUseCase.class, new SelectionMode(), "returnFromSeleccionarGrupo");
	}
	
	public void returnFromSeleccionarGrupo(UseCaseContext useCaseContext) {
		AdministrarGruposUseCaseModel model = (AdministrarGruposUseCaseModel) useCaseContext.getReturnedModel();
		grupo = model != null ? (Grupo) model.getSelectedEntities().iterator().next() : new Grupo();
	}
	
	public void seleccionarEntrega(UseCaseContext useCaseContext) {
		Predicate filter = new Predicate() {
			public boolean evaluate(Object object) {
				return ((Entrega) object).getGrupo().equals(grupo); 
			}
		};
		Map parameters = CollectionFactory.createMap();
		parameters.put(AdminBaseUseCaseModel.SEARCH_FILTER_KEY, filter);
		
		useCaseContext.goToChildUseCase(AdministrarEntregasUseCase.class, new SelectionMode(), parameters, "returnFromSeleccionarEntrega");
		useCaseContext.addMessage("Seleccione la entrega de grupo que ha sido evaluada.");
	}
	
	public void returnFromSeleccionarEntrega(UseCaseContext useCaseContext) {
		AdministrarEntregasUseCaseModel model = (AdministrarEntregasUseCaseModel) useCaseContext.getReturnedModel();
		entrega = model != null ? (Entrega) model.getSelectedEntities().iterator().next() : null;
	}
	
	
	public String getFechaEntregaEvaluada() {
		return entrega != null && entrega.getFechaEntrega() != null ? new SimpleDateFormat(SisopAdminConstants.DATEHOURMINUTE_FORMAT_PATTERN).format(entrega.getFechaEntrega()) : "";
	}

	public Tp getTp() {
		return tp;
	}

	public void setTp(Tp tp) {
		this.tp = tp;
	}

	public Set<Fase> getFases() {
		return fases;
	}

	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	public EvaluacionFaseConsolidada getEvConsolidada() {
		return evConsolidada;
	}

	public void setEvConsolidada(EvaluacionFaseConsolidada evConsolidada) {
		this.evConsolidada = evConsolidada;
	}

	public Long getFaseId() {
		return faseId;
	}

	public void setFaseId(Long faseId) {
		this.faseId = faseId;
	}

	public Ayudante getAyudanteActual() {
		return ayudanteActual;
	}

	public void setAyudanteActual(Ayudante ayudanteActual) {
		this.ayudanteActual = ayudanteActual;
	}

	public Integer getPlantillaIndex() {
		return plantillaIndex;
	}

	public void setPlantillaIndex(Integer plantillaIndex) {
		this.plantillaIndex = plantillaIndex;
	}

	public Boolean getFaseAprobada() {
		return faseAprobada;
	}

	public void setFaseAprobada(Boolean faseAprobada) {
		this.faseAprobada = faseAprobada;
	}

	public Entrega getEntrega() {
		return entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}
}