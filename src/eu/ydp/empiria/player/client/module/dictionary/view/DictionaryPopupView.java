package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;

public interface DictionaryPopupView extends IsWidget {

	void addClickHandler(ClickHandler clickHandler);

	void show();

	void hide();
}
