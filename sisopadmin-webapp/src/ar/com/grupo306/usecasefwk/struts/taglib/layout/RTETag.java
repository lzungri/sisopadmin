package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.html.Constants;

/**
 * Tag encargado de crear un Rich text editor.
 *
 * @author Leandro
 */
public class RTETag extends TagSupport {
	
	private String property;
	private String key;
	
	public int doStartTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		Object value = fr.improve.struts.taglib.layout.util.LayoutUtils.getBeanFromPageContext(pageContext, Constants.BEAN_KEY, this.property);
		
		buffer
			.append("<table align='right'><tr><th valign='top' class='formLabel'>")
			.append(this.key)
			.append("</th><td valign='top'><script>")
			.append("rteName = '" + this.property + "_content';")
			.append("rteFormName = '" + this.property + "';")
			.append("initRTE('" + (value != null ? value.toString().replaceAll("\n", "").replaceAll("\r", "") : "") + "');")
			.append("</script></td></tr></table>");
		
		try {
			pageContext.getOut().write(buffer.toString());
		}
		catch(IOException excep) {
			throw new JspException(excep);
		}
	
		return SKIP_BODY;
	}

	public void release() {
		super.release();
		this.property = null;
		this.key = null;
	}

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

}