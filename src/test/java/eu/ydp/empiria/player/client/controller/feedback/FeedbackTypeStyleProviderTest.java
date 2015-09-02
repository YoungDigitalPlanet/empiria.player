package eu.ydp.empiria.player.client.controller.feedback;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackTypeStyleProviderTest {

    @Mock
    private FeedbackProperties properties;
    @Mock
    private FeedbackStyleNameConstants styleNames;
    @InjectMocks
    private FeedbackTypeStyleProvider testObj;

    public static final String feedbackAllOkStyle = "qp-feedback-allok";
    public static final String feedbackOkStyle = "qp-feedback-ok";
    public static final String feedbackWrongStyle = "qp-feedback-wrong";

    @Before
    public void setUp() {
        when(styleNames.QP_FEEDBACK_ALLOK()).thenReturn(feedbackAllOkStyle);
        when(styleNames.QP_FEEDBACK_OK()).thenReturn(feedbackOkStyle);
        when(styleNames.QP_FEEDBACK_WRONG()).thenReturn(feedbackWrongStyle);
    }

    @Test
    public void shouldGetFeedbackAllOkStyle() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.ALL_OK)).thenReturn(true);
        testObj.prepareStyleName(properties);

        // when
        String styleName = testObj.getStyleName();

        // then
        assertEquals(styleName, styleNames.QP_FEEDBACK_ALLOK());
    }

    @Test
    public void shouldGetFeedbackOkStyle() {
        // given
        when(properties.getBooleanProperty(FeedbackPropertyName.OK)).thenReturn(true);
        testObj.prepareStyleName(properties);

        // when
        String styleName = testObj.getStyleName();

        // then
        assertEquals(styleName, styleNames.QP_FEEDBACK_OK());
    }

    @Test
    public void shouldGetFeedbackWrongStyle() {
        // given
        testObj.prepareStyleName(properties);

        // when
        String styleName = testObj.getStyleName();

        // then
        assertEquals(styleName, styleNames.QP_FEEDBACK_WRONG());
    }
}