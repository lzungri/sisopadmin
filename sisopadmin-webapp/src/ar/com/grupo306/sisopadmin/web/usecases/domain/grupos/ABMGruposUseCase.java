/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.grupos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ar.com.grupo306.sisopadmin.config.Module;
import ar.com.grupo306.sisopadmin.config.SisopAdminConfig;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ConfirmMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * @author Sole
 *
 */
public class ABMGruposUseCase extends ABMBaseUseCase {

	@Override
	public boolean isVisibleOnMenu() {
		String fechaStr = SisopAdminConfig.getInstance().getProperty(Module.GRUPOS, "fechaMaximaCarga");
		try{
			Date fecha = new SimpleDateFormat(SisopAdminConstants.DATE_FORMAT_PATTERN).parse(fechaStr);
			return fecha.after(new Date());
		}
		catch(Exception ex){
			return true;
		}		
	}

	@Override
	public void availableModes(List<UseCaseModelMode> availableModes) {		
		super.availableModes(availableModes);
		availableModes.add(new ConfirmMode());
	}

	public String getLongDescription() {
		return "Dar de Alta, modificar, visualizar y eliminar Grupos";
	}
	
	public String getShortDescription() {
		return "Crear Grupo";
	}
	
	public Class useCaseModelClass() {
		return ABMGruposUseCaseModel.class;
	}

}
