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
		
	public void reset(){
		values.clear();		
	}
	
	public String getValuesShort(){
		
		String output = "";
		
		for (int i = 0 ; i < values.size() ; i ++ ){
			output += escapeCSV (values.get(i) );
			if (i < values.size()-1)
				output += ";";
		}
		
		return output;
	}
	
	protected String escapeCSV(String value){
		if (value.contains("'")  ||  value.contains(";")){
			value = value.replace("'", "''");
			value = "'" + value + "'";
		}
		return value;
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
	
	public boolean matchFirstValue(String[] testValues) {
		String value = this.values.firstElement();

		for (String testValue : testValues)
				if (value.matches(testValue))
					return true;
		
		return false;
	}
	
	public abstract JSONValue toJSON();
	public abstract void fromJSON(JSONValue value);
}
