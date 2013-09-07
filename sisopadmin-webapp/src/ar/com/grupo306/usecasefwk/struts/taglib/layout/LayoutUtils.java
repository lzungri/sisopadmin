package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.jsp.PageContext;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.commons.utils.StringUtils;
import ar.com.grupo306.commons.utils.reflection.PropertyUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Permiso;
import ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants;
import ar.com.grupo306.sisopadmin.web.context.UserContext;
import ar.com.grupo306.usecasefwk.struts.forms.UseCaseForm;
import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import ar.com.grupo306.usecasefwk.usecases.utils.UseCaseUtils;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

/**
 * Utilidades para el taglib de Layout.
 *
 * @author Leandro
 */
public class LayoutUtils {
	public static short DEFAULT_MODE = AbstractModeFieldTag.MODE_EDIT;
	
	public static UseCaseModel getActualUseCaseModel(PageContext pageContext) {
		UseCaseForm useCaseForm = (UseCaseForm) pageContext.getRequest().getAttribute("useCaseForm");
		return useCaseForm.getUseCaseModel();
	}
	
	
	public static short computeFieldModeFromModeProperty(UseCaseModel useCaseModel, String mode) {
		StringTokenizer tokenizer = new StringTokenizer(mode, ",;:");
		while (tokenizer.hasMoreTokens()) {
			String modeToken = tokenizer.nextToken();
			if (!tokenizer.hasMoreTokens()) {
				throw new IllegalArgumentException("El modo " + mode + " es invalido.");
			}
			
			String fieldModeToken = tokenizer.nextToken();
 
			if (useCaseModel.getMode().getModeIdentifier().equals(modeToken)) { 
				switch (Character.toUpperCase(fieldModeToken.charAt(0))) {
					case 'E': return AbstractModeFieldTag.MODE_EDIT;
					case 'I': return AbstractModeFieldTag.MODE_INSPECT;
					case 'H': return AbstractModeFieldTag.MODE_HIDDEN;
					case 'N': return AbstractModeFieldTag.MODE_NODISPLAY;
					case 'S': return AbstractModeFieldTag.MODE_INSPECT_ONLY;
					case 'P': return AbstractModeFieldTag.MODE_INSPECT_PRESENT;
					case 'R': return AbstractModeFieldTag.MODE_READONLY;
					case 'D': return AbstractModeFieldTag.MODE_DISABLED;
					case 'C' : return AbstractModeFieldTag.MODE_CELL;
				}
			}				
		}
		
		return DEFAULT_MODE;
	}
	
	/**
	 * Indica el modo del campo en base a la especificación de seguridad asociada
	 * al UseCaseModel actual.
	 * 
	 * - Si el setter es seguro para el modo y el usuario logueado tiene el permiso entonces
	 * se lo muestra como EDIT.
	 * 
	 * - Si el setter es seguro para el modo y el usuario logueado no tiene el permiso se evalúa
	 * el getter:
	 * Si el getter es seguro para el modo y el usuario logueado tiene el permiso ó el
	 * getter no es seguro entonces INSPECT.
	 * Si el getter es seguro para el modo y el usuario logueado no tiene el permiso entonces DISABLED.
	 * 
	 * @param useCaseModel
	 * @param tag
	 * @pageContext
	 */
	public static short computeFieldModeFromSecuredMethods(UseCaseModel useCaseModel, String tagProperty, PageContext pageContext) {
		if(PropertyUtils.countProperties(tagProperty) > 1 && tagProperty.startsWith("useCaseModel")) {
			// Se obtienen los permisos del usuario.
			Collection<Permiso> userPermisos = ((UserContext) pageContext.getSession().getAttribute(SisopAdminWebConstants.SESSION_USER_CONTEXT)).getPermisosUsuario();
			
			// Property a presentar en pantalla.
			String propertyName = StringUtils.capitalize(PropertyUtils.getPropertyAt(tagProperty, 1));
			
			try {
				// En primer lugar se analiza la seguridad del setter.
				Class propertyType = useCaseModel.getClass().getMethod("get" + propertyName).getReturnType();
				if(UseCaseUtils.hasPermission(useCaseModel.getUseCase(), useCaseModel.getMode(), "set" + propertyName, new Class[] {propertyType}, userPermisos)) {
					// Se presenta en pantalla como editable.
					return AbstractModeFieldTag.MODE_EDIT;
				}
				else {
					// En segundo lugar se analiza la seguridad del getter.
					if(UseCaseUtils.hasPermission(useCaseModel.getUseCase(), useCaseModel.getMode(), "get" + propertyName, null, userPermisos)) {
						// Se presenta en pantalla como no modificable.
						return AbstractModeFieldTag.MODE_INSPECT;
					}
					else {
						// 	No se muestra en pantalla.
						return AbstractModeFieldTag.MODE_NODISPLAY;
					}
				}
			}
			catch (Exception excep) {
				throw ExceptionFactory.createProgramException("Error al intentar determinar el modo del campo " + tagProperty, excep);
			}
		}
		
		return LayoutUtils.DEFAULT_MODE;
	}


}