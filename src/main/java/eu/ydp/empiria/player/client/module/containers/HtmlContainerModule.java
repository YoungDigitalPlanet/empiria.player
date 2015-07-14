package eu.ydp.empiria.player.client.module.containers;

import com.google.gwt.user.client.ui.HTMLPanel;

public class HtmlContainerModule extends SimpleContainerModuleBase<HtmlContainerModule> {

    protected String tag;

    public HtmlContainerModule(String tag) {
        super(new HTMLPanel(tag, ""));
        this.tag = tag;
        setContainerStyleName("qp-" + tag);
    }

    @Override
    public HtmlContainerModule getNewInstance() {
        return new HtmlContainerModule(tag);
    }

}
