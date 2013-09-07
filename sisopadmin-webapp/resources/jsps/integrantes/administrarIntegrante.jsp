<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
    <layout:form action="administrarIntegranteUseCase" reqCode="acceptUseCase" styleClass="formColsColorx1" >

		<layout:panel styleClass="FORM" key="Criterios de Búsqueda" width="100%" align="center">
			    <layout:text property="useCaseModel.nombre" key="Nombre del integrante:" styleClass="formInput"/>
				<layout:text property="useCaseModel.apellido" key="Apellido del Integrante:" styleClass="formInput"/>
				<layout:select property="useCaseModel.tipoIntegrante" key="Tipo de Integrante">
					<layout:option value="Todos" />
					<layout:option value="Ayudante" />
					<layout:option value="Coordinador" /></layout:select>
				<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>				
		</layout:panel>		
		
		<layout:panel  styleClass="FORM" key="" width="100%" align="center">
			<display:table uid="integrante" name="useCaseForm.useCaseModel.entities" defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${informacion_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="submit" value=">" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='select'">
					    </core:if>
					</display:column>	     							
				</layout:isMode>
				  <display:column value="${integrante.nombre}" title="Nombre" />
			      <display:column value="${integrante.apellido}" title="Apellido"/>  		       	 			      
			      <display:column value="${integrante.email}" title="Correo Electrónico"/>
				  <display:column value="${integrante.tipoIntegrante}" title="Tipo Integrante"/>					
				<layout:isMode mode="operation">
				  <display:column><input type="submit" value="Visualizar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='view'"/>  </display:column>
				  <display:column><input type="submit" value="Modificar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='edit'"/>  </display:column>
   				  <display:column><input type="submit" value="Eliminar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='remove'"/>  </display:column>
   				  <display:column>
					<logic:equal name="integrante" property="tipoIntegrante" value="Coordinador">
				  		<input type="submit" value="AsignarAyudantes" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='asignarAyudantes'"/>  
				  	</logic:equal>
				  </display:column>
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
					<layout:submit reqCode="select" value="Seleccionar"/>		
				</core:if>
			</layout:isMode>
		</layout:panel>
		<layout:row>
			<layout:submit reqCode="cargarIntegrante" value="Cargar Integrante" styleClass="formSubmit"/>				
			<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
		</layout:row>
    </layout:form>
</layout:html>      