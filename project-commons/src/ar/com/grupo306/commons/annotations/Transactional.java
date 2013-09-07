package ar.com.grupo306.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation utilizada para indicar que un método es transaccional.
 * Esto hace que requiera, previa su ejecución, establecer una conexión a la
 * base y luego cerrarla con commit o rollback dependiendo de la condición de error
 * que pueda haber arrojado o no el método ante su ejecución.
 *
 * @author Leandro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {

}