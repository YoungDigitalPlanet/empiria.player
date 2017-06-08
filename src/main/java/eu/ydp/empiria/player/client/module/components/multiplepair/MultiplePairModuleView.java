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

package eu.ydp.empiria.player.client.module.components.multiplepair;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;

public interface MultiplePairModuleView extends IsWidget {

    void bindView();

    void setBean(MultiplePairBean<SimpleAssociableChoiceBean> modelInterface);

    /**
     * Jezeli istnieja jakies polaczenia i zostanie wywolana ta metoda to dla
     * wszystkich usunietych polaczen zostana wywolane handlery
     * {@link PairConnectEventHandler} zarejestrowane poprzez
     * {@link ConnectionModulePresenter#addConnectionEventHandler(PairConnectEventHandler)}
     * poprzez zdarzenie {@link PairConnectEventTypes.DISCONNECTED}
     *
     * @see eu.ydp.empiria.player.client.module.ActivityPresenter#reset()
     */
    void reset();

    /**
     * Blokowanie widoku
     *
     * @param locked
     * @see eu.ydp.empiria.player.client.module.ActivityPresenter#setLocked()
     */
    void setLocked(boolean locked);

    /**
     * Laczy wskazane elementy wywolanie tej metody jest rownowazne z
     * {@link ConnectionModulePresenter#connect(String, String, MultiplePairModuleConnectType)}
     * Wykonanie polaczenia musi zostac zakomunikowane poprzez powiadomienie
     * {@link PairConnectEventHandler} zarejestrowanych poprzez
     * {@link ConnectionModulePresenter#addConnectionEventHandler(PairConnectEventHandler)}
     * zdarzeniem {@link PairConnectEventTypes.CONNECTED}
     *
     * @param sourceIdentifier
     * @param targetIdentifier
     */
    void connect(String sourceIdentifier, String targetIdentifier, MultiplePairModuleConnectType type);

    /**
     * Rozdziela wskazane elementy. polaczenie powinno zostac usuniete z widoku.
     * Wykonanie rozloczenia musi zostac zakomunikowane poprzez powiadomienie
     * {@link PairConnectEventHandler} zarejestrowanych poprzez
     * {@link ConnectionModulePresenter#addConnectionEventHandler(PairConnectEventHandler)}
     * zdarzeniem {@link PairConnectEventTypes.DISCONNECTED}
     *
     * @param sourceIdentifier
     * @param targetIdentifier
     */
    void disconnect(String sourceIdentifier, String targetIdentifier);

    /**
     * Dodaje handlera nasuchujacego na zdarzenia z presentera
     *
     * @param handler
     * @see PairConnectEventTypes
     */
    void addPairConnectEventHandler(PairConnectEventHandler handler);

    void setModuleSocket(ModuleSocket socket);

    boolean isAttached();
}
