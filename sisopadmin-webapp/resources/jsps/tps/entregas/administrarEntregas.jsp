<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Administración de entregas</h3>	
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
			<layout:line>
					<td><span class="inputLabel">Grupo</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.entrega.grupo.nombre}</span></td>
					<layout:submit reqCode="seleccionarGrupo" value="..." styleClass="formSubmit"/>
			</layout:line>
		
			<layout:formActions align="right">
				<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>				
			</layout:formActions>
		</layout:panel>		
	
		<layout:panel styleClass="subPanel" key="Resultado de la búsqueda" width="100%" align="center">
			<display:table uid="entity" name="useCaseForm.useCaseModel.entities" defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
							<input type="checkbox" name="useCaseModel.index" value="${entity_rowNum - 1}">
						</core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
							<input type="image" onclick="document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='select'" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onmouseover="Tip('Seleccionar la entrega')"/>
						</core:if>
					</display:column>	     							
				</layout:isMode>
				<display:column value="${entity.fase.tp.nombre}" title="TP"/>
				<display:column value="${entity.fase.nombre}" title="Fase"/>
				<display:column value="${entity.grupo.nombre}" title="Grupo"/>  		       	 			      
				<%--display:column value="${entity.descargarDeCVS ? 'SI' : 'NO'}" title="Descargar de repositorio"/--%>  		       	 			      
				<display:column value="${entity.fechaEntrega}" title="Fecha de entrega" format="{0,date,dd/MM/yyyy hh:mm}"/>  		       	 			      
				<display:column>
					<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='view';" title=" Visualizar"/>
				</display:column>
				<display:column>
					<core:if test="${not empty entity.pathArchivo}">
						<input type="image" src="/sisopadmin/resources/images/buttons/download.gif" value="Descargar" onclick="document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='descargarEntrega';" title=" Descargar entrega"/>
					</core:if>
				</display:column>
			</display:table>

			<layout:formActions>
				<layout:isMode mode="operation">			
					<input type="hidden" id="index" type="text" name="useCaseModel.index">						
				</layout:isMode>
				<layout:isMode mode="selection">
					<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">	
						<input type="hidden"  id="index" type="text" name="useCaseModel.index"/>						
					</core:if>
					<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">	
						<layout:submit reqCode="select" value="Seleccionar"/>		
					</core:if>
				</layout:isMode>
			</layout:formActions>
		</layout:panel>
	
		<layout:line>
			<layout:formActions align="right">
				<layout:isMode mode="operation">			
					<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.entregas.NotificarEntregaUseCaseModel.initUseCase.create">
						<layout:submit reqCode="create" value="Nueva entrega" styleClass="formSubmit"/>
					</layout:hasPermission>
				</layout:isMode>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
			</layout:formActions>
		</layout:line>
	</layout:panel>
	
</layout:form>