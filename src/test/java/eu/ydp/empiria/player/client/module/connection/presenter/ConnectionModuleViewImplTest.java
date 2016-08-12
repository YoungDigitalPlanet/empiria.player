package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.TestJAXBParser;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemsFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurfaceStyleProvider;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.connection.structure.ConnectionModuleStructureMock;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ConnectionModuleViewImplTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {

        @Override
        public void configure(final Binder binder) {
            factory = mock(ConnectionItemsFactory.class);
            binder.bind(ConnectionEventHandler.class).toInstance(connectionEventHandler);
            binder.bind(ConnectionItemsFactory.class).toInstance(factory);
            binder.bind(ConnectionSurfacesManager.class).toInstance(mock(ConnectionSurfacesManager.class));
            binder.bind(CssHelper.class).toInstance(createCssHelperMock());
            binder.bind(ConnectionView.class).toInstance(createConnectionViewMock());
            binder.bind(ConnectionSurfaceStyleProvider.class).toInstance(mock(ConnectionSurfaceStyleProvider.class));
            binder.bind(ConnectionModuleViewStyles.class).toInstance(mock(ConnectionModuleViewStyles.class));
            binder.bind(UserAgentCheckerWrapper.class).toInstance(mock(UserAgentCheckerWrapper.class));
        }

        private ConnectionView createConnectionViewMock() {
            ConnectionView view = mock(ConnectionView.class);
            Widget widget = mock(Widget.class);
            Element element = mock(Element.class);
            doReturn(element).when(widget).getElement();
            doReturn(widget).when(view).asWidget();
            createNodeListMock(element);
            return view;
        }

        private CssHelper createCssHelperMock() {
            CssHelper cssHelper = spy(new CssHelper());
            Style style = mock(Style.class);
            doReturn("none").when(style).getProperty(Matchers.anyString());
            doReturn(style).when(cssHelper).getComputedStyle(Matchers.any(JavaScriptObject.class));
            return cssHelper;
        }

        private void createNodeListMock(final com.google.gwt.user.client.Element element) {
            NodeList<?> nodeList = mock(NodeList.class);
            doReturn(0).when(nodeList).getLength();
            doReturn(nodeList).when(element).getChildNodes();
        }

    }

    private final TestJAXBParser<MatchInteractionBean> jaxb = new TestJAXBParser<MatchInteractionBean>() {
    };

    private MatchInteractionBean bean;
    private ConnectionModuleViewImpl instance;
    private ModuleSocket moduleSocket;
    private ConnectionModuleFactory moduleFactory;
    private ConnectionItems connectionItems;
    private ConnectionItemsFactory factory;
    private final ConnectionEventHandler connectionEventHandler = spy(new ConnectionEventHandler());

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        prepareConnectionItems();
        prepareBean();
        prepareModuleSocket();
        prepareConnectionStyleChecker();
        prepareTestInstance();

        ConnectionSurfacesManager surfacesManager = injector.getInstance(ConnectionSurfacesManager.class);
        when(surfacesManager.getOrCreateSurface(anyMapOf(String.class, ConnectionSurface.class), anyString(), any(HasDimensions.class))).thenReturn(
                mock(ConnectionSurface.class));

    }

    private void prepareConnectionStyleChecker() {
        moduleFactory = injector.getInstance(ConnectionModuleFactory.class);
        ConnectionStyleChecker connectionStyleChacker = injector.getInstance(ConnectionStyleChecker.class);
        injector.getMembersInjector(ConnectionStyleChecker.class).injectMembers(connectionStyleChacker);
    }

    private void prepareModuleSocket() {
        moduleSocket = mock(ModuleSocket.class);
    }

    private void prepareBean() {
        bean = jaxb.parse(ConnectionModuleStructureMock.CONNECTION_XML);
    }

    private void prepareConnectionItems() {
        ConnectionItems connectionItems = new ConnectionItems(mock(InlineBodyGeneratorSocket.class));
        injector.getMembersInjector(ConnectionItems.class).injectMembers(connectionItems);
        this.connectionItems = spy(connectionItems);
        doReturn(this.connectionItems).when(factory).getConnectionItems(Matchers.any(InlineBodyGeneratorSocket.class));

    }

    private void prepareTestInstance() {
        instance = injector.getInstance(ConnectionModuleViewImpl.class);
        instance.setBean(bean);
        instance.setModuleSocket(moduleSocket);
        instance.bindView();
    }

    @Test
    public void connectTest() {
        // test
        ConnectionModuleViewImpl testObject = spy(instance);
        testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, bean.getSourceChoicesIdentifiersSet().get(0),
                bean.getTargetChoicesIdentifiersSet().get(0), false);
    }

    @Test
    public void wrongConnectTest() {
        // test
        ConnectionModuleViewImpl testObject = spy(instance);
        testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), "---", MultiplePairModuleConnectType.NORMAL);
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, bean.getSourceChoicesIdentifiersSet()
                        .get(0), "---",
                false);
        ConnectionSurface surface = moduleFactory.getConnectionSurface(new Size());
        Mockito.verify(surface, times(0)).drawLine(new Point(0, 0), new Point(0, 0));
    }

    @Test
    public void disconnectTest() {
        // test
        ConnectionModuleViewImpl testObject = spy(instance);
        String source = bean.getSourceChoicesIdentifiersSet().get(0);
        String target = bean.getTargetChoicesIdentifiersSet().get(0);
        testObject.connect(source, target, MultiplePairModuleConnectType.NORMAL);
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, source, target, false);
        testObject.disconnect(source, target);
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.DISCONNECTED, source, target, false);
    }

    @Test
    public void wrongDisconnectTest() {
        // test
        ConnectionModuleViewImpl testObject = spy(instance);
        String sourceIdentifier = bean.getSourceChoicesIdentifiersSet().get(0);
        String targetIdentifier = bean.getTargetChoicesIdentifiersSet().get(0);
        testObject.connect(sourceIdentifier, targetIdentifier, MultiplePairModuleConnectType.NORMAL);
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.CONNECTED, sourceIdentifier, targetIdentifier, false);
        testObject.disconnect(sourceIdentifier, "---");
        Mockito.verify(connectionEventHandler).fireConnectEvent(PairConnectEventTypes.WRONG_CONNECTION, sourceIdentifier, "---", false);
    }

    @Test
    public void resetTest() {
        // when
        instance.reset();

        // then
        verify(instance.getConnectionItems()).resetAllItems();
        assertThat(instance.getConnectionItemPair().getSource()).isNull();
        assertThat(instance.getConnectionItemPair().getTarget()).isNull();
    }

    @Test
    public void pairConnectEventHandlerTest() {
        ConnectionModuleViewImpl testObject = spy(instance);
        PairConnectEventHandler handler = mock(PairConnectEventHandler.class);
        testObject.addPairConnectEventHandler(handler);
        String sourceIdentifier = bean.getSourceChoicesIdentifiersSet().get(0);
        String targetIdentifier = bean.getTargetChoicesIdentifiersSet().get(1);
        testObject.connect(sourceIdentifier, targetIdentifier, MultiplePairModuleConnectType.NORMAL);
        verify(handler).onConnectionEvent(Matchers.any(PairConnectEvent.class));
    }

    @Test
    public void findConnectionTest() {
        ConnectionModuleViewImpl testObject = spy(instance);

        testObject.connect(bean.getSourceChoicesIdentifiersSet().get(0), bean.getTargetChoicesIdentifiersSet().get(0), MultiplePairModuleConnectType.NORMAL);
        ConnectionSurface surface = moduleFactory.getConnectionSurface(new Size());
        Mockito.when(surface.isPointOnPath(any(Point.class))).thenReturn(true);
        Mockito.verify(connectionEventHandler).fireConnectEvent(Matchers.eq(PairConnectEventTypes.CONNECTED), Matchers.anyString(), Matchers.anyString(),
                Matchers.anyBoolean());

    }

    @Test
    public void prepareAndAddStyleToSurfaceTest_startIsLeft() {
        // given
        ConnectionModuleViewImpl spyView = spy(instance);

        ConnectionSurfaceStyleProvider styleHelper = injector.getInstance(ConnectionSurfaceStyleProvider.class);
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

        connectionItems.addItemToLeftColumn(mock(PairChoiceBean.class));
        connectionItems.addItemToRightColumn(mock(PairChoiceBean.class));

        ConnectionItem leftItem = getFirstLeftItem();
        ConnectionItem rightItem = getFirstRightItem();

        MultiplePairBean<SimpleAssociableChoiceBean> modelInterface = mock(MatchInteractionBean.class);

        int leftIndex = 0;
        int rightIndex = 1;

        when(modelInterface.isLeftItem(leftItem.getBean())).thenReturn(true);
        when(modelInterface.getLeftItemIndex(leftItem.getBean())).thenReturn(leftIndex);
        when(modelInterface.getRightItemIndex(rightItem.getBean())).thenReturn(rightIndex);

        spyView.setBean(modelInterface);

        List<String> styles = Arrays.asList("qp-connection-line-0-1");
        when(styleHelper.getStylesForSurface(type, leftIndex, rightIndex)).thenReturn(styles);

        Widget widget = mockSurfaceWidget(spyView);

        // when
        spyView.connect(leftItem, rightItem, type, false);

        // then
        verify(styleHelper).getStylesForSurface(eq(type), eq(leftIndex), eq(rightIndex));
        verify(widget).addStyleName("qp-connection-line-0-1");
    }

    @Test
    public void prepareAndAddStyleToSurfaceTest_isMarkedOnBothSides() {
        // given
        ConnectionModuleViewImpl spyView = spy(instance);
        ConnectionSurfaceStyleProvider styleHelper = injector.getInstance(ConnectionSurfaceStyleProvider.class);

        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

        connectionItems.addItemToLeftColumn(mock(PairChoiceBean.class));
        connectionItems.addItemToRightColumn(mock(PairChoiceBean.class));

        ConnectionItem leftItem = getFirstLeftItem();
        ConnectionItem rightItem = getFirstRightItem();

        MultiplePairBean<SimpleAssociableChoiceBean> modelInterface = mock(MatchInteractionBean.class);

        int leftIndex = 0;
        int rightIndex = -1;

        when(modelInterface.isLeftItem(leftItem.getBean())).thenReturn(true);
        when(modelInterface.getLeftItemIndex(leftItem.getBean())).thenReturn(leftIndex);
        when(modelInterface.getRightItemIndex(rightItem.getBean())).thenReturn(rightIndex);

        spyView.setBean(modelInterface);

        List<String> styles = Arrays.asList("qp-connection-line-0-1");
        when(styleHelper.getStylesForSurface(type, leftIndex, rightIndex)).thenReturn(styles);

        Widget widget = mockSurfaceWidget(spyView);

        // when
        spyView.connect(leftItem, rightItem, type, false);

        // then
        verifyNoMoreInteractions(styleHelper);
        verifyNoMoreInteractions(widget);
    }

    @Test
    public void prepareAndAddStyleToSurfaceTest_startIsNotLeft() {
        // given
        ConnectionModuleViewImpl spyView = spy(instance);

        ConnectionSurfaceStyleProvider styleHelper = injector.getInstance(ConnectionSurfaceStyleProvider.class);
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

        connectionItems.addItemToLeftColumn(mock(PairChoiceBean.class));
        connectionItems.addItemToRightColumn(mock(PairChoiceBean.class));

        ConnectionItem leftItem = getFirstLeftItem();
        ConnectionItem rightItem = getFirstRightItem();

        MultiplePairBean<SimpleAssociableChoiceBean> modelInterface = mock(MatchInteractionBean.class);

        int leftIndex = 1;
        int rightIndex = 0;

        when(modelInterface.isLeftItem(leftItem.getBean())).thenReturn(false);
        when(modelInterface.getLeftItemIndex(leftItem.getBean())).thenReturn(leftIndex);
        when(modelInterface.getRightItemIndex(rightItem.getBean())).thenReturn(rightIndex);

        spyView.setBean(modelInterface);

        List<String> styles = Arrays.asList("qp-connection-line-1-0");
        when(styleHelper.getStylesForSurface(type, leftIndex, rightIndex)).thenReturn(styles);

        Widget widget = mockSurfaceWidget(spyView);

        // when
        spyView.connect(leftItem, rightItem, type, false);

        // then
        verify(styleHelper).getStylesForSurface(eq(type), eq(leftIndex), eq(rightIndex));
        verify(widget).addStyleName("qp-connection-line-1-0");
    }

    private Widget mockSurfaceWidget(ConnectionModuleViewImpl spyView) {
        ConnectionSurface surface = mock(ConnectionSurface.class);
        Widget widget = mock(Widget.class);
        when(surface.asWidget()).thenReturn(widget);
        doReturn(surface).when(spyView).getCurrentSurface();
        return widget;
    }

    private ConnectionItem getFirstRightItem() {
        return getFirstItem(connectionItems.getRightItems());
    }

    private ConnectionItem getFirstLeftItem() {
        return getFirstItem(connectionItems.getLeftItems());
    }

    private ConnectionItem getFirstItem(Collection<ConnectionItem> items) {
        ConnectionItem item = (ConnectionItem) items.toArray()[0];
        return item;
    }

    @Test
    public void postConstructTest() {
        ConnectionView view = injector.getInstance(ConnectionView.class);
        verify(view).setDrawFollowTouch(Matchers.eq(true));
    }

    @Test
    public void postConstructAndroidTest() {
        UserAgentCheckerWrapper userAgentCheckerWrapper = injector.getInstance(UserAgentCheckerWrapper.class);
        when(userAgentCheckerWrapper.isStackAndroidBrowser()).thenReturn(true);

        ConnectionView view = injector.getInstance(ConnectionView.class);
        Mockito.reset(view);

        instance.postConstruct();
        verify(view).setDrawFollowTouch(Matchers.eq(false));
    }
}
