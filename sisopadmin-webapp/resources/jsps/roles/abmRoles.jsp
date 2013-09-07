<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form styleClass="mainCUForm">
		<h3 class="tituloCU">Rol de la Cátedra</h3>
		<script type="text/javascript" src="/sisopadmin/resources/javascript/util/selectUtil.js"></script>

    	<layout:panel styleClass="panel" key="" width="100%" align="center">
    		<layout:panel key="" width="100%" align="center">
			    <layout:text styleClass="inputText" styleId="nombre" property="useCaseModel.nombre" key="Nombre:" mode="remove:I,view:I"/>
				<layout:text styleClass="inputText" styleId="descripcion" property="useCaseModel.descripcion" key="Descripción:" mode="remove:I,view:I"/>
			</layout:panel>
			<layout:panel styleClass="subPanel" key="Asignación de Permisos" width="100%" align="center">
				<table>
					<tr>
						<td class="inputSelectRol">Asignados</td>
						<td width="50px"></td>
						<td class="inputSelectRol">Disponibles</td>
					</tr>
					<tr valign="middle">
						<td class="inputSelectRol">
							<select class="inputSelectRol"  id="permisosDelRol" name="permisosDelRol" property="useCaseModel.permisosDelRol" multiple="multiple" size="15">
								<core:forEach var="item" items="${useCaseForm.useCaseModel.permisos}">
									<option  name="permisosDelRolOption"  value="${item.id}">${item.description}</option>
								</core:forEach>
							</select>
						</td>
						<td class="imageColumn" width="10px" align="center" valign="middle">
							<table width="10px" align="center">
							  <layout:column styleClass="imageColumn">
										<layout:submit styleClass="formSubmit" onclick="asignar('permisosDelRolDisponibles','permisosDelRol');return false;" mode="edit:E,create:E,remove:D,view:D">&lt;</layout:submit>
			  							<layout:submit styleClass="formSubmit" onclick="asignar('permisosDelRol','permisosDelRolDisponibles');return false;" mode="edit:E,create:E,remove:D,view:D">&gt;</layout:submit>
							  </layout:column>
							</table>	
						</td>
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="permisosDelRolDisponibles" name="permisosDelRolDisponibles" property="useCaseModel.permisosDelRolDisponibles"  multiple="multiple" size="15">
								<core:forEach var="item" items="${useCaseForm.useCaseModel.permisosDisponibles}">
									<option name="permisosDelRolDisponiblesOption" value="${item.id}">${item.description}</option>
								</core:forEach>
							</select>
						</td>
					</tr>
				</table>
			</layout:panel>
		
			<layout:panel styleClass="subPanel" key="Asignación de Usuarios" width="100%" align="center">
				<table width="100%">
					<tr>
						<td width="10px">Asignados</td>
						<td width="10px"></td>
						<td width="100px">Disponibles</td>
					</tr>
					<tr valign="middle">
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="usuariosDelRol" name="usuariosDelRol" property="useCaseModel.usuariosDelRol" multiple="multiple" size="15" >
								 <core:forEach var="item" items="${useCaseForm.useCaseModel.usuarios}">
									<option  name="usuariosDelRolOption"  value="${item.id}">${item.loginName}</option>
								 </core:forEach>
							</select>
						</td>
						<td class="imageColumn" width="10px" align="center"  valign="middle">
							<table width="10px" align="center">
							  <layout:column styleClass="imageColumn">
								    <layout:submit styleClass="formSubmit" onclick="asignar('usuariosDelRolDisponibles','usuariosDelRol');return false;" mode="edit:E,create:E,remove:D,view:D">&lt;</layout:submit>
								    <layout:submit styleClass="formSubmit" onclick="asignar('usuariosDelRol','usuariosDelRolDisponibles');return false;" mode="edit:E,create:E,remove:D,view:D">&gt;</layout:submit>
							  </layout:column>
							</table>						
						</td>
						<td class="inputSelectRol">
							<select class="inputSelectRol" id="usuariosDelRolDisponibles" name="usuariosDelRolDisponibles" property="useCaseModel.usuariosDelRolDisponibles"  multiple="multiple" size="15">
								<core:forEach var="item" items="${useCaseForm.useCaseModel.usuariosDisponibles}">
									<option name="usuariosDelRolDisponiblesOption" value="${item.id}">${item.loginName}</option>
								</core:forEach>
							</select>
						</td>
					</tr>
				</table>
			</layout:panel>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="altaRolCatedra" onclick="seleccionarTodos('permisosDelRol');seleccionarTodos('usuariosDelRol');" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:E,remove:N,view:N"/>
					<layout:submit reqCode="bajaRolCatedra" value="Aceptar" styleClass="formSubmit" mode="edit:N,create:N,remove:E,view:N"/>
					<layout:submit reqCode="modificacionRolCatedra" value="Aceptar" onclick="seleccionarTodos('permisosDelRol');seleccionarTodos('permisosDelRolDisponibles');seleccionarTodos('usuariosDelRol');seleccionarTodos('usuariosDelRolDisponibles');" styleClass="formSubmit" mode="edit:E,create:N,remove:N,view:N"/>
					<layout:submit reqCode="cancelarRolCatedra" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>
