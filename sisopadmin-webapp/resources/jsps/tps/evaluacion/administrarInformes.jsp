<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Administración de Informes de evaluación</h3>	
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
			<layout:line>
					<td><span class="inputLabel">Ayudante:&nbsp;&nbsp;&nbsp;</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.informe.ayudanteEvaluador.nombreCompleto}</span></td>
					<layout:submit reqCode="seleccionarAyudante" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:line>
					<td><span class="inputLabel">Grupo:&nbsp;&nbsp;&nbsp;</span></td>
					<td><span class="inputLabel">${useCaseForm.useCaseModel.informe.grupoEvaluado.nombre}</span></td>
					<layout:submit reqCode="seleccionarGrupo" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.enviarInforme">
				<layout:select property="useCaseModel.informe.estado.id" key="Estado" styleClass="inputSelect">
					<option value="">Todos</option>
					<layout:optionsCollection property="useCaseModel.estados" value="id" label="descripcion"/>
				</layout:select>
			</layout:hasPermission>
		
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
				<display:column class="textColumn" value="${entity.ayudanteEvaluador.nombreCompleto}" title="Ayudante evaluador" />
				<display:column class="textColumn" value="${entity.grupoEvaluado.nombre}" title="Grupo"/>  		       	 			      
				<display:column class="textColumnCentrado" value="${entity.faseAprobada ? 'SI' : 'NO'}" title="Aprobada"/>
				<display:column class="textColumnCentrado" value="${entity.porcentajeEvaluado}" title="Porcentaje evaluado (%)" format="{0,number,#.##}"/>
				<display:column class="textColumnCentrado" value="${entity.porcentajeCumplimientoFase}" title="Porcentaje aprobación (%)" format="{0,number,#.##}"/>
				<display:column class="textColumnCentrado" value="${entity.fechaAlta}" title="Fecha de alta" format="{0,date,dd/MM/yyyy}"/>
				<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.enviarInforme">
					<display:column class="textColumnCentrado" value="${entity.estado.domainCode}" title="Estado"/>
				</layout:hasPermission>
				<display:column class="imageColumn">
					<core:if test="${not empty entity.pathInforme}">
						<input type="image" src="/sisopadmin/resources/images/buttons/download.gif" value="Descargar" onclick="document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='descargarInforme';" title=" Descargar"/>
					</core:if>
				</display:column>
				<layout:isMode mode="operation">
					<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.evaluacion.AdministrarInformesUseCaseModel.remove">
						<display:column class="imageColumn">
							<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick="document.getElementById('index').value=${entity_rowNum - 1}; this.form.elements['reqCode'].value='remove'; return confirm('Realmente desea eliminar el informe')" title=" Eliminar"/>
						</display:column>
					</layout:hasPermission>
					<display:column class="imageColumn">
						<table><layout:submit reqCode="enviarInforme" onclick="document.getElementById('index').value=${entity_rowNum - 1}; return confirm('¿Realmente desea enviar su informe al grupo?');" value="Enviar informe" styleClass="formSubmit"/></table>
					</display:column>
				</layout:isMode>
			</display:table>
					
			<layout:formActions align="right">
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