package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.gwt.dom.client.Style.Display;

public class SafariMobileVisibilityChanger implements VisibilityChanger {

    private int prevScroll;

    @Override
    public void show(VisibilityClient client) {
        client.getElementStyle().setDisplay(Display.BLOCK);
        client.setScrollTop(prevScroll);
    }

    @Override
    public void hide(VisibilityClient client) {
        prevScroll = client.getScrollTop();
        client.getElementStyle().setDisplay(Display.NONE);
    }
}
