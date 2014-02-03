package eu.ydp.empiria.player.client.module.connection.view.event;

import com.google.gwt.dom.client.NativeEvent;

public class ConnectionMoveEvent {
	private final double xPos;
	private final double yPos;
	private final NativeEvent event;

	public ConnectionMoveEvent(double xPos, double yPos, NativeEvent event) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.event = event;
	}

	public double getX() {
		return xPos;
	}

	public double getY() {
		return yPos;
	}

	public NativeEvent getNativeEvent() {
		return event;
	}

	public void preventDefault() {
		event.preventDefault();
	}
}
