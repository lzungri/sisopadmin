<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

 <layout:form styleClass="mainCUForm">
 	<h3 class="tituloCU">Plantilla de corrección para la fase:&nbsp;${useCaseForm.useCaseModel.plantilla.fase.nombre}</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
	 	<layout:panel styleClass="subPanel" key="Datos de la Plantilla" width="100%" align="center">	
		 	<layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.plantilla.nombre" key="Nombre:" mode="view:D,remove:D"/>		 		
		 	<layout:text styleClass="inputText" styleId="peso" property="useCaseModel.plantilla.peso" key="Peso:" mode="view:D,remove:D"/>		 		
			<input id="useCaseModel.plantilla.obligatoria" type="hidden" name="useCaseModel.plantilla.obligatoria" value="${useCaseForm.useCaseModel.plantilla.obligatoria}">
			<layout:checkbox  styleClass="inputCheckbox" property="useCaseModel.plantilla.obligatoria" onchange="document.getElementById('useCaseModel.plantilla.obligatoria').value=this.checked" key="Obligatoria" mode="view:D,edit:E,create:E,remove:D"/>
		</layout:panel>
	 	<layout:panel styleClass="subPanel" key= "Items de la Plantilla" width="100%" align="center">
			<display:table uid="item" name="useCaseForm.useCaseModel.plantilla.items" defaultorder="ascending" defaultsort="1">
				<display:column class="textColumn" value="${item.nombre}"  title="Nombre" />				
				<display:column class="textColumnCentrado" value="${item.peso}"  title="Peso" />				
				<display:column class="textColumn" value="${item.procedimiento}"  title="Procedimiento" />				
				<display:column class="textColumnCentrado" title="Obligatorio">
					${item.obligatorio ? "SI" : 'NO'}
				</display:column>			
				<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif"  onclick=" document.getElementById('index').value=${item_rowNum - 1}; this.form.elements['reqCode'].value='viewItem';" title=" Visualizar"/></display:column>						
				<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove'}">
					<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" onclick=" document.getElementById('index').value=${item_rowNum - 1}; this.form.elements['reqCode'].value='editItem';" title=" Modificar"/> </display:column>				
					<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" onclick=" document.getElementById('index').value=${item_rowNum - 1}; this.form.elements['reqCode'].value='removeItem';" title=" Eliminar"/></display:column>					
				</core:if>
			</display:table>
			<tr><td><table align="right">	
				<layout:row>
					<layout:submit reqCode="cargarItem" value="Nuevo Item" styleClass="formSubmit" mode="view:N,edit:E,create:E,remove:N"/>				
				</layout:row>
			</table></td></tr>
		</layout:panel>
		<layout:formActions align="right">
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Aceptar" mode="view:N,edit:N,create:E,remove:N" />
					<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Modificar" mode="view:N,edit:E,create:N,remove:N"/>
					<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="view:N,edit:N,create:N,remove:E"/>
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
				</layout:row>
		</layout:formActions>
	</layout:panel>
	<input type="hidden" id="index" name="useCaseModel.index" value="0"/>
 </layout:form>