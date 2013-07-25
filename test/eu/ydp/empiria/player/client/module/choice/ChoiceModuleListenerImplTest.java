package eu.ydp.empiria.player.client.module.choice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;

@RunWith(MockitoJUnitRunner.class)
public class ChoiceModuleListenerImplTest {

	private static final String IDENTIFIER = "ID_1";
	@Mock
	SimpleChoicePresenter choice;
	@Mock
	ChoiceModuleModel model;
	@Mock
	ChoiceModulePresenter presenter;
	@InjectMocks
	ChoiceModuleListenerImpl listener;
	

	@Test
	public void shouldRemoveAnswerWhenChoiceIsSelected() {
		// given
		when(choice.isSelected()).thenReturn(true);
		when(choice.getIdentifier()).thenReturn(IDENTIFIER);

		// when
		listener.onChoiceClick(choice);

		// then
		verify(model).removeAnswer(IDENTIFIER);
		verify(presenter).showAnswers(ShowAnswersType.USER);
	}

	@Test
	public void shouldAddAnswerWhenChoiceIsNotSelected() {
		// given
		when(choice.isSelected()).thenReturn(false);
		when(choice.getIdentifier()).thenReturn(IDENTIFIER);

		// when
		listener.onChoiceClick(choice);

		// then
		verify(model).addAnswer(IDENTIFIER);
		verify(presenter).showAnswers(ShowAnswersType.USER);
	}
}
