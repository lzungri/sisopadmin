package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import ar.com.grupo306.usecasefwk.struts.forms.UseCaseForm;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;

/**
 * Agrega funcionalidades especifícas del UseCase FWK al struts-layout.
 *
 * @author Leandro
 */
public class FormTag extends fr.improve.struts.taglib.layout.FormTag {
	private static String DEFAULT_REQCODE = "cancelUseCase";

	/**
	 * Setea automáticamente el action en base al useCaseModel actual.
	 */
	public int doStartLayoutTag() throws JspException {
		if(action == null) {
			UseCaseForm useCaseForm = (UseCaseForm) pageContext.getRequest().getAttribute("useCaseForm");
			action = UseCaseUtils.getUseCasePath(useCaseForm.getUseCaseModel().getUseCase());
		}
		
		if(reqCode == null) {
			reqCode = DEFAULT_REQCODE;
		}
		
		return super.doStartLayoutTag();
	}

}