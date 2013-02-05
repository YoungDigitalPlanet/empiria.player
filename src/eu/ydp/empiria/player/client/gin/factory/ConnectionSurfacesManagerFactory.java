package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionSurfacesManager;
import eu.ydp.empiria.player.client.module.view.HasDimension;

public interface ConnectionSurfacesManagerFactory {
	public ConnectionSurfacesManager getConnectionSurfacesManager(HasDimension hasDimension);
}
