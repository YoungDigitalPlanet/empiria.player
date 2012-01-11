package eu.ydp.empiria.player.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StackMap<K, V> extends HashMap<K, V> {
	
	private static final long serialVersionUID = -5203287630904024114L;

	protected List<K> keys;
	protected List<V> values;

	public StackMap(){
		super();
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}

	@Override
	public V put(K key, V value){
		keys.add(key);
		values.add(value);
		return super.put(key, value);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m){
		keys.addAll(m.keySet());
		values.addAll(m.values());
		super.putAll(m);
	}
	
	@Override
	public void clear(){
		keys.clear();
		values.clear();
		super.clear();
	}
	
	public List<K> getKeys(){
		return keys;
	}
	
	public List<V> getValues(){
		return values;
	}
	
}
