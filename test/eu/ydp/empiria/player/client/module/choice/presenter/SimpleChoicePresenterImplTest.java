package eu.ydp.empiria.player.client.module.choice.presenter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoiceViewFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceView;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(Widget.class)
public class SimpleChoicePresenterImplTest {

	SimpleChoicePresenterImpl presenter;

	@Mock SimpleChoiceBean bean;
	@Mock InlineBodyGenerator bodyGenerator;
	@Mock SimpleChoiceViewFactory viewFactory;
	@Mock SimpleChoiceView view;
	@Mock ChoiceButtonBase button;
	@Mock Widget widget;
	@Mock Widget feedbackPlaceHolderMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(viewFactory.getSimpleChoiceView(any(SimpleChoicePresenter.class))).thenReturn(view);
		when(viewFactory.getSingleChoiceButton(anyString())).thenReturn(button);
		when(bean.getContent()).thenReturn(mock(XMLContent.class));
		when(bean.isMulti()).thenReturn(true);
		when(view.getFeedbackPlaceHolder()).thenReturn(feedbackPlaceHolderMock);
		when(view.asWidget()).thenReturn(widget);

		presenter = new SimpleChoicePresenterImpl(viewFactory, bean, bodyGenerator);
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void restore() {
		GWTMockUtilities.restore();
	}

	@Test
	public void testMarkCorrectAnswers() {
		// when
		presenter.markAnswer(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);

		// then
		verify(view).markCorrect();
	}

	@Test
	public void testMarkWrongAnswers() {
		// when
		presenter.markAnswer(MarkAnswersType.WRONG, MarkAnswersMode.MARK);

		// then
		verify(view).markWrong();
	}

	@Test
	public void testUnmarkCorrectAnswers() {
		// when
		presenter.markAnswer(MarkAnswersType.CORRECT, MarkAnswersMode.UNMARK);

		// then
		verify(view).unmarkCorrect();
	}

	@Test
	public void testUnmarkWrongAnswers() {
		// when
		presenter.markAnswer(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);

		// then
		verify(view).unmarkWrong();
	}

	@Test
	public void shouldCallListenerWhenClicked() {
		// given
		ChoiceModuleListener listener = mock(ChoiceModuleListener.class);
		presenter.setListener(listener);

		// when
		presenter.onChoiceClick();

		// then
		verify(listener).onChoiceClick(presenter);
	}

	@Test
	public void shouldSetSelectedOnView_TRUE() {
		// when
		presenter.setSelected(true);

		// then
		verify(view).setSelected(true);
	}

	@Test
	public void shouldSetSelectedOnView_FALSE() {
		// when
		presenter.setSelected(false);

		// then
		verify(view).setSelected(false);
	}

	@Test
	public void shouldAskViewForSelection() {
		// given
		when(view.isSelected()).thenReturn(true);
		// when
		boolean selected = presenter.isSelected();

		// then
		assertThat(selected, is(true));
		verify(view).isSelected();
	}

	@Test
	public void shouldReturnFeedbackPlaceholder() {
		// when
		Widget feedbackPlaceHolder = presenter.getFeedbackPlaceHolder();

		// then
		verify(view).getFeedbackPlaceHolder();
		assertThat(feedbackPlaceHolder, is(feedbackPlaceHolderMock));
	}

	@Test
	public void shouldReturnWidget() {
		// when
		Widget aswWidget = presenter.asWidget();

		// then
		verify(view).asWidget();
		assertThat(aswWidget, is(widget));
	}

	@Test
	public void shouldLockView() {
		// when
		presenter.setLocked(true);

		// then
		verify(view).setLocked(true);
	}

	@Test
	public void shouldResetView() {
		// when
		presenter.reset();

		// then
		verify(view).reset();
	}

	@Test
	public void shouldIsMulti() {
		// given
		

		// when
		boolean multi = presenter.isMulti();

		// then
		assertThat(multi, is(true));
	}
}
