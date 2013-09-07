package ar.com.grupo306.commons.factorys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ar.com.grupo306.commons.builder.MapBuilder;

/**
 * Abstrae la creación de colecciones.
 * 
 * @author Leandro
 */
public class CollectionFactory {
	
	public static Collection createCollection() {
		return new ArrayList();
	}
	
	public static <T extends Object> Collection<T> createCollection(Class<T> type) {
		return new ArrayList<T>();
	}
	
	/**
	 * Crea una nueva Collection en base a otra
	 * 
	 * @param <T>
	 * @param collection
	 */
	public static <T extends Object> Collection<T> createCollection(Collection<T> collection) {
		return new ArrayList<T>(collection);
	}
	
	public static List createList() {
		return new ArrayList();
	}
	
	public static <T extends Object> List<T> createList(Class<T> type) {
		return new ArrayList<T>();
	}
	
	public static List createList(Object []array) {
		List list = createList();
		for(Object element : array) {
			list.add(element);
		}
		
		return list;
	}
	
	public static Map createMap() {
		return new HashMap();
	}
	
	public static <K, V extends Object> Map<K, V> createMap(Class<K> keyType, Class<V> valueType) {
		return new HashMap<K, V>();
	}
	
	public static <K, V extends Object> MapBuilder<K, V> createMapBuilder(Class<K> keyType, Class<V> valueType) {
		return new MapBuilder<K, V>();
	}
	
	public static Set createSet() {
		return new HashSet();
	}
	
	public static <T extends Object> Set<T> createSet(Class<T> type) {
		return new HashSet<T>();
	}
	
	/**
	 * Crea un nuevo Set en base a una Collection.
	 * 
	 * @param <T>
	 * @param collection
	 */
	public static <T extends Object> Set<T> createSet(Collection<T> collection) {
		return new HashSet<T>(collection);
	}
	
	public static <T extends Object> Set<T> createTreeSet(Class<T> type) {
		return new TreeSet<T>();
	}
	
	public static <T extends Object> Set<T> createTreeSet(Class<T> type, Comparator comparator) {
		return new TreeSet<T>(comparator);
	}
	
	public static TreeSet createTreeSet(Comparator comparator){
		return new TreeSet(comparator);
	}
	
}