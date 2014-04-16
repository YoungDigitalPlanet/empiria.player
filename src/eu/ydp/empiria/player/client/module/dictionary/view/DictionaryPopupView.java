package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Panel;

import eu.ydp.gwtutil.client.event.factory.Command;

public interface DictionaryPopupView extends IsWidget {

	void addHandler(Command command);

	void show();

	void hide();

	Panel getContainer();
}
