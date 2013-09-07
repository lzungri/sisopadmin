package ar.com.grupo306.sisopadmin.persistence.interfaces;

/**
 * Interface que define a los objetos persistentes.
 * 
 * @author Rafa
 */
public interface Persistent {
	
	public void save();
	
	public void saveOrUpdate();
	
	public void update();
	
	public void remove();
	
}