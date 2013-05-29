package eu.ydp.empiria.player.client.module.colorfill;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

public class ColorfillBuilder {

	@Inject
	private ColorfillInteractionModuleJAXBParserFactory parserFactory;

	@Inject
	private ColorfillViewBuilder builder;

	public ColorfillInteractionView build(Element element, BodyGeneratorSocket bodyGeneratorSocket) {
		ColorfillInteractionBean structure = findStructure(element);
		ColorfillInteractionView view = builder.buildView(structure, bodyGeneratorSocket);
		return view;
	}

	private ColorfillInteractionBean findStructure(Element element) {
		JAXBParser<ColorfillInteractionBean> parser = parserFactory.create();
		return parser.parse(element.toString());
	}

}
