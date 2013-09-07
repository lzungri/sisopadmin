<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form action="aBMFaseUseCase" reqCode="acceptUseCase" styleClass="mainCUForm">
	<h3 class="tituloCU">Fase</h3>
   	<layout:panel styleClass="panel" key="Datos de la Fase" width="100%" align="center">
   			<layout:text styleClass="inputText"  styleId="numero" property="useCaseModel.fase.numero" mode="view:I,edit:E,create:E,remove:I" key="Número"/>
		    <layout:text styleClass="inputText"  styleId="nombre" property="useCaseModel.fase.nombre" mode="view:I,edit:E,create:E,remove:I" key="Nombre"/>
		    <layout:calendar style="inputCalendar" id="fechaInicio" beanName="useCaseForm" property="useCaseModel.fase.fechaInicio" mode="view:I,edit:E,create:E,remove:I" description="Fecha de Inicio" />			
			<layout:calendar style="inputCalendar" id="fechaFin" beanName="useCaseForm" property="useCaseModel.fase.fechaFin" mode="view:I,edit:E,create:E,remove:I" description="Fecha de Finalización" />			
			<input id="useCaseModel.fase.entregaObligatoria" type="hidden" name="useCaseModel.fase.entregaObligatoria" value="${useCaseForm.useCaseModel.fase.entregaObligatoria}">		 
			<layout:checkbox  styleClass="inputCheckbox" property="useCaseModel.fase.entregaObligatoria" onchange="document.getElementById('useCaseModel.fase.entregaObligatoria').value=this.checked"  key="Entrega Obligatoria" mode="view:D,edit:E,create:E,remove:D"/>			

			
			<layout:panel styleClass="subPanel" key= "Consignas de la Fase" width="100%" align="center">
				<display:table uid="consigna" name="useCaseForm.useCaseModel.fase.consignas" defaultorder="ascending" defaultsort="1">
					<display:column class="textColumnCentrado" value="${consigna.numero}"  title="Numero" />				
					<display:column class="textColumn" value="${consigna.descripcion}"  title="Descripcion"/>				
					<display:column class="imageColumn">
						<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif"  onclick=" document.getElementById('index').value=${consigna_rowNum - 1}; this.form.elements['reqCode'].value='viewConsigna';" title=" Visualizar"/>
					</display:column>						
					<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove'}">
						<display:column class="imageColumn">
							<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" onclick=" document.getElementById('index').value=${consigna_rowNum - 1}; this.form.elements['reqCode'].value='editConsigna';" title=" Modificar"/>
						</display:column>				
						<display:column class="imageColumn">
							<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" onclick=" document.getElementById('index').value=${consigna_rowNum - 1}; this.form.elements['reqCode'].value='removeConsigna';" title=" Eliminar"/>
						</display:column>					
					</core:if>
				</display:table>
				<tr><td><table align="right">
					<layout:row>
						<layout:submit styleClass="formSubmit" reqCode="cargarConsigna" value="Nueva Consigna" mode="view:N,remove:N"/>		
					</layout:row>
				</table></td></tr>
			</layout:panel>
			<layout:formActions align="right">
					<layout:row>
						<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Aceptar" mode="edit:N,create:E,remove:N,view:N"/>
						<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Actualizar" mode="edit:E,create:N,remove:N,view:N"/>
						<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="edit:N,create:N,remove:E,view:N"/>
						<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
					</layout:row>
			</layout:formActions>
	</layout:panel>			
	<input type="hidden" id="index" name="useCaseModel.index" value="0"/>	
</layout:form>
