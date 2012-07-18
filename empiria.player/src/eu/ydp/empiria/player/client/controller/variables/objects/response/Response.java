/*
  The MIT License
  
  Copyright (c) 2009 Krzysztof Langner
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/
package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.json.client.JSONValue;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;


public class Response extends Variable {

	/** List of correct responses */
	public Vector<String> 	correctAnswers;
	public Map<String, List<Integer>> groups;
	/** Determines whether the module corresponding to the response variable exists in the document*/
	private boolean isModuleAdded = false;
	private boolean initialized = false;
	
	public Mapping mapping;
	
	/**
	 * constructor
	 * @param item associated with this processing
	 */
	public Response(Node responseDeclarationNode){
    
		NodeList nodes = ((Element)responseDeclarationNode).getElementsByTagName("value");

		correctAnswers = new Vector<String>();
		
		values = new Vector<String>();
		
		groups = new HashMap<String, List<Integer>>();
		Vector<String> groupsNames = new Vector<String>();

		identifier = ((Element)responseDeclarationNode).getAttribute("identifier");

		cardinality = Cardinality.fromString( ((Element)responseDeclarationNode).getAttribute("cardinality") );
		
		baseType = BaseType.fromString( ((Element)responseDeclarationNode).getAttribute("baseType") );
		
		for(int i = 0; i < nodes.getLength(); i++){
			String nodeValue;
			if (nodes.item(i).hasChildNodes())
				nodeValue = nodes.item(i).getFirstChild().getNodeValue();
			else
				nodeValue = "";
			correctAnswers.add( nodeValue );
			Element valueElement = ((Element)nodes.item(i));
			if (valueElement.hasAttribute("group")  &&  (!valueElement.hasAttribute("groupMode")  ||  (valueElement.getAttribute("groupMode").equals("groupItem"))) ){
				String currGroupName = ((Element)nodes.item(i)).getAttribute("group"); 
				if (!groupsNames.contains(currGroupName)){
					groupsNames.add(currGroupName );
					groups.put(currGroupName, new ArrayList<Integer>());
				}
				groups.get(currGroupName).add(i);
			}
		}
		
		mapping = new Mapping(((Element)responseDeclarationNode).getElementsByTagName("mapping").item(0));
	}
	
	/**
	 * @return id
	 */
	public String getID(){
		return identifier;
	}
	
	/**
	 * @see IResponse#isCorrect(String)
	 */
	public boolean isCorrectAnswer(String key) {

		return correctAnswers.contains(key);
	}
	
	public String getCorrectAnswersValuesShort(){
		
		String output = "";
		
		for (int i = 0 ; i < correctAnswers.size() ; i ++ ){
			output += correctAnswers.get(i);
			if (i < correctAnswers.size()-1)
				output += ";";
		}
		
		return output;
	}

	/**
	 * implementation of IResponse interface
	 * @param key
	 */
	public void add(String key) {
		for (String currValue:values)
			if (currValue.equals(key))
				return;
		
		if (cardinality == Cardinality.SINGLE)
			values.clear();
		
		values.add(key);
		
		initialized = true;
	}

	/**
	 * implementation of IResponse interface
	 * @param key
	 */
	public void remove(String key) {
		
		for (int i = 0 ; i < values.size() ; i ++){
			if (values.get(i).equals(key)){
				values.remove(i);
				return;
			}
		}
				
	}
	
	public void set(Vector<String> keys){
		values = keys;
		initialized = true;
	}
	
	public boolean compare(Vector<String> test){
		if (values.size() != test.size())
			return false;
		
		for (int i = 0 ; i < values.size() ; i ++){
			if (values.get(i).compareTo(test.get(i)) != 0)
				return false;
		}
		
		return true;
			
	}
	
	public boolean isInitialized(){
		return initialized;
	}

	/**
	 * Reset results
	 */
	public void reset() {
		values.clear();
	}
	
	public String toString(){
		
		return "Id: " + identifier + "\n" + correctAnswers; 
	}
	
	public void setModuleAdded(){
		isModuleAdded = true;
	}
	
	public boolean isModuleAdded(){
		return isModuleAdded;
	}
	
	@Override
	public JSONValue toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromJSON(JSONValue value) {
		// TODO Auto-generated method stub
		
	}


}
