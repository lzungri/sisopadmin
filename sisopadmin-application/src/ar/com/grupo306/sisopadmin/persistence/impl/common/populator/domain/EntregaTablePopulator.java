package ar.com.grupo306.sisopadmin.persistence.impl.common.populator.domain;

import java.text.ParseException;

import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Entrega;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Fase;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;
import ar.com.grupo306.sisopadmin.persistence.impl.common.populator.TablePopulator;

public class EntregaTablePopulator extends TablePopulator {
	
	public void populateEntregas() throws ParseException{
		createAndSaveEntrega("grupo1", "Emulaso", 1, "02092007"); // desaprobada
		createAndSaveEntrega("grupo1", "Emulaso", 1, "19092007"); // aprobada
		createAndSaveEntrega("grupo1", "Emulaso", 2, "18092007"); // desaprobada		
		createAndSaveEntrega("grupo1", "Emulaso", 3, "24092007"); // desaprobada
		createAndSaveEntrega("grupo1", "Emulaso", 3, "28092007"); // aprobada
		createAndSaveEntrega("grupo1", "Emulaso", 3, "02102007"); // aprobada				
		createAndSaveEntrega("grupo1", "Emulaso", 5, "10112007"); // aprobada		
		createAndSaveEntrega("grupo1", "Emulaso", 6, "17112007"); // desaprobada
		createAndSaveEntrega("grupo1", "Emulaso", 6, "20112007"); // desaprobada
		createAndSaveEntrega("grupo1", "Emulaso", 6, "25112007"); // aprobada
	}
		
	private void createAndSaveEntrega(String loginUserGrupo, String nombreTP, Integer numeroFase, String fechaEntrega) throws ParseException{		
		Entrega entrega = new Entrega();
		entrega.setGrupo((Grupo)Grupo.findMeByLoginName(loginUserGrupo));		
		entrega.setFase(Fase.findMeByNombreTPYNumeroFase(nombreTP, numeroFase));
		entrega.setFechaEntrega(DateUtils.getDateFromString(fechaEntrega, "ddMMyyyy"));
		entrega.setPathArchivo("");
		entrega.setDescargarDeCVS(true);
		entrega.setObservaciones("");
		entrega.save();
	}
}
