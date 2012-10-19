package eu.ydp.empiria.player.client.module.connection.structure;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import junit.framework.Assert;

import org.junit.Test;

import com.google.gwt.xml.client.Document;
import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;

public class ConnectionModuleStructureJUnitTest extends AbstractModuleStructureTestBase<ConnectionModuleStructure, MatchInteractionBean, ConnectionModuleJAXBParser> {

	protected ConnectionModuleStructure createModuleStructure() {
		return new ConnectionModuleStructureMock();
	}
	
	@Test
	public void shouldInitializeStructure() {
		String xmlString = 
				"<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"false\">" +
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
		
		MatchInteractionBean bean = createFromXML(xmlString);
		
		assertThat(bean.getId(), is(equalTo("dummy1")));
	}
    
    class ConnectionModuleStructureMock extends ConnectionModuleStructure {

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
    							
    							final JAXBContext context = JAXBContext.newInstance(binds.toArray(new Class<?>[0]));
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
    		return mock(Document.class);
    	}
    	
    }	
}
