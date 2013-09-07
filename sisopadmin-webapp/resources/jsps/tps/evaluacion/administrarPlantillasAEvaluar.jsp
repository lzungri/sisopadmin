<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Evaluar Fase en base a plantillas</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:panel styleClass="subpanel" key="Criterios de Búsqueda" width="100%" align="center">
			<layout:line>
				<td><span class="inputLabel">Trabajo práctico</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.tp.nombre}</span></td>
				<layout:submit reqCode="seleccionarTP" value="..." styleClass="formSubmit"/>
			</layout:line>
			<layout:line>
				<layout:select property="useCaseModel.faseId" key="Fase" styleClass="inputSelect">
					<layout:optionsCollection property="useCaseModel.fases" value="id" label="nombre"/>
				</layout:select>
			</layout:line>
			<layout:line>
				<td><span class="inputLabel">Grupo</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.grupo.nombre}</span></td>
				<layout:submit reqCode="seleccionarGrupo" value="..." styleClass="formSubmit"/>
			</layout:line>
	
			<layout:formActions align="right">
				<layout:submit reqCode="find" value="Ver plantillas" styleClass="formSubmit"/>				
			</layout:formActions>
		</layout:panel>		
		
		<layout:panel styleClass="subpanel" key="Plantillas a evaluar" width="100%" align="center">
			<display:table uid="relacion" name="useCaseForm.useCaseModel.evConsolidada.relaciones" defaultorder="descending" defaultsort="2" requestURI="administrarPlantillasAEvaluarUseCase.do">
				<display:column class="textColumn" value="${relacion.plantilla.nombre}" title="Código"/>
				<display:column class="textColumnCentrado" value="${relacion.plantilla.peso}" title="Peso"/>
				<display:column class="textColumnCentrado" value="${relacion.plantilla.obligatoria ? 'Sí' : 'No'}" title="Obligatoria"/>
				<display:column class="textColumnCentrado" title="Estado de plantilla">
					<core:if test="${empty relacion.evaluacion}">
						No evaluada
					</core:if>
					<core:if test="${not empty relacion.evaluacion}">
						${relacion.evaluacion.plantillaAprobada ? 'Aprobada' : 'No aprobada'}
					</core:if>
				</display:column>
				<display:column class="textColumnCentrado" title="Estado de evaluación">
					<core:if test="${empty relacion.evaluacion}">
						No evaluada
					</core:if>
					<core:if test="${not empty relacion.evaluacion}">
						${relacion.evaluacion.estado.descripcion}
					</core:if>
				</display:column>
				<display:column class="textColumn" title="Acciones">
					<input type="image" value="Evaluar plantilla" onclick=" document.getElementById('plantillaIndex').value=${relacion_rowNum - 1}; this.form.elements['reqCode'].value='evaluar';" title=" Evaluar fase en base a la plantilla" src="/sisopadmin/resources/images/buttons/modificar.gif"/>
					<core:if test="${not empty relacion.evaluacion}">
						<input type="image" value="Visualizar evaluación" onclick="document.getElementById('plantillaIndex').value=${relacion_rowNum - 1}; this.form.elements['reqCode'].value='view';" title=" Visualizar la evaluación" src="/sisopadmin/resources/images/buttons/visualizar.gif"/>
						<input type="image" value="Eliminar evaluación" onclick="document.getElementById('plantillaIndex').value=${relacion_rowNum - 1}; this.form.elements['reqCode'].value='remove';" title=" Eliminar la evaluación" src="/sisopadmin/resources/images/buttons/eliminar.gif"/>					
					</core:if>
				</display:column>
			</display:table>
			
		</layout:panel>
		
		<input type="hidden" id="plantillaIndex" name="useCaseModel.plantillaIndex">
		

    	<logic:notEmpty name="useCaseForm" property="useCaseModel.evConsolidada.relaciones">
    		<layout:panel styleClass="subpanel" key="" width="100%" align="right">
				<layout:line>
					<td><span class="inputLabel">Entrega evaluada</span></td>
					<td><span class="inputLabel" onmouseover="Tip('Fecha en la que el grupo realizó la entrega.')">${useCaseForm.useCaseModel.fechaEntregaEvaluada}</span></td>
					<td>
							<layout:submit reqCode="seleccionarEntrega" value="..." styleClass="formSubmit" onmouseover="Tip('Selecciona la entrega del grupo utilizada para evaluar la fase.')"/>
					</td>
			 	</layout:line>
				<layout:space/>
				<layout:line>
	    			<td><span>Fase aprobada</span></td>
					<td>
						<select name="useCaseModel.faseAprobada" class="inputSelect">
							<option value="false">No</option>
							<option value="true">Sí</option>
						</select>
					</td>
					<td></td>
				</layout:line>
				<layout:line>
					<td></td>
					<layout:formActions align="right">
						<layout:submit reqCode="generarInforme" value="Generar informe" styleClass="formSubmit" onmouseover="Tip('Genera el informe de la evaluación. Recuerde indicar si la fase se encuentra o no aprobada.')" onclick="return confirm('¿Ha indicado la aprobación de la fase?')"/>
					</layout:formActions>
				</layout:line>
			</layout:panel>
		</logic:notEmpty>

		<layout:formActions align="right">
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit" style="margin-left:100px;"/>
		</layout:formActions>
				
	</layout:panel>
	
</layout:form>