<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<%@ page import="
	java.util.Collection, java.util.Iterator,
	ar.com.grupo306.usecasefwk.usecases.menu.MenuBuilder,
	ar.com.grupo306.usecasefwk.usecases.menu.DOMJSMenuBuilder,
	ar.com.grupo306.usecasefwk.usecases.registry.RegistryElement,
	ar.com.grupo306.sisopadmin.web.constants.SisopAdminWebConstants,
	ar.com.grupo306.sisopadmin.web.context.UserContext" %>

<%-- Estilos del domMenu. --%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/styles/domMenu/default.css"/>

<%--	Librería comprimida de domMenu.--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/javascript/domMenu/domMenu_min.js"></script>



<%--	Creación de la estructura del menú en base a los UseCases registrados y habilitados al usuario. --%>
<%
	UserContext userContext = (UserContext) request.getSession().getAttribute(SisopAdminWebConstants.SESSION_USER_CONTEXT);
	MenuBuilder menuBuilder = new DOMJSMenuBuilder("sisopadmin_menu");
	
	menuBuilder.appendHeader();
	for(Iterator<RegistryElement> it = userContext.getUserRegistryElements().iterator(); it.hasNext(); ) {
		it.next().createMenu(menuBuilder);
		
		if(it.hasNext())
			menuBuilder.hasNextElement();
	}
	menuBuilder.appendTail();
%>


<script>
	<%-- Construcción del menú. --%>
	<%=menuBuilder.build()%>

	<%--	Configuración de domMenu.	--%>
	domMenu_settings.set('sisopadmin_menu',
		new Hash(
	    'axis', 'vertical',
	    'verticalSubMenuOffsetX', 1,
	    'verticalSubMenuOffsetY', 0,
	    'horizontalSubMenuOffsetX', 0,
	    'horizontalSubMenuOffsetY', 0,
		'horizontalExpand', 'east',
		'subMenuMinWidth', 'auto',
		'baseUri', '../sisopadmin'		
	));
</script>

<table stile='width:100%'>
	<tr><td>
		<%-- Elemento contenedor del menú. --%>
		<div id="sisopadmin_menu"></div>
	</td></tr>
</table>

<script>	
	<%-- Activación del menú, se presenta en pantalla. --%>
	domMenu_activate('sisopadmin_menu');
</script>