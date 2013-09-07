<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<layout:form action="asignarGruposAAyudantesUseCase" reqCode="" styleClass="mainCUForm">
		<h3 class="tituloCU">Asignar grupos a ayudantes</h3>
		<layout:panel styleClass="panel" key="" width="100%" align="center">
	    	<layout:panel styleClass="subPanel" key="Seleccionar Algoritmo de asignación" width="100%" align="center">
	    		<layout:row>
		      		<layout:select styleClass="inputSelect" property="useCaseModel.algoritmo" key="Algoritmo: ">
						<layout:option key="Round Robin" value="ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes.RoundRobin"/>
						<layout:option key="FIFO" value="ar.com.grupo306.sisopadmin.web.usecases.domain.integrantes.algoritmosAsignacionGruposAAyudantes.FIFO"/>
					</layout:select>
		      		<layout:submit reqCode="asignar" value="Asignar" styleClass="formSubmit"/>
		      	</layout:row>
	    	</layout:panel>		
	
			<layout:panel styleClass="subPanel" key="Ayudantes Disponibles" width="100%" align="center">
				<display:table id="ayudante" name="useCaseForm.useCaseModel.ayudantesDisponibles" defaultorder="ascending" style="formColsColorx1">
					<display:column class="textColumn" value="${ayudante.nombreCompleto}" title="Ayudante"/>
				</display:table>
			</layout:panel>

			<layout:panel styleClass="subPanel" key="Grupos a asignar" width="100%" align="center">
				<display:table id="grupo" name="useCaseForm.useCaseModel.gruposAAsignar" defaultorder="ascending" style="formColsColorx1">
					<display:column class="textColumn" value="${grupo.nombre}" title="Grupo"/>
				</display:table>
			</layout:panel>
			
			<core:if test="${useCaseForm.useCaseModel.gruposAsignadosAnteriormenteSize == 0}">
				<layout:panel styleClass="subPanel" key="Grupos asignados" width="100%" align="center">
					<display:table id="grupo" name="useCaseForm.useCaseModel.gruposAsignados" defaultorder="ascending" style="formColsColorx1">
						<display:column class="textColumn" value="${grupo.nombre}" title="Grupo"/>
						<display:column class="textColumn" value="${grupo.ayudante.nombreCompleto}" title="Ayudante">
							<core:if test="${grupo.ayudante == null}">Sin ayudante</core:if>
						</display:column>
					</display:table>
					<core:if test="${useCaseForm.useCaseModel.gruposAsignadosSize >= 1}">
						Los grupos sin ayudante no poseen<br>la cantidad mínima de integrantes<br>necesaria para tener un ayudante asignado.
					</core:if>
				</layout:panel>	
			</core:if>
				
			<core:if test="${useCaseForm.useCaseModel.gruposAsignadosAnteriormenteSize > 0}">
				<layout:panel styleClass="subPanel" key="Grupos asignados en esta ejecución" width="100%" align="center">
					<display:table id="grupo" name="useCaseForm.useCaseModel.gruposAsignados" defaultorder="ascending" style="formColsColorx1">
						<display:column class="textColumn" value="${grupo.nombre}" title="Grupo"/>
						<display:column class="textColumn" value="${grupo.ayudante.nombreCompleto}" title="Ayudante">
							<core:if test="${grupo.ayudante == null}">Sin ayudante</core:if>
						</display:column>
					</display:table>
					<core:if test="${useCaseForm.useCaseModel.gruposAsignadosSize >= 1}">
						Los grupos sin ayudante no poseen<br>la cantidad mínima de integrantes<br>necesaria para tener un ayudante asignado.
					</core:if>
				</layout:panel>					
				<layout:panel styleClass="subPanel" key="Grupos asignados anteriormente" width="100%" align="center">
					<display:table id="grupo" name="useCaseForm.useCaseModel.gruposAsignadosAnteriormente" defaultorder="ascending" style="formColsColorx1">
						<display:column class="textColumn" value="${grupo.nombre}" title="Grupo"/>
						<display:column class="textColumn" value="${grupo.ayudante.nombreCompleto}" title="Ayudante">
							<core:if test="${grupo.ayudante == null}">Sin ayudante</core:if>
						</display:column>
					</display:table>
					<core:if test="${useCaseForm.useCaseModel.gruposAsignadosSize >= 1}">
						Los grupos sin ayudante no poseen<br>la cantidad mínima de integrantes<br>necesaria para tener un ayudante asignado.
					</core:if>
				</layout:panel>					
			</core:if>

			<layout:panel styleClass="subPanel" key="Grupos con conflictos" width="100%" align="center">
				<display:table id="grupo" name="useCaseForm.useCaseModel.gruposConConflicto" defaultorder="ascending" style="formColsColorx1">
					<display:column class="textColumn" value="${grupo.nombre}" title="Grupo"/>
					<display:column class="textColumn" value="${grupo.motivoConflicto}" title="Motivo conflicto"/>
				</display:table>
				<core:if test="${useCaseForm.useCaseModel.gruposConConflictoSize >= 1}">
					Ejecutar nuevamente el proceso una vez que los conflictos hayan sido resueltos.
				</core:if>
			</layout:panel>
			
			<layout:formActions align="right">
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Volver"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>	
	</layout:form>
</layout:html>
