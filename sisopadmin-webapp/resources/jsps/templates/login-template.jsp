<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<%--
	Estilo que se utilizar� en las p�ginas.
	En un futuro se podr�a personalizar por usuario, ser�a f�cil.
--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/styles.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/displaytag/displaytag.css">



<table border="1" width="100%" height="100%">
	<tr>
		<td>
			<tiles:insert attribute="header" />
		</td>
	</tr>
	<tr>
		<td align="center">
			<tiles:insert attribute="body.header" />
		</td>
	</tr>	
	<tr height="90%">
		<td align="center">
			<tiles:insert attribute="body" />
		</td>
	</tr>
	<tr>
		<td>
			<tiles:insert attribute="footer" />
		</td>
	</tr>
</table>