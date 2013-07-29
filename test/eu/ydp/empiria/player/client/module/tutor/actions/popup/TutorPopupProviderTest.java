package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.inject.Provider;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorPopupProviderTest {

	private TutorPopupProvider tutorPopupProvider;
	@Mock
	private Provider<TutorPopupPresenter> provider;
	@Mock
	private TutorPopupPresenter popupPresenter;

	@Before
	public void setUp() throws Exception {
		tutorPopupProvider = new TutorPopupProvider(provider);
	}

	@Test
	public void shouldCreateAndInitializeNewPresenterWhenNoCached() throws Exception {
		// given
		String tutorId = "tutor1";

		when(provider.get())
			.thenReturn(popupPresenter);

		// when
		TutorPopupPresenter resultPresenter = tutorPopupProvider.get(tutorId);

		// then
		assertEquals(popupPresenter, resultPresenter);
		verify(popupPresenter).init(tutorId);
	}

	@Test
	public void shouldReturnCachedPresenter() throws Exception {
		// given
		String tutorId = "tutor1";

		when(provider.get())
			.thenReturn(popupPresenter);

		// when
		TutorPopupPresenter firstlyReturned = tutorPopupProvider.get(tutorId);
		TutorPopupPresenter secondlyReturned = tutorPopupProvider.get(tutorId);

		// then
		assertEquals(popupPresenter, firstlyReturned);
		assertEquals(firstlyReturned, secondlyReturned);
		verify(popupPresenter, times(1)).init(tutorId);
	}
}
