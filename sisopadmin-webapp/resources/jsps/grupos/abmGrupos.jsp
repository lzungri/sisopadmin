<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

 <layout:form action="aBMGruposUseCase" reqCode="acceptUseCase" styleClass="mainCUForm">
   	<h3 class="tituloCU">Datos del grupo</h3>
	<layout:panel styleClass="panel" key="" width="100%" align="center">
	    <layout:text styleClass="inputText" property="useCaseModel.grupo.nombre" key="Nombre: " mode="view:I,remove:I,edit:I,confirm:I" />
		<layout:text styleClass="inputText" property="useCaseModel.grupo.loginName" key="Nombre de Usuario" mode="view:I,remove:I,edit:I,confirm:I"/>
		<layout:text styleClass="inputText" property="useCaseModel.grupo.email" key="Correo Electrónico" mode="view:I,remove:I,confirm:I"/>
		<layout:calendar style="inputCalendar" id="fechaAlta" beanName="useCaseForm" property="useCaseModel.grupo.fechaAlta" mode="create:N,view:I,remove:I,edit:I,confirm:I" description="Fecha de Alta: " />
		<layout:line>
			<layout:text styleClass="inputText" property="useCaseModel.ayudanteAsignado" key="Ayudante Asignado: " mode="create:N,view:I,remove:I,edit:I,confirm:I" />
			<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.grupos.ABMGruposUseCaseModel.seleccionarAyudante">
				<core:if test="${useCaseForm.useCaseModel.grupo.estado eq '5'}">	
					<layout:submit reqCode="seleccionarAyudante" value="..." mode="create:N,view:N,remove:N,confirm:I"/>
				</core:if>
			</layout:hasPermission>
		</layout:line>			
		<core:set var="cantidadAlumnos" value="${fn:length(useCaseForm.useCaseModel.grupo.alumnos)}"></core:set>				
		<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'confirm' && ( (cantidadAlumnos  lt useCaseForm.useCaseModel.cantMaxAlum) || useCaseForm.useCaseModel.alumnoVisible)}">
			<layout:panel styleClass="subPanel" key="Integrante" width="100%" align="center">
				    <layout:text styleClass="inputText" property="useCaseModel.nombreAlumno" key="Nombre:"/>
					<layout:text styleClass="inputText" property="useCaseModel.apellidoAlumno" key="Apellido:"/>
					<layout:text styleClass="inputText" property="useCaseModel.legajoAlumno" key="Legajo:"/>
					<layout:text styleClass="inputText" property="useCaseModel.emailAlumno" key="Correo electrónico:"/>
					<layout:formActions align="right">
						<layout:row>
			                
					<core:if test="${useCaseForm.useCaseModel.modificandoAlumno}">
						<layout:submit styleClass="formSubmit" reqCode="agregarIntegrante" value="Modificar"/>
					</core:if>
					<core:if test="${not useCaseForm.useCaseModel.modificandoAlumno}">
						<layout:submit styleClass="formSubmit" reqCode="agregarIntegrante" value="Crear"/>
					</core:if>
			                <layout:submit styleClass="formSubmit" reqCode="cancelarModificarIntegrante" value="Cancelar"/>		         
		                </layout:row>
	                </layout:formActions>
			</layout:panel>
		</core:if>
	
	<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
		<layout:panel styleClass="subPanel" key= "Integrantes del Grupo" width="100%" align="center">
			<display:table uid="alumno" name="useCaseForm.useCaseModel.grupo.alumnos" defaultorder="ascending">
				<display:column class="textColumn" value="${alumno.nombre}" title="Nombre" />
				<display:column class="textColumn" value="${alumno.apellido}" title="Apellido"/>
				<display:column class="textColumnCentrado" value="${alumno.legajo}" title="Legajo" />
				<display:column class="textColumn" value="${alumno.email}" title="Correo Electrónico"/>
				<core:if test="${useCaseForm.useCaseModel.mode.modeIdentifier ne 'view' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'confirm' && useCaseForm.useCaseModel.mode.modeIdentifier ne 'remove'}">
					<display:column class="imageColumn"> <input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick=" document.getElementById('index').value=${alumno_rowNum - 1}; this.form.elements['reqCode'].value='modificarIntegrante';" title=" Modificar"/> </display:column>
					<display:column class="imageColumn"> <input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick=" document.getElementById('index').value=${alumno_rowNum - 1}; this.form.elements['reqCode'].value='eliminarIntegrante';" title=" Eliminar"/> </display:column>				     
				 </core:if>
			</display:table>
		</layout:panel>
	
		<layout:formActions align="right">
			<layout:row>			
				<layout:submit styleClass="formSubmit" reqCode="confirmar" value="Confirmar Inscripción" mode="create:N,view:N,edit:N,remove:N"/>	
				<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Crear" mode="confirm:N,view:N,edit:N,remove:N"/>
				<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Modificar" mode="confirm:N,view:N,create:N,remove:N"/>				
				<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="confirm:N,view:N,edit:N,create:N"/>
				<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>	
			</layout:row>
		</layout:formActions>	
	</layout:panel>
   </layout:form>
