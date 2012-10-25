package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public interface ConnectionModuleFactory {
	//public ConnectionModuleListener getConnectionModuleListener(ConnectionModule module);
	public ConnectionModuleStructure getConnectionModuleStructure();
	public ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);
	public ConnectionItemView getConnectionItemView(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);
	public MultiplePairModuleView getMultiplePairModuleView();
	public ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);
	public ConnectionSurface getConnectionSurfce(int width, int height);
	public ConnectionView getConnectionView();
}
