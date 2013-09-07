package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.EvaluacionFasePorPlantilla;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.InformeEvaluacionFase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.ItemEvaluacion;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.ConEstadoModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.Estado;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Popula la base con los estados básicos.
 *
 * @author Leandro
 */
public class EstadoTablePopulator extends TablePopulator {

	public void populateEstadosInformeEvaluacion() {
		this.createAndSaveEstado(InformeEvaluacionFase.class, InformeEvaluacionFase.ESTADO_CODE_ENVIADA, "Informe enviado al grupo");
		this.createAndSaveEstado(InformeEvaluacionFase.class, InformeEvaluacionFase.ESTADO_CODE_NOENVIADA, "Informe no enviado aún al grupo");
	}
	
	public void populateEstadosEvaluacionFase() {
		this.createAndSaveEstado(EvaluacionFasePorPlantilla.class, EvaluacionFasePorPlantilla.ESTADO_CODE_INCOMPLETA, "Existe al menos un item obligatorio no evaluado");
		this.createAndSaveEstado(EvaluacionFasePorPlantilla.class, EvaluacionFasePorPlantilla.ESTADO_CODE_COMPLETA, "Todos los items obligatorios fueron evaluados");
	}
	
	public void populateEstadosItemEvaluacion() {
		this.createAndSaveEstado(ItemEvaluacion.class, ItemEvaluacion.ESTADO_CODE_CORREGIDO, "El item de la plantilla ha sido corregido");
		this.createAndSaveEstado(ItemEvaluacion.class, ItemEvaluacion.ESTADO_CODE_NOCORREGIDO, "El item de la plantilla aún no ha sido corregido");
	}
	
	private void createAndSaveEstado(Class<? extends ConEstadoModelObject> conEstado, String domainCode, String descripcion) {
		Estado estado = new Estado();
		estado.setDomainCode(domainCode);
		estado.setDescripcion(descripcion);
		estado.setClassName(conEstado.getName());
		
		save(estado);
	}
}