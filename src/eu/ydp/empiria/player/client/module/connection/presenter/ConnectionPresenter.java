package eu.ydp.empiria.player.client.module.connection.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.util.events.connection.ConnectionEventHandler;
import eu.ydp.empiria.player.client.util.events.connection.ConnectionEventTypes;

public interface ConnectionPresenter extends ActivityPresenter<ConnectionModuleModel, MatchInteractionBean> {
	
	/**
	 * Jezeli istnieja jakies polaczenia i zostanie wywolana ta metoda to dla
	 * wszystkich usunietych polaczen zostana wywolane handlery
	 * {@link ConnectionEventHandler} zarejestrowane poprzez
	 * {@link ConnectionPresenter#addConnectionEventHandler(ConnectionEventHandler)}
	 * poprzez zdarzenie {@link ConnectionEventTypes.DISCONNECTED}
	 *
	 * @see eu.ydp.empiria.player.client.module.ActivityPresenter#reset()
	 */
	@Override
	public void reset();

	/**
	 * Laczy wskazane elementy wywolanie tej metody jest rownowazne z
	 * {@link ConnectionPresenter#connect(String, String, ConnectType.NORMAL)}
	 * Wykonanie polaczenia musi zostac zakomunikowane poprzez powiadomienie
	 * {@link ConnectionEventHandler} zarejestrowanych poprzez
	 * {@link ConnectionPresenter#addConnectionEventHandler(ConnectionEventHandler)}
	 * zdarzeniem {@link ConnectionEventTypes.CONNECTED}
	 *
	 * @param sourceIdentifier
	 * @param targetIdentifier
	 */
	public void connect(String sourceIdentifier, String targetIdentifier);

//	/**
//	 * Laczy wskazane elementy Wykonanie polaczenia musi zostac zakomunikowane
//	 * poprzez powiadomienie {@link ConnectionEventHandler} zarejestrowanych
//	 * poprzez
//	 * {@link ConnectionPresenter#addConnectionEventHandler(ConnectionEventHandler)}
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
	 * {@link ConnectionEventHandler} zarejestrowanych poprzez
	 * {@link ConnectionPresenter#addConnectionEventHandler(ConnectionEventHandler)}
	 * zdarzeniem {@link ConnectionEventTypes.DISCONNECTED}
	 *
	 * @param sourceIdentifier
	 * @param targetIdentifier
	 */
	public void disconnect(String sourceIdentifier, String targetIdentifier);
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
	 * @see ConnectionEventTypes
	 * @param handler
	 */
	public void addConnectionEventHandler(ConnectionEventHandler handler);
}
