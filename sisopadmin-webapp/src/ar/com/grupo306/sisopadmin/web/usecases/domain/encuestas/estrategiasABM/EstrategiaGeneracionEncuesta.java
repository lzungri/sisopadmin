package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import java.util.Date;
import java.util.Iterator;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.AsignadorDeEncuestas;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.eventos.EventoNotificable;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;

/**
 * 
 * @author Rafa
 *
 */

public class EstrategiaGeneracionEncuesta extends EstrategiaAceptacionABM{
/**
 * Estrategia generada para Cargar una encuesta
 * @param context
 */
	public void ejecutar(ABMContext context){
		ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)context.getModel();
		UseCaseContext useCaseContext = context.getContext();
		try{
			
			Usuario usuario;
			UserContext userContext = (UserContext)useCaseContext.getUserContext();
			if(!userContext.isUserLogged()){
				useCaseContext.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
			}
			if (! validarAbmEncuesta(useCaseContext,useCaseModel)) return;
			LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
			usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
			PlantillaEncuesta plantilla  = new PlantillaEncuesta() ;
			plantilla.setNombre(useCaseModel.getNombrePlantilla());
			plantilla.setFechaAlta(useCaseModel.getFechaInicio());
			plantilla.setFechaFin(useCaseModel.getFechaFin());
			plantilla.setObligatoriedad(useCaseModel.isObligatoria()?new Long(0):new Long(1));
			plantilla.setUsuario(usuario);
			plantilla.setPuntosAEncuestar(useCaseModel.getPuntos());
			plantilla.setDestinatario(useCaseModel.getDestinatario());
			Iterator iPuntosAEncuestar = useCaseModel.getPuntos().iterator();
			plantilla.setEstado(new Long(1));
			
			while(iPuntosAEncuestar.hasNext()){
				PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEncuestar.next();
				punto.setPlantillaEncuesta(plantilla);
				punto.saveOrUpdate();
			}
			plantilla.saveOrUpdate();
			AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance(useCaseModel.getDestinatario());
			asignador.asignar(plantilla);
			useCaseContext.addMessage("Encuesta cargada exitosamente");
			
			useCaseModel.vaciarCU();
			
			useCaseContext.acceptUseCase();
			
		}catch(Exception e){
			useCaseContext.addErrorMessage("No se pudo cargar la encuesta. Consulte al administrador");
			useCaseContext.acceptUseCase();
		}
	}

}
