<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page isELIgnored = "false"%>

<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form action="administrarArchivosUseCase" reqCode="buscarArchivo" styleClass="mainCUForm">
		<h3 class="tituloCU">Administración de Archivos de la Cátedra</h3>
		<layout:panel  styleClass="panel" key="" width="100%" align="center">
	    	<layout:panel styleClass="subPanel" key="Busqueda" width="100%" align="center">
	    		<layout:row>
				    	<layout:select styleClass="inputSelect" property="useCaseModel.buscarPor" key="Buscar por ">
							<layout:option key="Nombre" value="0"/>
							<layout:option key="Descripción" value="1"/>
						</layout:select>
						<layout:text styleClass="inputSelect" styleId="valor" property="useCaseModel.valorBusqueda" key="Valor "/>
						<layout:submit reqCode="buscarArchivo" value="Buscar" styleClass="formSubmit"/>
				</layout:row>
			</layout:panel>
		
			<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>
		
			<display:table uid="archivo"  name="useCaseForm.useCaseModel.archivos" defaultorder="ascending" defaultsort="1" requestURI="administrarArchivosUseCase.do">
			      <display:column class="textColumn" value="${archivo.nombre}" title="Nombre"/>
			      <display:column class="textColumn" value="${archivo.descripcion}" title="Descripción"/>
			      <display:column class="imageColumn">
			      	  <input class="column" type="image" src="/sisopadmin/resources/images/buttons/download.gif" onclick="window.open('${archivo.ruta}','popup','');return false;" onmouseout="window.status='Download';return true;" title=" Download"/>
			      </display:column>
			      <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.archivos.AdministrarArchivosUseCaseModel.bajaArchivo" evaluateMode="false">
				      <display:column class="imageColumn"> 
					      <input class="column"  type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" onclick=" document.getElementById('index').value=${archivo.id};; this.form.elements['reqCode'].value='bajaArchivo'" onmouseover="window.status='Eliminar';return true;" onmouseout="window.status='';return true;" title=" Eliminar"/>
				      </display:column>
			      </layout:hasPermission>
			</display:table>
			<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="altaArchivo" value="Nuevo Archivo" styleClass="formSubmit"/>
					<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>
