package eu.ydp.empiria.player.client.controller.feedback;

import java.util.Map;

import com.google.common.collect.Maps;

import eu.ydp.gwtutil.client.StringUtils;

public class FeedbackProperties{
	
	Map<FeedbackPropertyName, Object> propertyMap = Maps.newHashMap();
	
	public void addBooleanProperty(FeedbackPropertyName name, Boolean value){
		addProperty(name, value);
	}
	
	private void addProperty(FeedbackPropertyName name, Object value){
		propertyMap.put(name, value);
	}
	
	public void addStringProperty(FeedbackPropertyName name, String value){
		addProperty(name, value);
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
	
	
}
