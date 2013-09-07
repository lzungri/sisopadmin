package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * 
 * @author Rafa
 *
 */
public class Coordinador extends Ayudante{
	Set ayudantes;

	public Set getAyudantes() {
		return ayudantes;
	}

	public void setAyudantes(Set ayudantes) {
		this.ayudantes = ayudantes;		
	}
	
	public String getTipoIntegrante(){
		return "Coordinador";
	}
	
	/**
	 * Devuelve al Coordinador con el ID correspondiente, o null en caso de no encontralo	 
	 *
	 */
	public static Coordinador findMeById(Long id){
		Criteria criteria;
		criteria = SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class);
		criteria.add(Expression.eq(ID,id));
		return (Coordinador) criteria.uniqueResult();
	}	
}
	
	
