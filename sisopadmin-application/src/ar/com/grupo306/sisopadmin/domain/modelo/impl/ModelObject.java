package ar.com.grupo306.sisopadmin.domain.modelo.impl;


import java.util.List;

import ar.com.grupo306.sisopadmin.domain.interfaces.usuarios.ModelObjectIdConstants;
import ar.com.grupo306.sisopadmin.persistence.interfaces.Persistent;
import ar.com.grupo306.sisopadmin.service.provider.SisopAdminServiceProvider;

/**
 * Clase que permite manejar los objetos del negocio  en forma global
 * 
 * @author Rafa
 */
public class ModelObject implements Persistent,ModelObjectIdConstants{
	private Long id;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName(){
		return "ModelObject";
	}
	
	public boolean validarNulls(){
		return true;
	}
	
	
	/**
	 * Levanta todas las instancias de la clase que se envia como parámetro
	 * 
	 * @param clazz
	 * @return lista de todas la instancias de la clase
	 */
	//Debería ser estático. Evaluarlo para futuros refactors
	public List findAll(Class clazz) {
		return SisopAdminServiceProvider.getPersistenceService().findAll(clazz);
	}



	public void remove() {
		SisopAdminServiceProvider.getPersistenceService().remove(this);
	}
	
	public void refresh() {
		SisopAdminServiceProvider.getPersistenceService().refresh(this);
	}

	public void save() {
		SisopAdminServiceProvider.getPersistenceService().save(this);
	}

	public void saveOrUpdate() {
		SisopAdminServiceProvider.getPersistenceService().saveOrUpdate(this);
	}

	public void update() {
		SisopAdminServiceProvider.getPersistenceService().update(this);
	}
	
	public boolean equals(Object object) {
		if (object == this)
		    return true;
		
		if(object != null && object instanceof ModelObject) {
			return this.id.equals(((ModelObject) object).getId());
		}
		return false;
	}
	
	public int hashCode() {
		if(this.id != null) {
			return this.id.hashCode();
		}
		return super.hashCode();
	}
}