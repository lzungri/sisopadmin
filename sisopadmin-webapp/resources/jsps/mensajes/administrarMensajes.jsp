<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<layout:form action="administrarMensajesUseCase" reqCode="" styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Comunicación de la Cátedra</h3>
    	<layout:panel styleClass="panel" key="Comunicar" width="100%" align="center">
	    	<layout:panel styleClass="subPanel" key="Busqueda" width="100%" align="center">
	    		<layout:row>
		      		<layout:select styleClass="inputSelect" property="useCaseModel.buscarPor" key="Buscar por: ">
						<layout:option key="Titulo" value="titulo"/>
						<layout:option key="Emisor" value="emisor"/>
						<layout:option key="Receptor" value="receptor"/>
						<layout:option key="Contenido" value="contenido"/>
					</layout:select>
			      	<layout:text styleId="valor " property="useCaseModel.valorBusqueda" key="Valor"/>
		      		<layout:submit reqCode="find" value="Aceptar" styleClass="formSubmit"/>
		      	</layout:row>
			</layout:panel>
		
			<input type="hidden" id="index" type="text" name="useCaseModel.index" value="0"/>
			<layout:panel styleClass="subPanel" key="" width="100%" align="center">
					<display:table id="mensaje" name="useCaseForm.useCaseModel.entities" defaultorder="ascending" style="formColsColorx1">
						<display:column class="imageColumn">
							<core:if test="${mensaje.leidoPorReceptor or mensaje.emisor.loginName == useCaseForm.useCaseModel.loggedUserLoginName}">
								<img src="/sisopadmin/resources/images/other/leido.gif" title="Leído"> 
							</core:if>
							<core:if test="${not mensaje.leidoPorReceptor and mensaje.emisor.loginName != useCaseForm.useCaseModel.loggedUserLoginName}">
								<img src="/sisopadmin/resources/images/other/no_leido.gif" title="No leído"> 
							</core:if>							
						</display:column>						
						<display:column class="textColumn" value="${mensaje.titulo}" title="Titulo"/>
						<display:column class="textColumn" value="${mensaje.emisor.nombreCompleto}" title="Emisor"/>
						<display:column class="textColumn" value="${mensaje.receptor.nombreCompleto}" title="Receptor"/>
						<display:column class="textColumn" value="${mensaje.fechaAlta}" title="Fecha" format="{0,date,dd/MM/yyyy}"/>
						<display:column class="imageColumn"> 
							<input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick=" document.getElementById('index').value=${mensaje_rowNum - 1}; this.form.elements['reqCode'].value='view'" onmouseout="window.status='Visualizar';return true;"  title=" Visualizar"  onmouseover="TagToTip('contenidoDiv_${mensaje.id}')"> 
							<div id="contenidoDiv_${mensaje.id}">${mensaje.contenido}</div>
						</display:column>
					</display:table>
			</layout:panel>

			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="create" value="Nuevo mensaje" styleClass="formSubmit"/>
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>
