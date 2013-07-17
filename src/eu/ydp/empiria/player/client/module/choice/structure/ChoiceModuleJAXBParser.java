package eu.ydp.empiria.player.client.module.choice.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = ChoiceInteractionBean.class, objects = { SimpleChoiceBean.class })
public interface ChoiceModuleJAXBParser extends JAXBParserFactory<ChoiceInteractionBean> {

}
