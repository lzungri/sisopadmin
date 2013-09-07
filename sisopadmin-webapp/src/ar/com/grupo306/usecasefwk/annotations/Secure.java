package ar.com.grupo306.usecasefwk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Annotation utilizada para indicar que un método es seguro, esto significa
 * que se deberá aplicar, previa ejecución de él, algún mecanismo de seguridad
 * para chequear que se posean los permisos para ejecutarlo.
 * 
 * Ejemplo 1:
 * 		@Secure ({CreateMode.class, EditMode.class})
 * Indicará que para ejecutar el método en los modos de creación y edición será 
 * necesario tener los permisos propios de cada modo. Para los restantes modos
 * disponibles (availableModes()) no es necesario, sólo se requiere el permiso 
 * normal de acceso al método.
 * 
 * Ejemplo 2:
 * 		Se quiere que la acción de aceptar sólo esté disponible en el modo de creación,
 * el UseCase adicionalmente puede funcionar en el modo visualización, entonces:
 * @Secure ({CreateMode.class})
 * tatata accept(tatatataa) {}
 * 
 * Cuando se encuentre en el modo Create se requerirá el permiso xxx.accept.create y el
 * permiso xxx.accept.
 * Cuando se encuentre en el modo Edit se requerirá sólo xxx.accept.
 *
 * @author Leandro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secure {
	
	/** Array de modos en los cuales se requieren permisos de ejecución. */
	Class<? extends UseCaseModelMode> []value();
}