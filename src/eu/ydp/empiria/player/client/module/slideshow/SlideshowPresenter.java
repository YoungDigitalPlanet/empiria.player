package eu.ydp.empiria.player.client.module.slideshow;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public interface SlideshowPresenter extends IsWidget {
	void setBodyGeneratorSocket(InlineBodyGeneratorSocket socket);

	void setTitle(Element titleNode);

	void setSlides(List<Slide> slidesList);

	void showSlide(int slideIndex);

	void setEnabledNextButton(boolean enabled);

	void setEnabledPreviousButton(boolean enabled);

	void setPlayButtonDown(boolean down);

	void setViewClass(String className);

	boolean isPlayButtonDown();
}
