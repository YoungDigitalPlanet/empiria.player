package eu.ydp.empiria.player.client.module.accordion.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = AccordionBean.class, objects = {AccordionSectionBean.class, AccordionContentBean.class, AccordionTitleBean.class})
public interface AccordionModuleJAXBParser extends JAXBParserFactory<AccordionBean> {

}
