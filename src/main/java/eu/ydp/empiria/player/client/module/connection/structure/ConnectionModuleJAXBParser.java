package eu.ydp.empiria.player.client.module.connection.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = MatchInteractionBean.class, objects = { SimpleMatchSetBean.class, SimpleAssociableChoiceBean.class })
public interface ConnectionModuleJAXBParser extends JAXBParserFactory<MatchInteractionBean> {

}
