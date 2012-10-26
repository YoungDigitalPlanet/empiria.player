package eu.ydp.empiria.player.client.controller.variables.objects;

import java.util.Vector;

import com.google.gwt.json.client.JSONValue;

import eu.ydp.gwtutil.client.regex.RegexMatcher;

public abstract class Variable {

	protected RegexMatcher regexMatcher = new RegexMatcher();

	public Variable() {
		values = new Vector<String>();
		identifier = "";
		cardinality = Cardinality.SINGLE;
		baseType = BaseType.STRING;
	}

	public String identifier;

	public Cardinality cardinality;

	public BaseType baseType;

	public Vector<String> values;

	public void reset() {
		values.clear();
	}

	public String getValuesShort() {

		StringBuilder output = new StringBuilder("");

		for (int i = 0; i < values.size(); i++) {
			output.append(escapeCSV(values.get(i)));
			if (i < values.size() - 1) {
				output.append(";");
			}
		}
		return output.toString();
	}

	protected String escapeCSV(String value) {
		if (value.contains("'") || value.contains(";")) {
			value = value.replace("'", "''");
			value = "'" + value + "'";
		}
		return value;
	}

	public boolean compareValues(String[] testValues) {
		Vector<String> vec = new Vector<String>();
		for (int i = 0; i < testValues.length; i++) {
			vec.add(testValues[i]);
		}
		return compareValues(vec);
	}

	public boolean compareValues(Vector<String> testValues){
		
		if (values.size() != testValues.size()) {
			return false;
		}
		
		boolean contains;
		
		for (String ref : values){
			contains = false;
			for (String test : testValues){
				if (ref.compareTo(test) == 0){
					contains = true;
					break;
				}
			}
			if (!contains) {
				return false;
			}
		}
		return true;
	}

	public boolean matchFirstValue(String[] testValues) {
		String value = this.values.firstElement();
		boolean result = false;
		for (String testValue : testValues) {
			if (regexMatcher.matches(value,"^(" + testValue + ")$")) {
				result = true;
				break;
			}
		}
		return result;
	}

	public abstract JSONValue toJSON();

	public abstract void fromJSON(JSONValue value);
}
