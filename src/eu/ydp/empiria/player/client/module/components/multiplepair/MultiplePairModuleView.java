package eu.ydp.empiria.player.client.module.components.multiplepair;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionModulePresenter;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;

public interface MultiplePairModuleView<T extends PairChoiceBean> extends IsWidget {

	void bindView();
	void setBean(MultiplePairBean<T> modelInterface);
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
	 *
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

//	/**
//	 * Laczy wskazane elementy Wykonanie polaczenia musi zostac zakomunikowane
//	 * poprzez powiadomienie {@link ConnectionEventHandler} zarejestrowanych
//	 * poprzez
//	 * {@link ConnectionModulePresenter#addConnectionEventHandler(ConnectionEventHandler)}
//	 * zdarzeniem {@link ConnectionEventTypes.CONNECTED}
//	 *
//	 * @param sourceIdentifier
//	 * @param targetIdentifier
//	 * @param connectType
//	 *            typ stylu jaki ma byc wykorzystany podczas laczenia
//	 */
//	public void connect(String sourceIdentifier, String targetIdentifier, ConnectType connectType);

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
//
//	/**
//	 * Powoduje oznaczenie polaczenia sourceIdentifier->targetIdentifier stylem
//	 * {@link StyleNameConstants#QP_CONNECTION_WRONG()} Jezeli polaczenie nie
//	 * istnieje brak reakcji po stronie widoku
//	 *
//	 * @param sourceIdentifier
//	 * @param targetIdentifier
//	 */
//	public void markWrong(String sourceIdentifier, String targetIdentifier);
//
//	/**
//	 * Powoduje oznaczenie polaczenia sourceIdentifier->targetIdentifier stylem
//	 * {@link StyleNameConstants#QP_CONNECTION_CORRECT()} Jezeli polaczenie nie
//	 * istnieje brak reakcji po stronie widoku
//	 *
//	 * @param sourceIdentifier
//	 * @param targetIdentifier
//	 */
//	public void markCorrect(String sourceIdentifier, String targetIdentifier);

	/**
	 * Dodaje handlera nasuchujacego na zdarzenia z presentera
	 *
	 * @see PairConnectEventTypes
	 * @param handler
	 */
	void addPairConnectEventHandler(PairConnectEventHandler handler);

	void setModuleSocket(ModuleSocket socket);
	boolean isAttached();

}
