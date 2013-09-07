<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form styleClass="mainCUForm">
		<script type="text/javascript" src="/sisopadmin/resources/javascript/util/selectUtil.js"></script>
	
		<layout:panel styleClass="panel" key="Asignación de Grupos" width="100%" align="center">
				<table width="100%">
					<tr>
						<td width="10 px">Asignados</td>
						<td width="10 px"></td>
						<td width="100 px">Disponibles</td>
					</tr>
					<tr valign="middle">
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="gruposAsignados" name="gruposAsignados" property="useCaseModel.gruposAsignados" multiple="multiple" size="15" >
								 <core:forEach var="item" items="${useCaseForm.useCaseModel.gruposAsignados}">
									<option  name="gruposAsignadosOption"  value="${item.id}">${item.nombre}</option>
								 </core:forEach>
							</select>
						</td>
						<td class="imageColumn" width="10px" align="center" valign="middle">
							<table width="10px" align="center">
							  <layout:column styleClass="imageColumn">
									    <layout:submit styleClass="formSubmit" onclick="asignar('gruposDisponibles','gruposAsignados');return false;" mode="edit:E,create:E,remove:D,view:D">&lt;</layout:submit>
									    <layout:submit styleClass="formSubmit" onclick="asignar('gruposAsignados','gruposDisponibles');return false;" mode="edit:E,create:E,remove:D,view:D">&gt;</layout:submit>
							  </layout:column>
							</table>													
						</td>
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="gruposDisponibles" name="gruposDisponibles" property="useCaseModel.gruposDisponibles"  multiple="multiple" size="15">
								<core:forEach var="item" items="${useCaseForm.useCaseModel.gruposDisponibles}">
									<option name="rolesDelUsuarioDisponiblesOption" value="${item.id}">${item.nombre}</option>
								</core:forEach>
							</select>
						</td>
					</tr>
					
				</table>
		</layout:panel>
			
		<layout:formActions align="right">
			<layout:row>
				<layout:submit reqCode="modificar" value="Aceptar" onclick="seleccionarTodos('gruposAsignados');seleccionarTodos('gruposDisponibles');" styleClass="formSubmit" mode="edit:E,create:E,remove:D,view:D"/>
				<layout:submit reqCode="cancelar" value="Cancelar" styleClass="formSubmit"/>
			</layout:row>
		</layout:formActions>
	</layout:form>
</layout:html>