package eu.ydp.empiria.player.client.module.speechscore;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.speechscore.presenter.SpeechScorePresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SpeechScoreModuleTest {

    @InjectMocks
    private SpeechScoreModule testObj;

    @Mock
    private SpeechScorePresenter presenter;

    @Test
    public void shouldBindPresenterOnInit() {
        // given
        Element element = mock(Element.class);

        // when
        testObj.initModule(element);

        // then
        verify(presenter).init(element);
    }
}
