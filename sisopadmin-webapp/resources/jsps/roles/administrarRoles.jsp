<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html align="center">
	<layout:form action="administrarRolesUseCase" reqCode="buscarRolCatedra" styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Roles de la Cátedra</h3>
		<layout:panel  styleClass="panel" key="" width="100%" align="center">
				<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>
				<layout:panel styleClass="subPanel" key="Busqueda" width="100%" align="center">
		    		<tr><td><table align="center">
				    	<layout:row>
				      		<layout:select  styleClass="inputSelect"  property="useCaseModel.buscarPor" key="Buscar por: ">
								<layout:option key="Nombre" value="0"/>
								<layout:option key="Descripción" value="1"/>
							</layout:select>
							<layout:text styleClass="inputSelect" styleId="valor" property="useCaseModel.valorBusqueda" key="Valor"/>
							<layout:submit reqCode="buscarRolCatedra" value="Buscar" styleClass="formSubmit"/>
						</layout:row>
					</table></td></tr>
				</layout:panel>
				<display:table id="rol" name="useCaseForm.useCaseModel.roles"  defaultorder="ascending" style="formColsColorx1">
				      <display:column class="textColumn" value="${rol.nombre}" title="Nombre" />
				      <display:column class="textColumn" value="${rol.descripcion}" title="Descripción"/>
				      <display:column class="imageColumn">			      
					      	<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" onclick=" document.getElementById('index').value=${rol.id};; this.form.elements['reqCode'].value='visualizarRolCatedra'" onmouseover="window.status='Visualizar';return true;" onmouseout="window.status='';return true;" title=" Visualizar"/>		
				      </display:column>				      
				      <display:column class="imageColumn"> 
					      	<core:if test="${!rol.deSistema}">
					      		<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" onclick=" document.getElementById('index').value=${rol.id};; this.form.elements['reqCode'].value='modificacionRolCatedra'" onmouseover="window.status='Modificar';return true;" onmouseout="window.status='';return true;" title=" Modificar"/>
					      	</core:if>
					      	<core:if test="${rol.deSistema}">
					      		<img src="/sisopadmin/resources/images/buttons/modificarNO.gif" onclick="return false;"; onmouseover="window.status='Modificar';return true;" onmouseout="window.status='';return true;" title="No se puede modificar - Elemento del Sistema"/>		
					      	</core:if>
					  </display:column>
				      <display:column class="imageColumn">
				      		<core:if test="${!rol.deSistema}">
					      		<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" onclick=" document.getElementById('index').value=${rol.id};; this.form.elements['reqCode'].value='bajaRolCatedra'" onmouseover="window.status='Eliminar';return true;" onmouseout="window.status='';return true;" title=" Eliminar"/>		
					      	</core:if>
					      	<core:if test="${rol.deSistema}">
					      		<img src="/sisopadmin/resources/images/buttons/eliminarNO.gif" onclick="return false;"; onmouseover="window.status='Eliminar';return true;" onmouseout="window.status='';return true;" title="No se puede eliminar - Elemento del Sistema"/>		
					      	</core:if>
				      </display:column>
				</display:table>
				<layout:formActions align="right">
					<layout:row>
						<layout:submit reqCode="altaRolCatedra" value="Nuevo Rol" styleClass="formSubmit"/>
						<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
					</layout:row>
				</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>
