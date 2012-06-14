package eu.ydp.empiria.player.client.util;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Element;

/**
 * Klasa pomocnicza do obslugi trybu pelnoekranowego w przegladarkach
 * 
 */
public class HTML5FullScreen {

	private static Set<FullScreenEventHandler> eventHandlers = new HashSet<HTML5FullScreen.FullScreenEventHandler>();

	public interface FullScreenEvent {
		/**
		 * zwraca true jesli przegladarka jest w trybie pelnoekranowym
		 * 
		 * @return
		 */
		public boolean isInFullScreen();
	}

	public interface FullScreenEventHandler {
		void handleEvent(FullScreenEvent event);
	}

	/**
	 * Dodaje handlera nasluchujacego na zmiany trybu pelnoekranowego
	 * 
	 * @param handler
	 */
	public static void addFullScreenEventHandler(FullScreenEventHandler handler) {
		if (handler == null)
			return;
		if (eventHandlers.size() == 0) {
			addFullScreenChangeHandler();
		}
		eventHandlers.add(handler);
	}

	/**
	 * probuje wyswietlic podany element w trybie fullScreen
	 * 
	 * @param element
	 * @return true jezeli sie uda false w przeciwnym razie
	 */
	public static boolean requestFullScreen(Element element) {
		return nativeRequestFullScrean(element);
	}

	private static native boolean nativeRequestFullScrean(Element element)/*-{
		try {
			if (element.requestFullscreen) {
				element.requestFullscreen();
				return true;
			} else if (element.mozRequestFullScreen) {
				element.mozRequestFullScreen();
				return true;
			} else if (element.webkitRequestFullScreen) {
				element.webkitRequestFullScreen();
				return true;
			}
		} catch (e) {
			console.log(e);
		}
		return false;
	}-*/;

	/**
	 * Probuje opuscic tryb fullScreen
	 * 
	 * @param element
	 * @return
	 */
	public static boolean exitFullScreen() {
		return nativeExitFullScreen();
	}

	private static native boolean nativeExitFullScreen()/*-{
		try {
			if ($doc.exitFullscreen) {
				$doc.exitFullscreen();
				return true;
			} else if ($doc.mozCancelFullScreen) {
				$doc.mozCancelFullScreen();
				return true;
			} else if ($doc.webkitCancelFullScreen) {
				$doc.webkitCancelFullScreen();
				return true;
			}

		} catch (e) {
			console.log(e);
		}
		return false;
	}-*/;

	/**
	 * metoda wywolywana z poziomu jsni dla obslugi zdarzen fullScreen
	 */
	protected static void handleEvent() {
		final boolean inFullScreen = isInFullScreen();
		FullScreenEvent fse = new FullScreenEvent() {

			@Override
			public boolean isInFullScreen() {
				return inFullScreen;
			}
		};
		for (FullScreenEventHandler h : eventHandlers) {
			h.handleEvent(fse);
		}
	}

	/**
	 * Czy w danym momencie jest aktywny tryb pelnoekranowy
	 * @return 
	 */
	public static native boolean isInFullScreen()/*-{
		try {
			return $doc.fullscreen || $doc.mozFullScreen
					|| $doc.webkitIsFullScreen ? true : false;
		} catch (e) {
			//console.log(e);
		}
		return false;
	}-*/;

	private static native void addFullScreenChangeHandler()/*-{
		$doc
				.addEventListener(
						"fullscreenchange",
						@eu.ydp.empiria.player.client.util.HTML5FullScreen::handleEvent(),
						false);
		$doc
				.addEventListener(
						"mozfullscreenchange",
						@eu.ydp.empiria.player.client.util.HTML5FullScreen::handleEvent(),
						false);
		$doc
				.addEventListener(
						"webkitfullscreenchange",
						@eu.ydp.empiria.player.client.util.HTML5FullScreen::handleEvent(),
						false);

	}-*/;

}
