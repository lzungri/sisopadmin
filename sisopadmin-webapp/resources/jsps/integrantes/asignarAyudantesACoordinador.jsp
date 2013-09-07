<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form action="asignarAyudantesACoordinadorUseCase" reqCode="acceptUseCase" styleClass="mainCUForm">	
		<layout:row>
			<h4>Coordinador actual:&nbsp;${useCaseForm.useCaseModel.coordinador.nombre}</h4>
		</layout:row>
		<layout:panel styleClass="panel" key="" align="center" width="50%">
			<layout:row>
				<layout:panel styleClass="subPanel" key="Ayudantes Disponibles" align="center" width="50%">
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
				<layout:panel styleClass="subPanel" key="Ayudantes Asignados" align="center" width="50%">
					<logic:notEmpty property="useCaseModel.ayudantesAsignados" name="useCaseForm">
						<input id="indexA" type="hidden" name="useCaseModel.indexAsignados" value="0"/>
					    <display:table id="ayudanteAsign" name="useCaseForm.useCaseModel.ayudantesAsignados" defaultorder="ascending" style="formColsColorx1">
							<display:column>
								<table><tr>
						    		<layout:submit  styleClass="formSubmit" value="<<" reqCode="desasignar" onclick="document.getElementById('indexA').value=${ayudanteAsign.id};"/>
						    	</tr></table>
							</display:column>
							<display:column value="${ayudanteAsign.nombre}" title="Nombre" />
							<display:column value="${ayudanteAsign.apellido}" title="Apellido"/>  		       	 			      			      
					    </display:table>
					</logic:notEmpty>			
					</layout:panel>
			</layout:row>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="aceptar" value="Aceptar" styleClass="formSubmit"/>
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
    </layout:form>
</layout:html>