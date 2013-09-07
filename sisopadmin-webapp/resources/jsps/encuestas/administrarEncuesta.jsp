<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:html align="center">
    <layout:form action="administrarEncuestaUseCase" reqCode="acceptUseCase"  styleClass="mainCUForm">
    	<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta" evaluateMode="true">
		    	
		    		<span id='avisoEncuestas' class='formLabel'><h2>Las encuestas son anónimas. Una vez llenadas quedarán sin asociación con el usuario</h2></span>					
		    	
		</layout:hasPermission>
    	

    	
    	<h3 class="tituloCU">Administración de Encuestas</h3>
    	<layout:panel styleClass="panel" key="" width="100%" align="center">
			
			<input id="tipoBuscar" type="hidden" name="useCaseModel.tipoBuscar" value="0"/>
			<layout:panel styleClass="subPanel" key="Búsqueda de encuestas" width="100%" align="center">
				    <layout:text styleId="nombreEncuesta" property="useCaseModel.nombrePlantillaBusqueda" key="Nombre de la encuesta:"/>
					<layout:calendar style="inputCalendar" id="fechaInicio" beanName="useCaseForm" property="useCaseModel.fechaInicioBusqueda" description="Inicio de Encuesta" />
					<layout:calendar style="inputCalendar" id="fechaFin" beanName="useCaseForm" property="useCaseModel.fechaFinBusqueda" description="Fin de la Encuesta"/>
					<layout:formActions align="right">
						<layout:row>
							<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.eliminarEncuesta" evaluateMode="true">
								<input type="submit" class="formSubmit" value="Buscar" onclick="document.getElementById('tipoBuscar').value='NORMAL'; this.form.elements['reqCode'].value='buscarEncuesta'"/>
							</layout:hasPermission>
							<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta" evaluateMode="true">
								<input type="submit" class="formSubmit" value="Buscar" onclick="document.getElementById('tipoBuscar').value='LLENAR'; this.form.elements['reqCode'].value='buscarEncuestaLlenado'"/>
							</layout:hasPermission>
						</layout:row>	
					</layout:formActions>
			</layout:panel>
		
			<layout:row>	
				<input id="index" type="hidden" name="useCaseModel.index" value="0"/>
				<layout:panel styleClass="subPanel" key="Resultado de la búsqueda" align="center">
			        <display:table id="encuesta" name="useCaseForm.useCaseModel.encuestas"  defaultorder="ascending">
					      <display:column class="textColumn" value="${encuesta.nombre}" title="Nombre" />
					      <display:column class="textColumn" value="${encuesta.descripcionEstado}" title="Estado"/>
					      <display:column class="textColumnCentrado" value="${encuesta.fechaAlta}" format="{0,date,dd/MM/yyyy}" title="Fecha de comienzo"/>
		   			      <display:column class="textColumnCentrado" value="${encuesta.fechaFin}" format="{0,date,dd/MM/yyyy}" title="Fecha de finalización"/>
		    		   	  
		   			      <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.consultarEncuesta" evaluateMode="true">
			   			  		<display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/visualizar.gif" value="Visualizar" onclick="document.getElementById('index').value=${encuesta.id};this.form.elements['reqCode'].value='consultarEncuesta'" title="Visualizar"/> </display:column>
			   			  </layout:hasPermission> 

		   			      <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.modificarEncuesta" evaluateMode="true">
			   			      <display:column class="imageColumn" >
			   			      		<core:if test="${encuesta.descripcionEstado == 'Sin Encuestar'}">
					   			      	<input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Modificar" onclick="document.getElementById('index').value=${encuesta.id};this.form.elements['reqCode'].value='modificarEncuesta'" title="Modificar"/> 						
									</core:if>			   			
					   		  </display:column>
			   			  </layout:hasPermission>   			  

			   			  <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.eliminarEncuesta" evaluateMode="true">
			   			      <display:column class="imageColumn">
			   			      		<input type="image" src="/sisopadmin/resources/images/buttons/eliminar.gif" value="Eliminar" onclick="document.getElementById('index').value=${encuesta.id}; this.form.elements['reqCode'].value='eliminarEncuesta'" title="Eliminar">
			   			      </display:column>  
		   			      </layout:hasPermission>   
		   			 
			   			  <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.visualizarResultadoEncuesta" evaluateMode="true">
			   			  	
			   			      <display:column class="imageColumn">
				   			      <core:if test="${encuesta.descripcionEstado != 'Sin Encuestar'}">
				   			      	<input type="image" src="/sisopadmin/resources/images/buttons/Chart_pie.png" value="Resultado" onclick="document.getElementById('index').value=${encuesta.id}; this.form.elements['reqCode'].value='visualizarResultadoEncuesta'" title="Resultado"/> 
				   			      </core:if>
			   			      </display:column>			   			   
			   			     
			   			  </layout:hasPermission>

			   			  <layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.llenarEncuesta" evaluateMode="true">
							 <display:column class="imageColumn"><input type="image" src="/sisopadmin/resources/images/buttons/modificar.gif" value="Llenar" onclick="document.getElementById('index').value=${encuesta.id};this.form.elements['reqCode'].value='llenarEncuesta'" title="Llenar"/> </display:column>
						  </layout:hasPermission>  
				    </display:table>
			    	<!-- reveer la condicion core:if test="${encuesta.descripcionEstado != 'Sin Encuestar'}"-->
				    	<table align="right" style="padding: 5px;">
							<tr><td style="padding: 5px;">
								<div class="toggleContainerClass">
									<span>Formato de salida del reporte</span>
									<select name="useCaseModel.outputFormat" class="inputSelect">
										<option value="pdf">PDF</option>
										<option value="xml">XML</option>					
									</select>
								</div>
							</td></tr>
						</table>
					<!--/core:if-->
					<layout:formActions align="right">
			   			 <layout:row>	
								<layout:hasPermission permissionKey="ar.com.grupo306.sisopadmin.web.usecases.domain.encuestas.AdministrarEncuestaUseCaseModel.cargarEncuesta" evaluateMode="true">
									<layout:submit styleClass="formSubmit" reqCode="cargarEncuesta" value="Nueva Encuesta"/>	

								</layout:hasPermission>
								<layout:submit styleClass="formSubmit" reqCode="cancelar" value="Cancelar" />		
						</layout:row>
					</layout:formActions>
			    </layout:panel>
			</layout:row>
		</layout:panel>	
    </layout:form>
</layout:html>       

<schema xmlns="http://www.w3.org/2001/XMLSchema" targetNamespace="http://www.example.org/" xmlns:tns="http://www.example.org/">