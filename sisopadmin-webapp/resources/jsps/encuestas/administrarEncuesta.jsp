<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/default.css"/>

<layout:html>
    <layout:form action="administrarEncuestaUseCase" reqCode="acceptUseCase" styleClass="formColsColorx1" >
		<layout:panel styleClass="FORM" key="Búsqueda de encuestas" width="100%" align="center">
			    <layout:text styleId="nombreEncuesta" property="useCaseModel.nombrePlantillaBusqueda" key="Nombre de la encuesta:" styleClass="formInput"/>
				<layout:text styleId="fechaInicio" property="useCaseModel.fechaInicioBusqueda" key="Fecha de inico:" styleClass="formInput"/>
				<layout:text styleId="fechaFin" property="useCaseModel.fechaFinBusqueda" key="Fecha de finalización:" styleClass="formInput"/>
				<layout:submit reqCode="buscarEncuesta" value="Buscar" styleClass="formSubmit"/>				
		</layout:panel>
		

		
			<layout:row>
				<layout:column>
					<h3 class="formText">Resultado de la encuesta</h3>
				</layout:column>
			</layout:row>
			<layout:row>	
			<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
			
	        <display:table id="encuesta" name="useCaseForm.useCaseModel.encuestas"  defaultorder="ascending" style="formColsColorx1">
			      <display:column value="${encuesta.nombre}" title="Nombre" />
			      <display:column value="${encuesta.estado}" title="Estado"/>
			      <display:column value="${encuesta.fechaAlta}" title="Fecha de comienzo"/>
   			      <display:column value="${encuesta.fechaFin}" title="Fecha de finalización"/>
   			      <display:column> <input type="submit" value="Eliminar" onclick=" document.getElementById('index').value=${encuesta.nombre}; this.form.elements['reqCode'].value='eliminarEncuesta'"/> </display:column>
   			      <display:column> <input type="submit" value="Modificar" onclick=" document.getElementById('index').value=${encuesta.nombre}; this.form.elements['reqCode'].value='modificarEncuesta'"/> </display:column>
   			      <display:column> <input type="submit" value="Consultar" onclick=" document.getElementById('index').value=${encuesta.nombre}; this.form.elements['reqCode'].value='consultarEncuesta'"/> </display:column>
   			      <display:column> <input type="submit" value="Seleccionar" onclick=" document.getElementById('index').value=${encuesta.nombre}; this.form.elements['reqCode'].value='seleccionarEncuesta'"/> </display:column>
				  <display:column> <input type="submit" value="Llenar" onclick=" document.getElementById('index').value=${encuesta.nombre}; this.form.elements['reqCode'].value='llenarEncuesta'"/> </display:column>
		    </display:table>
</layout:row>
				<layout:submit reqCode="cargarEncuesta" value="Cargar" styleClass="formSubmit"/>	
				<layout:submit reqCode="cancelar" value="Cancelar" styleClass="formSubmit"/>	
    </layout:form>
</layout:html>       