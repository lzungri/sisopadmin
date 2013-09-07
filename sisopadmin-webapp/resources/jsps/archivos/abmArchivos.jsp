<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form method="POST" enctype="multipart/form-data" styleClass="mainCUForm">
	<h3 class="tituloCU">Archivo de la Cátedra</h3>
   	<layout:panel styleClass="panel" key="" width="100%" align="center">
		    <layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="edit:E,create:E,remove:D,view:D"/>
			<layout:text styleClass="inputText" styleId="descripcion" property="useCaseModel.descripcion" key="Descripción:" mode="edit:E,create:E,remove:D,view:D"/>				
			<layout:file styleClass="inputText" fileKey="Archivo" property="useCaseModel.archivoSubido" key="Archivo:" mode="edit:E,create:E,remove:D,view:D"/>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="altaArchivo" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:E,remove:N,view:N"/>
					<layout:submit reqCode="bajaArchivo" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:N,remove:E,view:N"/>
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
	</layout:panel>
</layout:form>