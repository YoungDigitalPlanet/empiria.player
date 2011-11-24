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
package eu.ydp.empiria.player.client.util;

import java.util.Vector;

import com.google.gwt.user.client.Random;

public class RandomizedSet<T> {

	private Vector<T>	elements = new Vector<T>();
	
	/**
	 * Add element do the set
	 * @param item
	 */
	public void push(T item){
		elements.add(item);
	}
	
	/**
	 * check if there are more elements
	 * @return
	 */
	public boolean hasMore(){
		return (elements.size() > 0);
	}
	
	/**
	 * return element from the set. element will be removed
	 */
	public T pull(){
		if(elements.size() == 0)
			return null;
		
		int index = Random.nextInt(elements.size());
		T item = elements.get(index);
		elements.remove(index);

		return item;
	}
	
}
