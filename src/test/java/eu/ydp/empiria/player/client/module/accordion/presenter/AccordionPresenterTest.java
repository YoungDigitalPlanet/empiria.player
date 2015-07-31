package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionController;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionSectionsControllerProvider;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionBean;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;
import eu.ydp.empiria.player.client.module.accordion.view.AccordionView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class AccordionPresenterTest {

    @InjectMocks
    private AccordionPresenter testObj;
    @Mock
    private AccordionView view;
    @Mock
    private AccordionSectionFactory sectionFactory;
    @Mock
    private AccordionSectionPresenter section;
    @Mock
    private AccordionSectionsControllerProvider controllerProvider;
    @Mock
    private AccordionController accordionController;
    @Mock
    private AccordionContentGenerator generator;
    @Mock
    private Widget viewWidget;
    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    private AccordionBean bean = new AccordionBean();
    private AccordionSectionBean sectionBean = new AccordionSectionBean();
    private Transition transition = Transition.ALL;

    @Before
    public void init() {
        when(controllerProvider.getController(transition)).thenReturn(accordionController);
        when(sectionFactory.createSection(sectionBean, generator)).thenReturn(section);
        when(view.asWidget()).thenReturn(viewWidget);

        List<AccordionSectionBean> sections = new ArrayList<>();
        bean.setTransition(transition);
        bean.setSections(sections);
        sections.add(sectionBean);
    }

    @Test
    public void shouldInitializeSection() {
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);

        // when
        testObj.initialize(bean, generator);

        // then
        verify(section).init(transition);
        verify(section).addClickCommand(commandCaptor.capture());
        verify(view).addSection(section);

        Command clickCommand = commandCaptor.getValue();
        clickCommand.execute(nativeEvent);
        verify(accordionController).onClick(section);
    }
}