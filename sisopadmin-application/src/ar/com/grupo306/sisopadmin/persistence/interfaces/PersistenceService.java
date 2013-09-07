package ar.com.grupo306.sisopadmin.persistence.interfaces;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import ar.com.grupo306.commons.filters.Filter;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.CodedModelObject;
import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;
import ar.com.grupo306.sisopadmin.service.ApplicationService;

/**
 * Servicio de persistencia.
 *
 * @author Leandro
 */
public interface PersistenceService extends ApplicationService {

	/**
	 * Persiste un Persistent sobre la base.
	 */
	public void save(Persistent persistent);
	
	/**
	 * Actualiza el estado de un Persistent sobre la base.
	 */
	public void update(Persistent persistent);
	
	/**
	 * Realiza un save si el persistent no tiene un id que se encuentre en la base.
	 * Realiza un update si el persistent tiene un id que figura en la base.
	 * 
	 * @param persistent
	 */
	public void saveOrUpdate(Persistent persistent);
	
	/**
	 * Elimina un Persistent de la base.
	 */
	public void remove(Persistent persistent);
	

	
	/******************************************************************************
	 *	SERVICIOS DE B�SQUEDA. 
	 ******************************************************************************/
	
	/**
	 * Encuentra todas las instancias de una clase Persistent
	 */
	public List findAll(Class<? extends Persistent> type);
	
	/**
	 * Halla las instancias que cumplen con el filtro de b�squeda.
	 * El filtro es un HashMap que contiene el nombre de la variable y el valor 
	 * 
	 * @param type: Instancias de Class a buscar.
	 * @param filter: Flitro de b�squeda.
	 * @return Collection.
	 */
	public List findByFiltro(Class<? extends Persistent> type, HashMap filter);
	
	/**
	 * Encuentra la UNICA instancia que cumple con el filtro
	 * 
	 * @param type: Instancia de Class a buscar.
	 * @param filter: Flitro de b�squeda.
	 * @return Object
	 */
	public <T extends Persistent> T findInstance(Class<T> type, Filter filter);
	
	/**
	 * Encuentra la UNICA instancia que posee el id pasado por par�metro.
	 *  
	 * @param type: Instancia de Class a buscar.
	 * @param id: Id a filtrar..
	 * @return Object
	 */
	public <T extends ModelObject> T findInstanceById(Class<T> type, Serializable id);
	
	/**
	 * Encuentra la UNICA instancia que posee el domainCode pasado por par�metro.
	 *  
	 * @param type: Instancia de Class a buscar.
	 * @param domainCode: C�digo a filtrar.
	 * @return Object
	 */
	public <T extends CodedModelObject> T findByDomainCode(Class<T> codedModelType, String domainCode);
	
	/**
	 * Crea una Query que luego podr� ser ejecutada.
	 * 
	 * @TODO Si fuese una aplicaci�n "real" se deber�a retornar una interfaz
	 * de la aplicaci�n y no una que este ligada a Hibernate. Por cuestiones
	 * de tiempo y de que no se va a modificar la implementaci�n...
	 * 
	 * @param queryString
	 * @return Query
	 */
	public Query createQuery(String queryString);

	/**
	 * Crea una Query que luego podr� ser ejecutada.
	 * 
	 * @TODO Si fuese una aplicaci�n "real" se deber�a retornar una interfaz
	 * de la aplicaci�n y no una que este ligada a Hibernate. Por cuestiones
	 * de tiempo y de que no se va a modificar la implementaci�n...
	 * 
	 * @param queryString
	 * @return Query
	 */
	public SQLQuery createSQLQuery(String queryString);
	
	/**
	 * Se obtiene una lista por medio de un criterio.
	 * @param type
	 * @param criteria
	 * @return
	 */
	public List findByCriteria(Criteria criteria);	
	
	/**
	 * Crea un criterio de b�squeda. Sobre dicho Criteria se podr�n aplicar diferentes
	 * condiciones o filtros de b�squeda.
	 * 
	 * @param persistentType
	 */
	public Criteria createCriteria(Class<? extends Persistent> persistentType);
	
}