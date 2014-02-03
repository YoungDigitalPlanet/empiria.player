package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * This class converts test result into JS object
 * 
 * @author klangner
 * 
 */
public class JavaScriptResult {

	/** test max score */
	private int maxScore;
	/** test score */
	private int score;

	/**
	 * Constructor
	 */
	public JavaScriptResult(int score, int max) {

		this.score = score;
		this.maxScore = max;
	}

	/**
	 * @return JS object
	 */
	public JavaScriptObject getJSObject() {
		JavaScriptObject obj = JavaScriptObject.createFunction();

		initObject(obj, score, maxScore);

		return obj;
	}

	/**
	 * Initilize object
	 */
	private native static void initObject(JavaScriptObject obj, int score, int maxScore) /*-{
																							
																							obj.getScore = function(){
																							return score;
																							}
																							
																							obj.getMaxScore = function(){
																							return maxScore;
																							}
																							
																							
																							}-*/;

}
