package ar.com.grupo306.sisopadmin.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import ar.com.grupo306.commons.factorys.CollectionFactory;
import ar.com.grupo306.commons.factorys.ExceptionFactory;

/**
 * Punto de acceso a la configuración de la aplicación SisopAdmin.
 *
 * @author Leandro
 */
public class SisopAdminConfig {
	private static SisopAdminConfig instance;

	/** Mapa que asocia Módulos con properties. */
	private Map<Module, Properties> properties = CollectionFactory.createMap(Module.class, Properties.class);
	
	
	private SisopAdminConfig() {
		super();
	}
	
	public static synchronized SisopAdminConfig getInstance() {
		if(instance == null) {
			instance = new SisopAdminConfig();
		}
		return instance;
	}
	
	/**
	 * Obtiene, para un Module determinado, el valor de la propertyKey obtenida
	 * del archivo de configuración "sisopadmin_[module].properties".
	 * 
	 * @param module
	 * @param propertyKey
	 */
	public String getProperty(Module module, String propertyKey) {
		this.loadPropertiesIfNecessary(module);
		
		return this.properties.get(module).getProperty(propertyKey);
	}
	
	/**
	 * Carga las properties en caso de no habérselas cargado previamente.
	 * 
	 * @param module
	 */
	private void loadPropertiesIfNecessary(Module module) {
		try {
			if(this.properties.get(module) == null) {
				InputStream inputStream =
					Thread.currentThread().getContextClassLoader().getResourceAsStream("sisopadmin_" + module + ".properties");
				
				Properties moduleProperties = new Properties();
				moduleProperties.load(inputStream);
				inputStream.close();
				
				this.properties.put(module, moduleProperties);
			}
		}
		catch(FileNotFoundException excep) {
			throw ExceptionFactory.createProgramException("No existe el archivo sisopadmin_" + module + ".properties");
		}
		catch(IOException excep) {
			throw ExceptionFactory.createProgramException("Error al cargar las properties del archivo sisopadmin_" + module + ".properties");
		}
	}
	
	/**
	 * Recarga las properties.
	 * EN MODO LAZY.
	 */
	public void reloadProperties() {
		this.properties.clear();
	}
	
}