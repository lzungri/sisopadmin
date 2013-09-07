<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

    <layout:form styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Integrantes de la Cátedra </h3>
		
		<layout:panel  styleClass="panel" key="" width="100%" align="center">
			
			<layout:panel styleClass="subPanel" key="Criterios de Búsqueda" width="100%" align="center">
				    <layout:text styleClass="inputText" property="useCaseModel.nombre" key="Nombre del integrante:"/>
					<layout:text styleClass="inputText" property="useCaseModel.apellido" key="Apellido del Integrante:"/>
					<layout:select styleClass="inputSelect" property="useCaseModel.tipoIntegrante" key="Tipo de Integrante">
						<layout:option value="Todos" />
						<layout:option value="Ayudante" />
						<layout:option value="Coordinador" /></layout:select>
					<layout:submit reqCode="find" value="Buscar" styleClass="formSubmit"/>				
			</layout:panel>		
			
			<display:table uid="integrante" name="useCaseForm.useCaseModel.entities" defaultorder="ascending">
				<layout:isMode mode="selection">
					<display:column  class="imageColumn" title="Seleccionar">
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection > 1}">
					      	<input type="checkbox" name="useCaseModel.index" value="${integrante_rowNum - 1}">
					    </core:if>
						<core:if test="${useCaseForm.useCaseModel.mode.maxSelection <= 1}">
			      			<input type="image" src="/sisopadmin/resources/images/buttons/seleccionar.gif" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='select'" onmouseout="window.status='Seleccionar';return true;" title=" Seleccionar"/>
					    </core:if>
					</display:column>	     							
				</layout:isMode>
				  <display:column class="textColumn" value="${integrante.nombre}" title="Nombre" />
			      <display:column class="textColumn" value="${integrante.apellido}" title="Apellido"/>  		       	 			      
			      <display:column class="textColumn" value="${integrante.email}" title="Correo Electrónico"/>
				  <display:column class="textColumn" value="${integrante.tipoIntegrante}" title="Tipo Integrante"/>					
				<layout:isMode mode="operation">
				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='view';" title=" Visualizar"/>  </display:column>
				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='edit';" title=" Modificar"/>  </display:column>
				<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.roles.ABMRolesUsuarioUseCaseModel.initUseCase" evaluateMode="false">
   				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/roles.gif" value="Roles" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='roles';" title=" Roles"/>  </display:column>
				</layout:hasPermission>
   				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/asignarGrupo.gif" value="Asignar Grupos" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='asignarGrupos';" title=" Asignar Grupos"/>  </display:column>
   				  <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='remove';" title=" Eliminar"/>  </display:column>
   				  <display:column class="imageColumn">
					<logic:equal name="integrante" property="tipoIntegrante" value="Coordinador">
				  		<input type="image" src="/sisopadmin/resources/images/buttons/asignarAyudante.gif" value="Asignar Ayudantes" onclick=" document.getElementById('index').value=${integrante_rowNum - 1}; this.form.elements['reqCode'].value='asignarAyudantes'" title=" Asignar Ayudantes"/>  
				  	</logic:equal>
				  </display:column>
				</layout:isMode>
			</display:table>		
			
			<layout:formActions>
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
			</layout:formActions>
			
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="cargarIntegrante" value="Nuevo Integrante" styleClass="formSubmit"/>				
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
			
		</layout:panel>
    </layout:form>