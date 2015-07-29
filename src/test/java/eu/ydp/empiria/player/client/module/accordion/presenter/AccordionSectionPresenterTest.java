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

    @InjectMocks
    private AccordionSectionPresenter testObj;
    @Mock
    private AccordionSectionView view;
    @Mock
    private StyleNameConstants styleNameConstants;

    private String sectionHidden = "sectionHidden";
    private String zeroHeight = "zeroHeight";
    private String zeroWidth = "zeroWidth";
    private String transitionAll = "transitionAll";
    private String transitionWidth = "transitionWidth";
    private String transitionHeight = "transitionHeight";

    @Before
    public void init() {
        when(styleNameConstants.QP_ACCORDION_SECTION_HIDDEN()).thenReturn(sectionHidden);
        when(styleNameConstants.QP_ZERO_HEIGHT()).thenReturn(zeroHeight);
        when(styleNameConstants.QP_ZERO_WIDTH()).thenReturn(zeroWidth);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_ALL()).thenReturn(transitionAll);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_WIDTH()).thenReturn(transitionWidth);
        when(styleNameConstants.QP_ACCORDION_SECTION_CONTENT_TRANSITION_HEIGHT()).thenReturn(transitionHeight);
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
        // given
        // when
        testObj.hideVertically();
        testObj.hideHorizontally();

        // then
        verify(view, times(2)).addSectionStyleName(sectionHidden);
    }

    @Test
    public void shouldRemoveStyleHidden_onShow() {
        // given
        // when
        testObj.showVertically();
        testObj.showHorizontally();

        // then
        verify(view, times(2)).removeSectionStyleName(sectionHidden);
    }

    @Test
    public void shouldUpdateSize_onShow() {
        // given
        int h = 10;
        int w = 20;
        when(view.getContentHeight()).thenReturn(h);
        when(view.getContentWidth()).thenReturn(w);

        // when
        testObj.showVertically();
        testObj.showHorizontally();

        // then
        verify(view, times(2)).setSectionDimensions("20PX", "10PX");

    }

    @Test
    public void shouldAddStyleZeroHeight_whenHideVertically() {
        // given
        // when
        testObj.hideVertically();

        // then
        verify(view).addContentWrapperStyleName(zeroHeight);
    }

    @Test
    public void shouldAddStyleZeroWidth_whenHideHorizontally() {
        // given
        // when
        testObj.hideHorizontally();

        // then
        verify(view).addContentWrapperStyleName(zeroWidth);
    }

    @Test
    public void shouldRemoveStyleZeroHeight_whenShowVertically() {
        // given
        // when
        testObj.showVertically();

        // then
        verify(view).removeContentWrapperStyleName(zeroHeight);
    }

    @Test
    public void shouldRemoveStyleZeroWidth_whenShowingHorizontally() {
        // given
        // when
        testObj.showHorizontally();

        // then
        verify(view).removeContentWrapperStyleName(zeroWidth);
    }

    @Test
    public void shouldSetStyleTransitionAll() {
        // given
        Transition transition = Transition.ALL;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(transitionAll);
    }

    @Test
    public void shouldSetStyleTransitionWidth() {
        // given
        Transition transition = Transition.WIDTH;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(transitionWidth);
    }

    @Test
    public void shouldSetStyleTransitionHeight() {
        // given
        Transition transition = Transition.HEIGHT;

        // when
        testObj.init(transition);

        // then
        verify(view).addContentWrapperStyleName(transitionHeight);
    }
}