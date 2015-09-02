package eu.ydp.empiria.player.client.module.sourcelist;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.xml.XMLParser;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class SourceListModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private SourceListModule testObj;
    private SourceListPresenter presenter;
    private SourceListModuleStructure sourceListModuleStructure;
    private final static String sourcelistId = "id1";
    private SourceListBean bean;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    private static class CustomGuiceModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(SourceListPresenter.class).toInstance(mock(SourceListPresenter.class));
            binder.bind(SourceListModuleStructure.class).toInstance(mock(SourceListModuleStructure.class));
            binder.bind(SourcelistManager.class).annotatedWith(PageScoped.class).toInstance(mock(SourcelistManager.class));
        }
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Before
    public void before() {
        GuiceModuleConfiguration moduleConfiguration = new GuiceModuleConfiguration();
        moduleConfiguration.addAllClassToOmit(SourceListPresenter.class, SourceListView.class);
        setUpAndOverrideMainModule(moduleConfiguration, new CustomGuiceModule());
        testObj = injector.getInstance(SourceListModule.class);
        presenter = injector.getInstance(SourceListPresenter.class);
        sourceListModuleStructure = injector.getInstance(SourceListModuleStructure.class);
        bean = mock(SourceListBean.class);
        when(bean.getSourcelistId()).thenReturn(sourcelistId);
        when(sourceListModuleStructure.getBean()).thenReturn(bean);

        ModuleSocket moduleSocket = mock(ModuleSocket.class);
        inlineBodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
        when(moduleSocket.getInlineBodyGeneratorSocket()).thenReturn(inlineBodyGeneratorSocket);

        Element element = mock(Element.class);
        testObj.initModule(element, moduleSocket, injector.getInstance(EventsBus.class));
    }

    @Test
    public void initModuleTest() {
        // given
        Element documentElement = XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement();

        // when
        testObj.initModule(documentElement);

        // then
        InOrder inOrder = inOrder(sourceListModuleStructure, presenter);
        inOrder.verify(sourceListModuleStructure).createFromXml(anyString(), any(YJsonArray.class));
        inOrder.verify(sourceListModuleStructure).getBean();
        inOrder.verify(presenter).setBean(Matchers.any(SourceListBean.class));
        inOrder.verify(presenter).createAndBindUi(inlineBodyGeneratorSocket);

        assertEquals(presenter.asWidget(), testObj.getView());
    }

    @Test
    public void testGetItemValue() {
        String itemId = "id";
        testObj.getItemValue(itemId);
        verify(presenter).getItemValue(itemId);
    }

    @Test
    public void testUseItem() {
        String itemId = "id";
        testObj.useItem(itemId);
        verify(presenter).useItem(itemId);
    }

    @Test
    public void testRestockItem() {
        String itemId = "id";
        testObj.restockItem(itemId);
        verify(presenter).restockItem(itemId);
    }

    @Test
    public void testUseAndRestockItems() {
        List<String> items = mock(List.class);

        testObj.useAndRestockItems(items);
        verify(presenter).useAndRestockItems(items);
        verifyZeroInteractions(items);
    }

    @Test
    public void testGetIdentifier() {
        // given
        Element documentElement = XMLParser.parse(SourceListJAXBParserMock.XML).getDocumentElement();
        testObj.initModule(documentElement);
        // when
        String identifier = testObj.getIdentifier();
        // then
        assertThat(identifier).isEqualTo(sourcelistId);
    }

    @Test
    public void testLockSourceList() {
        testObj.lockSourceList();
        verify(presenter).lockSourceList();
    }

    @Test
    public void testUnlockSourceList() {
        testObj.unlockSourceList();
        verify(presenter).unlockSourceList();
    }

    @Test
    public void getItemSize() {
        HasDimensions dimension = mock(HasDimensions.class);
        doReturn(dimension).when(presenter).getMaxItemSize();
        HasDimensions itemSize = testObj.getItemSize();
        assertThat(dimension).isSameAs(itemSize);
    }
}
