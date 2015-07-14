package eu.ydp.empiria.player.client.module.choice.providers;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SingleChoiceStyleProvider implements SimpleChoiceStyleProvider {

    private StyleNameConstants styleNameConstants;

    @Inject
    public SingleChoiceStyleProvider(StyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
    }

    @Override
    public String getMarkCorrectStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_SINGLE_MARK_CORRECT();
    }

    @Override
    public String getMarkWrongStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_SINGLE_MARK_WRONG();
    }

    @Override
    public String getInactiveStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_SINGLE_INACTIVE();
    }

    @Override
    public String getResetStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_SINGLE_MARK_NONE();
    }

    @Override
    public String getAnswereStyle() {
        return styleNameConstants.QP_CHOICE_BUTTON_SINGLE_MARK();
    }

}
