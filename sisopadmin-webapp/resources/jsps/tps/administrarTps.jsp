<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form action="administrarTPsUseCase" reqCode="acceptUseCase" styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Trabajos Prácticos</h3>
		<layout:panel  styleClass="subPanel" key="" width="100%" align="center">
			<layout:panel styleClass="panel" key="Criterios de Búsqueda" width="100%" align="center">
			    <layout:text styleClass="inputText" property="useCaseModel.nombreTp" key="Nombre del TP:"/>				
				<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>
			</layout:panel>	
			<display:table uid="tp" name="useCaseForm.useCaseModel.entities" defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column class="imageColumn" title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${tp_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="image" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='select'" onmouseout="window.status='Seleccionar';return true;" title=" Seleccionar"/>
					    </core:if>
					</display:column>	     							
				</layout:isMode>
				  <display:column class="textColumn" value="${tp.nombre}" title="Nombre" />
			      <display:column class="textColumnCentrado" value="${tp.cantidadFases}" title="Cantidad Fases"/>  		       	 			      
				<layout:isMode mode="operation">
					<display:column class="imageColumn" >
		 				<input type="image" src="/sisopadmin/resources/images/buttons/visualizarDetalle.gif" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='verInformacion'" onmouseout="window.status='Ver Información';return true;" title=" Ver Información"/>
		 			</display:column>
					<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.view" evaluateMode="false" >
				 		<display:column class="imageColumn" >
				 				<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='view';" onmouseout="window.status='Visualizar';return true;"  title=" Visualizar"> 
				 		</display:column>
				 	</layout:hasPermission>
				 	<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.edit" evaluateMode="false" >
					  	<display:column class="imageColumn" >
					  			<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='edit';" onmouseout="window.status='Modificar';return true;" title=" Modificar">
					    </display:column>
				  	</layout:hasPermission>
				  	<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.AdministrarTPsUseCaseModel.remove" evaluateMode="false" >
			 			<display:column class="imageColumn">
			 					<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='remove';" onmouseout="window.status='Eliminar';return true;" title=" Eliminar"> 
			 			</display:column>
		 			</layout:hasPermission>
		 			<display:column class="imageColumn" >
		 				<input type="image" src="/sisopadmin/resources/images/buttons/download.gif" onclick=" document.getElementById('index').value=${tp_rowNum - 1}; this.form.elements['reqCode'].value='descargarArchivoTp';" title=" Descargar especificación"/>
		 			</display:column>
				</layout:isMode>
			</display:table>		
			<layout:isMode mode="operation">			
				<input type="hidden" id="index" type="text" name="useCaseModel.index" value="0"/>						
			</layout:isMode>
			<layout:isMode mode="selection">
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">	
					<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>						
				</core:if>
				<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">	
					<layout:submit reqCode="select" value="Seleccionar"/>		
				</core:if>
			</layout:isMode>
			<layout:formActions align="right">
				<layout:row>
					<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.tps.ABMTpUseCaseModel.initUseCase" evaluateMode="false" >	
						<layout:submit reqCode="cargarTp" value="Nuevo TP" styleClass="formSubmit"/>				
					</layout:hasPermission>
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
  </layout:form>