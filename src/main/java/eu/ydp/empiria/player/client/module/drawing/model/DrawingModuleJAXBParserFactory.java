package eu.ydp.empiria.player.client.module.drawing.model;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = DrawingBean.class, objects = {ImageBean.class, PaletteBean.class, ColorBean.class})
public interface DrawingModuleJAXBParserFactory extends JAXBParserFactory<DrawingBean> {

}
