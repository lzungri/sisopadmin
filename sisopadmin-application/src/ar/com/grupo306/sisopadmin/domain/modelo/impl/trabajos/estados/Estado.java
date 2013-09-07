package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos.estados;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Representa el estado de una entidad de negocio.
 * 
 * @author Rafa
 */
public class Estado extends CodedModelObject {
	
	private String descripcion;
	
	/** Model object propietario del estado. */
	private String className;
	
	public static Estado findMeByDomainCode(String domainCode){
		return SisopAdminServiceProvider.getPersistenceService().findByDomainCode(Estado.class, domainCode);		
	}
	
	/**
	 * Busca todos los estados propios de un ConEstado determinado.
	 * 
	 * @param conEstado
	 */
	public static List<Estado> findAllByClassNamePropietaria(Class<? extends ConEstadoModelObject> conEstado) {
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Estado.class);
		criteria.add(Expression.eq("className", conEstado.getName()));
		
		return (List<Estado>) criteria.list();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}