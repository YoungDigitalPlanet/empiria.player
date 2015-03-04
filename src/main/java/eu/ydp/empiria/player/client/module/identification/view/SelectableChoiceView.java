package eu.ydp.empiria.player.client.module.identification.view;

import com.google.gwt.user.client.ui.IsWidget;


public interface SelectableChoiceView extends IsWidget {
	void setCoverId(String coverId);

	void markNotSelectedAnswerCorrect();

	void markNotSelectedAnswerWrong();

	void markSelectedAnswerCorrect();

	void markSelectedAnswerWrong();

	void markSelectedOption();

	void unmarkSelectedOption();

	void lock();

	void unlock();
}
