<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<span class="errorClass">
	Ha ocurrido un error en la aplicaci�n. Intente nuevamente m�s tarde.<br>
	Disculpe las molestias.<br>
</span>
<br><br>

<core:if test="${useCaseForm.useCaseModel.exceptionDebugging}">
<pre class="exceptionClass">
${useCaseForm.useCaseModel.exceptionStackTrace}
</pre>
</core:if>
