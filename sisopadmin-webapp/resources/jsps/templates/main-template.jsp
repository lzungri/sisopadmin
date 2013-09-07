<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<html>
<head>
	<title>Sisop admin - Sistemas operativos (UTN - FRBA)</title>
	<link rel="shortcut icon" href="resources/images/favicon.png" type="image/x-icon"> 	
</head>

<%--
	Estilo que se utilizar� en las p�ginas.
	En un futuro se podr�a personalizar por usuario, ser�a f�cil.
--%>
<link rel="stylesheet" type="text/css" href="resources/styles/commons.css">
<link rel="stylesheet" type="text/css" href="resources/styles/styles.css">
<link rel="stylesheet" type="text/css" href="resources/styles/displaytag/displaytag.css">

<%-- Calendario. --%>
<script type="text/javascript" src="resources/javascript/calendar/date-picker.js"></script>

<%-- Mootools/ --%>
<script type="text/javascript" src="resources/javascript/mootools/mootools-release-1.11.js"></script>

<%-- Rich text editor --%>
<script type="text/javascript" src="resources/javascript/rte/richtext.js"></script>
<script type="text/javascript" src="resources/javascript/rte/config.js"></script>

<body>
<%-- Tool tip --%>
<script type="text/javascript" src="resources/javascript/tooltip/tip_folowscroll.js"></script>
<script type="text/javascript" src="resources/javascript/tooltip/wz_tooltip.js"></script>

<table align="center" width="100%" class="mainTable">
	<tr>
		<%-- Secci�n HEADER. --%>	
		<td class="mainHeader" colspan="3">
			<tiles:insert attribute="header" flush="true"/>
		</td>
	</tr>
	<tr>
		<%-- Secci�n LEFT. --%>
		<td class="mainLeft" rowspan=2>
			<tiles:insert attribute="left" flush="true"/>
		</td>
		<%-- Secci�n BODY HEADER. --%>		
		<td class="mainBodyHeader">
			<tiles:insert attribute="body.header" flush="true"/>
		</td>
		<%--  Secci�n RIGTH. --%>		
		<td class="mainRigth" rowspan=2>
			<tiles:insert attribute="rigth" flush="true"/>
		</td>
		</tr>
		<tr>
			<%-- Secci�n BODY. --%>	
			<td class="mainBody">
				<div class="mainBodyDiv">		
					<tiles:insert attribute="body" flush="true"/>
				</div>	
			</td>
		</tr>
</table>
</body>

</html>