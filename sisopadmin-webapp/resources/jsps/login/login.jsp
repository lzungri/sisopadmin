<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form>

	<layout:panel styleClass="FORM" key="Datos del usuario" width="100%" align="center">
		<layout:text styleId="userName" property="useCaseModel.usuario" key="Usuario"/>
		<layout:password property="useCaseModel.password" key="Password"/>
		
		<layout:submit reqCode="login" value="Login"/>
		<layout:submit reqCode="cancelUseCase" value="Cancelar"/>	
	</layout:panel>
	
</layout:form>

<script>
document.getElementById('userName').focus();
</script>