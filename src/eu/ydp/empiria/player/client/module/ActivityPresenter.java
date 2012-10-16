package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface ActivityPresenter<H> extends IsWidget {

	void bindView();
	/**
	 * Czysci wszystkie powiazania w module
	 */
	void reset();
	void setEnabled(boolean enabled);
	void showAnswers(List<H> answers);
	void markAnswers(List<H> correctAnswers, boolean mark);

}
