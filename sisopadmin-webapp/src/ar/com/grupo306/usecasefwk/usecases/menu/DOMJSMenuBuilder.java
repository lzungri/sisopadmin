package ar.com.grupo306.usecasefwk.usecases.menu;

import java.util.Stack;

import ar.com.grupo306.usecasefwk.usecases.domain.base.UseCase;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Crea el menú asociado a la herramienta de Javascript DOMMenu.
 *
 * @author Leandro
 */
public class DOMJSMenuBuilder implements MenuBuilder {
	private String menuContainerId;
	
	private StringBuffer menuBuffer = new StringBuffer();
	
	private Stack<Integer> actualPosition = new Stack<Integer>();
	
	
	
	public DOMJSMenuBuilder(String menuContainerId) {
		this.menuContainerId = menuContainerId;
	}

	public void appendHeader() {
		menuBuffer
			.append("domMenu_data.set('" + this.menuContainerId + "', ")
			.append("new Hash( \n");
		
		this.actualPosition.push(new Integer(0));
	}


	public void appendInitCompositeItem(String description, String url) {
		if(!actualPosition.isEmpty()) {
			actualPosition.push(actualPosition.pop() + 1);
			menuBuffer.append(actualPosition.peek() + ", ");
		}
		
		menuBuffer
			.append(" new Hash( \n")
			.append(description != null ? "'contents', '" + description + "', \n" : "")
			.append(url != null ? "'uri', '" + url + "', \n" : "");
		
		actualPosition.push(new Integer(0));
	}

	public void appendItem(UseCase useCase) {
		if(!actualPosition.isEmpty()) {
			actualPosition.push(actualPosition.pop() + 1);
			menuBuffer.append(actualPosition.peek() + ", ");
		}

		menuBuffer
			.append(" new Hash( \n")
			.append("'contents', '" + useCase.getShortDescription() + "', \n" )
			.append("'uri', '" + UseCaseUtils.getUseCaseInitPath(useCase) + "', \n")
			.append("'statusText', '"  + useCase.getLongDescription() + "') \n");
	}
	
	public void appendCloseCompositeItem() {
		if(!actualPosition.isEmpty()) {
			actualPosition.pop();
		} 
		menuBuffer.append(") \n");
	}
	
	public void appendTail() {
		menuBuffer.append(" ));");
	}

	public void hasNextElement() {
		menuBuffer.append(", ");
	}
	
	public Object build() {
		return this.menuBuffer.toString();
	}
	
}