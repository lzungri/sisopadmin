package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.AsignadorDeEncuestas;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
/**
 * 
 * @author yaprgn
 *
 */
public class EstrategiaModificacionEncuesta extends EstrategiaAceptacionABM{
	/**
	 * Estrategia generada para modificar una encuesta
	 * @param context
	 */
		public void ejecutar(ABMContext context){
			try{
				ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)context.getModel();
				UseCaseContext useCaseContext = context.getContext();
				
				Usuario usuario;
				UserContext userContext = (UserContext)useCaseContext.getUserContext();
				if(!userContext.isUserLogged()){
					useCaseContext.addErrorMessage("Usuario y/o contraseña incorrecta."); //copio el mensaje del loginUseCase debería ser MSG-Global.UsuarioInválido.
				}
				if (! validarAbmEncuesta(useCaseContext,useCaseModel)) return;
				LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
				usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
				PlantillaEncuesta plantilla = (PlantillaEncuesta)useCaseContext.getParameter(ABMBaseUseCaseModel.ENTITY_KEY);
				//plantilla.getPuntosAEncuestar().clear();
				plantilla = plantilla.findMeByName(plantilla.getNombre());
				plantilla.setFechaAlta(useCaseModel.getFechaInicio());
				plantilla.setFechaFin(useCaseModel.getFechaFin());
				plantilla.setObligatoriedad(useCaseModel.isObligatoria()?new Long(0):new Long(1));
				plantilla.setUsuario(usuario);
				Iterator puntosCargados = useCaseModel.getPuntos().iterator();
				Iterator iPuntosAEliminar = useCaseModel.getPuntosAEliminar().iterator();
				Collection puntos = plantilla.getPuntosAEncuestar();
				while(iPuntosAEliminar.hasNext()){
					PuntoAEncuestar punto = (PuntoAEncuestar)iPuntosAEliminar.next();
					
					//punto = punto.findMeById(punto.getId());
					//if(puntos.contains(punto)){
					boolean horrible = true;
					try{
						
						punto = punto.findMeById(punto.getId());
					}catch(Exception e){
						horrible = false;
					}	
					
					punto.setPlantillaEncuesta(null);
					punto.setPuntosEncuestados(null);
					puntos.remove(punto);
					
					if(horrible){
						punto.remove();
						punto.saveOrUpdate();
					}
					
					
					
				}
				
				
				while(puntosCargados.hasNext()){
					PuntoAEncuestar punto = (PuntoAEncuestar)puntosCargados.next();
					
					if(!puntos.contains(punto)){
						PuntoAEncuestar punto2;
						try{	
							 punto2 = punto.findMeById(punto.getId());
						}catch(Exception e){
							punto2 = punto;
						}
						puntos.add(punto2);
						punto2.setPlantillaEncuesta(plantilla);
						punto2.setNombre(punto.getNombre());
						punto2.setDescripcion(punto.getDescripcion());
						punto2.saveOrUpdate();}
				}
				
				
				plantilla.setDestinatario(useCaseModel.getDestinatario());
				plantilla.setNombre(useCaseModel.getNombrePlantilla());
				
				
				
				plantilla.saveOrUpdate();
				AsignadorDeEncuestas asignador=AsignadorDeEncuestas.getInstance(useCaseModel.getDestinatario());
				asignador.asignar(plantilla);
				
				
				useCaseContext.addMessage("Encuesta modificada exitosamente");
				
				useCaseModel.vaciarCU();
				useCaseContext.acceptUseCase();
			}catch(Exception e){
				throw new BusinessException("No se pudo generar la encuesta. Consulte al administrador");
			}
		}
		
		
		
		public Boolean validarAbmEncuesta(UseCaseContext context,UseCaseModel model){
			ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)model;
			Boolean valida = true;
			if("".compareToIgnoreCase(useCaseModel.getNombrePlantilla()) == 0){
				context.addErrorMessage("Debe ingresar el nombre de la plantilla");
				valida = false;
			}
			PlantillaEncuesta encuesta = (PlantillaEncuesta)context.getParameter(ABMBaseUseCaseModel.ENTITY_KEY);
			PlantillaEncuesta encuestaIgual = encuesta.findMeByName(useCaseModel.getNombrePlantilla());
			
			if(encuestaIgual != null && !encuestaIgual.getId().equals(encuesta.getId())){
				context.addErrorMessage("El nombre de la encuesta ya existe en el sistema.");
				valida = false;
			}
			
			if(useCaseModel.getPuntos().isEmpty()){
				context.addErrorMessage("Debe ingresar puntos en la encuesta");
				valida = false;
			}
			if(useCaseModel.getFechaInicio()==null){
				context.addErrorMessage("Debe ingresar la fecha de inicio");
				valida = false;
			}
			if(useCaseModel.getFechaFin() == null){
				context.addErrorMessage("Debe ingresar la fecha de finalización");
				valida = false;
			}
			if(useCaseModel.getFechaInicio() != null && useCaseModel.getFechaFin() != null && useCaseModel.getFechaInicio().compareTo(useCaseModel.getFechaFin())>0){
				context.addErrorMessage("La fecha de finalización debe ser menor a la de origen");
				valida = false;
			}
			
			if(useCaseModel.getFechaInicio() != null && useCaseModel.getFechaFin() != null && !DateUtils.isGreaterThanRange(useCaseModel.getFechaFin(), new Date())){
				context.addErrorMessage("La fecha de finalización debe ser mayor a la actual");
				valida = false;
			}
			
			if(useCaseModel.getFechaInicio() != null && useCaseModel.getFechaFin() != null && !DateUtils.isGreaterThanRange( useCaseModel.getFechaInicio(),new Date()) && !DateUtils.isSameDayMonthYear(useCaseModel.getFechaInicio(), new Date())){
				context.addErrorMessage("La fecha de inicio no puede ser menor que la actual");
				valida = false;
			}

			return valida;
		}

	}
