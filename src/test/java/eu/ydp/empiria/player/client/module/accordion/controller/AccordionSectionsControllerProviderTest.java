package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccordionSectionsControllerProviderTest {

    @InjectMocks
    private AccordionSectionsControllerProvider testObj;
    @Mock
    private Provider<AccordionSectionsBothDirectionsController> bothDirectionsController;
    @Mock
    private AccordionSectionsBothDirectionsController bothDirections;
    @Mock
    private Provider<AccordionSectionsHorizontalController> horizontalController;
    @Mock
    private AccordionSectionsHorizontalController horizontal;
    @Mock
    private Provider<AccordionSectionsVerticalController> verticalController;
    @Mock
    private AccordionSectionsVerticalController vertical;

    @Before
    public void init(){
        when(bothDirectionsController.get()).thenReturn(bothDirections);
        when(horizontalController.get()).thenReturn(horizontal);
        when(verticalController.get()).thenReturn(vertical);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsAll() {
        // given
        Transition transition = Transition.ALL;

        // when
        AccordionSectionsController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(bothDirections);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsWidth() {
        // given
        Transition transition = Transition.WIDTH;

        // when
        AccordionSectionsController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(horizontal);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsHeight() {
        // given
        Transition transition = Transition.HEIGHT;

        // when
        AccordionSectionsController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(vertical);
    }
}