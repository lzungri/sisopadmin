<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Administración de Evaluaciones de Fases</h3>	
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
			<layout:line>
					<td><span class="inputLabel">Ayudante</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacionToFind.ayudanteEvaluador.nombre}</span></td>
					<layout:submit reqCode="seleccionarAyudante" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:line>
					<td><span class="inputLabel">Grupo</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacionToFind.grupo.nombre}</span></td>
					<layout:submit reqCode="seleccionarGrupo" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:line>
					<td><span class="inputLabel">Trabajo práctico</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacionToFind.fase.tp.nombre}</span></td>
					<layout:submit reqCode="seleccionarTP" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:select property="useCaseModel.evaluacionToFind.fase.id" key="Fase" styleClass="inputSelect">
				<option value="">Todas</option>
				<layout:optionsCollection property="useCaseModel.fases" value="id" label="nombre"/>
			</layout:select>
			<layout:select property="useCaseModel.evaluacionToFind.estado.id" key="Estado" styleClass="inputSelect">
				<option value="">Todos</option>
				<layout:optionsCollection property="useCaseModel.estados" value="id" label="descripcion"/>
			</layout:select>
		
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
							<input type="submit" value=">" onclick=" document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='select'">
						</core:if>
					</display:column>	     							
				</layout:isMode>
				<display:column class="textColumn" value="${entity.fase.tp.nombre}" title="TP"/>
				<display:column class="textColumn" value="${entity.fase.nombre}" title="Fase"/>
				<display:column class="textColumn" value="${entity.plantilla.nombre}" title="Plantilla"/>
				<display:column class="textColumn" value="${entity.ayudanteEvaluador.nombre}" title="Ayudante evaluador" />
				<display:column class="textColumn" value="${entity.grupo.nombre}" title="Grupo"/>  		       	 			      
				<display:column class="textColumn" value="${entity.estado.domainCode}" title="Estado"/>
				<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='view';" title=" Visualizar"/>  </display:column>
				<layout:isMode mode="operation">
					<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='remove';" title=" Eliminar"/>  </display:column>
				</layout:isMode>
			</display:table>
					
			<layout:formActions  align="right">
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
		
		<layout:formActions align="right">
			<layout:row>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:panel>
	
</layout:form>