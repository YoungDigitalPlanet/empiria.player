package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessorTemplate;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.xml.XMLParser;

public class ConnectionModulePresenterJUnitTest  extends AbstractJAXBTestBase<MatchInteractionBean> {

	private ConnectionModulePresenterImpl connectionModulePresenter;
	private MultiplePairModuleView moduleView;
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
		doReturn(Arrays.asList(true, false, true)).when(connectionModulePresenter).evaluateResponse();
		
		connectionModulePresenter.markCorrectAnswers();
		
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_0", "CONNECTION_RESPONSE_1_1", MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_4", MultiplePairModuleConnectType.CORRECT);
		Mockito.verify(moduleView, Mockito.times(1)).connect("CONNECTION_RESPONSE_1_3", "CONNECTION_RESPONSE_1_1", MultiplePairModuleConnectType.WRONG);
	}
	
	@Before
	public void init() {
		connectionModulePresenter = spy(new ConnectionModulePresenterImpl());		
		connectionModuleModel = spy(new ConnectionModuleModel(new Response(mockResponseElement()), mock(ResponseModelChangeListener.class)));		
		connectionModulePresenter.setModel(connectionModuleModel);
		MatchInteractionBean bean = createBeanFromXMLString(mockStructure());
		connectionModulePresenter.setBean(bean);
		moduleView = mock(MultiplePairModuleView.class);
		connectionModulePresenter.setModuleView(moduleView);			
	}	

	VariableProcessor mockVariableProcessor() {
		XmlData xmlData = mock(XmlData.class);
		VariableProcessor variableProcessor = VariableProcessorTemplate.fromNode(xmlData.getDocument().getElementsByTagName("variableProcessing"));
		return variableProcessor;
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
	
	private String mockStructure() {
		return "<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"true\">" +
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
	}
		
	//	class ConnectionModulePresenterMock extends ConnectionModulePresenterImpl {		
	//		@Override
	//		List<Boolean> evaluateResponse() {
	//			VariableProcessor variableProcessor = mockVariableProcessor();
	//			return variableProcessor.evaluateAnswer(model.getResponse());
	//		}
	//	}
}
