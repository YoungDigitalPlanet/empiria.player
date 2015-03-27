package eu.ydp.empiria.player.client.module.slideshow.view.pager.button;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface SlideshowPagerButtonView extends IsWidget {
	void setOnClickCommand(Command onClickCommand);

	void activatePagerButton();

	void deactivatePagerButton();
}
