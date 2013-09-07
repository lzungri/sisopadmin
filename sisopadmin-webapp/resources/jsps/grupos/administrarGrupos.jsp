<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="mainCUForm">
	<h3 class="tituloCU">Administración de Grupos</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
		<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.find" evaluateMode="false">
			<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
				<layout:row>
				    <layout:text styleClass="inputText" property="useCaseModel.nombreGrupo" key="Nombre del Grupo:"/>		
					<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>
				</layout:row>
			</layout:panel>		
		</layout:hasPermission>

		<display:table uid="grupo" name="useCaseForm.useCaseModel.entities" defaultorder="ascending" requestURI="administrarGruposUseCase.do">
			<layout:isMode mode="selection">
				<display:column class="imageColumn" title="Seleccionar">
					<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
				      	<input type="checkbox" name="useCaseModel.index" value="${grupo_rowNum - 1}">
				    </core:if>
					<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
		      			<input type="image" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onclick=" document.getElementById('index').value=${grupo_rowNum - 1}; this.form.elements['reqCode'].value='select'" onmouseout="window.status='Seleccionar';return true;" title=" Seleccionar"/>
				    </core:if>
				</display:column>	     							
			</layout:isMode>

			<display:column class="imageColumn"> 
				<core:if test="${grupo.estado eq 4}">
					<img src="/sisopadmin/resources/images/other/warning.png" onmouseover="TagToTip('contenidoDiv_${grupo.id}')"> 
					<div id="contenidoDiv_${grupo.id}">Grupo con conflicto de inscripción: ${grupo.motivoConflicto}</div>
				</core:if>
			</display:column>

			  <display:column class="textColumn" value="${grupo.nombre}" title="Nombre"/>
		      <display:column class="textColumn" value="${grupo.email}" title="Correo electrónico"/>  		       	 			      
			<layout:isMode mode="operation">
				<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.view" evaluateMode="false">
					<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${grupo_rowNum - 1}; this.form.elements['reqCode'].value='view';"  title=" Visualizar"/>  </display:column>
				</layout:hasPermission>
			  	<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.edit" evaluateMode="false">
			  		<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${grupo_rowNum - 1}; this.form.elements['reqCode'].value='edit';"  title=" Modificar"/>  </display:column>
			  	</layout:hasPermission>
			  	<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.remove" evaluateMode="false">
			  		<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${grupo_rowNum - 1}; this.form.elements['reqCode'].value='remove';"  title=" Eliminar"/>  </display:column>			  
			  	</layout:hasPermission>
			  <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.AdministrarGruposUseCaseModel.confirm" evaluateMode="false">
				  <display:column  class="imageColumn" title="Confirmado">
				  	 <core:if test="${grupo.estado < 3}">	
				  	 	<input type="image" src="/sisopadmin/resources/images/other/okVacio.gif" value="Confirmar Inscripción" onclick=" document.getElementById('index').value=${grupo_rowNum - 1}; this.form.elements['reqCode'].value='confirm'"  title=" Confirmar Inscripción"/> 
				  	</core:if>
				  	<core:if test="${grupo.estado >= 3}">
				  		<input type="image" src="/sisopadmin/resources/images/other/ok.gif" value="Confirmado" onclick="return false;"  title=" Confirmado"/>  
				  	</core:if>			  
				  </display:column>			  
			  </layout:hasPermission>
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
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:panel>
 </layout:form>