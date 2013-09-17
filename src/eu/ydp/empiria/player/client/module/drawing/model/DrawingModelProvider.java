package eu.ydp.empiria.player.client.module.drawing.model;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DrawingModelProvider implements Provider<DrawingBean> {

	@Inject private DrawingModuleJAXBParserFactory jaxbFactory;
	@Inject @ModuleScoped Provider<Element> elementProvider;
	private final Map<Element, DrawingBean> cache = Maps.newHashMap();

	@Override
	public DrawingBean get() {
		Element element = elementProvider.get();
		if (!cache.containsKey(element)) {
			JAXBParser<DrawingBean> parser = jaxbFactory.create();
			DrawingBean bean = parser.parse(element.toString());
			cache.put(element, bean);
		}
		return cache.get(element);
	}

}
