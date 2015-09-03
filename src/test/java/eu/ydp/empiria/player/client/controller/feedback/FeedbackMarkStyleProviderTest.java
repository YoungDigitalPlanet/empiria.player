package eu.ydp.empiria.player.client.controller.feedback;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackMarkStyleProviderTest {

    public static final String FEEDBACK_ALLOK_STYLE = "qp-feedback-allok";
    public static final String FEEDBACK_OK_STYLE = "qp-feedback-ok";
    public static final String FEEDBACK_WRONG_STYLE = "qp-feedback-wrong";

    @InjectMocks
    private FeedbackMarkStyleProvider testObj;
    @Mock
    private FeedbackStyleNameConstants styleNames;

    @Before
    public void setUp() throws Exception {
        when(styleNames.QP_FEEDBACK_ALLOK()).thenReturn(FEEDBACK_ALLOK_STYLE);
        when(styleNames.QP_FEEDBACK_OK()).thenReturn(FEEDBACK_OK_STYLE);
        when(styleNames.QP_FEEDBACK_WRONG()).thenReturn(FEEDBACK_WRONG_STYLE);
    }

    @Test
    public void shouldGetFeedbackAllOkStyleName() {
        // when
        String styleName = testObj.getStyleName(FeedbackMark.ALL_OK);

        // then
        assertThat(styleName).isEqualTo(FEEDBACK_ALLOK_STYLE);
    }

    @Test
    public void shouldGetFeedbackOkStyleName() {
        // when
        String styleName = testObj.getStyleName(FeedbackMark.OK);

        // then
        assertThat(styleName).isEqualTo(FEEDBACK_OK_STYLE);
    }

    @Test
    public void shouldGetFeedbackWrongStyleName() {
        // when
        String styleName = testObj.getStyleName(FeedbackMark.WRONG);

        // then
        assertThat(styleName).isEqualTo(FEEDBACK_WRONG_STYLE);
    }

    @Test
    public void shouldGetEmptyStyleName() {
        // when
        String styleName = testObj.getStyleName(FeedbackMark.DEFAULT);

        // then
        assertThat(styleName).isEmpty();
    }
}