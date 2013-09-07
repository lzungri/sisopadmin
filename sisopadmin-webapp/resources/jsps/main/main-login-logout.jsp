<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<table class="" align="right">
	<tr class="" valign="middle" align="right">
		<core:if test="${not sessionScope.sisopAdmin_userContext.userLogged}">
			<td class="mainLogin" align="right" width="100px">
				<layout:form action="loginUseCase" align="right">
						<tr class="mainLogin" valign="middle" align="right">
							<td class="mainLogin" >
								<a align="right" href="loginUseCase.do?reqCode=initUseCase">Login</a>
							</td>
							<td class="mainLogin" >
								<input type="image" src="/sisopadmin/resources/images/buttons/login.gif" onclick="this.form.elements['reqCode'].value='initUseCase'" onmouseover="window.status='Login';return true;" onmouseout="window.status='';return true;" title=" Login">
							</td>
						</tr>
				</layout:form>
			</td>
		</core:if>
		<core:if test="${sessionScope.sisopAdmin_userContext.userLogged}">
			<td class="mainLogin" align="right" width="100px">
				<%-- No es un CU especifico sino que dispara el método logout, común a todos los UseCaseModels. --%>
				<layout:form align="right">
						<tr class="mainLogin" valign="middle" align="right">
							<td class="mainLogin" >
								<a align="right" href="loginUseCase.do?reqCode=logout">Logout</a>
							</td>
							<td class="mainLogin" >
								<input type="image" src="/sisopadmin/resources/images/buttons/logout.gif" onclick="this.form.elements['reqCode'].value='logout'" onmouseover="window.status='Logout';return true;" onmouseout="window.status='';return true;" title=" Logout"/>
							</td>
							
						</tr>
				</layout:form>
			</td>
		</core:if>
	</tr>
</table>