package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo;

/**
 * TODO - Definir la jerarquía de estados
 * @author Rafa
 *
 */

public abstract class EstadoGrupo {
	public static Long REGISTRADO  = 1L;
	public static Long CONFIRMADO_DIGITAL  = 2L;
	public static Long CONFIRMADO_MANUAL  = 3L;
	public static Long CON_CONFLICO_DE_INSCRIPCION  = 4L;
	public static Long INSCRIPTO  = 5L;
	public static Long RECHAZADO  = 6L;	
}


