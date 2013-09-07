package ar.com.grupo306.sisopadmin.persistence.impl.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.filters.Filter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.persistence.impl.hibernate.session.SessionManager;
import ar.com.grupo306.sisopadmin.persistence.interfaces.PersistenceService;
import ar.com.grupo306.sisopadmin.persistence.interfaces.Persistent;

/**
 * Implementación del servicio de persistencia acoplado a Hibernate.
 *
 * @author Leandro
 */
public class HibernatePersistenceServiceImpl implements PersistenceService {

	public void save(Persistent persistent) {
		this.getCurrentSession().save(persistent);
	}
	
	/**
	 * Rafa - metodo que salva o actualiza la base de acuerdo a si el objeto es creado o existe en la misma
	 */
	public void saveOrUpdate(Persistent persistent) {
		this.getCurrentSession().saveOrUpdate(persistent);
	}
	
	public void update(Persistent persistent) {
		this.getCurrentSession().update(persistent);
	}

	public void remove(Persistent persistent) {
		this.getCurrentSession().delete(persistent);
	}

	public List findByFiltro(Class<? extends Persistent> type, HashMap filter) {
		Criteria criteria = this.getCurrentSession().createCriteria(type);
		Iterator iEntries = filter.entrySet().iterator();
		while(iEntries.hasNext()){
			Entry entry = (Entry)iEntries.next();
			criteria.add(Expression.eq(entry.getKey().toString(),entry.getValue()));
			
		}
		return criteria.list();

	}
	/** Recibe un criterio ya creado y solo obtiene la lista a partir del mismo*/
	public List findByCriteria(Criteria criteria) {
		return criteria.list();
	}

	public <T extends Persistent> T findInstance(Class<T> type, Filter filter) {
		throw new ProgramException("Servicio no implementado.");
	}
	
	public Query createQuery(String queryString) {
		return this.getCurrentSession().createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return this.getCurrentSession().createSQLQuery(queryString);
	}
	
	private Session getCurrentSession() {
		return SessionManager.getCurrentSession();
	}

	public Criteria createCriteria(Class<? extends Persistent> persistentType) {
		return this.getCurrentSession().createCriteria(persistentType);
	}

	public List findAll(Class<? extends Persistent> type) {
		return this.getCurrentSession().createCriteria(type).list();
	}

	public <T extends ModelObject> T findInstanceById(Class<T> type, Serializable id) {
		return (T) this.getCurrentSession().get(type, id);
	}

	public <T extends CodedModelObject> T findByDomainCode(Class<T> codedModelType, String domainCode) {
		Criteria criteria = this.getCurrentSession().createCriteria(codedModelType);
		
		criteria.add(Expression.eq("domainCode", domainCode));
		return (T) criteria.uniqueResult();
	}

}