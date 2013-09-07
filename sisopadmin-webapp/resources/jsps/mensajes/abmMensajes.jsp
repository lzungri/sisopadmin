<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<script>
	
	function blanquearGrupo(){
		document.getElementsByName("useCaseModel.loginNameGrupoReceptor")[0].value = "";
	}
	
	function blanquearAyudante(){
		document.getElementsByName("useCaseModel.loginNameAyudanteReceptor")[0].value = "";
	}

	function blanquearCoordinador(){
		document.getElementsByName("useCaseModel.loginNameCoordinadorReceptor")[0].value = "";
	}
	
</script>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Mensaje</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
	<layout:isMode mode="create">	
		<layout:panel styleClass="subPanel" key="" width="100%" align="center">
		    <layout:text styleClass="inputText" property="useCaseModel.mensaje.titulo" key="Titulo:" mode="create:E"/>
			<layout:calendar id="fechaAlta" beanName="useCaseForm" property="useCaseModel.mensaje.fechaAlta" mode="create:I" description="Fecha alta:"/>
			<layout:text styleClass="inputText" property="useCaseModel.mensaje.emisor.nombreCompleto" key="Emisor:" mode="create:S"/>
			<logic:empty name="useCaseForm" property="useCaseModel.mensaje.receptor">
				<logic:notEmpty name="useCaseForm" property="useCaseModel.receptoresGrupos">
					<layout:select styleClass="inputSelect" property="useCaseModel.loginNameGrupoReceptor" key="Receptor Grupo:" onchange="blanquearAyudante();blanquearCoordinador();">
						<layout:option value=""/>
						<layout:optionsCollection property="useCaseModel.receptoresGrupos" label="nombre" value="loginName"/>
					</layout:select>			
				</logic:notEmpty>
				<logic:notEmpty name="useCaseForm" property="useCaseModel.receptoresAyudantes">
					<layout:select styleClass="inputSelect" property="useCaseModel.loginNameAyudanteReceptor" key="Receptor Ayudante:" onchange="blanquearGrupo();blanquearCoordinador();">
						<layout:option value=""/>
						<layout:optionsCollection property="useCaseModel.receptoresAyudantes" label="nombreCompleto" value="loginName"/>
					</layout:select>			
				</logic:notEmpty>
				<logic:notEmpty name="useCaseForm" property="useCaseModel.receptoresCoordinadores">
					<layout:select styleClass="inputSelect" property="useCaseModel.loginNameCoordinadorReceptor" key="Receptor Coordinador:" onchange="blanquearAyudante();blanquearGrupo();">
						<layout:option value=""/>
						<layout:optionsCollection property="useCaseModel.receptoresCoordinadores" label="nombreCompleto" value="loginName"/>
					</layout:select>			
				</logic:notEmpty>
			</logic:empty>
			<logic:notEmpty name="useCaseForm" property="useCaseModel.mensaje.receptor">
				<layout:text styleClass="inputText" property="useCaseModel.mensaje.receptor.nombreCompleto" key="Receptor:" mode="create:S"/>
			</logic:notEmpty>			
			<layout:rte property="useCaseModel.mensaje.contenido" key="Contenido:"/>
		</layout:panel>
	
		<layout:formActions align="right">
			<layout:row>
				<layout:submit reqCode="enviarMensaje" value="Enviar" styleClass="formSubmit"/>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>			
			</layout:row>	
		</layout:formActions>
	</layout:isMode>	

	<layout:isMode mode="view">	
		<layout:panel styleClass="subPanel" key="" width="100%" align="center">
		    <layout:text styleClass="inputText" property="useCaseModel.mensaje.titulo" key="Titulo:" mode="view:S"/>
			<layout:calendar id="fechaAlta" beanName="useCaseForm" property="useCaseModel.mensaje.fechaAlta" description="Fecha alta:" mode="view:I"/>
			<layout:text styleClass="inputText" property="useCaseModel.mensaje.emisor.nombreCompleto" key="Emisor:" mode="view:S"/>
			<layout:text styleClass="inputText" property="useCaseModel.mensaje.receptor.nombreCompleto" key="Receptor:" mode="view:S"/>
		</layout:panel>
		<layout:panel>${useCaseForm.useCaseModel.mensaje.contenido}</layout:panel>
		<layout:formActions align="right">
			<layout:row>
				<logic:notEmpty name="useCaseForm" property="useCaseModel.mensaje.mensajePadre">
					<layout:submit reqCode="irAMensajeAnterior" value="Anterior" styleClass="formSubmit"/>
				</logic:notEmpty>
				<logic:notEmpty name="useCaseForm" property="useCaseModel.mensaje.mensajeHijo">
					<layout:submit reqCode="irAMensajeSiguiente" value="Siguiente" styleClass="formSubmit"/>
				</logic:notEmpty>
				<logic:empty name="useCaseForm" property="useCaseModel.mensaje.mensajeHijo">
					<layout:submit reqCode="responder" value="Responder" styleClass="formSubmit"/>
				</logic:empty>
				<layout:submit reqCode="cancelUseCase" value="Volver" styleClass="formSubmit"/>	
			</layout:row>	
		</layout:formActions>
				
	</layout:isMode>	
	</layout:panel>
</layout:form>
