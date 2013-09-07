package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


/**
 * Genera un div que se puede ocultar o mostrar dinámicamente.
 *
 * @author Leandro
 */
public class ToggeableTag extends TagSupport {
	private String id;
	private String text;
	private String style;
	private String containerStyle;
	private String tooltip;
	
	public int doStartTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		buffer
			.append("<table " + this.resolveContainerStyle() + "><tr><td>")
			.append("<span " + this.resolveTooltip() + " onclick='" + this.id + "Slider.toggle();' " + this.resolveStyle() + ">")
			.append("\n<img src='" + ((HttpServletRequest) pageContext.getRequest()).getContextPath() + "/resources/images/buttons/plus.png'>")
			.append("\n" + this.text)
			.append("</span>")
			.append("\n<div id='" + this.id + "'>");
		
		try {
			pageContext.getOut().write(buffer.toString());
		}
		catch(IOException excep) {
			throw new JspException(excep);
		}
		
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		buffer
			.append("</div></td></tr></table>")
			.append("\n<script>var " + this.id + "Slider = new Fx.Slide('" + this.id + "', {duration: 500});</script>")
			.append("\n<div id='toolTipDiv_" + this.id + "'></div>");
		
		try {
			pageContext.getOut().write(buffer.toString());
		}
		catch(IOException excep) {
			throw new JspException(excep);
		}
		
		return EVAL_PAGE;
	}



	private String resolveTooltip() {
		return "onmouseover=\"if(" + this.id + "Slider.isMinimized()) {" +
			"document.getElementById('toolTipDiv_" + this.id + "').innerHTML = document.getElementById('" + this.id + "').innerHTML;" + 
			" TagToTip('toolTipDiv_" + this.id + "')}" + (this.tooltip != null ? "else {Tip('" + this.tooltip + "')}\"" : "") + "\"";
	}
	
	private String resolveStyle() {
		return this.style != null ? "class='" + this.style + "'" : "class='toggleSpan'";
	}
	
	private String resolveContainerStyle() {
		return this.containerStyle != null ? "class='" + this.containerStyle + "'" : "class='toggleContainerClass'";
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getStyle() {
		return style;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getTooltip() {
		return tooltip;
	}
	
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public void release() {
		super.release();
		this.id = null;
		this.style = null;
		this.text = null;
		this.tooltip = null;
		this.containerStyle = null;
	}

	public String getContainerStyle() {
		return containerStyle;
	}

	public void setContainerStyle(String tableStyle) {
		this.containerStyle = tableStyle;
	}
	
}