package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.archivos.Archivo;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para Caso de uso de Administrar Información de la Cátedra (CU 3.1.1).
 *
 * @author Damian
 */

public class AdministrarInformacionCatedraUseCaseModel extends BaseUseCaseModel{
	private String valorBusqueda;
	private String buscarPor;
	private int entradas=0;
	private Long id;
	private Collection<Informacion> informaciones = null;
	private Collection<Informacion> informacionesById = null;
	private Long index;
	
	
	@Secure ({})
	@Description ("Administración de Información de la Cátedra")
	@Transactional
	public void initUseCase(UseCaseContext context) {
		if(entradas==0){
			//informaciones = Informacion.findMeByMostrarInicio();
			informaciones = Informacion.findLike("nombre", "");
		}
		super.initUseCase(context);
	}
	
	
	@Transactional
	public void buscarInformacionCatedra(UseCaseContext context){
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
					
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede realizar la operación ya que el usuario tiene que estar logueado en el sistema");
		}
		
		
		if(buscarPor!=null && buscarPor.equalsIgnoreCase("0")){
			informaciones = Informacion.findLike("nombre",valorBusqueda);
		} else {
			informaciones = Informacion.findLike("descripcion",valorBusqueda);
		}
		
		int size = 0;
		if(informaciones!=null){
			size = informaciones.size();
		}
		context.addMessage("Se han encontrado " + size + " elementos que coinciden con el filtro.");
		entradas++;
		
		context.refreshUseCase();				
	}
	
	public void altaInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		context.goToChildUseCase(ABMInformacionCatedraUseCase.class, new CreateMode(), new HashMap(), "buscarInformacionCatedra");
	}
	
	@Transactional
	public void bajaInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		informacionesById = Informacion.findMeById(index);
		if(informacionesById!=null){
			Informacion informacion = (Informacion) informacionesById.iterator().next();
			parametros.put("informacionSelected",informacion);
		} 
		context.goToChildUseCase(ABMInformacionCatedraUseCase.class, new RemoveMode(), parametros,"buscarInformacionCatedra");
		
	}
	
	@Transactional
	public void modificacionInformacionCatedra(UseCaseContext context) {
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		Map parametros = new HashMap();
		informacionesById = Informacion.findMeById(index);
		if(informacionesById!=null){
			Informacion informacion = (Informacion) informacionesById.iterator().next();
			parametros.put("informacionSelected",informacion);
		} 
		context.goToChildUseCase(ABMInformacionCatedraUseCase.class, new EditMode(), parametros,"buscarInformacionCatedra");
		
	}
	
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertTextoSeguro(valorBusqueda,"El campo de busqueda contiene caracteres no admitidos por el sistema.");
		return !validationUtils.hasErrors();
	}
	
	
	// GETTER AND SETTERS 
	
	public String getBuscarPor() {
		return buscarPor;
	}

	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection getInformaciones() {
		return informaciones;
	}

	public void setInformaciones(Collection informaciones) {
		this.informaciones = informaciones;
	}

	public int getTest() {
		return entradas;
	}

	public void setTest(int test) {
		this.entradas = test;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}
	

	
	

}
