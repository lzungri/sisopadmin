package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;
import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuestada;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PuntoEncuestado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;

public class EstrategiaEliminacionEncuesta extends EstrategiaAceptacionABM{
	
	public boolean initializeEdicionPunto(){
		return false;
	}
	
	/**
	 * Estrategia generada para eliminar una encuesta
	 * @param context
	 */
		public void ejecutar(ABMContext context){
			ABMEncuestaUseCaseModel useCaseModel = (ABMEncuestaUseCaseModel)context.getModel();
			UseCaseContext useCaseContext = context.getContext();
			try{
				
				PlantillaEncuesta encuesta = useCaseModel.getEncuesta();
				Collection usuarios = new Usuario().findAll(Usuario.class);
				Iterator iUsuarios = usuarios.iterator();
				encuesta.remove();
				while(iUsuarios.hasNext()){
					Usuario usuario = (Usuario)iUsuarios.next();
					usuario.getEncuestasSinLlenar().remove(encuesta);
					usuario.saveOrUpdate();
				}
				//encuesta.refresh();
				useCaseContext.addMessage("Encuesta eliminada exitosamente.");
				useCaseContext.acceptUseCase();
			}catch(Exception e){
				useCaseContext.addErrorMessage("No se pudo generar la encuesta. Consulte al administrador");
			}
		}
}
