package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemViewLeft;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemViewRight;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public interface ConnectionModuleFactory {
	//public ConnectionModuleListener getConnectionModuleListener(ConnectionModule module);
	public ConnectionModuleStructure getConnectionModuleStructure();
	public ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket,Column column);
	public ConnectionItemViewLeft getConnectionItemViewLeft (PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);
	public ConnectionItemViewRight getConnectionItemViewRight (PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);
	public ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);
	public ConnectionSurface getConnectionSurface(@Assisted("width") Integer width, @Assisted("height") Integer height);
	public ConnectionView getConnectionView();
}
