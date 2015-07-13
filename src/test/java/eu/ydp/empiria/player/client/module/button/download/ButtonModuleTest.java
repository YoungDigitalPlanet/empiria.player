package eu.ydp.empiria.player.client.module.button.download;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.button.download.presenter.ButtonModulePresenter;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ButtonModuleTest {
    @Mock
    private ButtonModuleStructure buttonModuleStructure;
    @Mock
    private IJSONService ijsonService;
    @Mock
    private ButtonModulePresenter buttonModulePresenter;
    @Mock
    private YJsonArray jsonArray;
    @Mock
    private ButtonBean buttonBean;
    @InjectMocks
    private ButtonModule instance;

    @Before
    public void before() {
        doReturn(jsonArray).when(ijsonService).createArray();
        doReturn(buttonBean).when(buttonModuleStructure).getBean();
    }

    @Test
    public void getView() {
        instance.getView();
        verify(buttonModulePresenter).asWidget();
        verifyNoMoreInteractions(buttonModulePresenter);
        verifyZeroInteractions(ijsonService);
        verifyZeroInteractions(buttonModuleStructure);
    }

    @Test
    public void initModule() throws Exception {
        Element xmlElement = mock(Element.class);
        String xmlContent = "<>";
        doReturn(xmlContent).when(xmlElement).toString();

        instance.initModule(xmlElement);
        InOrder inOrder = inOrder(buttonModuleStructure, buttonModulePresenter);
        inOrder.verify(buttonModuleStructure).createFromXml(eq(xmlContent), eq(jsonArray));
        inOrder.verify(buttonModulePresenter).setBean(eq(buttonBean));
        inOrder.verify(buttonModulePresenter).init();
    }

}
