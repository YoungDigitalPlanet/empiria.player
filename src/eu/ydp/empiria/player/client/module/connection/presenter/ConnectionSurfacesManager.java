package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class ConnectionSurfacesManager {

	private static final int APPROXIMATION = 30;

	@Inject
	private ConnectionModuleFactory connectionFactory;

	private final Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces = Maps.newHashMap();
	private final Map<String, ConnectionSurface> surfaces = Maps.newHashMap();

	public void resetAll() {
		for (ConnectionSurface surface : connectedSurfaces.values()) {
			surface.removeFromParent();
		}
		connectedSurfaces.clear();
	}

	public boolean hasConnections(String identifier) {
		for (ConnectionPairEntry<String, String> connection : connectedSurfaces.keySet()) {
			if (identifier.equals(connection.getSource()) || identifier.equals(connection.getTarget())) {
				return true;
			}
		}
		return false;
	}

	public void clearConnectionSurface(ConnectionPairEntry<String, String> keyValue) {
		ConnectionSurface connectionSurface = connectedSurfaces.get(keyValue);
		if (connectionSurface != null) {
			connectionSurface.clear();
			connectionSurface.removeFromParent();
			connectedSurfaces.remove(keyValue);
		}
	}

	public ConnectionSurface getOrCreateSurface(String identifier, HasDimensions dimensions) {
		ConnectionSurface surface;
		if (surfaces.containsKey(identifier)) {
			surface = surfaces.get(identifier);
		} else {
			surface = connectionFactory.getConnectionSurface(dimensions.getWidth(), dimensions.getHeight());
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
			if (entry.getValue().isPointOnPath(point.getX(), point.getY(), APPROXIMATION)) {
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
