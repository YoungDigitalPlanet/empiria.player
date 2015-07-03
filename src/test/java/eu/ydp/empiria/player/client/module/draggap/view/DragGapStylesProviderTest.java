package eu.ydp.empiria.player.client.module.draggap.view;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DragGapStylesProviderTest {

    private DragGapStylesProvider dragGapStylesProvider;
    private StyleNameConstants styleNameConstants;

    @Before
    public void setUp() throws Exception {
        styleNameConstants = Mockito.mock(StyleNameConstants.class);
        dragGapStylesProvider = new DragGapStylesProvider(styleNameConstants);
    }

    @Test
    public void shouldSetCorrectStyleWhenAnswerTypeIsCorrect() throws Exception {
        String expectedStyleName = "correct";
        when(styleNameConstants.QP_DRAG_GAP_CORRECT()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.CORRECT);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetWrongStyleWhenAnswerTypeIsWrong() throws Exception {
        String expectedStyleName = "wrong";
        when(styleNameConstants.QP_DRAG_GAP_WRONG()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.WRONG);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetDefaultStyleWhenAnswerTypeIsDefault() throws Exception {
        String expectedStyleName = "default";
        when(styleNameConstants.QP_DRAG_GAP_DEFAULT()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.DEFAULT);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

    @Test
    public void shouldSetNoneStyleWhenAnswerTypeIsNone() throws Exception {
        String expectedStyleName = "none";
        when(styleNameConstants.QP_DRAG_GAP_NONE()).thenReturn(expectedStyleName);

        String styleName = dragGapStylesProvider.getCorrectGapStyleName(UserAnswerType.NONE);

        assertThat(styleName).isEqualTo(expectedStyleName);
    }

}
