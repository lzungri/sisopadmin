<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form action="asignarAyudantesACoordinadorUseCase" reqCode="acceptUseCase" styleClass="formColsColorx1">	
		<layout:row>
			<h4>Coordinador actual:&nbsp;${useCaseForm.useCaseModel.coordinador.nombre}</h4>
		</layout:row>
		<layout:row>
			<layout:panel styleClass="FORM" key="Ayudantes Disponibles" align="center" width="50%">
			<logic:notEmpty property="useCaseModel.ayudantesDisponibles" name="useCaseForm">
				<input id="indexD" type="hidden" name="useCaseModel.indexDisponibles" value="0"/>
			    <display:table id="ayudanteDisp" name="useCaseForm.useCaseModel.ayudantesDisponibles" defaultorder="ascending" style="formColsColorx1">
					  <display:column>
							<table><tr>
					    		<layout:submit value=">>" reqCode="asignar" onclick="document.getElementById('indexD').value=${ayudanteDisp.id};"/>
					    	</tr></table>					  
					    </display:column>
				      <display:column value="${ayudanteDisp.nombre}" title="Nombre" />
				      <display:column value="${ayudanteDisp.apellido}" title="Apellido"/>  		       	 			      			      
			    </display:table>
			</logic:notEmpty>			
			</layout:panel>	
			<layout:panel styleClass="FORM" key="Ayudantes Asignados" align="center" width="50%">
				<logic:notEmpty property="useCaseModel.ayudantesAsignados" name="useCaseForm">
					<input id="indexA" type="hidden" name="useCaseModel.indexAsignados" value="0"/>
				    <display:table id="ayudanteAsign" name="useCaseForm.useCaseModel.ayudantesAsignados" defaultorder="ascending" style="formColsColorx1">
						<display:column>
							<table><tr>
					    		<layout:submit value="<<" reqCode="desasignar" onclick="document.getElementById('indexA').value=${ayudanteAsign.id};"/>
					    	</tr></table>
						</display:column>
						<display:column value="${ayudanteAsign.nombre}" title="Nombre" />
						<display:column value="${ayudanteAsign.apellido}" title="Apellido"/>  		       	 			      			      
				    </display:table>
				</logic:notEmpty>			
				</layout:panel>
		</layout:row>
		<layout:row>
			<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
		</layout:row>
    </layout:form>
</layout:html>