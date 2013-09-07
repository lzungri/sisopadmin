package ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.asignacion;

import java.util.Collection;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.encuestas.PlantillaEncuesta;

public class AsignadorDeEncuestas {
	static String AYUDANTE = "Ayudante";
	static String GRUPO = "Grupo";
		
	
	static AsignadorDeEncuestas asignadorDefault;
	AsignadorDeEncuestas asignadorAAyudantes;
	static AsignadorDeEncuestas asignadorAGrupos;
	
	/**
	 * Funciona como singleton y factory de asignadores de encuestas
	 * @param strategy
	 * @return
	 */
	public static AsignadorDeEncuestas getInstance(String strategy){
		//Solo está implementada la asignacion a alumnos
		if(GRUPO.equalsIgnoreCase(strategy)){
			if(asignadorAGrupos == null)
				asignadorAGrupos = new AsignadorDeEncuestasAGrupos();
			return asignadorAGrupos;
		}
		if(AYUDANTE.equalsIgnoreCase(strategy)){
			if(asignadorAGrupos == null)
				asignadorAGrupos = new AsignadorDeEncuestasAAyudantes();
			return asignadorAGrupos;
		}
		if(asignadorDefault == null)
			asignadorDefault = new AsignadorDeEncuestas();
		return asignadorDefault;
	}
	
	/**
	 * asigna encuestas a todos los alumnos
	 * @param encuestas
	 * @return
	 */
	public boolean asignar(Collection encuestas){
		
		
		
		return false;
		
	}
	/**
	 * Asigna la encuesta a todos los usuarios
	 * @param encuesta
	 * @return
	 */
	public boolean asignar(PlantillaEncuesta encuesta){
		
		
		
		return false;
		
	}

	
}
