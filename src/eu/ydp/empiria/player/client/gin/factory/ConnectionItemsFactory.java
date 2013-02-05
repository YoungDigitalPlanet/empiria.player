package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public interface ConnectionItemsFactory {
	public ConnectionItems getConnectionItems(InlineBodyGeneratorSocket bodyGeneratorSocket);

}
