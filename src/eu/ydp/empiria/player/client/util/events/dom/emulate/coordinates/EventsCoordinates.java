package eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates;

import eu.ydp.empiria.player.client.util.position.Point;

public interface EventsCoordinates<E> {
	public void addEvent(E event);

	public Point getEvent(int index);

	public void removeEvent(E event);

	public int getLength();
}
