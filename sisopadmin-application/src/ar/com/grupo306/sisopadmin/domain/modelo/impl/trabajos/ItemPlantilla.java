package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.utils.HibernateUtils;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

public class ItemPlantilla extends ModelObject {
	private Consigna consigna;
	private Integer peso;
	private String procedimiento;
	private String resultadoEsperado;
	private String observacionBajaCalificacion;
	private Boolean obligatorio = Boolean.FALSE;
	private String nombre;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	public Consigna getConsigna() {
		return consigna;
	}
	public void setConsigna(Consigna consigna) {
		this.consigna = consigna;
	}
	public Boolean getObligatorio() {
		return obligatorio;
	}	
	public void setObligatorio(Boolean obligatorio) {
		this.obligatorio = obligatorio;
	}	
	public String getObservacionBajaCalificacion() {
		return observacionBajaCalificacion;
	}	
	public void setObservacionBajaCalificacion(String observacionBajaCalificacion) {
		this.observacionBajaCalificacion = observacionBajaCalificacion;
	}
	public Integer getPeso() {
		return peso;
	}	
	public void setPeso(Integer peso) {
		this.peso = peso;
	}	
	public String getProcedimiento() {
		return procedimiento;
	}	
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getResultadoEsperado() {
		return resultadoEsperado;
	}	
	public void setResultadoEsperado(String resultadoEsperado) {
		this.resultadoEsperado = resultadoEsperado;
	}	
	
	public static List<ItemPlantilla> findByConsigna(Long idConsigna) {
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(ItemPlantilla.class);
		HibernateUtils.addCriterionIfPropertyNotNullOrEmpty(criteria, Restrictions.eq("consigna.id", idConsigna), idConsigna);
		
		return (List<ItemPlantilla>) criteria.list();
	}
}