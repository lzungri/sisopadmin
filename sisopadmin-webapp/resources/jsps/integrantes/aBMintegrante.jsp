<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<div >
<layout:html>
	<layout:form action="aBMIntegranteUseCase" reqCode="acceptUseCase">
    	<layout:panel styleClass="FORM" key="Datos del integrante" width="100%" align="center">
			    <layout:text styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="view:D,remove:D"/>
				<layout:text styleId="apellido" property="useCaseModel.apellido" key="Apellido:" mode="view:D,remove:D"/>				
				<layout:select property="useCaseModel.tipoIntegrante" key="Tipo de Integrante" mode="edit:D,view:D,remove:D">
					<layout:option value="Ayudante" />
					<layout:option value="Coordinador" /></layout:select>
				<layout:text styleId="correoElectronico" property="useCaseModel.correoElectronico" key="Correo Electrónico: "  mode="view:D,remove:D"/>
				<layout:text styleId="nombreUsuario" property="useCaseModel.nombreUsuario" key="Nombre de Usuario:"  mode="view:D,remove:D" />			
				<layout:password property="useCaseModel.password" key="Contraseña: " mode="edit:N,view:N,remove:N"></layout:password>
				<layout:password property="useCaseModel.passwordBis" key="Repetir contraseña: " mode="edit:N,view:N,remove:N" ></layout:password>
				<layout:calendar id="fechaIngreso" beanName="useCaseForm" property="useCaseModel.fechaIngresoCatedra" description="Ingreso a la Cátedra"  mode="view:D,remove:D" />			
		</layout:panel>
		<layout:panel styleClass="FORM" key="Datos configurables" width="100%" align="center">
				<layout:text styleId="cantidadGrupos" property="useCaseModel.cantidadGrupos" key="Cantidad de Grupos:"  mode="view:D,remove:D"/>
				<layout:checkbox styleId="notificacionesEmail" property="useCaseModel.notificacionesEmail" key="Notificaciones por Email:"  mode="view:D,remove:D"/>
				<layout:checkbox styleId="resumenConsultas" property="useCaseModel.resumenConsultas" key="Resumen de Consultas:"  mode="view:D,remove:D"/>
		</layout:panel>
		<layout:row>
			<layout:submit reqCode="createAccept" value="Crear"/>
			<layout:submit reqCode="editAccept" value="Actualizar"/>
			<layout:submit reqCode="removeAccept" value="Eliminar"/>
			<layout:submit reqCode="cancelUseCase" value="Cancelar"/>
		</layout:row>
	</layout:form>
</layout:html>
</div>
