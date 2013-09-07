package ar.com.grupo306.usecasefwk.struts.taglib.layout;

import javax.servlet.jsp.PageContext;

import ar.com.grupo306.usecasefwk.usecases.models.base.UseCaseModel;
import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;

/**
 * Agrega funcionalidades especifícas del UseCase FWK al struts-layout.
 *
 * @author Leandro
 */
public class BasicFormUtils extends fr.improve.struts.taglib.layout.util.BasicFormUtils {

	public short computeFieldDisplayMode(AbstractModeFieldTag tag) {
		PageContext pageContext = tag.getPageContext();

		// Se obtiene el modelo actual.
		UseCaseModel useCaseModel = LayoutUtils.getActualUseCaseModel(pageContext);
		if(useCaseModel.getMode() == null) {
			return LayoutUtils.DEFAULT_MODE;
		}
		
		// Truchada porque el AbstractModeFieldTag inicializa el modo en E,E,I.
		if(tag.getMode() == null || tag.getMode().trim().equals("") || tag.getMode().equals("E,E,I")) {
			return LayoutUtils.computeFieldModeFromSecuredMethods(useCaseModel, tag.getProperty(), pageContext);
		}
		else {
			return LayoutUtils.computeFieldModeFromModeProperty(useCaseModel, tag.getMode());
		}
	}

}