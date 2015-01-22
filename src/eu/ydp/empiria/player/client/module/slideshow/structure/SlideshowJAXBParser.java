package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.*;

@JAXBBindings(value = SlideshowPlayerBean.class, objects = { SlideshowBean.class, SlideBean.class, SourceBean.class, SlideshowTemplate.class })
public interface SlideshowJAXBParser extends JAXBParserFactory<SlideshowPlayerBean> {

}
