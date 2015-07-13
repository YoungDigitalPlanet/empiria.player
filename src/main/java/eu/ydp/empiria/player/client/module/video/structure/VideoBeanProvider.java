package eu.ydp.empiria.player.client.module.video.structure;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoBeanProvider implements Provider<VideoBean> {

    @Inject
    private VideoModuleJAXBParserFactory jaxbFactory;
    @Inject
    @ModuleScoped
    private Provider<Element> elementProvider;

    @Override
    public VideoBean get() {
        Element element = elementProvider.get();
        JAXBParser<VideoBean> parser = jaxbFactory.create();
        return parser.parse(element.toString());
    }
}
