package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;

public class LabellingBuilder {

    @Inject
    private LabellingModuleJAXBParserFactory parserFactory;
    @Inject
    private LabellingViewBuilder builder;

    public LabellingView build(Element element, BodyGeneratorSocket bgs) {
        LabellingInteractionBean structure = findStructure(element);
        LabellingView view = builder.buildView(structure, bgs);
        return view;
    }

    private LabellingInteractionBean findStructure(Element element) {
        JAXBParser<LabellingInteractionBean> parser = parserFactory.create();
        return parser.parse(element.toString());
    }

}
