package ar.com.grupo306.commons.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation utilizada para indicar que un m�todo es transaccional.
 * Esto hace que requiera, previa su ejecuci�n, establecer una conexi�n a la
 * base y luego cerrarla con commit o rollback dependiendo de la condici�n de error
 * que pueda haber arrojado o no el m�todo ante su ejecuci�n.
 *
 * @author Leandro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {

}