<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<span class="errorClass">
	Ha ocurrido un error en la aplicación. Intente nuevamente más tarde.<br>
	Disculpe las molestias.<br>
</span>
<br><br>

<core:if test="${useCaseForm.useCaseModel.exceptionDebugging}">
<pre class="exceptionClass">
${useCaseForm.useCaseModel.exceptionStackTrace}
</pre>
</core:if>
