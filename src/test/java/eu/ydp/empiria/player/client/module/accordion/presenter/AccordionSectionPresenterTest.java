package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class AccordionSectionPresenterTest {

    private static final String SECTION_HIDDEN = "sectionHidden";
    private static final String ZERO_HEIGHT = "zeroHeight";
    private static final String ZERO_WIDTH = "zeroWidth";
    private static final String TRANSITION_ALL = "transitionAll";
    private static final String TRANSITION_WIDTH = "transitionWidth";
    private static final String TRANSITION_HEIGHT = "transitionHeight";

    @InjectMocks
    private AccordionSectionPresenter testObj;
    @Mock
    private AccordionSectionView view;
    @Mock
    private StyleNameConstants styleNameConstants;

    @Before
    public void init() {
        when(styleNameConstants.QP_ACCORDION_SECTION_HIDDEN()).thenReturn(SECTION_HIDDEN);
        when(styleNameConstants.QP_ZERO_HEIGHT()).thenReturn(ZERO_HEIGHT);
        when(styleNameConstants.QP_ZERO_WIDTH()).thenReturn(ZERO_WIDTH);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_ALL()).thenReturn(TRANSITION_ALL);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_WIDTH()).thenReturn(TRANSITION_WIDTH);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_HEIGHT()).thenReturn(TRANSITION_HEIGHT);
    }

    @Test
    public void shouldDelegateTitle() {
        // given
        Widget titleWidget = mock(Widget.class);

        // when
        testObj.setTitle(titleWidget);

        // then
        verify(view).setTitle(titleWidget);
    }

    @Test
    public void shouldDelegateClickCommand() {
        // given
        Command clickCommand = mock(Command.class);

        // when
        testObj.addClickCommand(clickCommand);

        // then
        verify(view).addClickCommand(clickCommand);
    }

    @Test
    public void shouldGetContainerFromView() {
        // given
        HasWidgets container = mock(HasWidgets.class);
        when(view.getContentContainer()).thenReturn(container);

        // when
        HasWidgets result = testObj.getContentContainer();

        // then
        assertThat(result).isEqualTo(container);
    }

    @Test
    public void shouldAddStyleHidden_onHide() {
        // when
        testObj.hideVertically();
        testObj.hideHorizontally();

        // then
        verify(view, times(2)).addSectionStyleName(SECTION_HIDDEN);
    }

    @Test
    public void shouldRemoveStyleHidden_onShow() {
        // when
        testObj.show();

        // then
        verify(view).removeSectionStyleName(SECTION_HIDDEN);
    }

    @Test
    public void shouldUpdateSize_onShow() {
        // given
        int h = 10;
        int w = 20;
        when(view.getContentHeight()).thenReturn(h);
        when(view.getContentWidth()).thenReturn(w);

        // when
        testObj.show();

        // then
        verify(view).setSectionDimensions("20PX", "10PX");

    }

    @Test
    public void shouldAddStyleZeroHeight_whenHideVertically() {
        // when
        testObj.hideVertically();

        // then
        verify(view).addContentWrapperStyleName(ZERO_HEIGHT);
    }

    @Test
    public void shouldAddStyleZeroWidth_whenHideHorizontally() {
        // when
        testObj.hideHorizontally();

        // then
        verify(view).addContentWrapperStyleName(ZERO_WIDTH);
    }

    @Test
    public void shouldRemoveStyleZeroHeightAndHeight_whenShow() {
        // when
        testObj.show();

        // then
        verify(view).removeContentWrapperStyleName(ZERO_HEIGHT);
        verify(view).removeContentWrapperStyleName(ZERO_WIDTH);
    }

    @Test
    public void shouldSetStyleTransitionAll() {
        // given
        Transition transition = Transition.ALL;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(TRANSITION_ALL);
    }

    @Test
    public void shouldSetStyleTransitionWidth() {
        // given
        Transition transition = Transition.WIDTH;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(TRANSITION_WIDTH);
    }

    @Test
    public void shouldSetStyleTransitionHeight() {
        // given
        Transition transition = Transition.HEIGHT;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(TRANSITION_HEIGHT);
    }
}