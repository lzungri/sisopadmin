<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

 <layout:form styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Plantillas de Trabajos Prácticos</h3>	
		<layout:panel  styleClass="panel" key="" width="100%" align="center" >
			<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
				<layout:row>
				<layout:line>
					<td><span class="inputLabel">Trabajo práctico:&nbsp;&nbsp;&nbsp;</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.tp.nombre}</span></td>
					<layout:submit reqCode="seleccionarTP" value="..." styleClass="formSubmit"/>
				</layout:line>
				</layout:row>
				<layout:select property="useCaseModel.id_fase" key="Fase">
					<layout:optionsCollection property="useCaseModel.fases" value="id" label="nombre"/>
				</layout:select>
				<layout:text styleClass="inputText" property="useCaseModel.nombre" key="Nombre de la plantila:"/>
			    <layout:text styleClass="inputText" property="useCaseModel.peso" key="Peso de la plantila:"/>
				<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>				
			</layout:panel>	
		
			<display:table uid="plantilla" name="useCaseForm.useCaseModel.entities" defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column  class="imageColumn" title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${plantilla_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="image" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onclick=" document.getElementById('index').value=${plantilla_rowNum - 1}; this.form.elements['reqCode'].value='select'" onmouseout="window.status='Seleccionar';return true;" title=" Seleccionar"/>
					    </core:if>
					</display:column>	     							
				</layout:isMode>
				  <display:column class="textColumn" value="${plantilla.nombre}" title="Nombre" />				
				  <display:column class="textColumn" value="${plantilla.fase.nombre}" title="Fase" />
			      <display:column class="textColumnCentrado" value="${plantilla.peso}" title="Peso"/>
			      <display:column class="textColumnCentrado" title="Obligatoria">
					${plantilla.obligatoria ? 'SI' : 'NO'}
				  </display:column>							  

				<layout:isMode mode="operation">				  
				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${plantilla_rowNum - 1}; this.form.elements['reqCode'].value='view';" title=" Visualizar"/>  </display:column>
				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${plantilla_rowNum - 1}; this.form.elements['reqCode'].value='edit';" title=" Modificar"/>  </display:column>
   				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${plantilla_rowNum - 1}; this.form.elements['reqCode'].value='remove';" title=" Eliminar"/>  </display:column>   				 
				</layout:isMode>
			</display:table>		
			<layout:isMode mode="operation">			
				<input type="hidden" id="index" type="text" name="useCaseModel.index" value="0"/>						
			</layout:isMode>
			<layout:isMode mode="selection">
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">	
					<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>						
				</core:if>
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">	
					<layout:submit styleClass="formSubmit" reqCode="select" value="Seleccionar"/>		
				</core:if>
			</layout:isMode>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="cargarPlantilla" value="Nueva Plantilla" styleClass="formSubmit"/>				
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
 </layout:form>