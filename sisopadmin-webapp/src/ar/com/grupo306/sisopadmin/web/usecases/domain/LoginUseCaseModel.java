package ar.com.grupo306.sisopadmin.web.usecases.domain;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;

/**
 * Modelo para el CU de login.
 *
 * @author Leandro
 */
public class LoginUseCaseModel extends BaseUseCaseModel {
	private String usuario = "";
	private String password = "";
	
	@Transactional
	public void login(UseCaseContext context) {
		// Si existe otro usuario logueado, entonces se lo desloguea.
		if(context.getUserContext().isUserLogged()) {
			logout(context);
		}
		
		// Se validan los datos del usuario.
		if(SisopAdminServiceProvider.getSecurityService().isValidUser(this.usuario, this.password)) {
			// En caso de ser válido se genera el contexto de usuario logueado.
			context.setUserContext(new LoggedUserContext(this.usuario));
			
			context.addMessage("Bienvenido/a " + this.usuario + ".");
			context.acceptUseCase();
		}
		else {
			context.addErrorMessage("Usuario y/o contraseña inválidos.");
		}		
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}