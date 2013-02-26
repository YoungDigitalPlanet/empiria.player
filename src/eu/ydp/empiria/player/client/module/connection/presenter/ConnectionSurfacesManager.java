package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.util.position.Point;

public class ConnectionSurfacesManager {

	private static final int APPROXIMATION = 10;

	@Inject
	private ConnectionModuleFactory connectionFactory;

	protected final Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces = new HashMap<ConnectionPairEntry<String, String>, ConnectionSurface>();
	protected final Map<String, ConnectionSurface> surfaces = new HashMap<String, ConnectionSurface>();

	private final HasDimensions view;

	@Inject
	public ConnectionSurfacesManager(@Assisted HasDimensions hasDimension) {
		this.view = hasDimension;
	}

	public void resetAll() {
		for (ConnectionSurface surfce : connectedSurfaces.values()) {
			surfce.removeFromParent();
		}
		connectedSurfaces.clear();
	}

	public boolean hasConnections(String identifier) {
		boolean hasConnection = false;
		for (ConnectionPairEntry<String, String> connection : connectedSurfaces.keySet()) {
			if (identifier.equals(connection.getSource()) || identifier.equals(connection.getTarget())) {
				hasConnection = true;
				break;
			}
		}
		return hasConnection;
	}

	public void clearConnectionSurface(ConnectionPairEntry<String, String> keyValue) {
		ConnectionSurface connectionSurface = connectedSurfaces.get(keyValue);
		if (connectionSurface != null) {
			connectionSurface.clear();
			connectionSurface.removeFromParent();
			connectedSurfaces.remove(keyValue);
		}
	}

	protected ConnectionSurface getOrCreateSurface(String identifier) {
		ConnectionSurface surface;
		if ((surface = surfaces.get(identifier)) == null) {// NOPMD
			surface = connectionFactory.getConnectionSurface(view.getWidth(), view.getHeight());
			surfaces.put(identifier, surface);
		}
		return surface;
	}

	public ConnectionPairEntry<String, String> findPointOnPath(Point point) {
		ConnectionPairEntry<String, String> foundPoint = null;
		for (Map.Entry<ConnectionPairEntry<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(point.getX(), point.getY(), APPROXIMATION)) {
				foundPoint = new ConnectionPairEntry<String, String>(entry.getKey().getSource(), entry.getKey().getTarget());// NOPMD
				break;
			}
		}
		return foundPoint;
	}

	public void putSurface(ConnectionPairEntry<String, String> keyValue, ConnectionSurface surface) {
		connectedSurfaces.put(keyValue, surface);
		surfaces.remove(keyValue.getSource());
	}

	public boolean containsSurface(ConnectionPairEntry<String, String> keyValue) {
		return connectedSurfaces.containsKey(keyValue);
	}

	public void removeSurfaceFromParent(ConnectionPairEntry<String, String> keyValue) {
		if (containsSurface(keyValue)) {
			connectedSurfaces.get(keyValue).removeFromParent();
		}
	}
}
