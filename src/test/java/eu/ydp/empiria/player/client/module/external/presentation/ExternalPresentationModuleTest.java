package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ExternalPresentationModuleTest {

    @InjectMocks
    private ExternalPresentationModule testObj;
    @Mock
    private ExternalPresentationPresenter presenter;
    @Mock
    private ExternalPaths externalPaths;
    @Mock
    private Element element;
    private static final String source = "SOURCE";
    private static final String ID = "ID";

    @Before
    public void setUp() throws Exception {
        when(element.getAttribute("src")).thenReturn(source);
        when(element.getAttribute("id")).thenReturn(ID);
    }

    @Test
    public void shouldRegisterAsFolderNameProviderAndThenInitializePresenter() {
        // when
        testObj.initModule(element);

        // then
        InOrder inOrder = inOrder(externalPaths, presenter);
        inOrder.verify(externalPaths).setExternalFolderNameProvider(testObj);
        inOrder.verify(presenter).init();
    }

    @Test
    public void shouldReturnPresentationNameAsModuleIdentifier() {
        // given
        testObj.initModule(element, mock(ModuleSocket.class));

        // when
        String actual = testObj.getIdentifier();

        // then
        assertThat(actual).isEqualTo(ID);
    }

    @Test
    public void shouldReturnPresentationNameAsExsternalFolderName() {
        // given
        testObj.initModule(element);

        // when
        String actual = testObj.getExternalFolderName();

        // then
        assertThat(actual).isEqualTo(source);
    }
}
