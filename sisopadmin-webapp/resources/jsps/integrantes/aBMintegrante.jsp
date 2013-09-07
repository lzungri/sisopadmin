<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Datos del Integrante de la Cátedra</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		    <layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="view:I,remove:I"/>
			<layout:text styleClass="inputText" styleId="apellido" property="useCaseModel.apellido" key="Apellido:" mode="view:I,remove:I"/>				
			<layout:select  styleClass="inputSelect" property="useCaseModel.tipoIntegrante" key="Tipo de Integrante" mode="edit:I,view:I,remove:I">
				<layout:option value="Ayudante" />
				<layout:option value="Coordinador" />
			</layout:select>
			<layout:text styleClass="inputText" styleId="correoElectronico" property="useCaseModel.correoElectronico" key="Correo Electrónico: "  mode="view:I,remove:I"/>
			<layout:text styleClass="inputText" styleId="nombreUsuario" property="useCaseModel.nombreUsuario" key="Nombre de Usuario:"  mode="view:I,remove:I" />			
			<layout:password styleClass="inputText" property="useCaseModel.password" key="Contraseña: " mode="edit:N,view:N,remove:N"></layout:password>
			<layout:password styleClass="inputText" property="useCaseModel.passwordBis" key="Repetir contraseña: " mode="edit:N,view:N,remove:N" ></layout:password>
			<layout:calendar style="inputCalendar" id="fechaIngreso" beanName="useCaseForm" property="useCaseModel.fechaIngresoCatedra" description="Ingreso a la Cátedra"  mode="view:D,remove:D" />			
	
			<layout:panel styleClass="subPanel" key="Datos configurables" width="100%" align="center">
					<layout:text styleClass="inputText" styleId="cantidadGrupos" property="useCaseModel.cantidadGrupos" key="Cantidad de Grupos:"  mode="view:I,remove:I"/>					
			</layout:panel>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Aceptar" mode="edit:N,create:E,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Actualizar" mode="edit:E,create:N,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="edit:N,create:N,remove:E,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
				</layout:row>
			</layout:formActions>
	</layout:panel>
	
</layout:form>
