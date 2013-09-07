/**
 * 
 */
package ar.com.grupo306.sisopadmin.web.usecases.domain.tps.plantillas;

import java.util.List;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Consigna;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;

/**
 * @author Sole
 *
 */
public class AdministrarConsignasUseCaseModel extends AdminBaseUseCaseModel {
	Fase fase;
	Integer numero;	
	
	@Override
	@Transactional
	public void initUseCase(UseCaseContext useCaseContext) {
		super.initUseCase(useCaseContext);
		
		fase = (Fase)useCaseContext.getParameter("FASE");
		List<ModelObject> lista = CollectionFactory.createList(ModelObject.class);
		lista.addAll(fase.getConsignas());
		this.setEntities(lista);
	}

	@Override
	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return null;
	}

	@Override
	protected List<? extends ModelObject> doFind(UseCaseContext useCaseContext) {
		List<ModelObject> lista = CollectionFactory.createList(ModelObject.class);
		lista.add(Consigna.findMeByNumero(numero, fase));
		this.setEntities(lista);
		return getEntities();
	}

	@Override
	protected Class<? extends ModelObject> entityClass() {		
		return Consigna.class;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

}
