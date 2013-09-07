<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<table class="mainHeader">
	<tr class="mainHeader" valign="middle">
		<td class="mainHeader">
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="1096" height="168" id="headerSisop" align="middle">
				<param name="allowScriptAccess" value="sameDomain" />
				<param name="movie" value="headerSisop.swf" />
				<param name="menu" value="false" />
				<param name="quality" value="low" />
				<param name="bgcolor" value="#ffffff" />
				<embed src="./resources/flash/compiled/headerSisop.swf" menu="false" quality="autolow" bgcolor="#ffffff" width="960" height="149" name="headerSisop" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
			</object>
		</td>
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