package ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.estrategiasABM;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;

public class ABMContext {
	private UseCaseContext context;
	private UseCaseModel model;
	private ModelObject modelObject;

	public UseCaseContext getContext() {
		return context;
	}

	public void setContext(UseCaseContext context) {
		this.context = context;
	}

	public UseCaseModel getModel() {
		return model;
	}

	public void setModel(UseCaseModel model) {
		this.model = model;
	}

	public ModelObject getModelObject() {
		return modelObject;
	}

	public void setModelObject(ModelObject modelObject) {
		this.modelObject = modelObject;
	}
}
