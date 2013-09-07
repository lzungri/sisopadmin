<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form  styleClass="mainLoginForm" align="center">

	<layout:panel styleClass="panel" key="Datos del usuario" width="100%" align="center">
		<layout:text styleClass="passwordInput" styleId="userName" property="useCaseModel.usuario" key="Usuario" onmouseover="Tip('Nombre de usuario con el que desea iniciar sesión.')"/>
		<layout:password styleClass="passwordInput" property="useCaseModel.password" key="Contraseña" onmouseover="Tip('Contraseña del usuario.')"/>
		<layout:formActions align="right">
			<layout:row styleClass="home">
				<layout:submit reqCode="login" value="Ingresar" styleClass="formSubmit" onmouseover="Tip('Inicia sesión con el usuario indicado.')"/>
				<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>	
			</layout:row>
		</layout:formActions>
	</layout:panel>
	<layout:text property="useCaseModel.forumReturnPath" value="<%=request.getParameter("forumReturnPath")%>" mode="edit:H"/>
	
</layout:form>

<script>
document.getElementById('userName').focus();
</script>