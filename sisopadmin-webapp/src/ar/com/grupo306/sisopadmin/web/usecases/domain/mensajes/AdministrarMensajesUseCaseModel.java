package ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes;

import java.util.List;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.ABMBaseUseCase;
import ar.com.grupo306.usecasefwk.usecases.models.base.AdminBaseUseCaseModel;

/**
 * @author Pablo
 */
public class AdministrarMensajesUseCaseModel extends AdminBaseUseCaseModel{
	
	private String buscarPor;
	private String valorBusqueda;
	private String loggedUserLoginName;
	
	public String getLoggedUserLoginName() {
		return loggedUserLoginName;
	}

	public void setLoggedUserLoginName(String loggedUserLoginName) {
		this.loggedUserLoginName = loggedUserLoginName;
	}

	public String getValorBusqueda() {
		return valorBusqueda;
	}

	public void setValorBusqueda(String valorBusqueda) {
		this.valorBusqueda = valorBusqueda;
	}

	public String getBuscarPor() {
		return buscarPor;
	}

	public void setBuscarPor(String buscarPor) {
		this.buscarPor = buscarPor;
	}

	protected Class<? extends ModelObject> entityClass() {
		return Mensaje.class;
	}	

	protected Class<? extends ABMBaseUseCase> abmEntityUseCase() {
		return ABMMensajesUseCase.class;
	}
	
	@Transactional
	public void returnFromChildDefaultMethod(UseCaseContext context) {
		setEntities(doFind(context));
	}
	
	@Secure ({})
	@Description ("Acceder")
	@Transactional
	public void initUseCase(UseCaseContext context) {		
		super.initUseCase(context);
		loggedUserLoginName = ((LoggedUserContext)context.getUserContext()).getUserLoginName();		
	}
	
	protected boolean prePopulateUseCase() {
		return true;
	}
	
	protected List<ModelObject> doFindOnInit(UseCaseContext useCaseContext) { 
		String loggedUserLoginName = ((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName();
		Usuario loggedUser = Usuario.findMeByLoginName(loggedUserLoginName);
		
		return SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Mensaje mensaje " +
				"where mensaje.emisor = :loggedUser or mensaje.receptor = :loggedUser ")
				.setParameter("loggedUser", loggedUser)
				.list();
	}
	
	protected List<ModelObject> doFind(UseCaseContext useCaseContext){
		String loggedUserLoginName = ((LoggedUserContext) useCaseContext.getUserContext()).getUserLoginName();
		Usuario loggedUser = Usuario.findMeByLoginName(loggedUserLoginName);
		
		String filtro;
		if ("titulo".equals(buscarPor)){
			filtro = "mensaje.titulo like :valorBusqueda "; 
		} else if ("emisor".equals(buscarPor)){
			filtro = "(mensaje.emisor.nombre like :valorBusqueda or mensaje.emisor.apellido like :valorBusqueda) ";
		} else if ("receptor".equals(buscarPor)){
			filtro = "(mensaje.receptor.nombre like :valorBusqueda or mensaje.receptor.apellido like :valorBusqueda) ";
		} else {
			filtro = "mensaje.contenido like :valorBusqueda ";
		}
		
		List results = SisopAdminServiceProvider.getPersistenceService().createQuery(
				"from Mensaje mensaje " +				 
				"where " + filtro +
				"and (mensaje.emisor = :loggedUser or mensaje.receptor = :loggedUser) ")
				.setParameter("valorBusqueda", "%" + valorBusqueda + "%")
				.setParameter("loggedUser", loggedUser)
				.list();
					
		useCaseContext.addMessage("Se han encontrado " + results.size() + " elementos que coinciden con el filtro.");
		useCaseContext.refreshUseCase();
		return results;
	}
		
}