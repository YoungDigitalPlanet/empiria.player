package eu.ydp.empiria.player.client.module.accordion.controller;

import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AccordionHorizontalControllerTest {

    private AccordionHorizontalController testObj = new AccordionHorizontalController();


    @Test
    public void shouldHideSectionHorizontally() {
        // given
        AccordionSectionPresenter section = mock(AccordionSectionPresenter.class);

        // when
        testObj.hide(section);

        // then
        verify(section).hideHorizontally();
        verifyNoMoreInteractions(section);
    }
}