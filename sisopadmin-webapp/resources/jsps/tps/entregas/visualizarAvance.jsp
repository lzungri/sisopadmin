<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Visualización de avance</h3>	
	<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
		<layout:line>
			<td><span class="inputLabel">Trabajo práctico</span></td>
			<td><span class="inputLabel">${useCaseForm.useCaseModel.tp.nombre}</span></td>
			<layout:submit reqCode="seleccionarTP" value="..." styleClass="formSubmit"/>
		</layout:line>
		<layout:line>
			<td><span class="inputLabel">Grupo</span></td>
			<td><span class="inputLabel">${useCaseForm.useCaseModel.grupo.nombre}</span></td>
			<core:if test="${not useCaseForm.useCaseModel.grupoLogueado}">
				<layout:submit reqCode="seleccionarGrupo" value="..." styleClass="formSubmit"/>
			</core:if>
		</layout:line>
		<layout:formActions align="right">
			<layout:submit reqCode="generateReport" value="Visualizar" styleClass="formSubmit"/>
		</layout:formActions>
	</layout:panel>
</layout:form>