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
package eu.ydp.empiria.player.client.controller.communication;


public class Result {

	/** Min possible points in results */
	private float	min;
	/** Max possible points in results */
	private float	max;
	/** scored points */
	private float score;
	
	/**
	 * constructor
	 */
	public Result(){
		min = 0;
		max = 1;
		score = 0;
	}

	/**
	 * constructor
	 */
	public Result(float outcomeScore, float lowerBound, float upperBound){
		this();
		min = lowerBound;
		max = upperBound;
		score = outcomeScore;
		
	}
	
	/**
	 * constructor
	 */
	public Result(Float outcomeScore, Float lowerBound, Float upperBound){
		this();
		if (lowerBound != null)
			min = lowerBound.floatValue();
		
		if (upperBound != null)
			max = upperBound.floatValue();

		if (outcomeScore != null)
			score = outcomeScore.floatValue();
		
	}

	/**
	 * @return score
	 */
	public float getScore(){
		return score;
	}

	/**
	 * @return min points
	 */
	public float getMinPoints(){
		return min;
	}
	
	/**
	 * @return max points
	 */
	public float getMaxPoints(){
		return max;
	}
	
	/**
	 * Merge data from another result
	 * @param result
	 */
	public void merge(Result result){
		score += result.getScore();
		min += result.getMinPoints();
		max += result.getMaxPoints();
	}
	/*
	public JavaScriptObject toJsObject(){
		return createJsObject();
	}
	
	private native JavaScriptObject createJsObject()/*-{
		var obj = [];
		var instance = this;
		obj.getMinPoints = function(){
			return instance.@eu.ydp.empiria.player.client.controller.communication.Result::min;
		}
		obj.getMaxPoints = function(){
			return instance.@eu.ydp.empiria.player.client.controller.communication.Result::max;
		}
		obj.getScore = function(){
			return instance.@eu.ydp.empiria.player.client.controller.communication.Result::score;
		}
		return obj;
	}-*/;
}
