<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form method="POST" enctype="multipart/form-data" styleClass="mainCUForm">
	<h3 class="tituloCU">Notificar entrega</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:panel styleClass="subpanel" key="Datos de la entrega" width="100%" align="center">
			<layout:line>
				<td><span class="inputLabel">Trabajo práctico</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.entrega.fase.tp.nombre}</span></td>
				<layout:submit reqCode="seleccionarTP" value="..." styleClass="formSubmit" mode="create:E,edit:N,view:N,remove:N"/>
			</layout:line>
			<layout:line>
				<layout:select property="useCaseModel.entrega.fase.id" key="Fase" styleClass="inputSelect" mode="create:E,edit:N,view:N,remove:N">
					<layout:optionsCollection property="useCaseModel.fases" value="id" label="nombre"/>
				</layout:select>
			</layout:line>
			<layout:space/>
			<%--layout:line>
				<layout:checkbox property="useCaseModel.entrega.descargarDeCVS" onchange="this.checked ? document.getElementById('archivoContainer').style.visibility = 'hidden' : document.getElementById('archivoContainer').style.visibility = 'visible'" mode="create:E,edit:N,view:D,remove:N" key="Descargar entrega desde CVS" onmouseover="Tip('Si se activa, el ayudante tomará la entrega desde el repositorio del grupo.')"/>
			</layout:line--%>
			<layout:isMode mode="create">
				<tr id="archivoContainer">
					<td><span class="inputLabel">Archivo de entrega</span></td>
					<td><span class="inputLabel"><html:file property="useCaseModel.archivoDeEntrega" styleClass="inputText" onmouseover="Tip('Archivo (comprimido) con el contenido necesario para evaluar la entrega.')"/></span></td>
				</tr>
			</layout:isMode>
			<layout:space/>
			<layout:line>
				<layout:textarea property="useCaseModel.entrega.observaciones" key="Observaciones" mode="create:E,edit:N,view:I,remove:N" styleClass="inputObservaciones" onmouseover="Tip('Observaciones sobre la entrega.')"/>
			</layout:line>
		</layout:panel>		

		<layout:formActions align="right">
			<layout:submit reqCode="aceptar" value="Aceptar" styleClass="formSubmit" mode="create:E,edit:N,view:N,remove:N"/>				
			<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
		</layout:formActions>
	</layout:panel>
</layout:form>