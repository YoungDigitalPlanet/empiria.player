package eu.ydp.empiria.player.client.module.ordering.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = OrderInteractionBean.class, objects = {SimpleOrderChoiceBean.class})
public interface OrderInteractionModuleJAXBParserFactory extends JAXBParserFactory<OrderInteractionBean> {

}
