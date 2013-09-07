package ar.com.grupo306.sisopadmin.persistence.impl.hibernate.utils;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 * Utilidades varias de Hibernate.
 *
 * @author Leandro
 */
public class HibernateUtils {

	/**
	 * Agrega el criterion si el valor de propertyValue es distinto de null y no vacío.
	 * 
	 * @param criteria
	 * @param criterion
	 * @param propertyValue
	 */
	public static void addCriterionIfPropertyNotNullOrEmpty(Criteria criteria, Criterion criterion, Object propertyValue) {
		if(propertyValue != null) {
			if(propertyValue instanceof String && !propertyValue.toString().trim().equals("")) {
				criteria.add(criterion);
			}
			else if(!(propertyValue instanceof String)) {
				criteria.add(criterion);
			}
		}
	}
	
}