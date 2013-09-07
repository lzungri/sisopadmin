<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<layout:form action="administrarInformacionCatedraUseCase" reqCode="">
    	<layout:panel styleClass="FORM" key="Información de la Catedra" width="100%" align="center">
	      		<layout:select property="useCaseModel.buscarPor" key="Buscar por: ">
					<layout:option key="Nombre" value="0"/>
					<layout:option key="Descripción" value="1"/>
				</layout:select>
		      	<layout:text styleId="valor" property="useCaseModel.valorBusqueda" key="Valor"/>
	      		<layout:submit reqCode="buscarInformacionCatedra" value="Aceptar" styleClass="formSubmit"/>
		</layout:panel>
		
		<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>
		<layout:panel  styleClass="FORM" key="" width="100%" align="center">
				<display:table id="informacion" name="useCaseForm.useCaseModel.informaciones"  defaultorder="ascending" style="formColsColorx1">
				      <display:column value="${informacion.nombre}" title="Nombre" />
				      <display:column value="${informacion.descripcion}" title="Descripción"/>
				      <display:column> <input type="submit" value="Eliminar" onclick=" document.getElementById('index').value=${informacion.id}; this.form.elements['reqCode'].value='bajaInformacionCatedra'"/> </display:column>
				      <display:column> <input type="submit" value="Modificar" onclick=" document.getElementById('index').value=${informacion.id}; this.form.elements['reqCode'].value='modificacionInformacionCatedra'"/> </display:column>
				</display:table>
		</layout:panel>

		<layout:panel styleClass="FORM" key="" width="100%" align="center">
				<layout:submit reqCode="altaInformacionCatedra" value="Cargar" styleClass="formSubmit"/>
		</layout:panel>
	</layout:form>
</layout:html>
