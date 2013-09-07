package ar.com.grupo306.sisopadmin.web.context;


/**
 * Contexto que almacena la información correspondiente a un usuario no
 * logueado en el sistema.
 * 
 * Existirá una única instancia en el sistema, de esta manera todo usuario que
 * no se encuentre logueado estará accediendo a la información cacheada aquí,
 * evitando así el acceso en exceso a la base. 
 *
 * @author Leandro
 */
public class NotLoggedUserContext extends UserContext {
	private static NotLoggedUserContext instance;
	
	private NotLoggedUserContext() {
		super();
		
		createMenu();
	}
	
	public static synchronized NotLoggedUserContext getInstance() {
		if(instance == null) {
			instance = new NotLoggedUserContext();
		}
		return instance;
	}

	public boolean isUserLogged() {
		return false;
	}
	
}