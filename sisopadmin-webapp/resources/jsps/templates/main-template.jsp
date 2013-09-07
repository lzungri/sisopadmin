<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<%--
	Estilo que se utilizar� en las p�ginas.
	En un futuro se podr�a personalizar por usuario, ser�a f�cil.
--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/styles.css">

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/displaytag/displaytag.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/calendar/date-picker.js"></script>



<table border='1px' style='height: 100%; width:100%;'>
	<tr>
		<%--	Secci�n HEADER.	--%>	
		<td colspan=3 style='height: 30%; width:100%;'>
			<tiles:insert attribute="header" />
		</td>
	</tr>
	<tr>
		<%--	Secci�n LEFT.	--%>
		<td rowspan=2 style="width: 16%;">
			<tiles:insert attribute="left" />
		</td>
		<%--	Secci�n BODY HEADER.--%>		
		<td>
			<tiles:insert attribute="body.header" />
		</td>
		<%--	Secci�n RIGTH.--%>		
		<td rowspan=2>
			<tiles:insert attribute="rigth" />
		</td>
	</tr>
	<tr>
		<%--	Secci�n BODY.--%>	
		<td style='height: 70%; width:70%'>
			<tiles:insert attribute="body" />
		</td>
	</tr>
</table>