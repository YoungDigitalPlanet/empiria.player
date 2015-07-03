package eu.ydp.empiria.player.client.module.tutor.presenter;

import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupPresenter;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupProvider;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TutorPresenterImplTest {

    private TutorPresenterImpl tutorPresenter;

    private final String tutorId = "tutorId";
    @Mock
    private TutorView tutorView;
    @Mock
    private TutorPopupProvider tutorPopupProvider;
    @Mock
    private TutorPopupPresenter popupPresenter;

    @Before
    public void setUp() throws Exception {
        tutorPresenter = new TutorPresenterImpl(tutorView, tutorId, tutorPopupProvider);
    }

    @Test
    public void shouldInitializeView() throws Exception {
        tutorPresenter.init();

        verify(tutorView).bindUi();
        verify(tutorView).addClickHandler(any(Command.class));
    }

    @Test
    public void shouldOpenPopupOnClick() throws Exception {
        when(tutorPopupProvider.get(tutorId)).thenReturn(popupPresenter);

        tutorPresenter.clicked();

        verify(popupPresenter).show();
    }
}
