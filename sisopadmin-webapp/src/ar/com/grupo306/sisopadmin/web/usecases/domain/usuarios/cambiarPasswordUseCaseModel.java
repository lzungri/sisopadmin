package ar.com.grupo306.sisopadmin.web.usecases.domain.usuarios;

import ar.com.grupo306.commons.annotations.Description;
import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.commons.exceptions.core.BusinessException;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.annotations.Secure;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.ABMBaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseValidationUtils;

/**
 * Modelo para el CU de cambio de Password.
 *
 * @author Damian
 */

public class cambiarPasswordUseCaseModel extends ABMBaseUseCaseModel{
	private String passwordOriginal01="";
	private String passwordOriginal02="";
	private String passwordNueva01="";
	private String passwordNueva02="";	
	private int entradas=0;
	
	@Secure ({})
	@Description ("Cambiar contrase�a")
	public void initUseCase(UseCaseContext context) {
		super.initUseCase(context);
		entradas++;
	}
	

	/**
	 * Se encarga de realizar el cambio de password.
	 * @param context
	 * 
	 */
	@Transactional
	public void cambiarPassword(UseCaseContext context) {
		Usuario usuario;
		UserContext userContext = (UserContext)context.getUserContext();
		if(!userContext.isUserLogged()){
			throw new BusinessException("No se puede crear una encuesta ya que tiene que estar logueado el usuario en el sistema");
		}
		LoggedUserContext userContextLogged = (LoggedUserContext) userContext;
		usuario  = Usuario.findMeByLoginName(userContextLogged.getUserLoginName());
		
		passwordOriginal02 = usuario.getPassword();
		
		if (!validacionOK(new UseCaseValidationUtils(context))) {
			return;
		}
		
		//Cuando est� arreglado el mapeo
		usuario.setPassword(passwordNueva01);
		usuario.saveOrUpdate();

		context.addMessage("Contrase�a cambiada exitosamente.");
		context.acceptUseCase();
	}
			
	private boolean validacionOK(UseCaseValidationUtils validationUtils) {
		validationUtils.assertNotEmpty(passwordOriginal01,"Debe ingresar la contrase�a actual.");
		validationUtils.assertNotEmpty(passwordNueva01,"Debe ingresar la contrase�a nueva.");
		validationUtils.assertNotEmpty(passwordNueva02,"Debe ingresar la repetici�n de la contrase�a nueva.");
		validationUtils.assertCadenasIguales(passwordNueva01,passwordNueva02,"La contrase�as ingresadas no coinciden.");
		validationUtils.assertCadenasIguales(passwordOriginal01,passwordOriginal02,"La contrase�as ingresadas no coinciden.");
		validationUtils.assertTextoSeguro(passwordNueva01, "La contrase�a contiene caracteres no admitidos por el sistema.");
		if (passwordNueva01 != null && !passwordNueva01.trim().equals("") )
			validationUtils.assertPasswordLength(passwordNueva01, "La contrase�a ingresada debe tener al menos 5 caracteres");			
		return !validationUtils.hasErrors();
	}


	public int getEntradas() {
		return entradas;
	}


	public void setEntradas(int entradas) {
		this.entradas = entradas;
	}


	public String getPasswordNueva01() {
		return passwordNueva01;
	}


	public void setPasswordNueva01(String passwordNueva01) {
		this.passwordNueva01 = passwordNueva01;
	}


	public String getPasswordNueva02() {
		return passwordNueva02;
	}


	public void setPasswordNueva02(String passwordNueva02) {
		this.passwordNueva02 = passwordNueva02;
	}


	public String getPasswordOriginal01() {
		return passwordOriginal01;
	}


	public void setPasswordOriginal01(String passwordOriginal01) {
		this.passwordOriginal01 = passwordOriginal01;
	}


	public String getPasswordOriginal02() {
		return passwordOriginal02;
	}


	public void setPasswordOriginal02(String passwordOriginal02) {
		this.passwordOriginal02 = passwordOriginal02;
	}
	
	

}