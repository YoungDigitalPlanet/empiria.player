/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.gin.factory;

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
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructure;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
