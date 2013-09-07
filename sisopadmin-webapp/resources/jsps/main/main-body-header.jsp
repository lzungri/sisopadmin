<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<table style="align:center; width:100%; height:100%;">
	<tr><td>
		<div id="messagesContainer">
			<logic:notEmpty name="useCaseFWK_messages">
				<table align="center" width="100%" height="100%">
					<tr><td>
						<input type="submit" onclick="javascript: document.getElementById('messagesContainer').style.display='none';" value="X">
					</td></tr>
					
					<tr><td>
						<display:table name="requestScope.useCaseFWK_messages">
							<display:column property="messageText" title="Información (${fn:length(requestScope.useCaseFWK_messages)})" />
						</display:table>
					</td></tr>						
				</table>	
			</logic:notEmpty>
		</div>
	</td></tr>
		
	<tr><td>	
		<div id="errorMessagesContainer">
			<logic:notEmpty name="useCaseFWK_errorMessages">
				<table align="center" width="100%" height="100%">
					<tr><td>
						<input type="submit" onclick="javascript: document.getElementById('errorMessagesContainer').style.display='none';" value="X">
					</td></tr>
					
					<tr><td>
						<display:table name="requestScope.useCaseFWK_errorMessages">
							<display:column property="messageText" title="Errores (${fn:length(requestScope.useCaseFWK_errorMessages)})" />
						</display:table>
					</td></tr>						
				</table>	
			</logic:notEmpty>
		</div>
	</td></tr>
</table>