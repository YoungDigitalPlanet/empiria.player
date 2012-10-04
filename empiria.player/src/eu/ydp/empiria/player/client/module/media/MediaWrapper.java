package eu.ydp.empiria.player.client.module.media;

import com.google.gwt.user.client.ui.Widget;

public interface MediaWrapper<T extends Widget> {
	/**
	 * zwraca konfiguracje dostpnych opcji na zasaobie
	 * @return
	 */
	MediaAvailableOptions getMediaAvailableOptions();
	/**
	 * zwraca piowiazany zasob
	 * @return
	 */
	T getMediaObject();
	/**
	 * Zwraca unikatowe id w obrebie calej aplikacji
	 * @return
	 */
	String getMediaUniqId();

	/**
	 * pozycja w strumieniu
	 * @return
	 */
	double getCurrentTime();

	/**
	 * dlugosc utworu jaka mozna odczytac z metadata.
	 * @return
	 */
	double getDuration();

	/**
	 * czy audio jest wyciszone przez metode mute
	 * @return
	 */
	boolean isMuted();

	/**
	 * naezenie dzwieku przedzial 0 do 1
	 * @return
	 */
	double getVolume();

	/**
	 * Czy audio jest gotowe do odtwarzania
	 * @return
	 */
	boolean canPlay();


}
