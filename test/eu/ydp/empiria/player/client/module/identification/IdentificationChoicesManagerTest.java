package eu.ydp.empiria.player.client.module.identification;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.module.identification.predicates.*;
import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class IdentificationChoicesManagerTest {

	@InjectMocks
	private IdentificationChoicesManager testObj;
	@Mock
	private SelectableChoicePresenter firstPresenter;
	@Mock
	private SelectableChoicePresenter secondPresenter;
	@Mock
	private SelectedChoicePredicate selectedChoicePredicate;
	@Mock
	private ChoiceToIdentifierTransformer choiceToIdentifierTransformer;

	@Before
	public void init() {
		testObj.addChoice(firstPresenter);
		testObj.addChoice(secondPresenter);
	}

	@Test
	public void shouldLockChoices() {
		// given

		// when
		testObj.lockAll();

		// then
		verify(firstPresenter).lock();
		verify(secondPresenter).lock();
	}

	@Test
	public void shouldUnlockChoices() {
		// given

		// when
		testObj.unlockAll();

		// then
		verify(firstPresenter).unlock();
		verify(secondPresenter).unlock();
	}

	@Test
	public void shouldMarkAnswers() {
		// given
		String firstId = "firstId";
		String secondId = "secondId";
		when(firstPresenter.getIdentifier()).thenReturn(firstId);
		when(secondPresenter.getIdentifier()).thenReturn(secondId);
		
		CorrectAnswers correctAnswers = mock(CorrectAnswers.class);
		boolean firstChoiceIsCorrectAnswer = true;
		when(correctAnswers.containsAnswer(firstId)).thenReturn(firstChoiceIsCorrectAnswer);
		boolean secondChoiceIsCorrectAnswer = false;
		when(correctAnswers.containsAnswer(secondId)).thenReturn(secondChoiceIsCorrectAnswer);

		boolean mark = true;
		
		// when
		testObj.markAnswers(mark, correctAnswers);

		// then
		verify(firstPresenter).markAnswers(mark, firstChoiceIsCorrectAnswer);
		verify(secondPresenter).markAnswers(mark, secondChoiceIsCorrectAnswer);
	}

	@Test
	public void shouldClearSelection() {
		// given

		// when
		testObj.clearSelections();

		// then
		verify(firstPresenter).setSelected(false);
		verify(secondPresenter).setSelected(false);
	}

	@Test
	public void shouldRestoreView() {
		// given
		String firstId = "firstId";
		String secondId = "secondId";
		when(firstPresenter.getIdentifier()).thenReturn(firstId);
		when(secondPresenter.getIdentifier()).thenReturn(secondId);

		List<String> selectedChoices = Lists.newArrayList();
		selectedChoices.add(firstId);

		// when
		testObj.restoreView(selectedChoices);

		// then
		verify(firstPresenter).setSelected(true);
		verify(secondPresenter).setSelected(false);
	}

	@Test
	public void shouldSelectAnswers() {
		// given
		String firstId = "firstId";
		String secondId = "secondId";
		when(firstPresenter.getIdentifier()).thenReturn(firstId);
		when(secondPresenter.getIdentifier()).thenReturn(secondId);

		CorrectAnswers correctAnswers = mock(CorrectAnswers.class);
		boolean firstChoiceIsCorrectAnswer = true;
		when(correctAnswers.containsAnswer(firstId)).thenReturn(firstChoiceIsCorrectAnswer);
		boolean secondChoiceIsCorrectAnswer = false;
		when(correctAnswers.containsAnswer(secondId)).thenReturn(secondChoiceIsCorrectAnswer);

		// when
		testObj.selectCorrectAnswers(correctAnswers);

		// then
		verify(firstPresenter).setSelected(firstChoiceIsCorrectAnswer);
		verify(secondPresenter).setSelected(secondChoiceIsCorrectAnswer);
	}

	@Test
	public void shouldSetNewState() {
		// given
		boolean firstChoiceIsSelected = true;
		boolean secondChoiceIsSelected = false;
		JSONArray newState = mock(JSONArray.class, Mockito.RETURNS_DEEP_STUBS);
		when(newState.get(0).isBoolean().booleanValue()).thenReturn(firstChoiceIsSelected);
		when(newState.get(1).isBoolean().booleanValue()).thenReturn(secondChoiceIsSelected);

		// when
		testObj.setState(newState);

		// then
		verify(firstPresenter).setSelected(firstChoiceIsSelected);
		verify(secondPresenter).setSelected(secondChoiceIsSelected);
	}

	@Test
	public void shouldReturnOnlySelectedChoices() {
		// given
		when(selectedChoicePredicate.apply(firstPresenter)).thenReturn(true);
		when(selectedChoicePredicate.apply(secondPresenter)).thenReturn(false);

		// when
		List<SelectableChoicePresenter> selectedChoices = testObj.getSelectedChoices();

		// then
		assertThat(selectedChoices).containsExactly(firstPresenter);
	}

	@Test
	public void shouldReturnIdentifiersOfSelectedChoices() {
		// given
		String firstId = "firstId";
		String secondId = "secondId";
		when(firstPresenter.getIdentifier()).thenReturn(firstId);
		when(secondPresenter.getIdentifier()).thenReturn(secondId);

		when(selectedChoicePredicate.apply(firstPresenter)).thenReturn(true);
		when(selectedChoicePredicate.apply(secondPresenter)).thenReturn(false);

		when(choiceToIdentifierTransformer.apply(firstPresenter)).thenReturn(firstId);
		when(choiceToIdentifierTransformer.apply(secondPresenter)).thenReturn(secondId);

		// when
		List<String> selectedChoices = testObj.getIdentifiersSelectedChoices();

		// then
		assertThat(selectedChoices).containsExactly(firstId);
	}

}
