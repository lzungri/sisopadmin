<%@ include file="/resources/jsps/templates/page-commons.jsp"%>

<layout:form styleClass="mainCUForm" align="center">
	<h3 class="tituloCU">Especificacion del Trabajo Práctico	${useCaseForm.useCaseModel.tp.nombre}</h3>
	<layout:panel styleClass="panel" key= "Fases" width="100%" align="center">
		<display:table uid="fase" name="useCaseForm.useCaseModel.tp.fases" defaultsort="1" style="formColsColorx1">
			<display:column class="textColumnCentrado" value="${fase.numero}" title="Número"/>
			<display:column class="textColumn" value="${fase.nombre}" title="Nombre" />
			<display:column class="textColumnCentrado" value="${fase.fechaInicio}" format="{0,date,dd/MM/yyyy}" title="Fecha Inicio" />
			<display:column class="textColumnCentrado" value="${fase.fechaFin}" format="{0,date,dd/MM/yyyy}" title="Fecha Finalización" />
			<display:column class="textColumnCentrado" title="Entrega Obligatoria">
					<core:if test="${!fase.entregaObligatoria}">	
				  	 	<input type="image" src="/sisopadmin/resources/images/other/okVacio.gif" value="Entrega No Obligatoria" onclick="return false;"  title=" Entrega No Obligatoria"/> 
				  	</core:if>
				  	<core:if test="${fase.entregaObligatoria}">
				  		<input type="image" src="/sisopadmin/resources/images/other/ok.gif" value="Entrega Obligatoria" onclick="return false;"  title=" Entrega Obligatoria"/>  
				  	</core:if>	
			</display:column>		
		</display:table>
		<layout:panel styleClass="subPanel" key= "Consignas" width="100%" align="center">
			${useCaseForm.useCaseModel.infoConsignas}	
		</layout:panel>
		<layout:formActions align="right">
				<layout:row>
					<layout:submit reqCode="cancelUseCase" value="Volver" styleClass="formSubmit"/>
				</layout:row>	
		</layout:formActions>
	</layout:panel>
</layout:form>
