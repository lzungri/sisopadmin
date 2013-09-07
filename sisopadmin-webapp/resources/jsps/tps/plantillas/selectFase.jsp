<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Administración de Plantillas de Trabajos Prácticos - Selección de Fase</h3>	
	<layout:panel styleClass="panel" key= "Seleccione la Fase" width="100%" align="center">
		<layout:row>
			<layout:column styleClass="textColumnCentrado">
				<layout:text property="useCaseModel.tp.nombre" key="Tp  " readonly="readonly"/>	
			</layout:column>
			<layout:column styleClass="textColumnCentrado">
				<layout:submit reqCode="seleccionarTP" value="..."/>	
			</layout:column>
		</layout:row>
		<layout:select property="useCaseModel.id_fase" key="Fase">
			<layout:optionsCollection property="useCaseModel.fases" value="id" label="nombre"/>
		</layout:select>
		<layout:formActions align="right">
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="irCrearPlantilla" value="Continuar"></layout:submit>	
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"></layout:submit>
				</layout:row>
		</layout:formActions>
	</layout:panel>
</layout:form>

