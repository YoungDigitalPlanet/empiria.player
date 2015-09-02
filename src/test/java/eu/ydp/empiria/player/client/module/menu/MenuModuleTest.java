package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.report.table.ReportTable;
import eu.ydp.empiria.player.client.controller.report.table.ReportTableGenerator;
import eu.ydp.empiria.player.client.gin.factory.ReportModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

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
    @Mock
    private FlowDataSupplier flowDataSupplier;
    @Mock
    private ReportTableGenerator reportTableGenerator;
    int currentPageIndex = 1;

    @Before
    public void init() {
        when(reportModuleFactory.createReportTableGenerator(bodyGeneratorSocket)).thenReturn(reportTableGenerator);
        when(flowDataSupplier.getCurrentPageIndex()).thenReturn(currentPageIndex);
    }

    @Test
    public void shouldCreateTable_onInit() {
        // given
        ReportTable reportTable = mock(ReportTable.class);
        when(reportTableGenerator.generate(element)).thenReturn(reportTable);

        // when
//        testObj.initModule(element, moduleSocket, bodyGeneratorSocket);

        // then
        verify(presenter).setReportTable(reportTable);
    }

    @Test
    public void shouldHideMenu_beforeFlow() {
        // given
        PlayerEvent playerEvent = mock(PlayerEvent.class);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.BEFORE_FLOW);

//        testObj.initModule(element, moduleSocket, bodyGeneratorSocket);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(presenter).hide();
    }

    @Test
    public void shouldUnmarkPage_beforeFlow() {
        // given
        PlayerEvent playerEvent = mock(PlayerEvent.class);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.BEFORE_FLOW);

//        testObj.initModule(element, moduleSocket, bodyGeneratorSocket);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(presenter).unmarkPage(currentPageIndex);
    }

    @Test
    public void shouldMarkRow_whenPageLoaded() {
        // given
        PlayerEvent playerEvent = mock(PlayerEvent.class);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.PAGE_LOADED);

//        testObj.initModule(element, moduleSocket, bodyGeneratorSocket);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(presenter).markPage(currentPageIndex);
    }
}