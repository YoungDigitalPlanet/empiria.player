package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value=SourceListBean.class, objects={SimpleSourceListItemBean.class})
public interface SourceListJAXBParser extends JAXBParserFactory<SourceListBean>{

}
