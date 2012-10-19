package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.IsWidget;

public interface SimpleChoicePresenter extends IsWidget{

	void setLocked(boolean locked);

	void setListener(ChoiceModuleListener listener);

	void reset();

	IsWidget getFeedbackPlaceHolder();

	boolean isSelected();

	void markCorrectAnswers();

	void markWrongAnswers();

	void unmarkCorrectAnswers();

	void unmarkWrongAnswers();

	void setSelected(boolean isCorrect);

}
