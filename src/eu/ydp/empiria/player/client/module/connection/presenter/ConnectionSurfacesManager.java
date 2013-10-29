package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import gwt.g2d.client.math.Vector2;

public class ConnectionSurfacesManager {

	@Inject
	private ConnectionModuleFactory connectionFactory;

	protected final Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces = new HashMap<ConnectionPairEntry<String, String>, ConnectionSurface>();
	protected final Map<String, ConnectionSurface> surfaces = new HashMap<String, ConnectionSurface>();

	private final HasDimensions dimensions;

	@Inject
	public ConnectionSurfacesManager(@Assisted HasDimensions dimensions) {
		this.dimensions = dimensions;
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
		if (surfaces.containsKey(identifier)) {
			surface = surfaces.get(identifier);
		} else {
			Vector2 vector = new Vector2(dimensions.getWidth(), dimensions.getHeight());
			surface = connectionFactory.getConnectionSurface(vector);
			surfaces.put(identifier, surface);
		}
		return surface;
	}

	public void removeSurfaceForItem(String identifier) {
		surfaces.remove(identifier);
	}

	public ConnectionPairEntry<String, String> findPointOnPath(Point point) {
		ConnectionPairEntry<String, String> foundPoint = null;
		for (Map.Entry<ConnectionPairEntry<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(point)) {
				foundPoint = new ConnectionPairEntry<String, String>(entry.getKey().getSource(), entry.getKey().getTarget());
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
