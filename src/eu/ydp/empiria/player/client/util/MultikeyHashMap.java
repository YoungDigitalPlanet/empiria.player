package eu.ydp.empiria.player.client.util;

import java.util.HashMap;
import java.util.Vector;

public class MultikeyHashMap<K extends Vector<SK>, V, SK> extends HashMap<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3418997339352893112L;
	
	public V getBySubkey(SK subkey){
		for (K currKey : keySet()){
			if (currKey.contains(subkey))
				return get(currKey);
		}
		
		return null;
	}

}
