Ha ocurrido un error en la aplicaci�n. Intente m�s tarde... va a volver a ocurrir.
<br><br>

<%--
	Solo se debuggear� la exception en caso de indicarlo mediante el par�metro de 
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


