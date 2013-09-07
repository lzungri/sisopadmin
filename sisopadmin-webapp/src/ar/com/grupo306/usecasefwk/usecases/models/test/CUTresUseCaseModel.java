package ar.com.grupo306.usecasefwk.usecases.models.test;

import java.util.Date;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUDosUseCase;
import ar.com.grupo306.usecasefwk.usecases.domain.test.CUTresUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.ViewMode;

/**
 * @author Leandro
 */
public class CUTresUseCaseModel extends BaseUseCaseModel {
	private String valor11;
	private Long valor12;
	private Integer valor13;
	private String valor21;
	private Long valor22;
	private Integer valor23;
	private String valor31;
	private Long valor32;
	private Integer valor33;
	
	private Date fecha1 = new Date();
	
	@Secure ({EditMode.class})
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		this.valor11 = (String) context.getParameters().get("valor11");
		this.valor12 = (Long) context.getParameters().get("valor12");
		this.valor13 = (Integer) context.getParameters().get("valor13");
		this.valor21 = (String) context.getParameters().get("valor21");
		this.valor22 = (Long) context.getParameters().get("valor22");
		this.valor23 = (Integer) context.getParameters().get("valor23");		
	}
	
	@Secure ({ViewMode.class})
	@Description ("Seguro para habilitar la visibilidad de la acción en el modo de visualización.")
	public void cancelUseCase(UseCaseContext context) {
		super.cancelUseCase(context);
	}

	public void irACU2(UseCaseContext context) {
		context.goToChildUseCase(CUDosUseCase.class, "volverDeCUDos");
	}
	
	public void irACU3(UseCaseContext context) {
		context.goToChildUseCase(CUTresUseCase.class, new ViewMode());
	}
	
	public void cambiarACreate(UseCaseContext context) {
		setMode(new CreateMode());
	}
	
	public void cambiarAEdit(UseCaseContext context) {
		setMode(new EditMode());
	}
	
	public void cambiarAView(UseCaseContext context) {
		setMode(new ViewMode());
	}
	
	public void volverDeCUDos(UseCaseContext context) {
		context.addElement("returnedFrom", "Caso de uso 2");
	}

	@Secure ({EditMode.class})
	@Description ("Seguro para habilitar la visibilidad del campo en el modo de edición.")
	public String getValor11() {
		return valor11;
	}

	@Secure ({EditMode.class})
	@Description ("Seguro para habilitar la edición del campo en el modo de edición.")
	public void setValor11(String valor11) {
		this.valor11 = valor11;
	}

	@Secure ({ViewMode.class})
	@Description ("Seguro para habilitar la visibilidad del campo en el modo de visualización.")
	public Long getValor12() {
		return valor12;
	}

	@Secure ({ViewMode.class})
	@Description ("Seguro para habilitar la edición del campo en el modo de visualización.")
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

	public String getValor31() {
		return valor31;
	}

	public void setValor31(String valor31) {
		this.valor31 = valor31;
	}

	public Long getValor32() {
		return valor32;
	}

	public void setValor32(Long valor32) {
		this.valor32 = valor32;
	}

	public Integer getValor33() {
		return valor33;
	}

	public void setValor33(Integer valor33) {
		this.valor33 = valor33;
	}

	public Date getFecha1() {
		return fecha1;
	}

	public void setFecha1(Date fecha1) {
		this.fecha1 = fecha1;
	}
	
}