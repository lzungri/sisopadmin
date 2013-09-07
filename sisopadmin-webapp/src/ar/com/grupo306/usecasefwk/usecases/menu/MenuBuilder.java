package ar.com.grupo306.usecasefwk.usecases.menu;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;

/**
 * Interfaz com�n para los creadores de men�s.
 *
 * @author Leandro
 */
public interface MenuBuilder {
	
	/**
	 * Agrega la cabecera correspondiente al Men�.
	 */
	public void appendHeader();
	
	/**
	 * Agrega la cola correspondiente al Men�.
	 */
	public void appendTail();
	
	/**
	 * Agrega un �tem simple al men�.
	 * 
	 * @param useCase
	 */
	public void appendItem(UseCase useCase);
	
	/**
	 * Agrega el inicio de un item compuesto.
	 */
	public void appendInitCompositeItem(String description, String url);
	
	/**
	 * Realiza el cierre del item compuesto anterior inmediato.
	 */
	public void appendCloseCompositeItem();
	
	/**
	 * Indica la existencia de un pr�ximo elemento.
	 */
	public void hasNextElement();
	
	/**
	 * Construye el men�. El Object retornado depender� del tipo de Builder.
	 */
	public Object build();

}