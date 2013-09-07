<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm" method="POST" enctype="multipart/form-data">
	<h3 class="tituloCU">Trabajo Práctico</h3>
   	<layout:panel styleClass="panel" key="Datos del TP" width="100%" align="center">
		    <layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.tp.nombre" key="Nombre:" mode="view:D,remove:D"/>			
			<layout:file styleClass="inputText" fileKey="Archivo" property="useCaseModel.archivoSubido" key="Archivo de Especificación:" mode="view:N,remove:N"/>
			<layout:isMode mode="edit">
				<layout:line>
					<td><span>(Ya posee un archivo asociado, presione examinar si desea modificarlo.)</span></td>
				</layout:line>
			</layout:isMode>
			<layout:space/>
			<layout:checkbox property="useCaseModel.enviarNotificacion" key="Enviar notificación" mode="view:N,edit:N,create:E,remove:N" onmouseover="Tip('Si se tilda se enviará una notificación a los ayudantes.')" styleClass="inputCheckbox"/>
			<layout:space/>

			<layout:panel styleClass="subPanel" key= "Fases del TP" width="100%" align="center">
				<display:table uid="fase" name="useCaseForm.useCaseModel.tp.fases" defaultorder="ascending" defaultsort="1">
					<display:column class="textColumnCentrado" value="${fase.numero}"  title="Número"/>				
					<display:column class="textColumn" value="${fase.nombre}"  title="Nombre"/>	
					<display:column class="textColumnCentrado" title="Entrega Obligatoria">
						<input type="checkbox" ${fase.entregaObligatoria ? "checked" : ""} disabled>
					</display:column>			
					<display:column class="imageColumn">
						<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${fase_rowNum - 1}; this.form.elements['reqCode'].value='viewFase';" title=" Visualizar"/>
					</display:column>		
					<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove'}">
						<display:column class="imageColumn">
							<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${fase_rowNum - 1}; this.form.elements['reqCode'].value='editFase';" title=" Modificar"/> 
						</display:column>				
						<display:column class="imageColumn">
							<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${fase_rowNum - 1}; this.form.elements['reqCode'].value='removeFase';" title=" Eliminar"/>
						</display:column>				
					</core:if>
				</display:table>
				<tr><td><table align="right">
					<layout:row>
						<layout:submit styleClass="formSubmit" reqCode="cargarFase" value="Nueva Fase" mode="view:N,remove:N"/>	
					</layout:row>
				</table></td></tr>
			</layout:panel>
			<layout:formActions align="right">						
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Aceptar" mode="edit:N,create:E,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Modificar" mode="edit:E,create:N,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="edit:N,create:N,remove:E,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
				</layout:row>
			</layout:formActions>
	</layout:panel>			
	<input type="hidden" id="index" name="useCaseModel.index" value="0"/>		
</layout:form>
