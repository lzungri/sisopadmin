Ha ocurrido un error en la aplicación. Intente más tarde... va a volver a ocurrir.
<br><br>

<%--
	Solo se debuggeará la exception en caso de indicarlo mediante el parámetro de 
	contexto "exceptionDebugging" en el web.xml.
--%> 
<%
	boolean exceptionDebugging = "true".equals(request.getSession().getServletContext().getInitParameter("exceptionDebugging"));
%>

<%if(exceptionDebugging) {%>
	<pre>
${useCaseForm.useCaseModel.exceptionStackTrace}
	</pre>
<%}%>


