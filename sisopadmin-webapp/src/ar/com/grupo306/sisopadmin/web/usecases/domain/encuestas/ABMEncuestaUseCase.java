package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas;

import java.util.List;

import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;

public class ABMEncuestaUseCase extends ABMBaseUseCase {

	public Class useCaseModelClass() {
		return ABMEncuestaUseCaseModel.class;
	}

	public boolean isBaseUseCase() {
		return false;
	}

	public String getShortDescription() {
		return "Cargar encuestas";
	}

	public String getLongDescription() {
		return "Cargar encuestas";
	}
	
	public void availableModes(List<UseCaseModelMode> availableModes) {
		availableModes.add(new CreateMode());
		availableModes.add(new EditMode());
		availableModes.add(new RemoveMode());
		availableModes.add(new ViewMode());
		availableModes.add(new OperationMode());
	}
	
	public boolean isVisibleOnMenu() {		
		return false;
	}
	
}