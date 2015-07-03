package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.AbstractActivityContainerModuleBase;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;

public class LabellingModule extends AbstractActivityContainerModuleBase {

    @Inject
    LabellingBuilder builder;

    private Widget view;

    @Override
    public void initModule(Element element, ModuleSocket ms, BodyGeneratorSocket bgs) {
        super.initModule(element, ms, bgs);
        view = createView(element, bgs);
    }

    private SimplePanel createView(Element element, BodyGeneratorSocket bgs) {
        SimplePanel container = new SimplePanel();
        LabellingView view = builder.build(element, bgs);
        container.add(view.getView());
        return container;
    }

    @Override
    public Widget getView() {
        return view;
    }

}
