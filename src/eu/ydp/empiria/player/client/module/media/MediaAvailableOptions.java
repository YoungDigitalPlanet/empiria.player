package eu.ydp.empiria.player.client.module.media;

/**
 * Obiekt opisujacy dostepne opcje mediow.
 *
 */
public interface MediaAvailableOptions {
	/**
	 * Czy mozemy wywolac akce play na kontrolerze
	 *
	 * @return true jezeli tak
	 */
	boolean isPlaySupported();

	/**
	 * Czy mozemy wykonac akcje pause na kontrelerze
	 *
	 * @return true jezeli tak
	 */
	boolean isPauseSupported();

	/**
	 * Czy mozemy wykonac akcje mute na kontrelerze
	 *
	 * @return true jezeli tak
	 */
	boolean isMuteSupported();

	/**
	 * Czy mozemy zmieniac natezenie dzwieku
	 *
	 * @return true jezeli tak
	 */
	boolean isVolumeChangeSupported();

	/**
	 * Czy mozemy wykonac akcje stop na kontrelerze
	 *
	 * @return true jezeli tak
	 */
	boolean isStopSupported();

	/**
	 * Czy mozemy dowlonie przemieszczac pozycje w strumieniu
	 *
	 * @return true jezeli tak
	 */
	boolean isSeekSupported();

	/**
	 * Czy mozemy wykonac akcje peny ekran na kontrolerze
	 *
	 * @return true jezeli tak
	 */
	boolean isFullScreenSupported();

	/**
	 * zwraca true jezeli mamy mozliwosc odczytania danych meta z zasobu takich
	 * jak dlugosc, pozycja w strumieniu itd..
	 *
	 * @return
	 */
	boolean isMediaMetaAvailable();

	/**
	 * Czy z danym odtwarzaczem mozliwe jest wykorzystanie szablonow
	 *
	 * @return
	 */
	boolean isTemplateSupported();
}
