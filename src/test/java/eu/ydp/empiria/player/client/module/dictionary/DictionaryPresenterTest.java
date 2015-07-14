package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.dom.client.NativeEvent;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MainController;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DictionaryPresenterTest {
    @InjectMocks
    private DictionaryPresenter testObj;

    @Mock
    private DictionaryButtonView dictionaryButtonView;
    @Mock
    private DictionaryPopupView dictionaryPopupView;
    @Mock
    private NativeEvent event;
    @Mock
    private MainController mainController;

    @Captor
    private ArgumentCaptor<Command> clickCaptor;

    @Test
    public void shouldShowPopupOnClick() {
        // given
        testObj.bindUi();
        verify(dictionaryButtonView).addHandler(clickCaptor.capture());

        // when
        clickCaptor.getValue().execute(event);

        // then
        verify(dictionaryPopupView).show();
    }

    @Test
    public void shouldHidePopupOnCloseButtonClick() {
        // given
        testObj.bindUi();
        verify(dictionaryPopupView).addHandler(clickCaptor.capture());

        // when
        clickCaptor.getValue().execute(event);

        // then
        verify(dictionaryPopupView).hide();
    }
}
