package ar.com.grupo306.commons.builder;

import java.util.HashMap;
import java.util.Map;

/**
 * Facilita la creación de mapas.
 *
 * @author Leandro
 */
public class MapBuilder<K, V> {
	private Map<K,V> map;
	
	public MapBuilder() {
		this.map = new HashMap<K,V>();;
	}
	
	public MapBuilder<K,V> put(K key, V value) {
		this.map.put(key, value);
		return this;
	}
	
	public Map<K,V> build() {
		return this.map;
	}

}