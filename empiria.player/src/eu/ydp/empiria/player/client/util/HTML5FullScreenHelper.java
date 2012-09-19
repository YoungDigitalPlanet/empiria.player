package eu.ydp.empiria.player.client.util;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Element;
import com.google.gwt.media.client.Video;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEvent;
import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEventHandler;

/**
 * Klasa pomocnicza do obslugi trybu pelnoekranowego w przegladarkach
 *
 */
public class HTML5FullScreenHelper {

	private final Set<FullScreenEventHandler> eventHandlers = new HashSet<FullScreenEventHandler>();

	/**
	 * Dodaje handlera nasluchujacego na zmiany trybu pelnoekranowego
	 *
	 * @param handler
	 */
	public void addFullScreenEventHandler(FullScreenEventHandler handler) {
		if (handler == null) {
			return;
		}
		if (eventHandlers.isEmpty()) {
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
	public boolean requestFullScreen(Element element) {
		return nativeRequestFullScrean(element);
	}

	private native boolean nativeRequestFullScrean(Element element)/*-{
		try {
			if (element.requestFullscreen) {
				element.requestFullscreen();
				return true;
			} else if (element.mozRequestFullScreen) {
				element.mozRequestFullScreen();
				return true;
			} else if (element.webkitRequestFullscreen) {
				element.webkitRequestFullscreen();
				return true;
			} else if (element.webkitRequestFullScreen) {
				element.webkitRequestFullScreen();
				return true;
			} else if (element.webkitEnterFullscreen) {
				element.webkitEnterFullscreen();
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
	public boolean exitFullScreen() {
		return nativeExitFullScreen();
	}

	private native boolean nativeExitFullScreen()/*-{
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

	public native boolean isInFullScrean(Element element)/*-{
		return !!e.webkitDisplayingFullscreen || !!e.webkitIsFullScreen;
	}-*/;

	/**
	 * metoda wywolywana z poziomu jsni dla obslugi zdarzen fullScreen
	 */
	protected void handleEvent() {
		final boolean inFullScreen = isInFullScreen();
		FullScreenEvent fse = new FullScreenEvent() {

			@Override
			public boolean isInFullScreen() {
				return inFullScreen;
			}
			@Override
			public MediaWrapper<?> getMediaWrapper() {
				return null;
			}
		};
		for (FullScreenEventHandler h : eventHandlers) {
			h.handleEvent(fse);
		}
	}

	/**
	 * Czy w danym momencie jest aktywny tryb pelnoekranowy
	 *
	 * @return
	 */
	public native boolean isInFullScreen()/*-{
		try {
			return !!$doc.fullscreen || !!$doc.mozFullScreen
					|| !!$doc.webkitIsFullScreen
					|| !!$doc.webkitDisplayingFullscreen ? true : false;
		} catch (e) {
			alert(e);
			//console.log(e);
		}
		return false;
	}-*/;

	private native void addFullScreenChangeHandler()/*-{
		return;
		try {
			$doc
					.addEventListener(
							"fullscreenchange",
							this
									.@eu.ydp.empiria.player.client.util.HTML5FullScreenHelper::handleEvent(),
							false);
			$doc
					.addEventListener(
							"mozfullscreenchange",
							this
									.@eu.ydp.empiria.player.client.util.HTML5FullScreenHelper::handleEvent(),
							false);
			$doc
					.addEventListener(
							"webkitfullscreenchange",
							this
									.@eu.ydp.empiria.player.client.util.HTML5FullScreenHelper::handleEvent(),
							false);
		} catch (e) {
			alert(e);
		}
	}-*/;

	/**
	 * sprawdza czy dany element mozna wyswietli w trybie pelnoekranowym
	 *
	 * @param video
	 * @return true jezeli mozna false w przeciwnym razie
	 */
	public native boolean supoortFullScreen(Video video)/*-{
		try {
			return !!video.getElement().webkitSupportsFullscreen;
		} catch (e) {
		}
	}-*/;

}
