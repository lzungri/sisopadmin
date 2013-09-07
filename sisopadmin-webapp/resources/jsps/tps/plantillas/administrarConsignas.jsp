<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

 <layout:form styleClass="mainCUForm">	
 		<h3 class="tituloCU">Administración de Plantillas de Trabajos Prácticos - Consignas</h3>	
		<layout:panel  styleClass="panel" key="" width="100%" align="center" >
			<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
				<layout:text styleClass="inputText" property="useCaseModel.numero" key="Número:"/>
				<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit" />				
			</layout:panel>	
			<display:table uid="consigna" name="useCaseForm.useCaseModel.entities" defaultorder="ascending" defaultsort="${useCaseForm.useCaseModel.mode.modeIdentifier eq 'selection' ? 2 : 1}" requestURI="administrarConsignasUseCase.do">
				<layout:isMode mode="selection">
					<display:column class="textColumnCentrado" title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${consigna_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="image" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onclick=" document.getElementById('index').value=${consigna_rowNum - 1}; this.form.elements['reqCode'].value='select'" onmouseout="window.status='Seleccionar';return true;" title=" Seleccionar"/>
					    </core:if>
					</display:column>	     							
				</layout:isMode>
				
				  <display:column class="textColumnCentrado" value="${consigna.numero}" title="Numero" />
			      <display:column class="textColumn" value="${consigna.descripcion}" title="Descripción" />
							</display:table>		
			<layout:isMode mode="operation">			
				<input type="hidden" id="index" type="text" name="useCaseModel.index" value="0"/>						
			</layout:isMode>
			<layout:isMode mode="selection">
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">	
					<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>						
				</core:if>
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">	
					<layout:submit styleClass="formSubmit" reqCode="select" value="Seleccionar"/>		
				</core:if>
			</layout:isMode>
			<layout:formActions align="right">
					<layout:row>
						<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
					</layout:row>
			</layout:formActions>
		</layout:panel>
 </layout:form>