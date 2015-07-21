package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.HTMLPanel;

public class HtmlContainerModule extends SimpleContainerModuleBase {

    protected String tag;

    public HtmlContainerModule(String tag) {
        super(new HTMLPanel(tag, ""));
        this.tag = tag;
        setContainerStyleName("qp-" + tag);
    }
}
