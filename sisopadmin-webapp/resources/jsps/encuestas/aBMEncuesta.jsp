<%@ include file="/resources/jsps/templates/page-commons.jsp" %>


<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html  align="center">
    <layout:form action="aBMEncuestaUseCase" reqCode="acceptUseCase"  styleClass="mainCUForm">
    	<h3 class="tituloCU">Encuesta</h3>
		<layout:panel styleClass="panel" key="" width="100%" align="center">
			    <layout:panel styleClass="subPanel" key="Datos del punto a encuestar" width="100%" align="center">
				    
			    <layout:text styleId="Nombre:" property="useCaseModel.nombrePlantilla" key="Nombre"  mode="edit:I,view:I,remove:I"/>
				<layout:calendar id="fechaInicio" beanName="useCaseForm" property="useCaseModel.fechaInicio" description="Inicio de la encuesta" mode="edit:I,view:I,remove:I"/>			
				<layout:calendar id="fechaFin" beanName="useCaseForm" property="useCaseModel.fechaFin" description="Fin de la encuesta" mode="edit:I,view:I,remove:I"/>			
				<!--layout:checkbox styleId="obligatoria" property="useCaseModel.obligatoria" key="Obligatoria"  mode="edit:D,view:D,remove:D"/-->
				<layout:select property="useCaseModel.destinatario" key="Destinatario"  mode="view:D,remove:D,edit:D">
					<core:forEach var="destin" items="${useCaseForm.useCaseModel.destinatarios}">
						<option>${destin}</option>
					</core:forEach>
				</layout:select>
		
			</layout:panel>
		
		<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.cargarPuntoAEncuestar" evaluateMode="true">
			<layout:panel styleClass="subPanel" key="Datos del punto a encuestar" width="100%" align="center">
				    <layout:text styleId="nombrePunto" property="useCaseModel.nombrePunto" key="Nombre del punto:"  mode="edit:D,view:D,remove:D"/>
					<layout:text styleId="descripcionPunto" property="useCaseModel.descripcionPunto" key="Descripcion del punto:"  mode="edit:D,view:D,remove:D"/>
					<layout:formActions align="right">
						<layout:row>
	                		<layout:submit styleClass="formSubmit" reqCode="cargarPuntoAEncuestar" value="Aplicar" />				
	                		<layout:submit styleClass="formSubmit" reqCode="cancelarCargaModificacion" value="Cancelar" />
	                	</layout:row>
	                </layout:formActions>
			</layout:panel>
		</layout:hasPermission>	
			<layout:row>
				<layout:column >
					<h3 class="tituloCU">Puntos de la encuesta</h3>
				</layout:column>
			</layout:row>
			<layout:row>
			<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
		    <display:table id="punto" name="useCaseForm.useCaseModel.puntos"  defaultorder="ascending">
				      <layout:row>
				      <display:column class="textColumnCentrado" value="${punto.numero}" title="Numero" />
				      <display:column class="textColumn" value="${punto.descripcion}" title="Descripcion"/>
				      <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.cargarPuntoAEncuestar" evaluateMode="true">							      
					      <display:column class="imageColumn"> <input type="submit"  class="formSubmit" value="Modificar" onclick=" document.getElementById('index').value=${punto.numero}; this.form.elements['reqCode'].value='modificarPunto'"  mode="view:D,remove:D"/> </display:column>
						  <display:column class="imageColumn"> <input type="submit"  class="formSubmit" value="Eliminar" onclick=" document.getElementById('index').value=${punto.numero}; this.form.elements['reqCode'].value='eliminarPunto'"  mode="view:D,remove:D"/> </display:column>					   
					   </layout:hasPermission> 
					   <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta" evaluateMode="false">		
					      <display:column class="imageColumn" title="Puntaje">
					      	<table align="center"><tr><td>
							      <layout:select property="useCaseModel.puntaje[${punto.numero}]" key=""  mode="view:D,remove:D,edit:E" onchange=" document.getElementById('index').value=${punto.numero}; this.form.elements['reqCode'].value='cargarPuntaje'; document.forms[2].submit()" tooltip="MB=8 a 10 , B= 6 a 7 , R= 4 a 5 , I= 1 a 3">
									<layout:option value="1" />
									<layout:option value="2" />
									<layout:option value="3" />
									<layout:option value="4" />
									<layout:option value="5" />
									<layout:option value="6" />
									<layout:option value="7" />
									<layout:option value="8" />
									<layout:option value="9" />
									<layout:option value="10" />
								   </layout:select>
							</td></tr></table>
				   		</display:column>
					</layout:hasPermission>		
					</layout:row>			    
			</display:table>
        </layout:row>
        <layout:formActions align="right">
			<layout:row>	
				<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.ABMEncuestaUseCaseModel.cargarPuntoAEncuestar" evaluateMode="true">							      
					<layout:submit styleClass="formSubmit" reqCode="resecuenciarEncuesta" value="Resecuenciar" mode="view:N"/>	
			</layout:hasPermission>	
			<layout:submit styleClass="formSubmit" reqCode="aceptar" value="Aceptar" mode="view:N"/>
	    	<layout:submit styleClass="formSubmit" reqCode="volver" value="Cancelar"/>
	     	</layout:row>
	     </layout:formActions>	
	     </layout:panel>

    </layout:form>
</layout:html>            
<schema xmlns="http://www.w3.org/2001/XMLSchema" targetNamespace="http://www.example.org/" xmlns:tns="http://www.example.org/">
