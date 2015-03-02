package eu.ydp.empiria.player.client.module.texteditor.structure;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorBeanProvider implements Provider<TextEditorBean> {

	@Inject
	private TextEditorJAXBParserFactory jaxbFactory;
	@Inject
	@ModuleScoped
	private Provider<Element> elementProvider;

	@Override
	public TextEditorBean get() {
		Element element = elementProvider.get();
		JAXBParser<TextEditorBean> parser = jaxbFactory.create();
		return parser.parse(element.toString());
	}
}
