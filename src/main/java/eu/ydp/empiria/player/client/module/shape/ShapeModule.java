package eu.ydp.empiria.player.client.module.shape;

import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;

public class ShapeModule extends SimpleModuleBase {

    protected PushButton button;

    public ShapeModule() {
        button = new PushButton();
        button.setStyleName("qp-shape");
    }

    @Override
    public void initModule(Element element) {
    }

    @Override
    public Widget getView() {
        return button;
    }
}
