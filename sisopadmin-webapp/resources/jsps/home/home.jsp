<%@ include file="/resources/jsps/templates/page-commons.jsp" %>
<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<layout:form>
		<display:table id="informacion" name="useCaseForm.useCaseModel.informaciones"  defaultorder="ascending">
				<display:column value="${informacion.contenido}" title="" />
		</display:table>
</layout:form>


