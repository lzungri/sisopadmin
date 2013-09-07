package ar.com.grupo306.sisopadmin.persistence.impl.dummy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import ar.com.grupo306.commons.filters.Filter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.persistence.interfaces.PersistenceService;
import ar.com.grupo306.sisopadmin.persistence.interfaces.Persistent;

/**
 * Servicio dummy de persistencia.
 *
 * @author Leandro
 */
public class DummyPersistenceServiceImpl implements PersistenceService {

	public void save(Persistent persistent) {
	}

	public void refresh(Persistent persistent) {
	
	}
	
	public void update(Persistent persistent) {
	}

	public void saveOrUpdate(Persistent persistent) {
	}

	public void remove(Persistent persistent) {
		
		
	}

	public List findAll(Class<? extends Persistent> type) {
		
		return null;
	}

	public List findByFiltro(Class<? extends Persistent> type, HashMap filter) {
		
		return null;
	}

	public <T extends Persistent> T findInstance(Class<T> type, Filter filter) {
		
		return null;
	}

	public <T extends ModelObject> T findInstanceById(Class<T> type, Serializable id) {
		
		return null;
	}

	public <T extends CodedModelObject> T findByDomainCode(Class<T> codedModelType, String domainCode) {
		
		return null;
	}

	public Query createQuery(String queryString) {
		
		return null;
	}

	public SQLQuery createSQLQuery(String queryString) {
		
		return null;
	}

	public List findByCriteria(Criteria criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	public Criteria createCriteria(Class<? extends Persistent> persistentType) {
		
		return null;
	}

}