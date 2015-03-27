package eu.ydp.empiria.player.client.module.texteditor.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = TextEditorBean.class)
public interface TextEditorJAXBParserFactory extends JAXBParserFactory<TextEditorBean> {
}
