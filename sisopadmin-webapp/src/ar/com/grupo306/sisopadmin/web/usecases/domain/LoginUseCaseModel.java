package ar.com.grupo306.sisopadmin.web.usecases.domain;

import javax.servlet.http.Cookie;

import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.commons.annotations.Transactional;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.mensajes.Mensaje;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Usuario;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;
import ar.com.grupo306.sisopadmin.web.context.LoggedUserContext;
import ar.com.grupo306.sisopadmin.web.usecases.domain.mensajes.AdministrarMensajesUseCase;
import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.models.base.BaseUseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Modelo para el CU de login.
 *
 * @author Leandro
 */
public class LoginUseCaseModel extends BaseUseCaseModel {
	private String usuario = "";
	private String password = "";
	
	private String forumReturnPath = "";
	
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
			
			context.addMessage("Bienvenido/a " + this.usuario + ". Podrá consultar las novedades en la barra lateral derecha.");
			
			Integer cantidadMensajesNoLeidos =  this.contarMensajesNoLeidos();
			if (cantidadMensajesNoLeidos > 0){
				context.addMessage("Usted tiene " + cantidadMensajesNoLeidos + " mensaje/s no leido/s. <a href='." + UseCaseUtils.getUseCaseInitPath(new AdministrarMensajesUseCase()) + "'>Ver mensajes</a>");
			}
			
			// Cookie para el login en el foro
			Cookie cookie = new Cookie("SISOP_ADMIN_FORO", this.usuario);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			context.addCookie(cookie);
			
			if (forumReturnPath != null && !"".equals(forumReturnPath)){
				context.addMessage("Presione <a href='.." + forumReturnPath + "'>aquí</a> para volver al foro");
			}
			
			context.acceptUseCase();
		}
		else {
			context.addErrorMessage("Usuario y/o contraseña inválidos.");
		}		
	}
	
	private Integer contarMensajesNoLeidos(){
		Usuario loggedUser = Usuario.findMeByLoginName(this.usuario);

		return SisopAdminServiceProvider.getPersistenceService().createCriteria(Mensaje.class)
				.add( Restrictions.eq("receptor", loggedUser) )
				.add( Restrictions.eq("leidoPorReceptor", false) )
				.list().size();
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

	public String getForumReturnPath() {
		return forumReturnPath;
	}

	public void setForumReturnPath(String forumReturnPath) {
		this.forumReturnPath = forumReturnPath;
	}

}