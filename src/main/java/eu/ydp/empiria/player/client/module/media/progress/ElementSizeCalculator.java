package eu.ydp.empiria.player.client.module.media.progress;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

public class ElementSizeCalculator {
    public int getWidth(IsWidget widget) {
        Element element = widget.asWidget().getElement();
        return element.getAbsoluteRight() - element.getAbsoluteLeft();
    }
}
