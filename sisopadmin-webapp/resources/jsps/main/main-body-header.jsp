<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<table class="mensajesTable">
	<tr><td>
		<logic:notEmpty name="useCaseFWK_messages">
			<layout:toggleable id="messagesContainer" text="Información (${fn:length(requestScope.useCaseFWK_messages)})" tooltip="Expandir/Contraer">
				<display:table name="requestScope.useCaseFWK_messages">
					<display:column property="messageText"/>
					<display:setProperty name="basic.show.header" value="false"/>
					<display:setProperty name="css.tr.even" value="mainBodyHeaderTableInfo"/>
					<display:setProperty name="css.tr.odd" value="mainBodyHeaderTableInfo"/>
					<display:setProperty name="css.table" value="mensajesTable"/>
				</display:table>
			</layout:toggleable>
		</logic:notEmpty>
	</td></tr>
		
	<tr><td>	
		<logic:notEmpty name="useCaseFWK_errorMessages">
			<layout:toggleable id="errorMessagesContainer" text="Errores (${fn:length(requestScope.useCaseFWK_errorMessages)})" tooltip="Expandir/Contraer" containerStyle="errorMessagesDiv">
				<display:table name="requestScope.useCaseFWK_errorMessages" >
					<display:column property="messageText"/>
					<display:setProperty name="basic.show.header" value="false"/>
					<display:setProperty name="css.tr.even" value="mainBodyHeaderTableError"/>
					<display:setProperty name="css.tr.odd" value="mainBodyHeaderTableError"/>
					<display:setProperty name="css.table" value="mensajesTable"/>
				</display:table>
			</layout:toggleable>
		</logic:notEmpty>
	</td></tr>
</table>