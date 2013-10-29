package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionSurfacesManager;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface ConnectionSurfacesManagerFactory {
	public ConnectionSurfacesManager getConnectionSurfacesManager(HasDimensions hasDimension);
}
