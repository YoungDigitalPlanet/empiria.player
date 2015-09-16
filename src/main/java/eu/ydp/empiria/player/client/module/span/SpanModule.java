package eu.ydp.empiria.player.client.module.span;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.core.base.ISimpleModule;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.TextStyleNameConstants;

public class SpanModule extends SimpleModuleBase implements ISimpleModule {

    protected Widget contents;
    private final TextStyleNameConstants styleNames;

    @Inject
    public SpanModule(TextStyleNameConstants styleNames) {
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
