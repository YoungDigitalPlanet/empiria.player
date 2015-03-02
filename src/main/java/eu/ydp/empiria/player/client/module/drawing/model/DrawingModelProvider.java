package eu.ydp.empiria.player.client.module.drawing.model;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingModelProvider implements Provider<DrawingBean> {

	@Inject
	private DrawingModuleJAXBParserFactory jaxbFactory;
	@Inject
	@ModuleScoped
	Provider<Element> elementProvider;

	@Override
	public DrawingBean get() {
		Element element = elementProvider.get();
		JAXBParser<DrawingBean> parser = jaxbFactory.create();
		return parser.parse(element.toString());
	}

}
