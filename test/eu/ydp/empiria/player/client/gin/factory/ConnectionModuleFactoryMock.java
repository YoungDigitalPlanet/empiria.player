package eu.ydp.empiria.player.client.gin.factory;

import static org.mockito.Mockito.mock;

import org.mockito.Mockito;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItemView;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;

public class ConnectionModuleFactoryMock implements ConnectionModuleFactory {
	//singleton na potrzeby testow wszystkie operacje na jednym
	ConnectionSurface surface = mock(ConnectionSurface.class);

	public ConnectionModuleFactoryMock() {

	}
	@Override
	public ConnectionModuleStructure getConnectionModuleStructure() {
		return null;
	}

	@Override
	public ConnectionItem getConnectionItem(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket) {
		ConnectionItem item = mock(ConnectionItem.class);
		Mockito.when(item.getBean()).thenReturn(element);
		Mockito.when(item.isOnPosition(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);
		Mockito.when(item.toString()).thenReturn("");
		return item;
	}

	@Override
	public ConnectionItemView getConnectionItemView(PairChoiceBean element, InlineBodyGeneratorSocket bodyGeneratorSocket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ConnectionModuleModel getConnectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener) {
		return null;
	}

	@Override
	public ConnectionView getConnectionView() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ConnectionSurface getConnectionSurface(@Assisted("width") Integer width, @Assisted("height") Integer height) {
		// TODO Auto-generated method stub
		return surface;
	}

}
