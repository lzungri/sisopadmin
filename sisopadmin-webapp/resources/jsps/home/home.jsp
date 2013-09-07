<%@ include file="/resources/jsps/templates/page-commons.jsp" %>

<layout:form styleClass="home">
		<core:forEach var="item" items="${useCaseForm.useCaseModel.informaciones}">
			<layout:toggleable id="informacion_${item.id}" text="${item.nombre}" containerStyle="informacionContainer" style="informacionLabel" tooltip="${item.descripcion}">
				<table class="informacionHome" align="center">
					<layout:row styleClass="home">${item.contenido}</layout:row>
				</table>
			</layout:toggleable>
		</core:forEach>
</layout:form>


