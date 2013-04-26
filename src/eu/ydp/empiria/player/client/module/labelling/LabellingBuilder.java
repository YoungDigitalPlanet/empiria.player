package eu.ydp.empiria.player.client.module.labelling;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingModuleJAXBParser;

public class LabellingBuilder {
	
	@Inject LabellingModuleJAXBParser parserFactory;

	public void buildAndAttach(HasWidgets container, Element element, BodyGeneratorSocket bgs) {
		LabellingInteractionBean structure = findStructure(element);
	}
	
	private LabellingInteractionBean findStructure(Element element){
		JAXBParser<LabellingInteractionBean> parser = parserFactory.create();
		return parser.parse(element.toString());		
	}

}
