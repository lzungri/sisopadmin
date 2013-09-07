package ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.usuarios.estadosGrupo.EstadoGrupo;
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
		return TIPO_INTEGRANTE_COORDINADOR;
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
	
	public List<Usuario> getPosiblesReceptoresGruposDeMensaje(){	
		return SisopAdminServiceProvider.getPersistenceService().createCriteria(Grupo.class)
				.add( Restrictions.eq("estado", EstadoGrupo.INSCRIPTO) )
				.list();
	}
	
	public List<Usuario> getPosiblesReceptoresCoordinadoresDeMensaje(){	
		return SisopAdminServiceProvider.getPersistenceService().createCriteria(Coordinador.class)
				.add( Restrictions.not( Restrictions.eq("loginName", this.loginName)) )
				.list();
				
	}

}
	
	
