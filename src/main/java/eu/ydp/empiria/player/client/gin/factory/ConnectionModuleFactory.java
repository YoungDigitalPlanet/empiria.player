package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.*;
import eu.ydp.empiria.player.client.module.connection.*;
import eu.ydp.empiria.player.client.module.connection.item.*;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem.Column;
import eu.ydp.empiria.player.client.module.connection.presenter.*;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.*;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface ConnectionModuleFactory {
	ConnectionModuleStructure getConnectionModuleStructure();

	ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket, Column column);

	ConnectionItemViewLeft getConnectionItemViewLeft(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);

	ConnectionItemViewRight getConnectionItemViewRight(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket);

	ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);

	ConnectionSurface getConnectionSurface(HasDimensions dimensions);

	ConnectionColumnsBuilder getConnectionColumnsBuilder(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface, ConnectionItems connectionItems,
			ConnectionView view);

	ConnectionSurfaceView getConnectionSurfaceView(HasDimensions dimensions);

}
