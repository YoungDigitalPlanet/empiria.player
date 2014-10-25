package eu.ydp.empiria.player.client.module.speechscore.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface SpeechScoreLinkView extends IsWidget {
	void addHandler(Command command);

	String getUrl();
}
