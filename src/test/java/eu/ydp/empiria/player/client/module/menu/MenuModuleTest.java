package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MenuModuleTest {

    @InjectMocks
    private MenuModule testObj;
    @Mock
    private MenuPresenter presenter;
    @Mock
    private ReportModuleFactory reportModuleFactory;
    @Mock
    private Element element;
    @Mock
    private ModuleSocket moduleSocket;
    @Mock
    private BodyGeneratorSocket bodyGeneratorSocket;
    @Mock
    private EventsBus eventsBus;

    @Test
    public void shouldCreateTable_onInit() {
        // given
        FlexTable flexTable = mock(FlexTable.class);
        ReportTableGenerator reportTableGenerator = mock(ReportTableGenerator.class);
        when(reportModuleFactory.createReportTableGenerator(bodyGeneratorSocket)).thenReturn(reportTableGenerator);
        when(reportTableGenerator.generate(element)).thenReturn(flexTable);

        // when
        testObj.initModule(element, moduleSocket, bodyGeneratorSocket);

        // then
        verify(presenter).setTable(flexTable);
    }

    @Test
    public void shouldHideMenu_onPlayerEvent() {
        // given

        PlayerEvent playerEvent = mock(PlayerEvent.class);
        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(presenter).hide();
    }
}