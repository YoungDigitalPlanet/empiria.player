package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.UIObject;

public abstract class GapModulePesenterBase implements GapModulePresenter {

    public abstract UIObject getComponent();

    @Override
    public void setWidth(double value, Unit unit) {
        getComponent().setWidth(value + unit.getType());
    }

    @Override
    public int getOffsetWidth() {
        return getComponent().getOffsetWidth();
    }

    @Override
    public void setHeight(double value, Unit unit) {
        getComponent().setHeight(value + unit.getType());
    }

    @Override
    public int getOffsetHeight() {
        return getComponent().getOffsetHeight();
    }

    @Override
    public void setFontSize(double value, Unit unit) {
        getComponent().getElement().getStyle().setFontSize(value, unit);
    }

    @Override
    public int getFontSize() {
        return Integer.parseInt(getComponent().getElement().getStyle().getFontSize().replace("px", ""));
    }
}
