package eu.ydp.empiria.player.client.module.choice.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.junit.GWTMockUtilities;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.gin.factory.SimpleChoiceViewFactory;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.choice.view.SimpleChoiceView;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;

@RunWith(MockitoJUnitRunner.class)
public class SimpleChoicePresenterImplTest {

	SimpleChoicePresenterImpl presenter;

	@Mock
	SimpleChoiceBean bean;
	@Mock
	InlineBodyGenerator bodyGenerator;
	@Mock
	SimpleChoiceViewFactory viewFactory;
	@Mock
	SimpleChoiceView view;
	@Mock
	ChoiceButtonBase button;

	@Before
	public void setUp() throws Exception {
		when(viewFactory.getSimpleChoiceView(any(SimpleChoicePresenter.class)))
				.thenReturn(view);
		when(viewFactory.getSingleChoiceButton(anyString())).thenReturn(button);
		when(bean.getContent()).thenReturn(mock(XMLContent.class));

		presenter = new SimpleChoicePresenterImpl(viewFactory, bean,
				bodyGenerator);
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
	public void testunmarkWrongAnswers() {
		// when
		presenter.markAnswer(MarkAnswersType.WRONG, MarkAnswersMode.UNMARK);

		// then
		verify(view).unmarkWrong();
	}
}
