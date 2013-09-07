package ar.com.grupo306.usecasefwk.usecases.models.base;

import ar.com.grupo306.usecasefwk.usecases.context.UseCaseContext;
import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Representa el modelo(pantalla) que ser� utilizado en el caso de uso.
 * Administrar� los atributos que se visualizar�n en el form y las funciones
 * de negocio que el usuario puede ejecutar desde la pantalla.
 * 
 * Aclaraci�n: Los m�todos que posean la Annotation Transactional ser�n
 * transaccionales, es decir, podr�n hacer uso del servicio de persistencia
 * ya que tienen una transacci�n creada a la base.
 * 
 * @author Leandro
 */
public interface UseCaseModel {
	public static String RETURN_FROM_CHILD_DEFAULT_METHOD = "returnFromChildDefaultMethod";
	
	public static String INIT_USE_CASE_METHOD = "initUseCase";

	/**
	 * Inicia el caso de uso, es decir, lo apila en el stack de caso de uso navegados.
	 * 
	 * <p>Util para cargar sobre el modelo la informaci�n que ser� mostrada en la 
	 * p�gina. Por ejemplo los combos o inputs con valores por defecto.
	 * 
	 * ACLARACION: SIEMPRE REALIZAR super.initUseCase(context) AL SOBREESCRIBIRLO.
	 * 
	 * @param context
	 */
	public void initUseCase(UseCaseContext context);
	
	/**
	 * 	Finaliza y ACEPTA el caso de uso, elimin�ndolo de la pila.
	 * Luego se inserta el modelo resultado sobre el el caso de uso padre.
	 * De esta manera el CU padre puede acceder al modelo que se obt�vo como
	 * resultado de llamar a un CU hijo.
	 * 
	 * ACLARACION: SIEMPRE REALIZAR super.acceptUseCase(context) AL SOBREESCRIBIRLO.
	 * 
	 * @param context
	 */
	public void acceptUseCase(UseCaseContext context);
	
	/**
	 * Finaliza y CANCELA el caso de uso, elimin�ndolo de la pila.
	 * Luego se inserta NULL sobre el modelo resultado en el caso de uso padre.
	 * 
	 * ACLARACION: SIEMPRE REALIZAR super.cancelUseCase(context) AL SOBREESCRIBIRLO.
	 * 
	 * @param context
	 */
	public void cancelUseCase(UseCaseContext context);

	
	
	/**
	 * M�todo que se invocar� cuando se retorne de un caso de uso hijo
	 * al cual no se le indic� el m�todo espec�fico de retorno.
	 * 
	 * @param context
	 */
	public void returnFromChildDefaultMethod(UseCaseContext context);
	

	
	/**
	 * Asigna al UseCaseModel su UseCase asociado.
	 * @param useCase
	 */
	public void setUseCase(UseCase useCase);
	
	/**
	 * Retorna el UseCase asociado al model.
	 */
	public UseCase getUseCase();
	
	/**
	 * Retorna el modo actual de UseCaseModel.
	 */
	public UseCaseModelMode getMode();

	public void setMode(UseCaseModelMode mode);
	
	/** Inicializa el modo del UseCaseModel. */
	public void initMode();
	
}