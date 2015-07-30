package eu.ydp.empiria.player.client.module.accordion.controller;

import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccordionControllerTest {

    @InjectMocks
    private AccordionController<AccordionHideController> testObj;
    @Mock
    private AccordionHideController hideController;
    @Mock
    private AccordionSectionPresenter firstSection;
    @Mock
    private AccordionSectionPresenter secondSection;
    @Mock
    private EventsBus eventsBus;

    @Test
    public void shouldShowSection_onFirstClick() {
        //given
        // when
        testObj.onClick(firstSection);

        // then
        verify(firstSection).showHorizontally();
        verify(firstSection).showVertically();
    }

    @Test
    public void shouldHideSection_onSecondClick() {
        // given
        // when
        testObj.onClick(firstSection);
        testObj.onClick(firstSection);

        // then
        verify(hideController).hide(firstSection);
    }

    @Test
    public void shouldShowSection_andHidePrevious() {
        // given
        // when
        testObj.onClick(firstSection);
        testObj.onClick(secondSection);

        // then
        verify(hideController).hide(firstSection);
        verify(secondSection).showHorizontally();
        verify(secondSection).showVertically();
    }

    @Test
    public void shouldUpdateSection_onPlayerEvent() {
        // given
        PlayerEvent playerEvent = mock(PlayerEvent.class);
        testObj.onClick(firstSection);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(firstSection, times(2)).showHorizontally();
        verify(firstSection, times(2)).showVertically();
    }
}