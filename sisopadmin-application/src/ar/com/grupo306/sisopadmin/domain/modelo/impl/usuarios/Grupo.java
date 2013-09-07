package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.componentesGrupo.Alumno;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * @author Rafa
 * Modificada por Sole (13-09) para quitar el atributo nombre, que pasa a ser del Usuario 
 */

public class Grupo extends Usuario{	
	Long estado;
	Set<Alumno> alumnos;
	
	
	public Set<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(Set<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}	
	
	public static Grupo findMeByName(String name){
		Criteria criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class);
		criteria.add(Expression.eq("nombre", name));
		try{
			return (Grupo)(SisopAdminServiceProvider.getPersistenceService().findByCriteria(criteria).iterator().next());
		}catch(Exception e){
			return null;
		}
	}
}
