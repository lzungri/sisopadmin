package ar.com.grupo306.usecasefwk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.grupo306.usecasefwk.usecases.models.mode.UseCaseModelMode;

/**
 * Annotation utilizada para indicar que un m�todo es seguro, esto significa
 * que se deber� aplicar, previa ejecuci�n de �l, alg�n mecanismo de seguridad
 * para chequear que se posean los permisos para ejecutarlo.
 * 
 * Ejemplo 1:
 * 		@Secure ({CreateMode.class, EditMode.class})
 * Indicar� que para ejecutar el m�todo en los modos de creaci�n y edici�n ser� 
 * necesario tener los permisos propios de cada modo. Para los restantes modos
 * disponibles (availableModes()) no es necesario, s�lo se requiere el permiso 
 * normal de acceso al m�todo.
 * 
 * Ejemplo 2:
 * 		Se quiere que la acci�n de aceptar s�lo est� disponible en el modo de creaci�n,
 * el UseCase adicionalmente puede funcionar en el modo visualizaci�n, entonces:
 * @Secure ({CreateMode.class})
 * tatata accept(tatatataa) {}
 * 
 * Cuando se encuentre en el modo Create se requerir� el permiso xxx.accept.create y el
 * permiso xxx.accept.
 * Cuando se encuentre en el modo Edit se requerir� s�lo xxx.accept.
 *
 * @author Leandro
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Secure {
	
	/** Array de modos en los cuales se requieren permisos de ejecuci�n. */
	Class<? extends UseCaseModelMode> []value();
}