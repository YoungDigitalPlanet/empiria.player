package eu.ydp.empiria.player.client.controller.variables.objects.outcome;

import java.util.Vector;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class Outcome extends Variable {

	public Outcome(){
		super();
	}

	public Outcome(String identifier, Cardinality cardinality, BaseType baseType){
		super();
		this.identifier = identifier;
		this.cardinality = cardinality;
		this.baseType = baseType;
		interpretation = "";		
		normalMaximum = 0.0d;
	}
	
	public Outcome(String identifier, Cardinality cardinality, BaseType baseType, String value0){
		super();
		this.identifier = identifier;
		this.cardinality = cardinality;
		this.baseType = baseType;
		values.add(value0);
		interpretation = "";		
		normalMaximum = 0.0d;
	}
	
	public Outcome(Node responseDeclarationNode){
		
		values = new Vector<String>();

		identifier = ((Element)responseDeclarationNode).getAttribute("identifier");

		cardinality = Cardinality.fromString( ((Element)responseDeclarationNode).getAttribute("cardinality") );
		
		baseType = BaseType.fromString( ((Element)responseDeclarationNode).getAttribute("baseType") );
		
		interpretation = "";
		
		normalMaximum = 0.0d;
		
		NodeList defaultValueNodes = ((Element)responseDeclarationNode).getElementsByTagName("defaultValue");
		if (defaultValueNodes.getLength() > 0){
			NodeList valueNodes = defaultValueNodes.item(0).getChildNodes();
			String value;
			for (int i = 0 ; i < valueNodes.getLength() ; i ++){
				if (valueNodes.item(i) instanceof Element  &&  "value".equals( ((Element)valueNodes.item(i)).getNodeName() )   &&  valueNodes.item(i).hasChildNodes()){
					value = valueNodes.item(i).getFirstChild().getNodeValue();
					if (value != null){
						values.add(value);
					}
				}
				
			}
		}
	}
	
	public String interpretation;
	
	public Double normalMaximum;

	
	@Override
	public void reset() {
		
		
	}

	@Override
	public JSONValue toJSON() {
		JSONArray jsonArr = new JSONArray();
		jsonArr.set(0, new JSONString("Outcome"));
		jsonArr.set(1, new JSONString(identifier));
		jsonArr.set(2, new JSONString(cardinality.toString()));
		jsonArr.set(3, new JSONString(baseType.toString()));

		JSONArray valuesArr = new JSONArray();
		for (int v = 0 ; v < values.size() ; v ++){
			valuesArr.set(v, new JSONString(values.get(v)));
		}
		jsonArr.set(4, valuesArr);
		jsonArr.set(5, new JSONString(interpretation));
		jsonArr.set(6, new JSONNumber(normalMaximum));
		
		return jsonArr;
	}

	@Override
	public void fromJSON(JSONValue value) {
		JSONArray jsonArr = value.isArray();
		
		if (jsonArr != null){
			identifier = jsonArr.get(1).isString().stringValue();
			cardinality = Cardinality.valueOf( jsonArr.get(2).isString().stringValue() );
			baseType = BaseType.valueOf( jsonArr.get(3).isString().stringValue() );
			
			JSONArray jsonValues = jsonArr.get(4).isArray();
			values.clear();
			if (jsonValues != null){
				for (int i = 0 ; i < jsonValues.size() ; i ++){
					values.add(jsonValues.get(i).isString().stringValue());
				}
			}
			
			interpretation = jsonArr.get(5).isString().stringValue();
			normalMaximum = Double.valueOf( jsonArr.get(6).isNumber().doubleValue() );
		}
	} 
}
