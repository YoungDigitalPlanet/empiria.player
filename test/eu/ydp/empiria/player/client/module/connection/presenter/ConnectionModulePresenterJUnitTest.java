package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.gwtutil.xml.XMLParser;

public class ConnectionModulePresenterJUnitTest  extends AbstractJAXBTestBase<MatchInteractionBean> {

	private ConnectionModulePresenterImpl connectionModulePresenter;
	private MultiplePairModuleView<SimpleAssociableChoiceBean> moduleView;
	private ConnectionModuleModel connectionModuleModel;
		
	@Test
	public void shouldValidateConnectionRequestAsInvalid() {
		String source = "CONNECTION_RESPONSE_1_0";
		String target = "CONNECTION_RESPONSE_1_3";
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, source, target);
		
		connectionModulePresenter.onConnectionEvent(event);
		
		Mockito.verify(moduleView, Mockito.times(1)).disconnect(source, target);
	}

	@Test
	public void shouldValidateConnectionRequestAsValid() {
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_4");
		
		connectionModulePresenter.onConnectionEvent(event);
		
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event.getItemsPair());
	}
	
	@Test
	public void shouldDisconnectPairOnDisconnectedEvent() {
		PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.DISCONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_4");
		
		connectionModulePresenter.onConnectionEvent(event);
		
		Mockito.verify(connectionModuleModel, Mockito.times(1)).removeAnswer(event.getItemsPair());
	}
	
	@Test
	public void markCorrectAnswers() {
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1"));
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1"));
		connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4"));
		doReturn(Arrays.asList(true, false, true)).when(connectionModuleModel).evaluateResponse();
		
		connectionModulePresenter.markCorrectAnswers();
		connectionModulePresenter.markWrongAnswers();
		
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1", MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4", MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1", MultiplePairModuleConnectType.WRONG);
	}
	
	@Test
	public void shouldDetectMaximumOverallAssociationsNumberAchieved() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));
		
		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4");		
		connectionModulePresenter.onConnectionEvent(event3);
		
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event1.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event2.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(event3.getItemsPair());
	}

	@Test
	public void shouldIgnoreOverallAssociationsNumberLimits() {
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, null)));
		
		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4");		
		connectionModulePresenter.onConnectionEvent(event3);
		PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_4");		
		connectionModulePresenter.onConnectionEvent(event4);		
		
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event1.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event2.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event3.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event4.getItemsPair());
	}	
	
	@Test
	public void shouldDetectChoiceMaxMatchLimits() {		
		Map<String, Integer> matchMaxMap = new HashMap<String, Integer>();
		matchMaxMap.put("CONNECTION_RESPONSE_1_0", 1);
		matchMaxMap.put("CONNECTION_RESPONSE_1_1", 1);
		matchMaxMap.put("CONNECTION_RESPONSE_1_4", 2);
		matchMaxMap.put("CONNECTION_RESPONSE_1_3", 2);		
		connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, matchMaxMap)));
		
		PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_4");		
		connectionModulePresenter.onConnectionEvent(event1);
		PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event2);
		PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4");		
		connectionModulePresenter.onConnectionEvent(event3);
		PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, "CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1");		
		connectionModulePresenter.onConnectionEvent(event4);		
		
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event1.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event2.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.times(1)).addAnswer(event3.getItemsPair());
		Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(event4.getItemsPair());
	}	
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		connectionModulePresenter = spy(new ConnectionModulePresenterImpl());		
		connectionModuleModel = spy(new ConnectionModuleModel(new Response(mockResponseElement()), mock(ResponseModelChangeListener.class)));		
		connectionModulePresenter.setModel(connectionModuleModel);
		MatchInteractionBean bean = createBeanFromXMLString(mockStructure(null, null));
		connectionModulePresenter.setBean(bean);		
		moduleView = mock(MultiplePairModuleView.class);
		connectionModulePresenter.setModuleView(moduleView);			
	}	
	
	private Element mockResponseElement() {
		StringBuilder sb = new StringBuilder();
		sb.append("<responseDeclaration baseType=\"directedPair\" cardinality=\"multiple\" evaluate=\"user\" identifier=\"CONNECTION_RESPONSE_1\">");
		sb.append("		<correctResponse>");
		sb.append("			<value>CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		sb.append("			<value>CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4</value>");
		sb.append("		</correctResponse>");
		sb.append("</responseDeclaration>");
		return XMLParser.parse(sb.toString()).getDocumentElement();
	}
	
	private String mockStructure(Integer maxAssociations, Map<String, Integer> matchMaxMap) {
		String maxAssociationsXMLAttr = (maxAssociations!=null) ? " maxAssociations=\"" + maxAssociations + "\"" : "";
		return "<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"true\"" + maxAssociationsXMLAttr +">" +
				"<simpleMatchSet>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_0\" matchMax=\"" + getMatchMax("CONNECTION_RESPONSE_1_0", matchMaxMap) + "\">" +
						"a	</simpleAssociableChoice>" +
					"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_3\" matchMax=\"" + getMatchMax("CONNECTION_RESPONSE_1_3", matchMaxMap) + "\">" +
						"b	</simpleAssociableChoice>" +
					"</simpleMatchSet>" +
					"<simpleMatchSet>" +
						"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_4\" matchMax=\"" + getMatchMax("CONNECTION_RESPONSE_1_4", matchMaxMap) + "\">" +
							"c	</simpleAssociableChoice>" +
						"<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_1\" matchMax=\"" + getMatchMax("CONNECTION_RESPONSE_1_1", matchMaxMap) + "\">" +
							"d	</simpleAssociableChoice>" +
						"</simpleMatchSet>" +
				"</matchInteraction>";			
	}
	
	private int getMatchMax(String identifier, Map<String, Integer> matchMaxMap) {
		final int defaultMatchMax = 2;
		Integer value = null;
		if (matchMaxMap != null) {
			value = matchMaxMap.get(identifier);
		}
		return (value != null) ? value : defaultMatchMax;
	}
	
}
