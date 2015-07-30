package eu.ydp.empiria.player.client.module.accordion.controller;

import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionSectionPresenter;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AccordionBothDirectionsControllerTest {

    private AccordionBothDirectionsController testObj = new AccordionBothDirectionsController();

    @Test
    public void shouldHideSectionHorizontally() {
        // given
        AccordionSectionPresenter section = mock(AccordionSectionPresenter.class);

        // when
        testObj.hide(section);

        // then
        verify(section).hideHorizontally();
        verify(section).hideVertically();
    }
}