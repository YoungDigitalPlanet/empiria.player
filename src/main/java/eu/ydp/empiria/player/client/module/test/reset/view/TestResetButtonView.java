package eu.ydp.empiria.player.client.module.test.reset.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.gwtutil.client.event.factory.Command;

public interface TestResetButtonView extends IsWidget {
	void addHandler(Command command);

	void lock();

	void unlock();

	void enablePreviewMode();
}
