package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.ConditionalTagBase;

import ar.com.grupo306.usecasefwk.struts.forms.UseCaseForm;

/**
 * Evalua el body si el modo actual se corresponde con el indicado.
 * 
 * @author Sole
 */
public class IsModeTag extends ConditionalTagBase {
	private String mode;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	protected boolean condition() throws JspException {
		UseCaseForm useCaseForm = (UseCaseForm) pageContext.getRequest().getAttribute("useCaseForm");
		
		return useCaseForm.getUseCaseModel().getMode().getModeIdentifier().equals(this.mode);
	}

	public void release() {
		super.release();
		this.mode = null;
	}
	
}