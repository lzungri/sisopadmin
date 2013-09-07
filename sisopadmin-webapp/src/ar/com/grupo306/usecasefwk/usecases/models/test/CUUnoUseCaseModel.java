package ar.com.grupo306.usecasefwk.usecases.models.test;

import java.util.List;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra.ABMInformacionCatedraUseCase;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUDosUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUTresUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUUnoUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.OperationMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.SelectionMode;

/**
 * @author Leandro
 */
public class CUUnoUseCaseModel extends AdminBaseUseCaseModel{
	private String valor11;
	private Long valor12;
	private Integer valor13;
	
	protected Class<? extends ModelObject> entityClass() {
		return Informacion.class;
	}	

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return ABMInformacionCatedraUseCase.class;
	}
	
	protected boolean prePopulateUseCase() {
		return true;
	}

	@Secure ({})
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
	}

	protected List<ModelObject> doFind(UseCaseContext useCaseContext) {
		return null;
	}
	
	public void operationMode(UseCaseContext context) {
		setMode(new OperationMode());
	}

	
	public void irACU1MultiSelect(UseCaseContext context) {
		context.goToChildUseCase(CUUnoUseCase.class, new SelectionMode(5), "volverDeSeleccion");
	}
	
	public void irACU1(UseCaseContext context) {
		context.goToChildUseCase(CUUnoUseCase.class, new SelectionMode(), "volverDeSeleccion");
	}
	
	public void volverDeSeleccion(UseCaseContext context) {
		CUUnoUseCaseModel model = (CUUnoUseCaseModel) context.getReturnedModel();
		if(model != null) {
			for(ModelObject selectedEntity : model.getSelectedEntities()) {
				context.addMessage(((Informacion) selectedEntity).getContenido());
			}
		}
	}
	
	public void irACU2(UseCaseContext context) {
		Map parametros = CollectionFactory.createMap();
		parametros.put("valor11", this.valor11);
		parametros.put("valor12", this.valor12);
		parametros.put("valor13", this.valor13);
		
		context.goToChildUseCase(CUDosUseCase.class, new EditMode(), parametros, "volverDeCUDos");
	}
	
	public void irACU3(UseCaseContext context) {
		Map parametros = CollectionFactory.createMap();
		parametros.put("valor11", this.valor11);
		parametros.put("valor12", this.valor12);
		parametros.put("valor13", this.valor13);
		
		context.goToChildUseCase(CUTresUseCase.class, new EditMode(), parametros, "volverDeCUTres");
	}
	
	public void volverDeCUDos(UseCaseContext context) {
		context.addElement("returnedFrom", "Caso de uso 2");
	}
	
	public void volverDeCUTres(UseCaseContext context) {
		context.addElement("returnedFrom", "Caso de uso 3");
	}
	
	public String getValor11() {
		return valor11;
	}
	public void setValor11(String valor11) {
		this.valor11 = valor11;
	}
	public Long getValor12() {
		return valor12;
	}
	public void setValor12(Long valor12) {
		this.valor12 = valor12;
	}
	public Integer getValor13() {
		return valor13;
	}
	public void setValor13(Integer valor13) {
		this.valor13 = valor13;
	}

}