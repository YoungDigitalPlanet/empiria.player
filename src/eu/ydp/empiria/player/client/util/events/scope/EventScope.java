package eu.ydp.empiria.player.client.util.events.scope;

import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBusOld;

/**
 * Zakres widocznosci eventu
 *
 */
public interface EventScope<T> extends Comparable<T> {
	/**
	 * do uproszczenia porownywania budujemy hierarchie zakresow przoprzez
	 * kolejne definiowanie enumow. Scope.ordinal == 0 okresla element
	 * najwyzszego poziomu dalsze elementy to wezszy zakres.<br/>
	 * <b>UWAGA zmiany w kolejnosci maja wplyw na dzialanie
	 * {@link PlayerEventsBusOld} </b>
	 *
	 */
	public enum Scope {
		LESSON, PAGE, GROUP
	}

	public Scope getScope();

}
