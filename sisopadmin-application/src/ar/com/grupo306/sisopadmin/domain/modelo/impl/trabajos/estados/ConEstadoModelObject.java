package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

/**
 * Representa una entidad que posee un estado.
 *
 * @author Leandro
 */
public class ConEstadoModelObject extends ModelObject {
	private Estado estado;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
}