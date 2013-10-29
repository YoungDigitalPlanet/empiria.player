package eu.ydp.empiria.player.client.gin.factory;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;

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
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionStyleChecker;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionsBetweenItems;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.gwtutil.client.xml.XMLParser;
import gwt.g2d.client.math.Vector2;

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
		Mockito.when(item.isOnPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
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
	public ConnectionSurface getConnectionSurface(Vector2 vector) {
		return surfaceProvider.get();
	}

	@Override
	public ConnectionsBetweenItems getConnectionsBetweenItems(IsWidget widget, ConnectionItems connectionItems) {
		return getConnectionsBetweemItems(widget, connectionItems);
	}

	ConnectionsBetweenItems connectionsBetweenItems;

	private ConnectionsBetweenItems getConnectionsBetweemItems(IsWidget widget, ConnectionItems connectionItems) {
		if (connectionsBetweenItems == null) {
			connectionsBetweenItems = spy(new ConnectionsBetweenItems(widget, connectionItems));
		}
		return connectionsBetweenItems;
	}

	@Override
	public ConnectionColumnsBuilder getConnectionColumnsBuilder(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface, ConnectionItems connectionItems,
			ConnectionView view) {
		return spy(new ConnectionColumnsBuilder(modelInterface, connectionItems, view));
	}

	@Override
	public ConnectionSurfaceView getConnectionSurfaceView(Vector2 vector) {
		return mock(ConnectionSurfaceView.class);
	}

}
