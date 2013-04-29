package eu.ydp.empiria.player.client.module.labelling.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value=LabellingInteractionBean.class, objects={ImgBean.class, ChildrenBean.class, ChildBean.class})
public interface LabellingModuleJAXBParserFactory extends JAXBParserFactory<LabellingInteractionBean> {

}
