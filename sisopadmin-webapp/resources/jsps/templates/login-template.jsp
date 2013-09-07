<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<html>
<head>
	<title>Sisop admin - Sistemas operativos (UTN - FRBA)</title>
	<link rel="shortcut icon" href="resources/images/favicon.png" type="image/x-icon">
</head>

<%--
	Estilo que se utilizará en las páginas.
	En un futuro se podría personalizar por usuario, sería fácil.
--%>
<link rel="stylesheet" type="text/css" href="resources/styles/commons.css">
<link rel="stylesheet" type="text/css" href="resources/styles/styles.css">
<link rel="stylesheet" type="text/css" href="resources/styles/displaytag/displaytag.css">

<script type="text/javascript" src="resources/javascript/mootools/mootools-release-1.11.js"></script>


<body>
<%-- Tool tip --%>
<script type="text/javascript" src="resources/javascript/tooltip/tip_folowscroll.js"></script>
<script type="text/javascript" src="resources/javascript/tooltip/wz_tooltip.js"></script>

<table border="0" align="center" width="65" height="100%" class="mainLogin">
	<tr>
		<td class="mainHeader">
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
</table>
</body>

</html>