package eu.ydp.empiria.player.client.module.span;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class SpanModule extends SimpleModuleBase implements ISimpleModule {

    protected Widget contents;
    private final SpanStyleNameConstants styleNames;

    @Inject
    public SpanModule(SpanStyleNameConstants styleNames) {
        this.styleNames = styleNames;
    }

    @Override
    public void initModule(Element element) {
        contents = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element);
        contents.setStyleName(styleNames.QP_SPAN());
    }

    @Override
    public Widget getView() {
        return contents;
    }
}
