package ar.com.grupo306.usecasefwk.usecases.message;

/**
 * Implementaci�n de un Message.
 *
 * @author Leandro
 */
public class BaseMessage implements Message {
	private String messageText;
	
	public BaseMessage(String messageText) {
		this.messageText = messageText;
	}

	public String getMessageText() {
		return this.messageText;
	}

}