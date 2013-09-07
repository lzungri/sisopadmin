package ar.com.grupo306.sisopadmin.domain.modelo.impl.trabajos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * 
 * @author Rafa
 *
 */

public class Tp extends ModelObject{	
	String nombre;
	Set<Fase> fases;
	String archivoEspecificacion;

	
	public static Tp findMeByNombre(String name){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Tp.class);
		criteria.add(Expression.eq("nombre",name));
		return (Tp)criteria.uniqueResult();		
	}
	
	public static List findUs(String nombre){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Tp.class);
		criteria.add(Expression.like(NOMBRE,"%" + nombre + "%"));
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);				
	}
	
	public static List findByNombre(String nombre){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Tp.class);
		criteria.add(Expression.like("nombre","%" + nombre + "%"));	
		return SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria);	
	}	
	
	public static List findByCantidadFases(Integer cantFases){		
		List result = new Tp().findAll(Tp.class);	
		
		List<Tp> tps = CollectionFactory.createList(Tp.class);
		
		for(Iterator it = result.iterator(); it.hasNext();){
			Tp tp = (Tp)it.next();
			if( tp.getCantidadFases() == cantFases){
				tps.add(tp);
			}
		}
		return tps;		
	}
	
	//GETTERS AND SETTERS
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public Set<Fase> getFases() {
		if(fases == null)
			fases = CollectionFactory.createSet(Fase.class);
		return fases;
	}

	public void setFases(Set<Fase> fases) {
		this.fases = fases;
	}

	public int getCantidadFases(){
		return this.getFases().size();
	}

	public String getArchivoEspecificacion() {
		return archivoEspecificacion;
	}

	public void setArchivoEspecificacion(String archivoEspecificacion) {
		this.archivoEspecificacion = archivoEspecificacion;
	}
}
