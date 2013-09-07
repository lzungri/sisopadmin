package ar.com.grupo306.commons.utils.reflection;

import java.lang.reflect.InvocationTargetException;

import ar.com.grupo306.commons.factorys.ExceptionFactory;
import ar.com.grupo306.commons.utils.StringUtils;



/**
 * Añade nuevas funcionalidades al PropertyUtils de Apache.
 * 
 * @see org.apache.commons.beanutils.PropertyUtils
 * @author lzungri
 */
public class PropertyUtils extends org.apache.commons.beanutils.PropertyUtils {

	/**
	 * Retorna la property indicada por la posición <code>position</code>.
	 * Recordar que la numeración comienza desde 0, es decir para indexar la property
	 * se trabaja en la misma manera que un vector, el primer elemento es 0(cero).<br>
	 * Ejemplo:
	 * <li> "a.b.c.d", position = 3 -> "d"
	 * 
	 * @param fullProperty : Property completa sobre la que se realizará la búsqueda.
	 * @param position : Posición de la property, comienza desde 0(cero)-
	 * @return String
	 */
	public static String getPropertyAt(String fullProperty, int position) {
		if(countProperties(fullProperty) > position && position >= 0) {
			return StringUtils.split(fullProperty, ".")[position];
		}
		throw ExceptionFactory.createProgramException("Fallo al intentar acceder a la property de posicion: " + position +
				"dentro de la siguiente fullProperty: " + fullProperty);
	}
	
	/**
	 * Retorna la property anterior a la indicada por la posición <code>position</code>.
	 * Recordar que la numeración comienza desde 0, es decir para indexar la property
	 * se trabaja en la misma manera que un vector, el primer elemento es 0(cero).<br>
	 * Ejemplo:
	 * <li> "a.b.c.d", position = 3 -> "a.b.c"
	 * <li> "a.b.c.d", position = 4 -> no, no existe la property 4 => ProgramException.
	 * Si se quiere la property completa utilizar getCompletePropertyAt().
	 * 
	 * @param fullProperty : Property completa sobre la que se realizará la búsqueda.
	 * @param position : Posición de la property, comenzar desde 0(cero)-
	 * @return String
	 */
	public static String getPreviousPropertyAt(String fullProperty, int position) {
		if(countProperties(fullProperty) > position && position > 0) {
			return fullProperty.substring(0, StringUtils.positionOf(fullProperty, ".", position));
		}
		
		throw ExceptionFactory.createProgramException("Fallo al intentar acceder a la property anterior a la posicion: " + position +
				"dentro de la siguiente fullProperty: " + fullProperty);
	}
	
	/**
	 * Retorna la property siguiente a la indicada por la posición <code>position</code>.
	 * Recordar que la numeración comienza desde 0, es decir para indexar la property
	 * se trabaja en la misma manera que un vector, el primer elemento es 0(cero).<br>
	 * Ejemplo:
	 * <li> "a.b.c.d", position = 3 -> null
	 * <li> "a.b.c.d", position = 1 -> "c.d"
	 * 
	 * @param fullProperty : Property completa sobre la que se realizará la búsqueda.
	 * @param position : Posición de la property, comenzar desde 0(cero)-
	 * @return String
	 */
	public static String getCompleteNextPropertyAt(String fullProperty, int position) {
		if(countProperties(fullProperty) > position) {
			int beginIndex = StringUtils.positionOf(fullProperty, ".", position + 1);
			if(beginIndex > 0)
				return fullProperty.substring(beginIndex + 1);	
			else
				return null;
		}
		
		throw ExceptionFactory.createProgramException("Fallo al intentar acceder a la property siguiente a la posicion: " + position +
				"dentro de la siguiente fullProperty: " + fullProperty);
	}
	
	/**
	 * Retorna la property completa a la indicada por la posición <code>position</code>.
	 * Recordar que la numeración comienza desde 0, es decir para indexar la property
	 * se trabaja en la misma manera que un vector, el primer elemento es 0(cero).<br>
	 * Ejemplo:
	 * <li> "a.b.c.d", position = 2 -> "a.b.c"
	 * <li> "a.b.c.d", position = 3 -> "a.b.c.d"
	 * 
	 * @param fullProperty : Property completa sobre la que se realizará la búsqueda.
	 * @param position : Posición de la property, comienza desde 0(cero)-
	 * @return String
	 */
	public static String getCompletePropertyAt(String fullProperty, int position) {
		if(position == 0)
			return getPropertyAt(fullProperty, position);
		else
			return getPreviousPropertyAt(fullProperty, position) + "." + getPropertyAt(fullProperty, position);
	}
	
	/**
	 * Retorna la cantidad de properties que tiene una <code>fullProperty</code><br>
	 * Ejemplo:
	 * <li>"a.b.c.d.e" -> 5
	 * 
	 * @param fullProperty: Property completa sobre la que se realizará la búsqueda.
	 * @return int
	 */
	public static int countProperties(String fullProperty) {
		return StringUtils.countMatches(fullProperty, ".") + 1;
	}
	
	/**
	 * Se encarga de instanciar sobre el bean <code>bean</code> la property de posición 
	 * <code>position</code> en caso de que ésta sea null.<br>
	 * Ejemplo:
	 * <li> fullProperty = "a.b.c.d", position = 2 -> Instanciará la property c en caso
	 * que ésta sea null.
	 * 
	 * @param bean : Bean sobre el que se instanciará la property.
	 * @param fullProperty: Property completa sobre la que se realizará la búsqueda. 
	 * @param position: Posición de la property, comienza desde 0(cero)-
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 */
	public static void instancePropertyIfNull(Object bean, String fullProperty, int position) throws SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		String completeProperty = getCompletePropertyAt(fullProperty, position);

		if(PropertyUtils.getProperty(bean, completeProperty) == null) {
			Class previousPropertyType = PropertyUtils.getProperty(bean, getPreviousPropertyAt(fullProperty, position)).getClass(); 
			Class propertyType = previousPropertyType.getMethod("get" + StringUtils.capitalize(getPropertyAt(fullProperty, position)), null).getReturnType();
		
			PropertyUtils.setProperty(bean, completeProperty, propertyType.newInstance());
		}
	}	

}