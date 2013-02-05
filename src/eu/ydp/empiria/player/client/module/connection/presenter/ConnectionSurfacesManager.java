package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.view.HasDimension;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionSurfacesManager {

	private static final int APPROXIMATION = 10;

	@Inject
	private ConnectionModuleFactory connectionFactory;

	protected final Map<KeyValue<String, String>, ConnectionSurface> connectedSurfaces = new HashMap<KeyValue<String, String>, ConnectionSurface>();
	protected final Map<String, ConnectionSurface> surfaces = new HashMap<String, ConnectionSurface>();

	private final HasDimension view;

	@Inject
	public ConnectionSurfacesManager(@Assisted HasDimension hasDimension) {
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
		for (KeyValue<String, String> connection : connectedSurfaces.keySet()) {
			if (identifier.equals(connection.getKey()) || identifier.equals(connection.getValue())) {
				hasConnection = true;
				break;
			}
		}
		return hasConnection;
	}

	public void clearConnectionSurface(KeyValue<String, String> keyValue) {
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

	public KeyValue<String, String> findPointOnPath(Point point) {
		KeyValue<String, String> findedPoint = null;
		for (Map.Entry<KeyValue<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(point.getX(), point.getY(), APPROXIMATION)) {
				findedPoint = new KeyValue<String, String>(entry.getKey().getKey(), entry.getKey().getValue());// NOPMD
				break;
			}
		}
		return findedPoint;
	}

	public void putSurface(KeyValue<String, String> keyValue, ConnectionSurface surface) {
		connectedSurfaces.put(keyValue, surface);
		surfaces.remove(keyValue.getKey());
	}

	public boolean containsSurface(KeyValue<String, String> keyValue) {
		return connectedSurfaces.containsKey(keyValue);
	}

	public void removeSurfaceFromParent(KeyValue<String, String> keyValue) {
		if (containsSurface(keyValue)) {
			connectedSurfaces.get(keyValue).removeFromParent();
		}
	}
}
