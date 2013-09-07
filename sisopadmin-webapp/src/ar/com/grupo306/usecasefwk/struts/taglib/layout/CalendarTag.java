package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.commons.utils.reflection.PropertyUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;


/**
 * Tag encargado de generar código HTML para visualizar un campo Fecha y una accíón
 * que muestra un calendario para cargar ese campo.
 * 
 * @author lzungri
 */
public class CalendarTag extends TagSupport {
	
	private static String DEFAULT_NAME = "datebox";
	private static String DEFAULT_IMAGE_RELATIVE_PATH = "/resources/images/calendar/calendar.gif";
	
	/* Ver cbank.tld para consultar info sobre cada campo.*/
	private String form;
	private String id;
	private String name;
	private String image;
	private String description;
	private String initialDate;
	private String toolTip;
	private String beanName;
	private String property;
	private String mode;
	
	private short fieldMode;
	

	public int doStartTag() throws JspException{
		this.computeFieldMode();
		
		if(fieldMode != AbstractModeFieldTag.MODE_NODISPLAY) {
			StringBuffer buffer = new StringBuffer();
			
			this.createInputDescriptionHTML(buffer, fieldMode);
			this.createInputDateHTML(buffer, fieldMode);
			this.createCalendarHTML(buffer, fieldMode);
	
			try {
				pageContext.getOut().write(buffer.toString());
			}
			catch(IOException excep) {
				throw new JspException(excep);
			}
		}
		
		return SKIP_BODY;
	}
	
	private void computeFieldMode() {
		// Se obtiene el modelo actual.
		UseCaseModel useCaseModel = LayoutUtils.getActualUseCaseModel(pageContext);
		if(useCaseModel.getMode() == null) {
			this.fieldMode = LayoutUtils.DEFAULT_MODE;
		}
		
		// Truchada porque el AbstractModeFieldTag inicializa el modo en E,E,I.
		if(this.getMode() == null || this.getMode().trim().equals("") || this.getMode().equals("E,E,I")) {
			this.fieldMode = LayoutUtils.computeFieldModeFromSecuredMethods(useCaseModel, this.getProperty(), pageContext);
		}
		else {
			this.fieldMode = LayoutUtils.computeFieldModeFromModeProperty(useCaseModel, this.getMode());
		}
	}
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void release() {
		this.image = null;
		this.description = null;
		this.form = null;
		this.initialDate = null;
		this.toolTip = null;
		this.id = null;
		this.beanName = null;
		this.property = null;
	}
	
	
	
	public String getId() {
		return id;
	}	
	
	public String getName() {
		return this.name;
	}
	
	public String getImage() {
		return this.image;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getInitialDate() {
		return this.initialDate;
	}
	
	public String getForm() {
		return this.form;
	}
	
	public String getToolTip() {
		return this.toolTip;
	}
	
	public String getBeanName() {
		return beanName;
	}

	public String getProperty() {
		return property;
	}	
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String fieldDescription) {
		this.description = fieldDescription;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public void setInitialDate(String initialDate) {
		this.initialDate = initialDate;
	}
	
	public void setForm(String form) {
		this.form = form;
	}
	
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setProperty(String property) {
		this.property = property;
	}	
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * Los métodos resolve se encargan de entregar un valor por defecto si la property
	 * es null, ó su propio valor en caso que sea != null.
	 * @return
	 */
	private String resolveImage() {
		/* Por defecto se carga calendar.gif. */
		return (this.image != null) ? this.image : this.getDefaultImage();
	}
	
	private String resolveId() {
		return (this.id != null) ? this.id : "" ;
	}	
	
	private String resolveName() {
		if(this.name != null) {
			return this.name;
		}
		
		return (this.property != null) ? this.property : DEFAULT_NAME ;
	}
	
	private String resolveDescription() {
		return (this.description != null) ? this.description : "" ;
	}
	
	private String resolveToolTip() {
		return (this.toolTip != null) ? this.toolTip : this.resolveDescription();
	}
	
	private String resolveInitialDate() {
		if(this.initialDate != null) {
			return this.initialDate;
		}
		if(this.beanName != null && this.property != null) {
			Object bean = this.getBeanFromScope(this.beanName);
			try {
				if(bean != null) {
					Object propertyValue = PropertyUtils.getProperty(bean, this.property);
					if(propertyValue != null && propertyValue instanceof Date) {
						return DateUtils.getDateTimeFormat((Date) propertyValue, SisopAdminConstants.DATE_FORMAT_PATTERN);
					}
					else {
						return propertyValue != null ? propertyValue.toString() : "";
					}
				}
			}
			catch(Exception excep) {
	 			throw ExceptionFactory.createProgramException("Falla al intentar acceder a la property " + this.property + " sobre el bean " + this.beanName, excep);
			}
		}
		return "";
	}
	
	/**
	 * Realiza la búsqueda del beanName sobre los siguientes scopes (en el mismo orden):
	 * <li>PageContext.
	 * <li>Request.
	 * <li>Session.
	 * 
	 * @param beanName
	 */
	private Object getBeanFromScope(String beanName) {
		Object bean = null;
		
		if((bean = pageContext.getAttribute(this.beanName)) != null)
			return bean;
		else if((bean = pageContext.getRequest().getAttribute(this.beanName)) != null) 
			return bean;
		else if((bean = pageContext.getSession().getAttribute(this.beanName)) != null)
			return bean;
		
		return null;
	}
	
	private String getDefaultImage() {
		return ((HttpServletRequest) pageContext.getRequest()).getContextPath() + DEFAULT_IMAGE_RELATIVE_PATH;
	}
	
	/**
	 * Encargado de crear la Descripción del campo date.
	 * @param buffer
	 */
	private void createInputDescriptionHTML(StringBuffer buffer, int fieldMode) {
		buffer.append("\n<td class=\"formLabel\">" + this.resolveDescription() + "</td>");		
	}
	
	/**
	 * Encargado de crear el campo date. 
	 * @param buffer
	 */
	private void createInputDateHTML(StringBuffer buffer, int fieldMode) {
		buffer.append("\n<td class=\"formColsColorx1\">");
		buffer.append("\n<input id=\"" + this.resolveId() + "\" name=\"" + this.resolveName() + "\" size=\"11\" \"readonly\" " +
				"class=\"formEdit\" type=\"text\" title=\"" + this.resolveToolTip() + "\"  value=\""
				+ this.resolveInitialDate() + "\" " + (fieldMode == AbstractModeFieldTag.MODE_INSPECT ? "disabled" : "") + ">&nbsp;");
	}
	
	/**
	 * Encargado de crear el ícono del calendario.
	 * @param buffer
	 */
	private void createCalendarHTML(StringBuffer buffer, int fieldMode) {
		if(fieldMode != AbstractModeFieldTag.MODE_DISABLED && fieldMode != AbstractModeFieldTag.MODE_INSPECT && fieldMode != AbstractModeFieldTag.MODE_READONLY) {
			buffer.append("\n<a href=\"javascript:show_calendar('" + this.getId() + "');\" " +
				"onmouseover=\"window.status='Calendario';return true;\" onmouseout=\"window.status='';" +
				"return true;\">");
			buffer.append("\n<img src=\"" + this.resolveImage() +"\" border=\"0\"  title=\" " + 
					this.resolveToolTip() +"\"/>");
			buffer.append("\n</a><br>\n</td>");
		}
	}

}