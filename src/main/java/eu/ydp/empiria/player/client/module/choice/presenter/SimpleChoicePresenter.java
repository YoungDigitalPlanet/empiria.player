package eu.ydp.empiria.player.client.module.choice.presenter;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;

public interface SimpleChoicePresenter extends IsWidget {

    void setLocked(boolean locked);

    void setListener(ChoiceModuleListener listener);

    void reset();

    IsWidget getFeedbackPlaceHolder();

    boolean isSelected();

    void markAnswer(MarkAnswersType type, MarkAnswersMode mode);

    void setSelected(boolean isCorrect);

    boolean isMulti();

    void onChoiceClick();

    String getIdentifier();
}
