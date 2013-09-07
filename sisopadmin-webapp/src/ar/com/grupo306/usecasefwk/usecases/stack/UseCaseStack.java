package ar.com.grupo306.usecasefwk.usecases.stack;

import java.util.EmptyStackException;
import java.util.Stack;

import ar.com.grupo306.usecasefwk.usecases.stack.elements.StackElement;
import ar.com.grupo306.usecasefwk.usecases.stack.elements.UseCaseParametersElement;

/**
 * Estructura de pila donde se almacena la información de los casos de uso
 * visitados.
 * 
 * @author lzungri
 */
public class UseCaseStack {
	
	private Stack useCaseStack = new Stack();
	
	/**
	 * Retorna el último elemento de la pila sin quitarlo.
	 */
	public StackElement viewStackElement() {
		if(!this.isEmpty()) {
			return (StackElement) this.useCaseStack.get(this.size() - 1);
		}
		
		throw new EmptyStackException();
	}
	
	/**
	 * Indica si el último elemento ingresado a la pila es del tipo <code>typeElement</code>
	 */
	public boolean isStackElementOfType(Class typeElement) {
		StackElement element = this.viewStackElement();
		
		return  element != null && typeElement.isAssignableFrom(element.getClass()); 
	}
	
	/**
	 * Retorna el último elemento de la pila y luego lo quita.
	 */
	public StackElement pop() {
		return (StackElement) this.useCaseStack.pop();
	}
	
	/**
	 * Popupea aquellos StackElements que tienen el atributo canBePopupeable en true.
	 * Los que poseen el atributo en true son aquellos que representan casos de uso
	 * básicos del sistema, como por ejemplo el HomeUseCase o el CU inicial.
	 */
	public void clearAllPopupeables() {
		while(!this.isEmpty()) {
			StackElement element = this.viewStackElement();
			if(element != null && element.getCanBePopupeable()) {
				this.pop();	
			}
			else {
				break;
			}
		}
	}
	
	/**
	 * Popupea todos los CU apilados excepto el primero.
	 */
	public void clearAllExceptFirst() {
		if(!this.isEmpty()) {
			StackElement first = (StackElement) this.useCaseStack.get(0);
			this.clear();
			this.push(first);
		}
	}
	
	/**
	 * Vacía la pila.
	 */
	public void clear() {
		this.useCaseStack.clear();
	}
	
	/**
	 * Inserta un elemento sobre la pila.
	 * 
	 * @param StackElement
	 */
	public void push(StackElement stackElement) {
		this.useCaseStack.push(stackElement);
	}
	
	public int size() {
		return this.useCaseStack.size();
	}
	
	public boolean isEmpty() {
		return this.size() <= 0;
	}
	
	/**
	 * Indica si el CU actual tiene asociado parámetros de entrada. 
	 */
	public boolean hasUseCaseParameters() {
		try {
			return this.isStackElementOfType(UseCaseParametersElement.class);
		}
		catch(EmptyStackException excep) {
			return false;
		}
	}	
	
}