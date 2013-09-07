<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
<layout:form  styleClass="mainCUForm" align="center">
	<layout:panel styleClass="panel" key="Cambiar contrase�a" align="center">
		<layout:password styleClass="passwordInput" property="useCaseModel.passwordOriginal01" key="Contrase�a Actual"/>
		<layout:password styleClass="passwordInput" property="useCaseModel.passwordNueva01" key="Contrase�a Nueva"/>
		<layout:password styleClass="passwordInput" property="useCaseModel.passwordNueva02" key="Repetir Contrase�a"/>
		<layout:formActions align="right">
			<layout:row styleClass="home">
				<layout:submit reqCode="cambiarPassword" value="Aceptar" styleClass="formSubmit"/>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:panel>
</layout:form>
</layout:html>