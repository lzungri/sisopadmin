package ar.com.grupo306.usecasefwk.usecases.stack.elements;

/**
 * Elemento que se almacena dentro del UseCaseStack.
 * 
 * @author lzungri
 */
public class StackElement {
	/**
	 * Indica si el StackElement puede ser extraído de la pila mediante el llamado a
	 * UseCaseStack#clearAllPopupeables
	 */
	private boolean canBePopupeable = true;

	
	public boolean getCanBePopupeable() {
		return canBePopupeable;
	}

	public void setCanBePopupeable(boolean canBePopupeable) {
		this.canBePopupeable = canBePopupeable;
	}

}