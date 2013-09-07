package ar.com.grupo306.sisopadmin.web.context;


/**
 * Contexto que almacena la informaci�n correspondiente a un usuario no
 * logueado en el sistema.
 * 
 * Existir� una �nica instancia en el sistema, de esta manera todo usuario que
 * no se encuentre logueado estar� accediendo a la informaci�n cacheada aqu�,
 * evitando as� el acceso en exceso a la base. 
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