package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import java.util.Map;

public class ConnectionSurfacesManager {

	private final class IdentifierMatchConnectionPairPredicate implements Predicate<ConnectionPairEntry<String, String>> {
		private final String identifier;

		private IdentifierMatchConnectionPairPredicate(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public boolean apply(ConnectionPairEntry<String, String> connection) {
			return identifier.equals(connection.getSource()) || identifier.equals(connection.getTarget());
		}
	}

	@Inject
	private ConnectionModuleFactory connectionFactory;

	public void resetAll(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces) {
		for (ConnectionSurface surface : connectedSurfaces.values()) {
			surface.removeFromParent();
		}
		connectedSurfaces.clear();
	}

	public boolean hasConnections(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces, final String identifier) {
		return Iterables.any(connectedSurfaces.keySet(), new IdentifierMatchConnectionPairPredicate(identifier));
	}

	public void clearConnectionSurface(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces,
			ConnectionPairEntry<String, String> keyValue) {
		ConnectionSurface connectionSurface = connectedSurfaces.get(keyValue);
		if (connectionSurface != null) {
			connectionSurface.clear();
			connectionSurface.removeFromParent();
			connectedSurfaces.remove(keyValue);
		}
	}

	public ConnectionSurface getOrCreateSurface(Map<String, ConnectionSurface> surfaces, String identifier, HasDimensions dimensions) {
		ConnectionSurface surface;
		if (surfaces.containsKey(identifier)) {
			surface = surfaces.get(identifier);
		} else {
			surface = connectionFactory.getConnectionSurface(dimensions);
			surfaces.put(identifier, surface);
		}
		return surface;
	}

	public void removeSurfaceForItem(Map<String, ConnectionSurface> surfaces, String identifier) {
		surfaces.remove(identifier);
	}

	public ConnectionPairEntry<String, String> findPointOnPath(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces, Point point) {
		ConnectionPairEntry<String, String> foundPoint = null;
		for (Map.Entry<ConnectionPairEntry<String, String>, ConnectionSurface> entry : connectedSurfaces.entrySet()) {
			if (entry.getValue().isPointOnPath(point)) {
				foundPoint = new ConnectionPairEntry<String, String>(entry.getKey().getSource(), entry.getKey().getTarget());
				break;
			}
		}
		return foundPoint;
	}

	public void putSurface(Map<String, ConnectionSurface> surfaces, Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces,
			ConnectionPairEntry<String, String> connectionPairForSurface, ConnectionSurface surface) {
		connectedSurfaces.put(connectionPairForSurface, surface);
		surfaces.remove(connectionPairForSurface.getSource());
	}

	public boolean containsSurface(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces,
			ConnectionPairEntry<String, String> keyValue) {
		return connectedSurfaces.containsKey(keyValue);
	}

	public void removeSurfaceFromParent(Map<ConnectionPairEntry<String, String>, ConnectionSurface> connectedSurfaces,
			ConnectionPairEntry<String, String> keyValue) {
		if (containsSurface(connectedSurfaces, keyValue)) {
			connectedSurfaces.get(keyValue).removeFromParent();
		}
	}
}
