package eu.ydp.empiria.player.client.module.selection.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = SelectionInteractionBean.class, objects = { SelectionSimpleChoiceBean.class, SelectionItemBean.class })
public interface SelectionModuleJAXBParser extends JAXBParserFactory<SelectionInteractionBean> {

}
