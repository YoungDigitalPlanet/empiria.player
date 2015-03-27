package eu.ydp.empiria.player.client.module.identification.predicates;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectedChoicePredicateTest {

	@InjectMocks
	private SelectedChoicePredicate testObj;
	@Mock
	private SelectableChoicePresenter presenter;

	@Test
	public void shouldReturnTrue_whenChoiceIsSelected() {
		// given
		when(presenter.isSelected()).thenReturn(true);

		// when
		boolean result = testObj.apply(presenter);

		// then
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse_whenChoiceIsNotSelected() {
		// given
		when(presenter.isSelected()).thenReturn(false);

		// when
		boolean result = testObj.apply(presenter);

		// then
		assertThat(result).isFalse();
	}
}
