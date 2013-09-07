<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<layout:form action="aBMInformacionCatedraUseCase" reqCode="acceptUseCase">
    	<layout:panel styleClass="FORM" key="Información de la Catedra" width="100%" align="center">
			    <layout:text styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="remove:D"/>
				<layout:text styleId="descripcion" property="useCaseModel.descripcion" key="Descripción:" mode="remove:D"/>
				<layout:textarea cols="50" rows="20" property="useCaseModel.contenido" key="Contenido:" mode="remove:D"/>
				<tr>
					<layout:calendar id="fechaInicioID" beanName="useCaseForm" property="useCaseModel.fechaInicio" description="Fecha de Inicio de Publicación:"  mode="remove:D" />
				</tr>			
				<tr>
					<layout:calendar id="fechaFinID" beanName="useCaseForm" property="useCaseModel.fechaFin" description="Fecha de Finilización de Publicación:"  mode="remove:D" />
				</tr>
				<layout:select property="useCaseModel.estado" key="Estado" mode="remove:D">
					<layout:option key="Activo" value="1"/>
					<layout:option key="Inactivo" value="0"/>
				</layout:select>
				
		</layout:panel>
		<layout:submit reqCode="altaInformacionCatedra" value="Aceptar(ALTA)" styleClass="formSubmit" mode="edit:N,create:Y,remove:N"/>
		<layout:submit reqCode="bajaInformacionCatedra" value="Aceptar(BAJA)" styleClass="formSubmit" mode="edit:N,create:N,remove:Y"/>
		<layout:submit reqCode="modificacionInformacionCatedra" value="Aceptar(MODIF)" styleClass="formSubmit" mode="edit:Y,create:N,remove:N"/>
		<layout:submit reqCode="cancelarInformacionCatedra" value="Cancelar" styleClass="formSubmit"/>
	</layout:form>
</layout:html>
