package eu.ydp.empiria.player.client.module.choice.presenter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoicePresenterFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleModel;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.ChoiceModuleView;

@RunWith(MockitoJUnitRunner.class)
public class ChoiceModulePresenterImplTest {

	@InjectMocks
	ChoiceModulePresenterImpl presenter;

	@Mock ChoiceModuleModel model;
	@Mock ChoiceModuleView view;
	@Mock ChoiceInteractionBean bean;
	@Mock SimpleChoicePresenterFactory choiceModuleFactory;
	@Mock InlineBodyGeneratorSocket bodyGenerator;
	@Mock SimpleChoicePresenter simplePresenter1;
	@Mock SimpleChoicePresenter simplePresenter2;
	@Mock SimpleChoicePresenter simplePresenter3;
	@Mock SimpleChoicePresenter simplePresenter4;

	private static final String IDENTIFIER_1 = "1";
	private static final String IDENTIFIER_2 = "2";
	private static final String IDENTIFIER_3 = "3";
	private static final String IDENTIFIER_4 = "4";

	@Before
	public void setUp() throws Exception {
		prepareBean();
		presenter.setBean(bean);
		presenter.setInlineBodyGenerator(bodyGenerator);
		
		presenter.bindView();
	}

	private void prepareBean() {
		bean = new ChoiceInteractionBean();
		List<SimpleChoiceBean> simpleChoices = Lists.newArrayList(
				createSimpleBean(IDENTIFIER_1, simplePresenter1),
				createSimpleBean(IDENTIFIER_2, simplePresenter2),
				createSimpleBean(IDENTIFIER_3, simplePresenter3),
				createSimpleBean(IDENTIFIER_4, simplePresenter4));
		bean.setSimpleChoices(simpleChoices);
	}

	private SimpleChoiceBean createSimpleBean(String id,
			SimpleChoicePresenter simplePresenter) {
		SimpleChoiceBean bean = new SimpleChoiceBean();
		bean.setIdentifier(id);
		when(choiceModuleFactory.getSimpleChoicePresenter(bean, bodyGenerator))
				.thenReturn(simplePresenter);
		return bean;
	}

	@Test
	public void shouldInitializeView() {
		// then
		verify(view).clear();
		verify(view, times(4)).addChoice(any(Widget.class));
		verify(simplePresenter1).setListener(any(ChoiceModuleListener.class));
		verify(simplePresenter2).setListener(any(ChoiceModuleListener.class));
		verify(simplePresenter3).setListener(any(ChoiceModuleListener.class));
		verify(simplePresenter4).setListener(any(ChoiceModuleListener.class));
	}

	@Test
	public void shouldLockAlleSimplePresenters() {
		// when
		presenter.setLocked(true);

		// then
		verify(simplePresenter1).setLocked(true);
		verify(simplePresenter2).setLocked(true);
		verify(simplePresenter3).setLocked(true);
		verify(simplePresenter4).setLocked(true);
	}

	@Test
	public void shouldResetAllSimplePresenters() {
		// when
		presenter.reset();

		// then
		verify(simplePresenter1).reset();
		verify(simplePresenter2).reset();
		verify(simplePresenter3).reset();
		verify(simplePresenter4).reset();

		verify(model).removeAnswer(IDENTIFIER_1);
		verify(model).removeAnswer(IDENTIFIER_2);
		verify(model).removeAnswer(IDENTIFIER_3);
		verify(model).removeAnswer(IDENTIFIER_4);
	}

	@Test
	public void shouldShowUserAnswers() {
		// given
		when(model.isUserAnswer(IDENTIFIER_1)).thenReturn(true);
		when(model.isUserAnswer(IDENTIFIER_2)).thenReturn(false);
		when(model.isUserAnswer(IDENTIFIER_3)).thenReturn(false);
		when(model.isUserAnswer(IDENTIFIER_4)).thenReturn(true);

		// when
		presenter.showAnswers(ShowAnswersType.USER);

		// then
		verify(simplePresenter1).setSelected(true);
		verify(simplePresenter2).setSelected(false);
		verify(simplePresenter3).setSelected(false);
		verify(simplePresenter4).setSelected(true);
	}

	@Test
	public void shouldReturnCorrectPlaceholder() {
		// when
		presenter.getFeedbackPlaceholderByIdentifier(IDENTIFIER_2);

		// then
		verify(simplePresenter2).getFeedbackPlaceHolder();
		verify(simplePresenter1, never()).getFeedbackPlaceHolder();
		verify(simplePresenter3, never()).getFeedbackPlaceHolder();
		verify(simplePresenter4, never()).getFeedbackPlaceHolder();
	}

	@Test
	public void shouldReturnCorrectSimpleChoicePresenterIdentifier() {
		// when
		String identifier = presenter.getChoiceIdentifier(simplePresenter3);

		// then
		assertThat(identifier, is(equalTo(IDENTIFIER_3)));
	}

	@Test
	public void shouldMarkAnswers() {
		// given
		when(simplePresenter1.isSelected()).thenReturn(true);
		when(model.isCorrectAnswer(IDENTIFIER_1)).thenReturn(true);
		
		when(simplePresenter2.isSelected()).thenReturn(true);
		when(model.isCorrectAnswer(IDENTIFIER_2)).thenReturn(false);

		when(simplePresenter3.isSelected()).thenReturn(false);
		when(model.isCorrectAnswer(IDENTIFIER_3)).thenReturn(false);
		
		when(simplePresenter4.isSelected()).thenReturn(false);
		when(model.isCorrectAnswer(IDENTIFIER_4)).thenReturn(true);
		
		// when
		presenter.markAnswers(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
		
		// then
		verify(simplePresenter1).markAnswer(MarkAnswersType.CORRECT, MarkAnswersMode.MARK);
		verify(simplePresenter2, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
		verify(simplePresenter3, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
		verify(simplePresenter4, never()).markAnswer(any(MarkAnswersType.class), any(MarkAnswersMode.class));
	}

}
