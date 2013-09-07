<%@taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<logic:notEmpty scope="request" name="login.message">
	<p class="formText"><%=request.getAttribute("login.message")%></p>
</logic:notEmpty>
<layout:html>
	<h3 class="tituloCU">Consigna</h3>
	<layout:form action="aBMConsignaUseCase" reqCode="acceptUseCase" styleClass="mainCUForm">
    	<layout:panel styleClass="panel" key="Información de la Consigna" width="100%" align="center">
		    <layout:text styleClass="inputText"  styleId="numero" property="useCaseModel.consigna.numero" key="Número:" mode="view:D,remove:D"/>
			<layout:textarea styleClass="inputTextArea" cols="50" rows="20" property="useCaseModel.consigna.descripcion" key="Descripcion:" mode="view:D,remove:D"/>				
			<layout:formActions align="right">
				<layout:row>
					<layout:submit styleClass="formSubmit" reqCode="createAccept" value="Aceptar" mode="edit:N,create:E,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="editAccept" value="Actualizar" mode="edit:E,create:N,remove:N,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="removeAccept" value="Eliminar" mode="edit:N,create:N,remove:E,view:N"/>
					<layout:submit styleClass="formSubmit" reqCode="cancelUseCase" value="Cancelar"/>
				</layout:row>
			</layout:formActions>
		</layout:panel>
	</layout:form>
</layout:html>