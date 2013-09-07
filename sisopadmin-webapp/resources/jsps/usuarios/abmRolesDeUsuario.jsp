<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form styleClass="mainCUForm">
		<script type="text/javascript" src="/sisopadmin/resources/javascript/util/selectUtil.js"></script>
	
		<layout:panel styleClass="panel" key="Asignación de Roles" width="100%" align="center">
				<table width="100%">
					<tr>
						<td width="10 px">Asignados</td>
						<td width="10 px"></td>
						<td width="100 px">Disponibles</td>
					</tr>
					<tr>
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="rolesDelUsuario" name="rolesDelUsuario" property="useCaseModel.rolesDelUsuario" multiple="multiple" size="15" >
								 <core:forEach var="item" items="${useCaseForm.useCaseModel.roles}">
									<option  name="rolesDelUsuarioOption"  value="${item.id}">${item.nombre}</option>
								 </core:forEach>
							</select>
						</td>
						<td width="10 px" align="center">
							<table width="10 px" align="center">
							  <layout:column>
							    <layout:submit onclick="asignar('rolesDelUsuarioDisponibles','rolesDelUsuario');return false;" mode="edit:E,create:E,remove:D,view:D">&lt;</layout:submit>
							    <layout:submit onclick="asignar('rolesDelUsuario','rolesDelUsuarioDisponibles');return false;" mode="edit:E,create:E,remove:D,view:D">&gt;</layout:submit>
							  </layout:column>
							</table>						
						</td>
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="rolesDelUsuarioDisponibles" name="rolesDelUsuarioDisponibles" property="useCaseModel.rolesDelUsuarioDisponibles"  multiple="multiple" size="15">
								<core:forEach var="item" items="${useCaseForm.useCaseModel.rolesDisponibles}">
									<option name="rolesDelUsuarioDisponiblesOption" value="${item.id}">${item.nombre}</option>
								</core:forEach>
							</select>
						</td>
					</tr>
				</table>
		</layout:panel>
			
		<layout:formActions align="right">
			<layout:row>
				<layout:submit reqCode="modificacionRoldeUsuario" value="Aceptar" onclick="seleccionarTodos('rolesDelUsuario');seleccionarTodos('rolesDelUsuarioDisponibles');" styleClass="formSubmit" mode="edit:E,create:N,remove:N,view:N"/>
				<layout:submit reqCode="cancelarRoldeUsuario" value="Cancelar" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:form>
</layout:html>
