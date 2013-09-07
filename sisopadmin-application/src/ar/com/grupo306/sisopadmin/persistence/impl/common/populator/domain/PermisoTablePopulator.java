package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

/**
 * Esta clase cargar� la base con aquellos permisos que correspondan a 
 * funcionalidades no asociadas a m�todos de UseCases.
 * Es decir, permisos creados manualmente para autorizar ciertas operaciones, 
 * como por ejemplo, mostrar en pantalla o no un bot�n.
 * 
 * @author Leandro
 */
public class PermisoTablePopulator extends TablePopulator {
	
	public void populateBotoncito() {
//		save(new Permiso("altaTP.botonAceptar", "botonAceptar", "Presenta en pantalla el bot�n aceptar TP."));
//		save(new Permiso("altaTP.botonModificar", "botonModificar", "Presenta en pantalla el bot�n modificar TP."));
	}
	
}