package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class Consigna extends ModelObject{
	String descripcion;
	Fase fase;
	Integer numero;

	public Fase getFase() {
		return fase;
	}

	public void setFase(Fase fase) {
		this.fase = fase;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public static Consigna findMeById(Long id){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Consigna.class);
		criteria.add(Expression.eq(ID,id));
		
		return (Consigna) criteria.uniqueResult();	
	}	
	
	public static Consigna findMeByNumero(Integer numero, Fase fase){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Consigna.class);
		
		criteria.add(Expression.eq("numero",numero));
		criteria.add(Expression.eq("fase" , fase));
		return (Consigna) criteria.uniqueResult();	
	}	
}
