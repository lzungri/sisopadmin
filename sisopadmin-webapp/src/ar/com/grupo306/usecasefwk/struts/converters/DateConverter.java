package ar.com.grupo306.usecasefwk.struts.converters;

import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

import ar.com.grupo306.commons.utils.DateUtils;
import ar.com.grupo306.sisopadmin.constants.SisopAdminConstants;


/**
 * Encargado de convertir un Object a un Date de Java. Es utilizado por el BeanUtils para realizar
 * conversiones de tipo.
 * 
 * La implementación de la clase está basada en SqlDateConverter.
 * @see org.apache.commons.beanutils.converters.SqlDateConverter
 * 
 * @author lzungri
 */
public class DateConverter implements Converter {
    private Object defaultValue = null;
    private boolean useDefault = true;

    public DateConverter() {
        this.defaultValue = null;
        this.useDefault = false;
    }

    public DateConverter(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.useDefault = true;
    }

    public Object convert(Class type, Object value) {
        if (value == null) {
            if (useDefault) {
                return (defaultValue);
            } else {
                throw new ConversionException("No value specified");
            }
        }

        if (value instanceof Date) {
            return (value);
        }

        try {
            // Formato de fechas utilizados en la Banca.
            return DateUtils.getDateFromString(value.toString(), SisopAdminConstants.DATE_FORMAT_PATTERN);
        } catch (Exception e) {
        	if (useDefault) {
        		return (defaultValue);
        	} else {
        		throw new ConversionException(e);
        	}
        }
    }
	
}