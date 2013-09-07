<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty name="returnedFrom">
	<h3 class="formText">Retornando del caso de uso: <%=request.getAttribute("returnedFrom")%></h3>
</logic:notEmpty>

<layout:form action="cUDosUseCase" reqCode="acceptUseCase" styleClass="formColsColorx1">
	<table>
		<layout:row>
			<layout:column>
				<layout:text property="useCaseModel.valor11" key="Valor 1.1" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor12" key="Valor 1.2" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor13" key="Valor 1.3" styleClass="formInput"/>
			</layout:column>			
		</layout:row>
		<layout:row>
			<layout:column>
				<layout:text property="useCaseModel.valor21" key="Valor 2.1" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor22" key="Valor 2.2" styleClass="formInput"/>
				<layout:text property="useCaseModel.valor23" key="Valor 2.3" styleClass="formInput"/>
			</layout:column>			
		</layout:row>		
		<layout:row>
			<layout:column>
				<layout:submit reqCode="irACU3" value="CU 3 (Create)" styleClass="formSubmit"/>				
			</layout:column>			
			<layout:column>
				<layout:submit reqCode="cancelUseCase" value="Salir" styleClass="formSubmit"/>				
			</layout:column>
		</layout:row>		
</layout:form>