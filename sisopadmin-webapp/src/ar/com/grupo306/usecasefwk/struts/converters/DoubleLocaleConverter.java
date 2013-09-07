package ar.com.grupo306.usecasefwk.struts.converters;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DoubleConverter;

/**
 * Convierte un Object a Double.
 * La principal responsabilidad de esta clase que lo diferencia del DoubleConverter
 * es que considera la Locale es_Ar para la conversi�n.
 * 
 * <br>Ejemplo: 
 * <li> "1,1" lo convertir� a "1.1" y luego invocar� la misma l�gica que DoubleConverter.
 * <li> "1.1" ejecutar� la l�gica del DoubleConverter.
 * 
 * @see org.apache.commons.beanutils.converters.DoubleConverter
 *
 * @author lzungri
 */
public class DoubleLocaleConverter implements Converter {
    private Object defaultValue = null;
    private boolean useDefault = true;
	
	public DoubleLocaleConverter() {
        this.defaultValue = null;
        this.useDefault = false;
    }

	public DoubleLocaleConverter(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.useDefault = true;
    }

    public Object convert(Class type, Object value) {
    	// Aqu� se realiza la conversi�n.
    	Object finalValue = value != null ? value.toString().replaceAll(",", ".") : null;
    	
    	DoubleConverter converter = this.useDefault ? new DoubleConverter(this.defaultValue) : new DoubleConverter();  
    	
    	return converter.convert(type, finalValue);
    }
}