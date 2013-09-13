package eu.ydp.empiria.player.client.module.drawing.model;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingModelProvider implements Provider<DrawingBean> {

	@Inject private DrawingModuleJAXBParserFactory jaxbFactory;
	@Inject @ModuleScoped Provider<Element> xmlProvider;
	private DrawingBean bean;

	@Override
	public DrawingBean get() {
		if (bean == null) {
			JAXBParser<DrawingBean> parser = jaxbFactory.create();
			bean = parser.parse(xmlProvider.get().toString());
		}
		return bean;
	}

}
