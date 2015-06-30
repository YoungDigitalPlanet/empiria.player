package eu.ydp.empiria.player.client.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Utility class to ease creation of typed maps. It wraps given map and Recommended usage:
 * <p/>
 * <pre>
 * import static pl.ydp.cat.util.MapCreator.m;
 *
 * Map&lt;String,Boolean&gt; string2boolean =
 *    m( "false", Boolean.FALSE ).
 *    p( "true", Boolean.TRUE ).
 *    map;
 *
 * Map&lt;String, Class&lt;? extends BaseTag&gt;&gt; tag2class =
 *    m( new HashMap&lt;String, Class&lt;? extends BaseTag&gt;&gt;() ).
 *    p("dummy", Dummy.class).
 *    p("entry", Entry.class).
 *    map;
 * </pre>
 *
 * @param <K> map key type parameter
 * @param <V> map value type parameter
 */
public class MapCreator<K, V> implements Map<K, V> {
    /**
     * Map where values are added
     */
    public Map<K, V> map;

    /**
     * Create map creator with HashMap and added these values
     *
     * @param key   key added to map
     * @param value value mapped
     * @return map creator
     */
    static public <K, V> MapCreator<K, V> m(K key, V value) {
        return new MapCreator<K, V>().p(key, value);
    }

    /**
     * Create map creator with given map type
     *
     * @param map map where values will be added
     * @return map creator
     */
    static public <K, V> MapCreator<K, V> m(Map<K, V> map) {
        return new MapCreator<K, V>(map);
    }

    /**
     * Create map creator with given map type
     *
     * @param map map where values will be added
     */
    public MapCreator(Map<K, V> m) {
        this.map = m;
    }

    /**
     * Create map creator with HashMap and added these values
     */
    public MapCreator() {
        map = new HashMap<K, V>();
    }

    /**
     * Put key/value pair into contained map
     *
     * @param key   key
     * @param value mapped value
     * @return map creator, that wraps map.
     */
    public MapCreator<K, V> p(K key, V value) {
        map.put(key, value);

        return this;
    }

    // map interface implementation
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object obj) {
        return map.containsValue(obj);
    }

    @Override
    public V get(Object obj) {
        return map.get(obj);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object obj) {
        return map.remove(obj);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object obj) {
        return map.equals(obj);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
