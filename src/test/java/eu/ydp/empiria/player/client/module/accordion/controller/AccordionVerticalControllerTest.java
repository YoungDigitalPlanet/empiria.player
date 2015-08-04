package eu.ydp.empiria.player.client.module.accordion.controller;

import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AccordionVerticalControllerTest {

    private AccordionVerticalController testObj = new AccordionVerticalController();

    @Test
    public void shouldHideSectionVertically() {
        // given
        AccordionSectionPresenter section = mock(AccordionSectionPresenter.class);

        // when
        testObj.hide(section);

        // then
        verify(section).hideVertically();
        verifyNoMoreInteractions(section);
    }
}