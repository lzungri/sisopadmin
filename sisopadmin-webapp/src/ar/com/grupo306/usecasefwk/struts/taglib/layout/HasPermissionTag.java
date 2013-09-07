package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import java.util.Collection;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.ConditionalTagBase;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.struts.forms.UseCaseForm;

/**
 * Evalua el body si el modo actual se corresponde con el indicado.
 * 
 * @author Sole
 */
public class HasPermissionTag extends ConditionalTagBase {
	private String permissionKey;
	private Boolean evaluateMode;

	protected boolean condition() throws JspException {
		// Se obtienen los permisos del usuario.
		Collection<Permiso> userPermisos = ((UserContext) pageContext.getSession().getAttribute(SisopAdminWebConstants.SESSION_USER_CONTEXT)).getPermisosUsuario();
		UseCaseForm useCaseForm = (UseCaseForm) pageContext.getRequest().getAttribute("useCaseForm");
		
		boolean permissionOk = false;
		boolean permissionModeOk = false;
		
		for(Permiso permiso : userPermisos) {
			if(permiso.getDomainCode().equals(this.permissionKey)) {
				permissionOk = true;
			}
			if(this.evaluateMode && permiso.getDomainCode().equals(this.permissionKey + "." + useCaseForm.getUseCaseModel().getMode().getModeIdentifier())) {
				permissionModeOk = true;
			}
		}
		return (permissionOk && !this.evaluateMode) || (permissionOk && this.evaluateMode && permissionModeOk);
	}

	public String getPermissionKey() {
		return permissionKey;
	}

	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

	public void release() {
		super.release();
		this.permissionKey = null;
		this.evaluateMode = null;
	}

	public Boolean getEvaluateMode() {
		return evaluateMode;
	}

	public void setEvaluateMode(Boolean evaluateMode) {
		this.evaluateMode = evaluateMode;
	}

}