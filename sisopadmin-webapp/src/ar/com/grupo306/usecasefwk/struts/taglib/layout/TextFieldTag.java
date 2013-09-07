package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import javax.servlet.jsp.JspException;

/**
 * Redefine el comportamiento del TextFieldTag de layout.
 *
 * @author Leandro
 */
public class TextFieldTag extends fr.improve.struts.taglib.layout.field.TextFieldTag {

	/**
	 * Se setea un maxlength por defecto.
	 */
	public int doStartLayoutTag() throws JspException {
		if(maxlength == null) {
			maxlength = "255";
		}
		
		return super.doStartLayoutTag();
	}

}