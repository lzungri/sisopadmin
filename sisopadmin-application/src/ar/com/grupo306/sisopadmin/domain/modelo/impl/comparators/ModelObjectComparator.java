package ar.com.grupo306.sisopadmin.domain.modelo.impl.comparators;

import java.util.Comparator;

import ar.com.grupo306.sisopadmin.domain.modelo.impl.ModelObject;

public class ModelObjectComparator implements Comparator{

	public int compare(Object o1, Object o2) {
		ModelObject object1 = (ModelObject)o1;
		ModelObject object2 = (ModelObject)o2;
		try{
		
			return object1.getClass().equals(object2.getClass()) && object1.getId().equals(object2.getId())==true?0:1;
		}catch(Exception e){
			return object1.getClass().equals(object2.getClass()) && object1.getName().equals(object2.getName()) == true
			?0:1;
		}
	}

}
