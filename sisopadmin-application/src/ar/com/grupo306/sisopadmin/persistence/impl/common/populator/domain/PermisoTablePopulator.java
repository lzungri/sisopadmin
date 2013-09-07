package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Esta clase cargará la base con aquellos permisos que correspondan a 
 * funcionalidades no asociadas a métodos de UseCases.
 * Es decir, permisos creados manualmente para autorizar ciertas operaciones, 
 * como por ejemplo, mostrar en pantalla o no un botón.
 * 
 * @author Leandro
 */
public class PermisoTablePopulator extends TablePopulator {
	
	public void populateBotoncito() {
//		save(new Permiso("altaTP.botonAceptar", "botonAceptar", "Presenta en pantalla el botón aceptar TP."));
//		save(new Permiso("altaTP.botonModificar", "botonModificar", "Presenta en pantalla el botón modificar TP."));
	}
	
}