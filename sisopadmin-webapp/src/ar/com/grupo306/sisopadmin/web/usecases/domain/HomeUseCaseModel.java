package ar.com.grupo306.sisopadmin.web.usecases.domain;

import java.util.Collection;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * Modelo para el caso de uso principal.
 *
 * @author Leandro
 */
public class HomeUseCaseModel extends BaseUseCaseModel {
	private Collection<Informacion> informaciones = null;
	
	@Transactional
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		
		informaciones = Informacion.findMeByMostrarInicio();
		int size = 0;
		if(informaciones!=null){
			size = informaciones.size();
		}
	}
	
	public void acceptUseCase(UseCaseContext context) {
		throw ExceptionFactory.createBusinessException("No es posible ejecutar esta operación.");
	}
	
	public void cancelUseCase(UseCaseContext context) {
		throw ExceptionFactory.createBusinessException("No es posible ejecutar esta operación.");
	}

	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		this.informaciones = Informacion.findMeByMostrarInicio();
	}
	
//	----------

	public Collection getInformaciones() {
		return informaciones;
	}

	public void setInformaciones(Collection informaciones) {
		this.informaciones = informaciones;
	}
	
}