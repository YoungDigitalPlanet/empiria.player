package eu.ydp.empiria.player.client.module.identification.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.IdentificationModuleFactory;
import eu.ydp.empiria.player.client.module.identification.view.SelectableChoiceView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SelectableChoicePresenterTest {

    private SelectableChoicePresenter testObj;
    @Mock
    private Widget contentWidget;
    @Mock
    private IdentificationModuleFactory moduleFactory;
    @Mock
    private SelectableChoiceView view;

    private final String identifier = "identifier";
    private final String coverId = "coverId";

    @Before
    public void init() {
        when(moduleFactory.createSelectableChoiceView(contentWidget)).thenReturn(view);
        testObj = new SelectableChoicePresenter(contentWidget, identifier, coverId, moduleFactory);
    }

    @Test
    public void shouldInitPresenter() {
        // given

        // when

        // then
        verify(view).setCoverId(coverId);
        verify(view).unmarkSelectedOption();
    }

    @Test
    public void shouldSelectChoice() {
        // given
        boolean selected = true;

        // when
        testObj.setSelected(selected);
        boolean result = testObj.isSelected();

        // then
        verify(view).markSelectedOption();
        assertThat(result).isEqualTo(selected);
    }

    @Test
    public void shouldDeselectChoice() {
        // given
        boolean selected = false;

        // when
        testObj.setSelected(selected);
        boolean result = testObj.isSelected();

        // then
        verify(view, atLeastOnce()).unmarkSelectedOption();
        assertThat(result).isEqualTo(selected);
    }

    @Test
    public void shouldMarkSelectedCorrectChoice() {
        // given
        boolean mark = true;
        boolean isChoiceCorrect = true;
        boolean selected = true;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, isChoiceCorrect);

        // then
        verify(view).markSelectedAnswerCorrect();
    }

    @Test
    public void shouldMarkNotSelectedCorrectChoice() {
        // given
        boolean mark = true;
        boolean isChoiceCorrect = true;
        boolean selected = false;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, isChoiceCorrect);

        // then
        verify(view).markNotSelectedAnswerWrong();
    }

    @Test
    public void shouldMarkSelectedWrongChoice() {
        // given
        boolean mark = true;
        boolean isChoiceCorrect = false;
        boolean selected = true;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, isChoiceCorrect);

        // then
        verify(view).markSelectedAnswerWrong();
    }

    @Test
    public void shouldMarkNotSelectedWrongChoice() {
        // given
        boolean mark = true;
        boolean isChoiceCorrect = false;
        boolean selected = false;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, isChoiceCorrect);

        // then
        verify(view).markNotSelectedAnswerCorrect();
    }

    @Test
    public void shouldMarkSelectedOption() {
        // given
        boolean mark = false;
        boolean selected = true;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, false);

        // then
        verify(view, atLeastOnce()).markSelectedOption();
    }

    @Test
    public void shouldUnmarkSelectedOption() {
        // given
        boolean mark = false;
        boolean selected = false;
        testObj.setSelected(selected);

        // when
        testObj.markAnswers(mark, false);

        // then
        verify(view, atLeastOnce()).unmarkSelectedOption();
    }

    @Test
    public void shouldReturnIdentifier() {
        // given

        // when
        String result = testObj.getIdentifier();

        // then
        assertThat(result).isEqualTo(identifier);
    }

    @Test
    public void shouldReturnViewsWidget() {
        // given
        Widget widget = mock(Widget.class);
        when(view.asWidget()).thenReturn(widget);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(widget);
    }

    @Test
    public void shouldLockView() {
        // given

        // when
        testObj.lock();

        // then
        verify(view).lock();
    }

    @Test
    public void shouldUnlockView() {
        // given

        // when
        testObj.unlock();

        // then
        verify(view).unlock();
    }
}
