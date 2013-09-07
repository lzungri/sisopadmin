package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoAEncuestar;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion.AsignadorDeEncuestas;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;

public class EstrategiaLlenadoEncuesta extends EstrategiaAceptacionABM{
	/**
	 * Estrategia generada para llenar una encuesta
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
				//if (! validarAbmEncuesta(useCaseContext,useCaseModel)) return;
				LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
				usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
				PlantillaEncuesta plantilla = (PlantillaEncuesta)useCaseContext.getParameter(ABMBaseUseCaseModel.ENTITY_KEY);
				PlantillaEncuestada plantillaEncuestada = (PlantillaEncuestada)useCaseContext.getParameter("PLANTILLA_ENCUESTADA");
				usuario.getEncuestasSinLlenar().remove(plantilla);
				usuario.saveOrUpdate();
				if(plantilla.getPlantillasEncuestadas() == null)
					plantilla.setPlantillasEncuestadas(CollectionFactory.createTreeSet(PlantillaEncuestada.class));
				plantilla.getPlantillasEncuestadas().add(plantillaEncuestada);
				plantillaEncuestada.setPlantillaEncuesta(plantilla);
				Map map = (Map)useCaseContext.getParameter("PUNTOS_MAP");
				Iterator iMap = map.entrySet().iterator();
				while(iMap.hasNext()){
					Entry entry = (Entry)iMap.next();
					PuntoEncuestado punto = (PuntoEncuestado)entry.getValue();
					PuntoAEncuestar aEncuestar = (PuntoAEncuestar)entry.getKey();
					punto.setPuntuacion(new Long(useCaseModel.getPuntaje()[aEncuestar.getNumero().intValue()]));
					if(aEncuestar.getPuntosEncuestados()== null || aEncuestar.getPuntosEncuestados().isEmpty())
						aEncuestar.setPuntosEncuestados(new TreeSet());
					aEncuestar.getPuntosEncuestados().add(punto);
					punto.setPuntoAEncuestar(aEncuestar);
					punto.setPlantillaEncuestada(plantillaEncuestada);
					punto.saveOrUpdate();
					//aEncuestar.saveOrUpdate();
				}
				
				//plantilla.getUsuariosSinEncuestar().remove(usuario);
				//plantilla.saveOrUpdate();
				plantillaEncuestada.saveOrUpdate();
				useCaseContext.addMessage("Se ha completado la encuesta correctamente");
				useCaseContext.acceptUseCase();
			}catch(Exception e){
				throw new BusinessException("No se pudo generar la encuesta. Consulte al administrador");
			}
		}

}
