<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

 <layout:form styleClass="mainCUForm">
 	<h3 class="tituloCU">Item para la fase:&nbsp;${useCaseForm.useCaseModel.fase.nombre}</h3>
 	<layout:panel styleClass="panel" key="Datos del Item" width="100%" align="center">
	
	 	<layout:panel styleClass="panel" key="Consigna" width="100%" align="center">
			<layout:row>	
			<core:if test="${useCaseForm.useCaseModel.item.consigna != null}">			
				<layout:text property="useCaseModel.item.consigna.numero" key="Numero" mode="create:I,edit:I,view:D,remove:D"   />	
				<layout:textarea property="useCaseModel.item.consigna.descripcion" key="Descripcion" mode="create:I,edit:I,view:D,remove:D"/>	
			</core:if>
				<layout:panel key= "" width="100%" align="center">
					<tr valign="middle"><td><table align="center">
						<layout:submit styleClass="formSubmit" valign="middle" reqCode="seleccionarConsigna" value="..." mode="view:D,remove:D"/>	
					</table></td></tr>
				</layout:panel>
			</layout:row>
		</layout:panel>
	
	 	<layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.item.nombre" key="Nombre:" mode="view:D,remove:D"/>		
	 	<layout:text styleClass="inputText" styleId="peso" property="useCaseModel.item.peso" key="Peso:" mode="view:D,remove:D"/>		
		<layout:textarea styleClass="inputText" cols="50" rows="5" property="useCaseModel.item.procedimiento" key="Procedimiento:" mode="view:D,remove:D"/>		
		<layout:textarea styleClass="inputText" cols="50" rows="5" property="useCaseModel.item.resultadoEsperado" key="Resultado Esperado:" mode="view:D,remove:D"/>
		<layout:textarea styleClass="inputText" cols="50" rows="5" property="useCaseModel.item.observacionBajaCalificacion" key="Observaciones por baja calificación:" mode="view:D,remove:D"/>		 
		<input id="useCaseModel.item.obligatorio" type="hidden" name="useCaseModel.item.obligatorio" value="${useCaseForm.useCaseModel.item.obligatorio}">		 
 		<layout:checkbox styleClass="inputCheckbox" property="useCaseModel.item.obligatorio" onchange="document.getElementById('useCaseModel.item.obligatorio').value=this.checked" key="Obligatorio" mode="view:D,remove:D"/>

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