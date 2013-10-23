package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceView;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemViewLeft;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemViewRight;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionColumnsBuilder;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import gwt.g2d.client.math.Vector2;

public interface ConnectionModuleFactory {
	ConnectionModuleStructure getConnectionModuleStructure();

	ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket, Column column);

	ConnectionItemViewLeft getConnectionItemViewLeft(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);

	ConnectionItemViewRight getConnectionItemViewRight(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);

	ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);

	ConnectionSurface getConnectionSurface(Vector2 vector);

	ConnectionColumnsBuilder getConnectionColumnsBuilder(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface, ConnectionItems connectionItems,
			ConnectionView view);

	ConnectionSurfaceView getConnectionSurfaceView(Vector2 vector);

}
