package eu.ydp.empiria.player.client.controller.feedback;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.ydp.gwtutil.client.StringUtils;

public class FeedbackProperties{
	
	public static final String EMPTY = "empty";
	
	private final Map<FeedbackPropertyName, Object> propertyMap = Maps.newHashMap();
	
	public void addBooleanProperty(FeedbackPropertyName name, Boolean value){
		addProperty(name, value);
	}
	
	public void addStringProperty(FeedbackPropertyName name, String value){
		addProperty(name, value);
	}
	
	public void addIntegerProperty(FeedbackPropertyName name, Integer value) {
		addProperty(name, value);
	}
	
	public void addDoubleProperty(FeedbackPropertyName name, Double value){
		addProperty(name, value);
	}
	
	private void addProperty(FeedbackPropertyName name, Object value){
		propertyMap.put(name, value);
	}
	
	public Object getProperty(String value){
		FeedbackPropertyName propertyName = FeedbackPropertyName.getPropertyName(value);
		Object property = EMPTY;
		
		if(FeedbackPropertyName.exists(propertyName) && getProperty(propertyName) != null){
			property = getProperty(propertyName);
		}
		
		return property;
	}
	
	public Object getProperty(FeedbackPropertyName name){
		return propertyMap.get(name);
	}
	
	public Boolean getBooleanProperty(FeedbackPropertyName name){
		Object value = propertyMap.get(name);
		Boolean output = false;
		
		if(value instanceof Boolean){
			output = (Boolean) value;
		}
		
		return output;
	}
	
	public Integer getIntegerProperty(FeedbackPropertyName name) {
		Object value = propertyMap.get(name);
		Integer output = Integer.MAX_VALUE;
		
		if(value instanceof Integer){
			output = (Integer) value;
		}
		
		return output;
	}
	
	public Double getDoubleProperty(FeedbackPropertyName name) {
		Object value = propertyMap.get(name);
		Double output = Double.MAX_VALUE;
		
		if(value instanceof Double){
			output = (Double) value;
		}
		
		return output;
	}
	
	public String getStringProperty(FeedbackPropertyName name){
		Object value = propertyMap.get(name);
		String output = StringUtils.EMPTY_STRING;
		
		if(value instanceof String){
			output = (String) value;
		}
		
		return output;
	}
	
	public void appendProperties(FeedbackProperties properties){
		propertyMap.putAll(properties.propertyMap);		
	}

	public Boolean hasValue(String name) {
		return !EMPTY.equals(getProperty(name));
	}

	public void merge(FeedbackProperties propertiesToMerge) {
		for(FeedbackPropertyName name: propertiesToMerge.propertyMap.keySet()){
			Object currentValue = getProperty(name);
			Object value = propertiesToMerge.propertyMap.get(name);
			
			if(currentValue == null){
				currentValue = getDefaultValue(value);
			}
			
			addProperty(name, mergeValue(currentValue, value));
		}
	}	
	
	private Object mergeValue(Object value1, Object value2){
		Object result = null;
		
		if(value1 instanceof Boolean && value2 instanceof Boolean){
			result = (Boolean)value1 && (Boolean)value2;
		}else if(value1 instanceof Integer && value2 instanceof Integer){
			result = (Integer)value1 + (Integer)value2;
		}else if(value2 instanceof String){
			result = value2;
		}else if(value1 instanceof Double && value2 instanceof Double){
			result = (Double)value1 + (Double)value2;
		}
		
		return result;
	}
	
	private Object getDefaultValue(Object value){
		Object defValue = null;
		
		if(value instanceof Boolean){
			defValue = true;
		}else if(value instanceof Integer){
			defValue = 0;
		}else if(value instanceof Double){
			defValue = 0.0;
		}
		
		return defValue;
	}
	
}
