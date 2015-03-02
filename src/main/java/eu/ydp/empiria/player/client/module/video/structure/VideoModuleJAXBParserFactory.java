package eu.ydp.empiria.player.client.module.video.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = VideoBean.class, objects = { SourceBean.class })
public interface VideoModuleJAXBParserFactory extends JAXBParserFactory<VideoBean> {

}
