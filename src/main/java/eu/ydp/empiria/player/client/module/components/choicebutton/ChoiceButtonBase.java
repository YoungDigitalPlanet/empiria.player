package eu.ydp.empiria.player.client.module.components.choicebutton;

import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public abstract class ChoiceButtonBase extends CustomPushButton implements ISelectableButton {

    protected boolean selected;
    protected boolean over;
    protected String moduleStyleNamePart;

    public ChoiceButtonBase(String moduleStyleNamePart) {
        selected = false;
        over = false;
        this.moduleStyleNamePart = moduleStyleNamePart;
    }

    public void setButtonEnabled(boolean value) {
        setEnabled(value);
        updateStyle();
    }

    public void setSelected(boolean value) {
        selected = value;
        updateStyle();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setMouseOver(boolean o) {
        over = o;
        updateStyle();
    }

    @Override
    public void select() {
        setSelected(true);
    }

    @Override
    public void unselect() {
        setSelected(false);
    }

    protected void updateStyle() {
        String styleName = findStyleName();
        setStyleName(styleName);
    }

    protected String findStyleName() {
        String styleName = "qp-" + moduleStyleNamePart + "-button";
        if (selected) {
            styleName += "-selected";
        } else {
            styleName += "-notselected";
        }
        if (!isEnabled()) {
            styleName += "-disabled";
        }
        if (over) {
            styleName += "-over";
        }
        return styleName;
    }
}
