<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
    <layout:form action="aBMGruposUseCase" reqCode="acceptUseCase">
		<layout:panel styleClass="FORM" key="Datos de la encuesta" width="100%" align="center">
			    <layout:text property="useCaseModel.nombre" key="Nombre: " styleClass="formInput" />
				<layout:text property="useCaseModel.nombreUsuario" key="Nombre de Usuario" styleClass="formInput"/>
				<layout:text property="useCaseModel.email" key="Correo Electrónico" styleClass="formInput"/>				
		</layout:panel>
		<layout:panel styleClass="FORM" key="Nuevo Integrante" width="100%" align="center">
			    <layout:text property="useCaseModel.nombreAlumno" key="Nombre:" styleClass="formInput"/>
				<layout:text property="useCaseModel.apellidoAlumno" key="Apellido:" styleClass="formInput"/>
				<layout:text property="useCaseModel.legajoAlumno" key="Legajo:" styleClass="formInput"/>
				<layout:text property="useCaseModel.emailAlumno" key="Correo electrónico:" styleClass="formInput"/>
                <layout:submit reqCode="agregarIntegrante" value="Agregar Integrante"/>				
		</layout:panel>
		
			<layout:row>
				<layout:column>
					<h3 class="formText">Integrantes del Grupo</h3>
				</layout:column>
			</layout:row>
			<layout:row>
			<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
	
		        <display:table uid="alumno" name="useCaseForm.useCaseModel.alumnos"  defaultorder="ascending">
				      <display:column value="${alumno.nombre}" title="Nombre" />
				      <display:column value="${alumno.apellido}" title="Apellido"/>
   				      <display:column value="${alumno.legajo}" title="Legajo"/>
  				      <display:column value="${alumno.email}" title="Correo Electrónico"/>
				      <display:column> <input type="submit" value="Eliminar" onclick=" document.getElementById('index').value=${alumno_rowNum - 1}; this.form.elements['reqCode'].value='eliminarIntegrante'"/> </display:column>
				      <display:column> <input type="submit" value="Modificar" onclick=" document.getElementById('index').value=${alumno_rowNum - 1}; this.form.elements['reqCode'].value='modificarIntegrante'"/> </display:column>
				</display:table>
				    
        </layout:row >             
        <layout:submit reqCode="cargarGrupo" value="Aceptar"/>
	    <layout:submit reqCode="volver" value="Cancelar"/>
    </layout:form>
</layout:html>