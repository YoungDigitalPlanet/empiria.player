package eu.ydp.empiria.player.client.module.connection.structure;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import com.google.gwt.xml.client.Document;
import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.gwtutil.xml.XMLParser;

public class ConnectionModuleStructureMock extends ConnectionModuleStructure {

	public final static String CONNECTION_XML =
			"<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"true\">" +
			"<simpleMatchSet>" +
				"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_0\" matchMax=\"2\">" +
					"a	</simpleAssociableChoice>" +
				"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_3\" matchMax=\"2\">" +
					"b	</simpleAssociableChoice>" +
				"</simpleMatchSet>" +
				"<simpleMatchSet>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_4\" matchMax=\"2\">" +
						"c	</simpleAssociableChoice>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_1\" matchMax=\"2\">" +
						"d	</simpleAssociableChoice>" +
					"</simpleMatchSet>" +
			"</matchInteraction>";

	@Override
	public ConnectionModuleJAXBParser getParserFactory() {
		return mockParserFactory();
	}

	private ConnectionModuleJAXBParser mockParserFactory() {
		return new ConnectionModuleJAXBParser() {
			@Override
			public JAXBParser<MatchInteractionBean> create() {
				return new JAXBParser<MatchInteractionBean>() {
					@Override
					public MatchInteractionBean parse(String xml) {
						try {
							JAXBBindings bindings = ConnectionModuleJAXBParser.class.getAnnotation(JAXBBindings.class);
							ArrayList<Class<?>> binds = new ArrayList<Class<?>>();
							binds.add(bindings.value());

							for(Class<?> c: bindings.objects()) {
								binds.add(c);
							}

							final JAXBContext context = JAXBContext.newInstance(binds.toArray(new Class<?>[0])); // NOPMD
							return (MatchInteractionBean) context.createUnmarshaller().unmarshal(new StringReader(xml));
						} catch (JAXBException e) {
							Assert.fail(e.getMessage());
							return null;
						}
					}
				};
			}
		};
	}

	@Override
	protected Document parseXML(String xml) {
		return XMLParser.parse(xml);
	}

}
