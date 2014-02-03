package eu.ydp.empiria.player.client.module.button.download.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface ButtonModuleView extends IsWidget {

	void setDescription(String description);

	void setId(String id);

	void setUrl(String url);

	void addAnchorClickHandler(ClickHandler clickHandler);
}