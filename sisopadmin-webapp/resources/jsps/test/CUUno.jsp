<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty name="returnedFrom">
	<h3 class="formText">Retornando del caso de uso: <%=request.getAttribute("returnedFrom")%></h3>
</logic:notEmpty>

<layout:form action="cUUnoUseCase" reqCode="acceptUseCase" styleClass="formColsColorx1">
	<table>
		<layout:row>
			<layout:column>
				<layout:text property="useCaseModel.valor11" key="Valor 1.1" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor12" key="Valor 1.2" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor13" key="Valor 1.3" styleClass="formInput"/>
			</layout:column>			
		</layout:row>		
		<layout:panel  styleClass="FORM" key="" width="100%" align="center">
			<display:table uid="informacion" name="useCaseForm.useCaseModel.entities"  defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${informacion_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="submit" value=">" onclick=" document.getElementById('index').value=${informacion_rowNum - 1}; this.form.elements['reqCode'].value='select'">
					    </core:if>
					</display:column>					
				</layout:isMode>
				<display:column value="${informacion.nombre}" title="Nombre" />
				<display:column value="${informacion.descripcion}" title="Descripción"/>
				<layout:isMode mode="operation">
					<display:column> <layout:submit value="Eliminar" reqCode="remove" onclick="document.getElementById('index').value=${informacion_rowNum - 1};"/> </display:column>
					<display:column> <layout:submit value="Editar" reqCode="edit" onclick="document.getElementById('index').value=${informacion_rowNum - 1};"/> </display:column>
				</layout:isMode>
			</display:table>		
			
			<layout:isMode mode="operation">
				<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>									
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
			<layout:column>
				<layout:submit reqCode="irACU1MultiSelect" value="CU 1 (Selección múltiple)" styleClass="formSubmit"/>				
			</layout:column>				
			<layout:column>
				<layout:submit reqCode="irACU1" value="CU 1 (Selección simple)" styleClass="formSubmit"/>				
			</layout:column>			
			<layout:column>
				<layout:submit reqCode="operationMode" value="CU 1 (OperationMode)" styleClass="formSubmit"/>				
			</layout:column>			
			<layout:column>
				<layout:submit reqCode="irACU2" value="CU 2 (Edit)" styleClass="formSubmit"/>				
			</layout:column>				
			<layout:column>			
				<layout:submit reqCode="irACU3" value="CU 3 (Edit)" styleClass="formSubmit"/>				
			</layout:column>
			<layout:column>
				<layout:submit reqCode="cancelUseCase" value="Salir" styleClass="formSubmit"/>				
			</layout:column>
		</layout:row>		
</layout:form>