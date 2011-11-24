package eu.ydp.empiria.player.client.controller.variables.objects;

import java.util.Vector;

import com.google.gwt.json.client.JSONValue;

public abstract class Variable {
	
	public Variable(){
		values = new Vector<String>();
		identifier = "";
		cardinality = Cardinality.SINGLE;
		baseType = BaseType.STRING;
	}
	
	public String identifier;

	public Cardinality cardinality;
	
	public BaseType baseType;
	
	public Vector<String> values;
		
	public abstract void reset();
	
	public String getValuesShort(){
		
		String output = "";
		
		for (int i = 0 ; i < values.size() ; i ++ ){
			output += values.get(i);
			if (i < values.size()-1)
				output += ";";
		}
		
		return output;
	}
	
	public boolean compareValues(String[] testValues){
		Vector<String> vec = new Vector<String>();
		for (int i = 0 ; i < testValues.length ; i ++)
			vec.add(testValues[i]);
		return compareValues(vec);
		
	}
	public boolean compareValues(Vector<String> testValues){
		
		if (values.size() != testValues.size())
			return false;
		
		boolean contains;
		
		for (String ref : values){
			contains = false;
			for (String test : testValues){
				if (ref.compareTo(test) == 0){
					contains = true;
					break;
				}
			}	
			if (!contains)
				return false;
		}
		return true;
	}

	public abstract JSONValue toJSON();
	public abstract void fromJSON(JSONValue value);
}
