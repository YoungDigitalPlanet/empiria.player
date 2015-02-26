package eu.ydp.empiria.player.client.gin.factory;

import static org.mockito.Mockito.*;

import com.google.inject.*;
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
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.mockito.Mockito;

public class ConnectionModuleFactoryMock implements ConnectionModuleFactory {
	// singleton na potrzeby testow wszystkie operacje na jednym
	ConnectionSurface surface = mock(ConnectionSurface.class);

	@Inject
	Provider<ConnectionSurface> surfaceProvider;

	@Inject
	XMLParser xmlParser;

	@Inject
	ConnectionStyleChecker connectionStyleChecker;

	@Override
	public ConnectionModuleStructure getConnectionModuleStructure() {
		return null;
	}

	@Override
	public ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket, Column column) {
		ConnectionItem item = mock(ConnectionItem.class);
		Mockito.when(item.getBean()).thenReturn(element);
		Mockito.when(item.toString()).thenReturn("");
		return item;
	}

	@Override
	public ConnectionItemViewLeft getConnectionItemViewLeft(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket) {
		return null;
	}

	@Override
	public ConnectionItemViewRight getConnectionItemViewRight(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket) {
		return null;
	}

	@Override
	public ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener) {
		return null;
	}

	@Override
	public ConnectionSurface getConnectionSurface(HasDimensions dimensions) {
		return surfaceProvider.get();
	}

	@Override
	public ConnectionColumnsBuilder getConnectionColumnsBuilder(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface, ConnectionItems connectionItems,
			ConnectionView view) {
		return spy(new ConnectionColumnsBuilder(modelInterface, connectionItems, view));
	}

	@Override
	public ConnectionSurfaceView getConnectionSurfaceView(HasDimensions dimensions) {
		return mock(ConnectionSurfaceView.class);
	}

}
