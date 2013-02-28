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

import java.util.List;

import com.google.gwt.json.client.JSONValue;

import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

public class Response extends Variable {

	/** List of correct responses */
	public CorrectAnswers correctAnswers;
	public List<String> groups;
	/**
	 * Determines whether the module corresponding to the response variable
	 * exists in the document
	 */
	private boolean initialized = false;
	public Evaluate evaluate;
	private CountMode countMode = CountMode.SINGLE;


	public Response(CorrectAnswers correctAnswers, List<String> values, List<String> groups, String identifier, Evaluate evaluate, BaseType baseType, Cardinality cardinality){
		this.correctAnswers = correctAnswers;
		this.values = values;
		this.groups = groups;
		this.identifier = identifier;
		this.evaluate = evaluate;
		this.baseType = baseType;
		this.cardinality = cardinality;
	}

	/**
	 * @return id
	 */
	public String getID() {
		return identifier;
	}

	/**
	 * @see IResponse#isCorrect(String)
	 */
	public boolean isCorrectAnswer(String key) {
		return correctAnswers.containsAnswer(key);
	}

	public String getCorrectAnswersValuesShort() {

		String output = "";

		for (int i = 0; i < correctAnswers.getResponseValuesCount(); i++) {
			for (String answer : correctAnswers.getResponseValue(i).getAnswers()) {
				output += answer + ";";
			}
		}
		output = output.substring(0, output.length() - 1);

		return output;
	}

	/**
	 * implementation of IResponse interface
	 *
	 * @param key
	 */
	public void add(String key) {
		if (cardinality == Cardinality.SINGLE) {
			values.clear();
		}

		values.add(key);
		initialized = true;
	}

	/**
	 * implementation of IResponse interface
	 *
	 * @param key
	 */
	public void remove(String key) {

		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).equals(key)) {
				values.remove(i);
				return;
			}
		}

	}

	public void set(List<String> keys) {
		values = keys;
		initialized = true;
	}

	public void setCountMode(CountMode mode) {
		this.countMode = mode;
	}

	public CountMode getCountMode() {
		return cardinality == Cardinality.SINGLE ? CountMode.SINGLE : countMode;
	}

	public boolean compare(List<String> test) {
		if (values.size() != test.size()) {
			return false;
		}

		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).compareTo(test.get(i)) != 0) {
				return false;
			}
		}
		return true;

	}

	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Reset results
	 */
	@Override
	public void reset() {
		values.clear();
	}

	@Override
	public String toString() {
		return "Id: " + identifier + "\n" + correctAnswers;
	}

	@Override
	public JSONValue toJSON() {
		return null;
	}

	@Override
	public void fromJSON(JSONValue value) {
	}

}
