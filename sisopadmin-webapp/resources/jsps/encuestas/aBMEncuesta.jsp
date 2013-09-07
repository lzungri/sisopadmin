<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/default.css"/>




<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
    <layout:form action="aBMEncuestaUseCase" reqCode="acceptUseCase">
		<layout:panel styleClass="FORM" key="Datos de la encuesta" width="100%" align="center">
			    <layout:text styleId="Nombre:" property="useCaseModel.nombrePlantilla" key="Nombre"/>
				<layout:text styleId="fechaInicio" property="useCaseModel.fechaInicio" key="Fecha de inicio"/>
				<layout:text styleId="fechaFin" property="useCaseModel.fechaFin" key="Fecha de finalización"/>
				<layout:checkbox styleId="obligatoria" property="useCaseModel.obligatoria" key="Obligatoria"/>
				<layout:select property="useCaseModel.destinatario" key="Destinatario">
					<layout:option value="Grupo" />
					<layout:option value="Ayudante" />
				</layout:select>
				
		</layout:panel>
		<layout:panel styleClass="FORM" key="Datos del punto a encuestar" width="100%" align="center">
			    <layout:text styleId="nombrePunto" property="useCaseModel.nombrePunto" key="Nombre del punto:"/>
				<layout:text styleId="descripcionPunto" property="useCaseModel.descripcionPunto" key="Descripcion del punto:"/>
                		<layout:submit reqCode="cargarPuntoAEncuestar" value="AgregarPunto"/>				
		</layout:panel>
		
			<layout:row>
				<layout:column>
					<h3 class="formText">Puntos de la encuesta</h3>
				</layout:column>
			</layout:row>
			<layout:row>
			<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
	
		        <display:table id="punto" name="useCaseForm.useCaseModel.puntos"  defaultorder="ascending">
				      <display:column value="${punto.numero}" title="Numero" />
				      <display:column value="${punto.descripcion}" title="Descripcion"/>
				      <display:column> <input type="submit" value="Eliminar" onclick=" document.getElementById('index').value=${punto.numero}; this.form.elements['reqCode'].value='eliminarPunto'"/> </display:column>
				      <display:column> <input type="submit" value="Modificar" onclick=" document.getElementById('index').value=${punto.numero}; this.form.elements['reqCode'].value='modificarPunto'"/> </display:column>
				</display:table>
				    
        </layout:row>
    	<layout:submit reqCode="cargarPlantillaEncuesta" value="Aceptar"/>
    	<layout:submit reqCode="volver" value="Cancelar"/>

    </layout:form>
</layout:html>            