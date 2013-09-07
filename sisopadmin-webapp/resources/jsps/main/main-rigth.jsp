<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<table>
	<tr>
		<td>
			<table class="linksTable">
				<tr><td>
					<core:if test="${sessionScope.sisopAdmin_userContext.userLogged}">
						<img src="./resources/images/main/user.png" onmouseover="Tip('Usuario logueado.')">
						<span style="font-size: 12px;">Usuario:</span><span> ${sessionScope.sisopAdmin_userContext.userLoginName}</span>
					</core:if>
					<layout:space/>
					<layout:space/>
				</td></tr>
				<tr><td>
					<layout:toggleable id="links" text="Links importantes" style="linksDiv" tooltip="Ver/Ocultar links">
						<table>
							<tr><td>
								<a class="link" href="homeUseCase.do?reqCode=initUseCase">Página principal</a>
							</td></tr>
							<tr><td>
								<a href="#" onclick="window.open('../jforum', 'Foro oficial de la Cátedra de Sistemas operativos', '')">Foro oficial</a>
							</td></tr>
							<core:if test="${sessionScope.sisopAdmin_userContext.userLogged}">
								<%-- Solo si el usuario esta logueado puede cambiar la contraseña. --%>
								<tr><td>
									<a class="link" href="cambiarPasswordUseCase.do?reqCode=initUseCase">Cambiar contraseña</a>
								</td></tr>
							</core:if>
						</table>
					</layout:toggleable>
				</td></tr>
			</table>
		</td>
	</tr>
	<core:if test="${sessionScope.sisopAdmin_userContext.userLogged}">
	<tr>
		<td>
			<table class="novedadesTable">
				<tr><td>
					<layout:toggleable id="novedades" text="Novedades (${fn:length(sessionScope.sisopAdmin_userContext.eventosNotificables)})" style="novedadesDiv" tooltip="Ver/Ocultar novedades">
						<logic:iterate id="evento" name="sisopAdmin_userContext" property="eventosNotificables" scope="session">
							<table><tr><td>
								<core:if test="${evento.url ne null}">
									<a class="novedad" href="${evento.url}">${evento.descripcion}</a>
								</core:if>
								<core:if test="${evento.url eq null}">
									<span class="novedad">${evento.descripcion}</span>
								</core:if>
							</td></tr></table>
						</logic:iterate>
					</layout:toggleable>
				</td></tr>
			</table>
		</td>
	</tr>
	</core:if>
</table>