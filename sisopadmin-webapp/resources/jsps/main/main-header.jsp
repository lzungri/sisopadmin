<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<%@ page import="ar.com.grupo306.sisopadmin.web.context.UserContext" %>

<table>
	<tr>
		<td>
			<img src="${pageContext.request.contextPath}/resources/images/main/header.jpg">
		</td>
	</tr>
	<tr>
		<td>
			<layout:form styleId="headerForm">
				<a href="../sisopadmin/loginUseCase.do?reqCode=initUseCase">Login</a>
				<layout:submit reqCode="logout" value="logout"/>
			</layout:form>
		</td>
	</tr>
</table>	