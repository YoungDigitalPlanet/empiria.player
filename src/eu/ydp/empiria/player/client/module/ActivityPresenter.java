package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;

public interface ActivityPresenter {
	
	void bindView();
	void reset();
	void setEnabled(boolean enabled);
	void showAnswers(List<String> answers);
	void markAnswers(List<String> correctAnswers, boolean mark);
	Panel getMainPanel();
	
}
