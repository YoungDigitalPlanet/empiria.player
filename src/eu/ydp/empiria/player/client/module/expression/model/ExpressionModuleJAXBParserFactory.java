package eu.ydp.empiria.player.client.module.expression.model;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

@JAXBBindings(value=ExpressionsBean.class, objects={ExpressionBean.class})
public interface ExpressionModuleJAXBParserFactory extends JAXBParserFactory<ExpressionsBean> {

}
