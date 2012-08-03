package eu.ydp.empiria.player.client.view.player;

import java.util.HashMap;
import java.util.Map;

import eu.ydp.empiria.player.client.PlayerGinjector;

/**
 * Cache przechowujacy elementy page
 *
 */
public abstract class AbstractElementCache<T> {
	private final Map<Integer,T> cache = new HashMap<Integer, T>();
	protected PlayerGinjector injector = PlayerGinjector.INSTANCE;

	/**
	 * Sprawdza czy w cechu znajduje sie strona o danym indeksie
	 *
	 * @param index
	 * @return
	 */
	public boolean isPresent(int index) {
		return cache.get(index) != null;
	}

	/**
	 * Zwraca pageview dla konkretnego indeksu jezeli nie istnieje w cechu
	 * zostanie utworzony
	 *
	 * @param index
	 * @return
	 */
	public T get(int index) {
		T contentView = cache.get(index);
		if (contentView == null) {
			contentView = getElement(index);
			cache.put(index, contentView);
		}
		return contentView;
	}

	public void put(int index,T object){
		cache.put(index, object);
	}

	protected abstract T getElement(int index);

}
