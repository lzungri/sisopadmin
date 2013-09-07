<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Información de la Cátedra</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		    <layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="remove:I"/>
			<layout:text styleClass="inputText" styleId="descripcion" property="useCaseModel.descripcion" key="Descripción:" mode="remove:I"/>
			<tr>
				<layout:calendar style="inputCalendar" id="fechaInicioID" beanName="useCaseForm" property="useCaseModel.fechaInicio" description="Fecha de Inicio de Publicación:"  mode="remove:I" />
			</tr>			
			<tr>
				<layout:calendar style="inputCalendar" id="fechaFinID" beanName="useCaseForm" property="useCaseModel.fechaFin" description="Fecha de Finilización de Publicación:"  mode="remove:I" />
			</tr>
			<layout:select styleClass="inputComboBox" property="useCaseModel.estado" key="Estado" mode="remove:I">
				<layout:option key="Activo" value="1"/>
				<layout:option key="Inactivo" value="0"/>
			</layout:select>
			
			<layout:isMode mode="remove">
				<layout:panel styleClass="subPanel" key="" width="100%" align="center">
					<table class="informacionHome" align="center">
						<layout:row styleClass="home">${useCaseForm.useCaseModel.contenido}</layout:row>
					</table>
				</layout:panel>	
			</layout:isMode>
			
			<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove'}">
				<layout:rte key="Contenido de la información" property="useCaseModel.contenido"/>
			</core:if>
					
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="altaInformacionCatedra" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:E,remove:N"/>
					<layout:submit reqCode="bajaInformacionCatedra" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:N,remove:E"/>
					<layout:submit reqCode="modificacionInformacionCatedra" value="Aceptar" styleClass="formSubmit" mode="edit:E,create:N,remove:N"/>
					<layout:submit reqCode="cancelarInformacionCatedra" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
	
	</layout:panel>
</layout:form>
