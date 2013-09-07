package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.ArrayList;
import java.util.Collection;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados.ConEstado;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;

/**
 * 
 * @author Rafa
 *
 */

public class EvaluacionFase extends ModelObject{
	ConEstado estadoApropiacion;
	Grupo grupo;
	Ayudante corrector;
	Collection itemsCorregidos;
	
	
	public Ayudante getCorrector() {
		return corrector;
	}
	public void setCorrector(Ayudante corrector) {
		this.corrector = corrector;
	}
	public ConEstado getEstadoApropiacion() {
		return estadoApropiacion;
	}
	public void setEstadoApropiacion(ConEstado estadoApropiacion) {
		this.estadoApropiacion = estadoApropiacion;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public Collection getItemsCorregidos() {
		return itemsCorregidos;
	}
	public void setItemsCorregidos(Collection itemsCorregidos) {
		this.itemsCorregidos = itemsCorregidos;
	}
}
