package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.peterfranza.gwt.jaxb.client.parser.*;

@JAXBBindings(value = PicturePlayerBean.class, objects = { PicturePlayerTitleBean.class })
public interface PicturePlayerJAXBParser extends JAXBParserFactory<PicturePlayerBean> {
}
