package eu.ydp.empiria.player.client.module.tutor;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.mediator.powerfeedback.PowerFeedbackMediator;
import eu.ydp.empiria.player.client.module.tutor.presenter.TutorPresenter;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.internal.EventHandler;
import eu.ydp.gwtutil.client.event.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorModuleTest {

    @InjectMocks
    private TutorModule tutorModule;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private TutorPresenter presenter;
    @Mock
    private TutorView view;
    @Mock
    private ActionEventGenerator eventGenerator;
    @Mock(answer = Answers.RETURNS_MOCKS)
    private PageScopeFactory eventScopeFactory;
    @Mock
    private PowerFeedbackMediator mediator;

    private TutorEventHandler tutorEventHandler;
    private final EventType<TutorEventHandler, TutorEventTypes> TUTOR_CHANGED = TutorEvent.getType(TutorEventTypes.TUTOR_CHANGED);

    private final CurrentPageScope singlePageScope = mock(CurrentPageScope.class);

    @Test
    public void init() {
        // when
        tutorModule.initModule(mock(Element.class));

        // then
        verify(presenter).init();
        verify(mediator).registerTutor(tutorModule);
    }

    @Test
    public void stateChangedEvent() {
        // given
        EndHandler endHandler = mock(EndHandler.class);

        // when
        tutorModule.processUserInteraction(endHandler);

        // then
        verify(eventGenerator).stateChanged(endHandler);
    }

    @Test
    public void shouldExecuteDefaultActionOnReset() {
        // when
        tutorModule.resetPowerFeedback();

        // then
        verify(eventGenerator).executeDefaultAction();
    }

    @Test
    public void pageUnloadedEvent() {
        // when
        tutorModule.terminatePowerFeedback();

        // then
        verify(eventGenerator).stop();
    }

    @Test
    public void testPageLoadedEvent() {
        // when
        tutorModule.initPowerFeedbackClient();

        // then
        verify(eventGenerator).start();
    }

    @Test
    public void testTutorChangedEvent() {
        // given
        initEventHandlersInterception();
        mockScopeFactoryToReturnSinglePageScope();
        tutorModule.initModule(mock(Element.class));
        TutorEvent tutorEvent = mock(TutorEvent.class);

        // when
        tutorEventHandler.onTutorChanged(tutorEvent);

        // then
        verify(eventGenerator).tutorChanged(any(Integer.class));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initEventHandlersInterception() {
        doAnswer(new Answer<Void>() {

            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                EventType type = (EventType) invocation.getArguments()[0];
                Object handler = invocation.getArguments()[1];

                if (handler instanceof TutorEventHandler && type == TUTOR_CHANGED) {
                    tutorEventHandler = (TutorEventHandler) handler;
                }

                return null;
            }
        }).when(eventsBus).addHandler(any(EventType.class), any(EventHandler.class), any(EventScope.class));
    }

    private void mockScopeFactoryToReturnSinglePageScope() {
        when(eventScopeFactory.getCurrentPageScope()).thenReturn(singlePageScope);
    }
}
