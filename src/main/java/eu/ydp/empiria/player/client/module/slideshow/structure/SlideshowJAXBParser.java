package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = SlideshowPlayerBean.class, objects = {SlideshowBean.class, SlideBean.class, SourceBean.class, SlideshowTemplate.class,
        SlideshowPagerBean.class, SlideTitleBean.class, SlideshowTitleBean.class, SlideNarrationBean.class, SoundBean.class})
public interface SlideshowJAXBParser extends JAXBParserFactory<SlideshowPlayerBean> {

}
