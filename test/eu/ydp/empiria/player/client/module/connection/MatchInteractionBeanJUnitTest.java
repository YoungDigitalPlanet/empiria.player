package eu.ydp.empiria.player.client.module.connection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleJAXBParser;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;

public class MatchInteractionBeanJUnitTest {

	ConnectionModuleJAXBParser parser;
		
	@Test
	public void shouldReturnMartchInteraction(){
		MatchInteractionBean matchInteractionBean = parser.create().parse(
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
				"</matchInteraction>");

		assertThat(matchInteractionBean.getId(), is(equalTo("dummy1")));
		assertThat(matchInteractionBean.getResponseIdentifier(), is(equalTo("CONNECTION_RESPONSE_1")));
		
		assertThat(matchInteractionBean.getFirstChoicesSet(), notNullValue());
		assertThat(matchInteractionBean.getSecondChoicesSet(), notNullValue());
		
		assertThat(matchInteractionBean.getFirstChoicesSet().get(0).getIdentifier(), is(equalTo("CONNECTION_RESPONSE_1_0")));
		assertThat(matchInteractionBean.getFirstChoicesSet().get(0).isFixed(), is(equalTo(false)));
	}
	
	@Before
	public void setup() {
		parser = new ConnectionModuleJAXBParser() {
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
							throw new RuntimeException(e);
						}
					}
				};
			}
		};
	}
	
}
