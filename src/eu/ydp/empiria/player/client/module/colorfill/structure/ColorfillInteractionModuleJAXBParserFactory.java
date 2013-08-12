package eu.ydp.empiria.player.client.module.colorfill.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value=ColorfillInteractionBean.class, objects={ButtonsContainer.class, ColorButton.class, EraserButton.class, AreaContainer.class, Area.class, Image.class, FakeAreas.class})
public interface ColorfillInteractionModuleJAXBParserFactory extends JAXBParserFactory<ColorfillInteractionBean> {

}
