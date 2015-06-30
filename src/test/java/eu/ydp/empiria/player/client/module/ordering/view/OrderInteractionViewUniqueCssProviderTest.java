package eu.ydp.empiria.player.client.module.ordering.view;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionViewUniqueCssProviderTest {

    @InjectMocks
    private OrderInteractionViewUniqueCssProvider testObj;

    @Mock
    private StyleNameConstants styleNameConstants;

    @Test
    public void getNextTest() {
        // given
        when(styleNameConstants.QP_ORDERED_UNIQUE()).thenReturn("qp-ordered-unique");
        // when
        String result1 = testObj.getNext();
        String result2 = testObj.getNext();
        String result3 = testObj.getNext();

        // then
        assertEquals("qp-ordered-unique-1", result1);
        assertEquals("qp-ordered-unique-2", result2);
        assertEquals("qp-ordered-unique-3", result3);

    }

}
