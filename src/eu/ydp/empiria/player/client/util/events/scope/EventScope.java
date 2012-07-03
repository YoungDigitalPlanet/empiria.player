package eu.ydp.empiria.player.client.util.events.scope;

/**
 * Zakres widocznosci eventu
 *
 */
public interface EventScope<T> extends Comparable<T> {
	public enum Scope {
		PAGE, LESSON
	}

	public Scope getScope() ;

}
