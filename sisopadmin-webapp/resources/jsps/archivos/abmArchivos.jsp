<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<layout:form action="aBMArchivosUseCase" reqCode="acceptUseCase">
    	<layout:panel styleClass="FORM" key="Upload de Archivo" width="100%" align="center">
			    <layout:text styleId="nombre" property="useCaseModel.nombre" key="Nombre:"/>
				<layout:text styleId="descripcion" property="useCaseModel.descripcion" key="Descripción:"/>				
				<layout:file fileKey="Archivo" property="useCaseModel.ruta" key="Archivo:"/>
		</layout:panel>
		<layout:submit reqCode="cargarArchivo" value="Aceptar" styleClass="formSubmit"/>
	</layout:form>
</layout:html>
