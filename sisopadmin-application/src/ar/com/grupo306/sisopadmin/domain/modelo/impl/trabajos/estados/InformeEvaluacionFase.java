package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados;

import java.util.Date;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.Tp;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Ayudante;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.Grupo;

/**
 * 
 * @author Rafa
 *
 */

public class InformeEvaluacionFase extends ConEstado{
	Ayudante ayudanteEvaluador;
	boolean faseAprobada;
	Date fechaAlta;
	Date fechaEvaluado;
	Grupo grupoEvaluado;
	String pathInforme;
	Tp tp;
}
