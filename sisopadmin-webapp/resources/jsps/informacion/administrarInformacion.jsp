<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html>
	<layout:form action="administrarInformacionCatedraUseCase" reqCode="buscarInformacionCatedra" styleClass="mainCUForm">
		<h3 class="tituloCU">Administraci�n de Informaci�n de la C�tedra</h3>
  		<input type="hidden"  id="index" type="text" name="useCaseModel.index" value="0"/>
		<layout:panel  styleClass="panel" key="" width="100%" align="center">
			<layout:panel styleClass="subPanel" key="" width="100%" align="center">
	    		<layout:row>
				    	<layout:select property="useCaseModel.buscarPor" key="Buscar por ">
							<layout:option key="Nombre" value="0"/>
							<layout:option key="Descripci�n" value="1"/>
						</layout:select>
						<layout:text styleId="valor" property="useCaseModel.valorBusqueda" key="Valor "/>
						<layout:submit reqCode="buscarInformacionCatedra" value="Buscar" styleClass="formSubmit"/>
				</layout:row>
			</layout:panel>
			<display:table id="informacion" name="useCaseForm.useCaseModel.informaciones"  defaultorder="ascending" style="formColsColorx1">
				  <display:column class="imageColumn" title="">
				  		<core:if test="${informacion.inHome}">
				  			<img src="/sisopadmin/resources/images/buttons/home.png" onclick="" onmouseover="window.status=' Esta informaci�n actualmente se esta mostrando en la P�gina Principal.';return true;" onmouseout="window.status='';return true;" title=" Esta informaci�n actualmente se esta mostrando en la P�gina Principal."/>
				  		</core:if>
				  </display:column>	
			      <display:column class="textColumn" value="${informacion.nombre}" title="Nombre" />
			      <display:column class="textColumn" value="${informacion.descripcion}" title="Descripci�n"/>
			      <display:column class="imageColumn">
				      	<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" onclick=" document.getElementById('index').value=${informacion.id};; this.form.elements['reqCode'].value='modificacionInformacionCatedra'" onmouseover="window.status='Modificar';return true;" onmouseout="window.status='';return true;" title=" Modificar"/>
			      </display:column>
			      <display:column class="imageColumn"> 
				      <input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" onclick=" document.getElementById('index').value=${informacion.id};; this.form.elements['reqCode'].value='bajaInformacionCatedra'" onmouseover="window.status='Eliminar';return true;" onmouseout="window.status='';return true;" title=" Eliminar"/>
			      </display:column>
			</display:table>
	
			<layout:formActions align="right">
				<layout:row>
						<layout:submit reqCode="altaInformacionCatedra" value="Nueva Informaci�n" styleClass="formSubmit"/>
						<layout:submit reqCode="cancelUseCase" value="Cancelar" styleClass="formSubmit"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>
