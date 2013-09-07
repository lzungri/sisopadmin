<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Evaluación de fase mediante plantilla</h3>	
	<layout:panel styleClass="panel" width="100%" align="center">
		<layout:panel styleClass="subPanel" key="Datos de la evaluación" width="100%" align="center">
			<layout:line>
				<td><span class="inputLabel">Trabajo práctico</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacion.fase.tp.nombre}</span></td>
			</layout:line>
			<layout:line>
				<td><span class="inputLabel">Fase</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacion.fase.nombre}</span></td>
			</layout:line>
			<layout:line>
				<td><span class="inputLabel">Grupo</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacion.grupo.nombre}</span></td>
			</layout:line>
			<layout:line>
				<td><span class="inputLabel">Plantilla</span></td>
				<td><span class="inputLabel">${useCaseForm.useCaseModel.evaluacion.plantilla.nombre}</span></td>
			</layout:line>
			<layout:line>
				<td><span class="inputLabel">Cantidad de items</span></td>
				<td><span class="inputLabel">${fn:length(useCaseForm.useCaseModel.evaluacion.plantilla.items)}</span></td>
			</layout:line>
		</layout:panel>
		
		<layout:panel styleClass="subPanel" key="Items de plantilla" width="100%" align="center">
			<logic:iterate id="itemEvaluacion" name="useCaseForm" property="useCaseModel.evaluacion.itemsEvaluacion">
				<layout:toggleable id="itemEvaluacion_${itemEvaluacion.itemPlantilla.id}" text="${itemEvaluacion.itemPlantilla.nombre} ${itemEvaluacion.itemPlantilla.obligatorio ? '(OBLIGATORIO)' : ''}" containerStyle="itemEvaluacionContainer">
					<table>
						<layout:line>
							<td><span class="inputLabel">Peso del ítem</span></td>
							<td><span class="inputLabel">${itemEvaluacion.itemPlantilla.peso}&nbsp;de&nbsp;${useCaseForm.useCaseModel.evaluacion.plantilla.sumatoriaPesosItems}</span></td>
						</layout:line>
						<layout:space/>
						<core:if test="${not empty itemEvaluacion.itemPlantilla.consigna}">
							<layout:line>
								<td><span class="inputLabel">Consigna asociada</span></td>
								<td><span class="inputLabel">${itemEvaluacion.itemPlantilla.consigna.descripcion}</span></td>
							</layout:line>	
						</core:if>					
						<layout:line>
							<td><span class="inputLabel">Procedimiento</span></td>
							<td><span class="inputLabel">${itemEvaluacion.itemPlantilla.procedimiento}</span></td>
						</layout:line>
						<layout:line>
							<td><span class="inputLabel">Resultados esperados</span></td>
							<td><span class="inputLabel">${itemEvaluacion.itemPlantilla.resultadoEsperado}</span></td>
						</layout:line>
						<layout:space/>
						<layout:line>
							<td><span class="inputLabel">Porcentaje de cumplimiento</span></td>
							<td>
								<layout:isMode mode="create,edit">
									<input
										type="text" name="porcentajeCumplimiento_${itemEvaluacion.itemPlantilla.id}"
										value="${itemEvaluacion.porcentajeCumplimiento}" 
										onchange="(this.value != '' && this.value < 50) ? document.getElementById('ayuda_${itemEvaluacion.itemPlantilla.id}').style.visibility='visible' : document.getElementById('ayuda_${itemEvaluacion.itemPlantilla.id}').style.visibility='hidden'"
										class="inputText" maxlength="3" style="width:30px;">
								</layout:isMode>
								<layout:isMode mode="view,remove">
									<span class="inputLabel">${itemEvaluacion.porcentajeCumplimiento}</span>
								</layout:isMode>
								<span class="inputLabel">%</span>
							</td>
						</layout:line>
						<layout:line>
							<td><span class="inputLabel">Observaciones</span></td>
							<td>
								<layout:isMode mode="create,edit">
									<textarea name="observacionesParticulares_${itemEvaluacion.itemPlantilla.id}" class="inputTextArea" style="width: 450px">${itemEvaluacion.observaciones}</textarea>
								</layout:isMode>
								<layout:isMode mode="view,remove">
									<span class="inputLabel">${itemEvaluacion.observaciones}</span>
								</layout:isMode>
							</td>
						</layout:line>
						<layout:isMode mode="create,edit">
							<layout:line>
								<td></td>
								<td>
									<span id="ayuda_${itemEvaluacion.itemPlantilla.id}" class="spanTooltip" onmouseover="TagToTip('ayudaContent_${itemEvaluacion.itemPlantilla.id}', TITLE, 'Observaciones ante baja calificación', CLOSEBTN, true, STICKY, true, TITLEFONTSIZE, '7pt')">
										<img src="resources/images/buttons/help.png">
									</span>
								</td>
								<div id="ayudaContent_${itemEvaluacion.itemPlantilla.id}">${itemEvaluacion.itemPlantilla.observacionBajaCalificacion}</div>
							</layout:line>
						</layout:isMode>
						<layout:space/>
					</table>
				</layout:toggleable>
			</logic:iterate>
			
			<table align="right" style="padding: 5px;">
				<tr><td>
					<layout:select property="useCaseModel.evaluacion.plantillaAprobada" key="Plantilla aprobada" styleClass="inputSelect" mode="create:E,edit:E,view:D,remove:D">
						<option value="false">No</option>
						<option value="true" ${useCaseForm.useCaseModel.evaluacion.plantillaAprobada ? 'selected' : ''}>Sí</option>
					</layout:select>
				</td></tr>
			</table>
		</layout:panel>
		
		<layout:formActions  align="right">
			<layout:row>
				<layout:submit reqCode="aceptarEvaluacion" value="Aceptar" mode="create:E,edit:E,view:N,remove:N" styleClass="formSubmit"/>
				<layout:submit reqCode="eliminarEvaluacion" value="Aceptar" mode="create:N,edit:N,view:N,remove:E" styleClass="formSubmit"/>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" mode="create:E,edit:E,view:E,remove:E" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:panel>
	
</layout:form>