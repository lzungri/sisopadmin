package ar.com.grupo306.sisopadmin.persistence.impl.common.populator;

import java.util.Collection;
import java.util.Iterator;

import ar.com.grupo306.commons.exceptions.core.ProgramException;
import ar.com.grupo306.commons.factorys.CollectionFactory;

/**
 * Clase encargada de popular la base de datos mediante TablePopulators.
 * 
 * @author lzungri
 */
public class RepositoryPopulator {
	private Collection<TablePopulator> tablePopulators = CollectionFactory.createCollection(TablePopulator.class);
	
	public void run() {
		for(Iterator it = tablePopulators.iterator(); it.hasNext(); ) {
			TablePopulator tablePopulator = (TablePopulator) it.next();
			try {
				tablePopulator.doPopulate();
			}
			catch(Exception excep) {
				throw new ProgramException("Falla al ejecutar el TablePopulator: " + tablePopulator.getClass().getName(), excep);
			}
		}
	}
	
	public RepositoryPopulator addTP(TablePopulator tablePopulator) {
		this.tablePopulators.add(tablePopulator);
		
		return this;
	}
	
}