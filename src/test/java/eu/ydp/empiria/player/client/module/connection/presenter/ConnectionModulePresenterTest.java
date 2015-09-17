package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.core.answer.ShowAnswersType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ConnectionModulePresenterTest extends AbstractJAXBTestBase<MatchInteractionBean> {

    private static final String CONNECTION_RESPONSE_1_4 = "CONNECTION_RESPONSE_1_4";
    private static final String CONNECTION_RESPONSE_1_1 = "CONNECTION_RESPONSE_1_1";
    private static final String CONNECTION_RESPONSE_1_3 = "CONNECTION_RESPONSE_1_3";
    private static final String CONNECTION_RESPONSE_1_0 = "CONNECTION_RESPONSE_1_0";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private static final Integer DEFAULT_MATCH_MAX = 2;

    private ConnectionModulePresenterImpl connectionModulePresenter;
    private MultiplePairModuleView<SimpleAssociableChoiceBean> moduleView;
    private ConnectionModuleModel connectionModuleModel;

    @Test
    public void shouldValidateConnectionRequestAsInvalid_whenRequestIsInvalid() {
        // given
        String source = CONNECTION_RESPONSE_1_0;
        String target = CONNECTION_RESPONSE_1_3;
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, source, target, true);
        //when
        connectionModulePresenter.onConnectionEvent(event);
        //then
        Mockito.verify(moduleView).disconnect(source, target);
    }

    @Test
    public void shouldResetView_andSetUserAnswers_whenIsAttachedOnRepaintEvent() {
        //given
        String source = CONNECTION_RESPONSE_1_0;
        String target = CONNECTION_RESPONSE_1_3;
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.REPAINT_VIEW, source, target, true);
        //when
        when(moduleView.isAttached()).thenReturn(true);

        connectionModulePresenter.onConnectionEvent(event);
        //then
        verify(connectionModulePresenter).reset();
        verify(connectionModulePresenter).showAnswers(Matchers.eq(ShowAnswersType.USER));
    }

    @Test
    public void shouldResetView_andSetCorrectAnswers_whenCorrectAnswersAreShownOnRepaintEvent() {
        //given
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.REPAINT_VIEW);
        //when
        when(moduleView.isAttached()).thenReturn(true);

        connectionModulePresenter.showAnswers(ShowAnswersType.CORRECT);
        connectionModulePresenter.onConnectionEvent(event);
        //then
        verify(connectionModulePresenter).reset();
        verify(connectionModulePresenter, times(2)).showAnswers(Matchers.eq(ShowAnswersType.CORRECT));
    }

    @Test
    public void shouldDoNothing_whenIsDeAttachedOnRepaintEvent() {
        // given
        String source = CONNECTION_RESPONSE_1_0;
        String target = CONNECTION_RESPONSE_1_3;
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.REPAINT_VIEW, source, target, true);
        // when
        when(moduleView.isAttached()).thenReturn(false);

        connectionModulePresenter.onConnectionEvent(event);
        // then
        verify(connectionModulePresenter, times(0)).reset();
        verify(connectionModulePresenter, times(0)).showAnswers(ShowAnswersType.USER);
    }

    @Test
    public void shouldValidateConnectionRequestAsValid_whenRequestIsValid() {
        //given
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
        //when
        connectionModulePresenter.onConnectionEvent(event);
        //then
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
    }

    @Test
    public void shouldDisconnectPair_whenOnDisconnectedEvent() {
        //given
        PairConnectEvent event = new PairConnectEvent(PairConnectEventTypes.DISCONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
        //when
        connectionModulePresenter.onConnectionEvent(event);
        //then
        Mockito.verify(connectionModuleModel).removeAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
    }

    @Test
    public void shouldReversedConnectionsOrderBeProcessedCorrectly() {
        //given
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));
        PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_0, true);
        PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
        //when
        connectionModulePresenter.onConnectionEvent(event1);
        connectionModulePresenter.onConnectionEvent(event2);
        //then
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
    }

    @Test
    public void shouldPreventInvalidConnection() {
        //given
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));
        PairConnectEvent bothLeftNodesEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_0, true);
        PairConnectEvent correctEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
        //when
        connectionModulePresenter.onConnectionEvent(bothLeftNodesEvent);
        connectionModulePresenter.onConnectionEvent(correctEvent);
        //then
        Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_3));
        Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_0));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
    }

    @Test
    public void shouldPreventDoubleCombination() {
        //given
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));
        PairConnectEvent firstEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
        PairConnectEvent reConnectEvent = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent reConnectEvent2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_1, CONNECTION_RESPONSE_1_3, true);
        //when
        connectionModulePresenter.onConnectionEvent(firstEvent);
        connectionModulePresenter.onConnectionEvent(reConnectEvent);
        connectionModulePresenter.onConnectionEvent(reConnectEvent2);
        //then
        Mockito.verify(moduleView).disconnect(reConnectEvent.getSourceItem(), reConnectEvent.getTargetItem());
        Mockito.verify(moduleView).disconnect(reConnectEvent2.getSourceItem(), reConnectEvent2.getTargetItem());
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
    }

    @Test
    public void shouldMarkCorrectAnswers() {
        //given
        connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1,
                true));
        connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1,
                true));
        connectionModulePresenter.onConnectionEvent(new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4,
                true));
        doReturn(Arrays.asList(true, false, true)).when(connectionModuleModel).evaluateResponse();
        //when
        connectionModulePresenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
        connectionModulePresenter.markAnswers(MarkAnswersType.WRONG, MarkAnswersMode.MARK);
        //then
        Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, MultiplePairModuleConnectType.CORRECT);
        Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, MultiplePairModuleConnectType.CORRECT);
        Mockito.verify(moduleView).connect(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, MultiplePairModuleConnectType.WRONG);
    }

    @Test
    public void shouldDetectMaximumOverallAssociationsNumber_whenItIsAchieved() {
        //given
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(2, null)));
        PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
        //when
        connectionModulePresenter.onConnectionEvent(event1);
        connectionModulePresenter.onConnectionEvent(event2);
        connectionModulePresenter.onConnectionEvent(event3);
        //then
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
    }

    @Test
    public void shouldIgnoreOverallAssociationsNumberLimits() {
        //given
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, null)));
        PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
        PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
        //when
        connectionModulePresenter.onConnectionEvent(event1);
        connectionModulePresenter.onConnectionEvent(event2);
        connectionModulePresenter.onConnectionEvent(event3);
        connectionModulePresenter.onConnectionEvent(event4);
        //then
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
    }

    @Test
    public void shouldDetectChoiceMaxMatchLimits() {
        //given
        Map<String, Integer> matchMaxMap = new HashMap<String, Integer>();
        matchMaxMap.put(CONNECTION_RESPONSE_1_0, 1);
        matchMaxMap.put(CONNECTION_RESPONSE_1_1, 1);
        matchMaxMap.put(CONNECTION_RESPONSE_1_4, 2);
        matchMaxMap.put(CONNECTION_RESPONSE_1_3, 2);
        connectionModulePresenter.setBean(createBeanFromXMLString(mockStructure(null, matchMaxMap)));
        PairConnectEvent event1 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4, true);
        PairConnectEvent event2 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1, true);
        PairConnectEvent event3 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4, true);
        PairConnectEvent event4 = new PairConnectEvent(PairConnectEventTypes.CONNECTED, CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1, true);
        //when
        connectionModulePresenter.onConnectionEvent(event1);
        connectionModulePresenter.onConnectionEvent(event2);
        connectionModulePresenter.onConnectionEvent(event3);
        connectionModulePresenter.onConnectionEvent(event4);
        //then
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_4));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_1));
        Mockito.verify(connectionModuleModel).addAnswer(concatNodes(CONNECTION_RESPONSE_1_3, CONNECTION_RESPONSE_1_4));
        Mockito.verify(connectionModuleModel, Mockito.never()).addAnswer(concatNodes(CONNECTION_RESPONSE_1_0, CONNECTION_RESPONSE_1_1));
    }

    @SuppressWarnings("unchecked")
    @Before
    public void init() {
        connectionModulePresenter = spy(new ConnectionModulePresenterImpl());
        connectionModuleModel = spy(new ConnectionModuleModel(createResponseObject(), mock(ResponseModelChangeListener.class)));
        connectionModulePresenter.setModel(connectionModuleModel);
        MatchInteractionBean bean = createBeanFromXMLString(mockStructure(null, null));
        connectionModulePresenter.setBean(bean);
        moduleView = mock(MultiplePairModuleView.class);
        connectionModulePresenter.setModuleView(moduleView);
    }

    /*
     * Method below private Response createResponseObject() is creating response object with adequate as created from given xml:
     *
     * <responseDeclaration cardinality="multiple" evaluate="user" identifier="CONNECTION_RESPONSE_1"> <correctResponse>" <value>CONNECTION_RESPONSE_1_0 + " " +
     * CONNECTION_RESPONSE_1_1"</value> <value>CONNECTION_RESPONSE_1_3 + " " + CONNECTION_RESPONSE_1_4</value> </correctResponse> </responseDeclaration>
     */
    private Response createResponseObject() {
        CorrectAnswers correctAnswers = new CorrectAnswers();
        ResponseValue firstCorrectAnswer = new ResponseValue(CONNECTION_RESPONSE_1_0 + " " + CONNECTION_RESPONSE_1_1);
        ResponseValue secondCorrectAnswer = new ResponseValue(CONNECTION_RESPONSE_1_3 + " " + CONNECTION_RESPONSE_1_4);
        correctAnswers.add(firstCorrectAnswer);
        correctAnswers.add(secondCorrectAnswer);

        List<String> values = Lists.newArrayList();
        List<String> groups = Lists.newArrayList();
        String identifier = "CONNECTION_RESPONSE_1";
        Evaluate evaluate = Evaluate.USER;
        Cardinality cardinality = Cardinality.MULTIPLE;
        Response response = new ResponseBuilder().withCorrectAnswers(correctAnswers).withValues(values).withGroups(groups).withIdentifier(identifier)
                .withEvaluate(evaluate).withCardinality(cardinality).build();
        return response;
    }

    private String mockStructure(Integer maxAssociations, Map<String, Integer> matchMaxMap) {
        String maxAssociationsXMLAttr = "";
        if (maxAssociations != null) {
            maxAssociationsXMLAttr = " maxAssociations=\"" + maxAssociations + "\"";
        }
        return "<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"" + TRUE + "\"" + maxAssociationsXMLAttr + ">"
                + "<simpleMatchSet>" + "<simpleAssociableChoice fixed=\"" + FALSE
                + "\" identifier=\""
                + CONNECTION_RESPONSE_1_0
                + "\" matchMax=\""
                + getMatchMax(CONNECTION_RESPONSE_1_0, matchMaxMap)
                + "\">"
                + // NOPMD
                "a	</simpleAssociableChoice>" + "<simpleAssociableChoice fixed=\"" + FALSE + "\" identifier=\"" + CONNECTION_RESPONSE_1_3 + "\" matchMax=\""
                + getMatchMax(CONNECTION_RESPONSE_1_3, matchMaxMap) + "\">" + "b	</simpleAssociableChoice>" + "</simpleMatchSet>" + "<simpleMatchSet>"
                + "<simpleAssociableChoice fixed=\"" + FALSE + "\" identifier=\"" + CONNECTION_RESPONSE_1_4 + "\" matchMax=\""
                + getMatchMax(CONNECTION_RESPONSE_1_4, matchMaxMap) + "\">" + "c	</simpleAssociableChoice>" + "<simpleAssociableChoice fixed=\"" + FALSE
                + "\" identifier=\"" + CONNECTION_RESPONSE_1_1 + "\" matchMax=\"" + getMatchMax(CONNECTION_RESPONSE_1_1, matchMaxMap) + "\">"
                + "d	</simpleAssociableChoice>" + "</simpleMatchSet>" + "</matchInteraction>";
    }

    private int getMatchMax(String identifier, Map<String, Integer> matchMaxMap) {
        Integer value = null;
        if (matchMaxMap != null) {
            value = matchMaxMap.get(identifier);
        }
        if (value == null) {
            value = DEFAULT_MATCH_MAX;
        }
        return value;
    }

    private String concatNodes(String source, String target) {
        return source + " " + target;
    }

}
