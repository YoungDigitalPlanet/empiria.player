package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value = ResponseBean.class, objects = { ResponseBean.class, CorrectResponseBean.class, ValueBean.class })
public interface ResponseNodeJAXBParserFactory extends JAXBParserFactory<ResponseBean> {

}
