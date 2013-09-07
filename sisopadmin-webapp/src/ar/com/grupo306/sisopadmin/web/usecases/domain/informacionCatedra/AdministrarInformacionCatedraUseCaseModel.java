package ar.com.grupo306.sisopadmin.web.usecases.domain.informacionCatedra;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.informacion.Informacion;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.models.mode.CreateMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.EditMode;
import ar.com.grupo306.usecasefwk.usecases.models.mode.RemoveMode;

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
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
	}
	
	
	@Transactional
	public void buscarInformacionCatedra(UseCaseContext context){
		try{
			if (! validarBusqueda(context)) return;
						
			UserContext userContext = (UserContext)context.getUserContext();
			if(!userContext.isUserLogged()){
				throw new BusinessException("No se puede realizar la operación ya que el usuario tiene que estar logueado en el sistema");
			}
			
			informaciones = Informacion.findLike("nombre",valorBusqueda);
			int size = 0;
			if(informaciones!=null){
				size = informaciones.size();
			}
			context.addMessage("Se han encontrado " + size + " elementos que coinciden con el filtro.");
			entradas++;
			
			context.refreshUseCase();			
		}catch(Exception e){
			context.addElement("login.message", "No se pudo cargar la información de la cátedra");
			/*Debug*/
			context.addElement("login.message", e.toString());
			context.refreshUseCase();
		}
		
	}
	
	public void altaInformacionCatedra(UseCaseContext context) {
		if (! validarAlta(context)) return;
		/**TODO
		 * Llamar al CU ABMInformacionCatedra 
		 * Modo: ALTA
		 */
		context.goToChildUseCase(ABMInformacionCatedraUseCase.class,new CreateMode(), new HashMap(), "buscarInformacionCatedra");
	}
	
	@Transactional
	public void bajaInformacionCatedra(UseCaseContext context) {
		if (! validarBaja(context)) return;
		/**TODO
		 * Llamar al CU ABMInformacionCatedra 
		 * Modo: BAJA
		 */
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
		if (! validarModificacion(context)) return;
		/**TODO
		 * Llamar al CU ABMInformacionCatedra 
		 * Modo: MODIFICACION
		 */
		Map parametros = new HashMap();
		informacionesById = Informacion.findMeById(index);
		if(informacionesById!=null){
			Informacion informacion = (Informacion) informacionesById.iterator().next();
			parametros.put("informacionSelected",informacion);
		} 
		context.goToChildUseCase(ABMInformacionCatedraUseCase.class, new EditMode(), parametros,"buscarInformacionCatedra");
		
	}
	
	private Boolean validar(UseCaseContext context){
		Boolean valida = true;

		return valida;
	}

	private Boolean validarAlta(UseCaseContext context){
		Boolean valida = true;
		
		/**TODO
		 * Validar datos para ejecutar el CU de ALTA de Información de la Cátedra
		 */
		
		return valida;
	}
	
	private Boolean validarBaja(UseCaseContext context){
		Boolean valida = true;
		
		/**TODO
		 * Validar datos para ejecutar el CU de BAJA de Información de la Cátedra
		 * 
		 * por ej, el id de la info a borrar es válido? 
		 */
		
		return valida;
	}
	
	private Boolean validarModificacion(UseCaseContext context){
		Boolean valida = true;
		
		/**TODO
		 * Validar datos para ejecutar el CU de Modificacion de Información de la Cátedra
		 */
		
		return valida;
	}
	
	private Boolean validarBusqueda(UseCaseContext context){
		Boolean valida = true;
		
		/**TODO
		 * Validar datos para ejecutar el filtro de Información de la Cátedra
		 */
		
		return valida;
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
