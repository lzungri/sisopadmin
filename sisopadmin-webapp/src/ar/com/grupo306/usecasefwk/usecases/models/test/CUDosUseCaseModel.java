package ar.com.grupo306.usecasefwk.usecases.models.test;

import java.util.Map;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUTresUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;

/**
 * @author Leandro
 */
public class CUDosUseCaseModel extends BaseUseCaseModel {
	private String valor11;
	private Long valor12;
	private Integer valor13;
	private String valor21;
	private Long valor22;
	private Integer valor23;
	
	@Secure ({EditMode.class}) 
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		this.valor11 = (String) context.getParameters().get("valor11");
		this.valor12 = (Long) context.getParameters().get("valor12");
		this.valor13 = (Integer) context.getParameters().get("valor13");
	}
	
	public void irACU3(UseCaseContext context) {
		Map parametros = CollectionFactory.createMap();
		parametros.put("valor11", this.valor11);
		parametros.put("valor12", this.valor12);
		parametros.put("valor13", this.valor13);
		parametros.put("valor21", this.valor21);
		parametros.put("valor22", this.valor22);
		parametros.put("valor23", this.valor23);		
		
		context.goToChildUseCase(CUTresUseCase.class, new CreateMode(), parametros, "volverDeCUTres");
	}
	
	@Secure ({EditMode.class})
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
	public String getValor21() {
		return valor21;
	}
	public void setValor21(String valor21) {
		this.valor21 = valor21;
	}
	public Long getValor22() {
		return valor22;
	}
	public void setValor22(Long valor22) {
		this.valor22 = valor22;
	}
	public Integer getValor23() {
		return valor23;
	}
	public void setValor23(Integer valor23) {
		this.valor23 = valor23;
	}
	
}