package ar.com.grupo306.usecasefwk.usecases.menu;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;

/**
 * Interfaz común para los creadores de menús.
 *
 * @author Leandro
 */
public interface MenuBuilder {
	
	/**
	 * Agrega la cabecera correspondiente al Menú.
	 */
	public void appendHeader();
	
	/**
	 * Agrega la cola correspondiente al Menú.
	 */
	public void appendTail();
	
	/**
	 * Agrega un ítem simple al menú.
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
	 * Indica la existencia de un próximo elemento.
	 */
	public void hasNextElement();
	
	/**
	 * Construye el menú. El Object retornado dependerá del tipo de Builder.
	 */
	public Object build();

}