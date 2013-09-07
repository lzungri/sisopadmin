package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.Predicate;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.mailSender.Mail;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Coordinador;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCase;
import ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.AdministrarIntegranteUseCaseModel;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU para gestionar los informes generados ante una evaluación de fase.
 * 
 * @author Leandro
 */
public class AdministrarInformesUseCaseModel extends AdminBaseUseCaseModel {
	private InformeEvaluacionFase informe;
	private List<Estado> estados;

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return null;
	}

	protected Class<? extends ModelObject> entityClass() {
		return InformeEvaluacionFase.class;
	}
	
	protected boolean prePopulateUseCase() {
		return true;
	}

	protected Predicate searchFilter(final Usuario loggedUser) {
		if(loggedUser != null) {
			// Si el usuario sólo es ayudante, entonces se filtran sus grupos.
			if(loggedUser instanceof Ayudante && !(loggedUser instanceof Coordinador)) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						return ((Ayudante) loggedUser).getGrupos().contains(((InformeEvaluacionFase) object).getGrupoEvaluado());
					}
				};
			}
			
			// Si el usuario es un grupo, sólo se muestran sus informes en estado ENVIADO.
			if(loggedUser instanceof Grupo) {
				return new Predicate() {
					public boolean evaluate(Object object) {
						InformeEvaluacionFase informe = (InformeEvaluacionFase) object;
						return informe.getGrupoEvaluado().equals(loggedUser) && informe.getEstado().getDomainCode().equals(InformeEvaluacionFase.ESTADO_CODE_ENVIADA);
					}
				};
			}
		}
		return null;
	}

	@Secure ({})
	@Description ("Iniciar el caso de uso")
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		informe = new InformeEvaluacionFase();
		
		// TODO ver si es correcto no ifear si está logueado.
		Usuario usuario = Usuario.findMeByLoginName(((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName());
		if(usuario instanceof Grupo) {
			informe.setAyudanteEvaluador(((Grupo) usuario).getAyudante());
			informe.setGrupoEvaluado((Grupo) usuario);
		}
		else if(!(usuario instanceof Coordinador) && usuario instanceof Ayudante) {
			informe.setAyudanteEvaluador((Ayudante) usuario);
			informe.setGrupoEvaluado(new Grupo());
		}
		else {
			informe.setAyudanteEvaluador(new Ayudante());
			informe.setGrupoEvaluado(new Grupo());
		}
		
		informe.setEstado(new Estado());
		estados = Estado.findAllByClassNamePropietaria(InformeEvaluacionFase.class);
	}
	
	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		return InformeEvaluacionFase.findBy(informe.getEstado().getId(), informe.getAyudanteEvaluador().getId(), informe.getGrupoEvaluado().getId());
	}

	
	@Secure ({})
	@Description ("Eliminación de Informe")
	@Transactional
	public void remove(UseCaseContext useCaseContext) {
		getSelectedEntity(useCaseContext).remove();
		useCaseContext.addMessage("Se ha eliminado el informe de evaluación.");
		
		// Se actualiza la lista actual.
		find(useCaseContext);
	}
	
	@Secure ({})
	@Description ("Selección de ayudante")
	public void seleccionarAyudante(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarIntegranteUseCase.class, new SelectionMode(), "returnFromSeleccionarAyudante");
	}
	
	public void returnFromSeleccionarAyudante(UseCaseContext useCaseContext) {
		AdministrarIntegranteUseCaseModel model = (AdministrarIntegranteUseCaseModel) useCaseContext.getReturnedModel();
		informe.setAyudanteEvaluador( model != null ? (Ayudante) model.getSelectedEntities().iterator().next() : new Ayudante());
	}
	
	
	@Secure ({})
	@Description ("Selección de grupo")
	public void seleccionarGrupo(UseCaseContext useCaseContext) {
		useCaseContext.goToChildUseCase(AdministrarGruposUseCase.class, new SelectionMode(), "returnFromSeleccionarGrupo");
	}
	
	public void returnFromSeleccionarGrupo(UseCaseContext useCaseContext) {
		AdministrarGruposUseCaseModel model = (AdministrarGruposUseCaseModel) useCaseContext.getReturnedModel();
		informe.setGrupoEvaluado(model != null ? (Grupo) model.getSelectedEntities().iterator().next() : new Grupo());
	}
	
	public void descargarInforme(UseCaseContext context) {
		InformeEvaluacionFase informeADescargar = (InformeEvaluacionFase) getSelectedEntity(context);
		
		UseCaseValidationUtils validationUtils = new UseCaseValidationUtils(context);
		validationUtils.assertNotEmpty(informeADescargar.getPathInforme(), "El informe no tiene un archivo asociado.");
		if(validationUtils.hasErrors()) {
			return;
		}
		
		context.addFileAttachment(informeADescargar.getPathInforme(), "informeEvaluacion.pdf");
	}
	
	/**
	 * Hace que el informe sea visible al grupo y lo envía por mail.
	 * 
	 * @param context
	 */
	@Secure ({})
	@Description ("Enviar informe a grupo")
	@Transactional
	public void enviarInforme(UseCaseContext context) {
		InformeEvaluacionFase informe = (InformeEvaluacionFase) getSelectedEntity(context);

		informe.enviar();
		
		// Se genera el evento notificable para el grupo.
		new EventoNotificable(DateUtils.addDays(new Date(), 5), 
				"Ya se encuentra disponible el informe de evaluación correspondiente a la fase '" + informe.getFase().getNombre() + "'.", 
				"." + UseCaseUtils.getUseCaseInitPath(new AdministrarInformesUseCase()))
			.addNotificable(informe.getGrupoEvaluado())
			.save();
		
		// Envío de mail al grupo.
		ArrayList<String> destinatarios = new ArrayList();		
		destinatarios.add(informe.getGrupoEvaluado().getEmail());	
		Collection<String> attachments = CollectionFactory.createCollection(String.class);
		attachments.add(informe.getPathInforme());
								
		Mail mail = new Mail(null, null, "Informe de evaluacion - Fase '" + informe.getFase().getNombre() + "'", "bodyTemplateEnvioInforme");		
		mail.addClavesRemplazo("grupoNombre", informe.getGrupoEvaluado().getNombreCompleto());
		mail.addClavesRemplazo("ayudanteNombre", informe.getAyudanteEvaluador().getNombreCompleto());		
		mail.addClavesRemplazo("faseNombre", informe.getFase().getNombre());		
		mail.doSendMails(destinatarios, attachments);
		
		context.addMessage("El informe se ha enviado al grupo.");
		// Se actualiza la lista actual.
		find(context);
	}

	public InformeEvaluacionFase getInforme() {
		return informe;
	}

	public void setInforme(InformeEvaluacionFase informe) {
		this.informe = informe;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

}