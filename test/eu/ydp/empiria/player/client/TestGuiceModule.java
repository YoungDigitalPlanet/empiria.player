package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.BindDescriptor.BindType;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactoryMock;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.SchedulerImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlersMock;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.xml.XMLParser;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;
import eu.ydp.gwtutil.test.mock.GwtPanelFactoryMock;

@SuppressWarnings("PMD")
public class TestGuiceModule extends ExtendTestGuiceModule {
	private final Set<Class<?>> classToMock = new HashSet<Class<?>>();
	private final Set<Class<?>> classToSpy = new HashSet<Class<?>>();
	private final Set<BindDescriptor<?>> bindDescriptors = new HashSet<BindDescriptor<?>>();

	public TestGuiceModule() {

	}

	public TestGuiceModule(Class<?>[] classToOmit, Class<?>[] classToMock, Class<?>[] classToSpy) {
		super(classToOmit);
		setClassToMock(classToMock);
		setClassToSpy(classToSpy);
	}

	public void setClassToMock(Class<?>... clazz) {
		classToMock.addAll(Arrays.asList(clazz));
	}

	public void setClassToSpy(Class<?>... clazz) {
		classToSpy.addAll(Arrays.asList(clazz));
	}

	private void init() {
		bindDescriptors.add(new BindDescriptor<Scheduler>().bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<EventsBus>().bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<ConnectionModuleFactory>().bind(ConnectionModuleFactory.class).to(ConnectionModuleFactoryMock.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<VideoFullScreenHelper>().bind(VideoFullScreenHelper.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<PanelCache>().bind(PanelCache.class));
	}

	@Override
	public void configure() {
		init();
		for (BindDescriptor<?> bindDescriptor : bindDescriptors) {
			if (classToMock.contains(bindDescriptor.getBind())) {
				bind(bindDescriptor, BindType.MOCK);
			} else if (classToSpy.contains(bindDescriptor.getBind())) {
				bind(bindDescriptor, BindType.SPY);
			} else {
				bind(bindDescriptor, BindType.SIMPLE);
			}
		}
		install(new FactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
		install(new FactoryModuleBuilder().build(PageScopeFactory.class));
		install(new FactoryModuleBuilder().build(TextTrackFactory.class));
	}

	@Provides
	@Singleton
	public StyleNameConstants getNameConstants() {
		return GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class), StyleNameConstants.class);
	}

	@Provides
	public MediaControllerFactory getMediaControllerFactory() {
		MediaControllerFactory factory = mock(MediaControllerFactory.class);
		return factory;
	}

	@Provides
	@Singleton
	public VideoTextTrackElementPresenter getVideoTextTrackElementPresenter() {
		return mock(VideoTextTrackElementPresenter.class);
	}

	@Provides
	public ObjectTemplateParser<?> getObjectTemplateParser(MediaControllerFactory factory) {
		ObjectTemplateParser<?> parser = new ObjectTemplateParser();
		parser.setFactory(factory);
		return parser;
	}

	@Provides
	public GWTPanelFactory gwtPanelFactory() {
		return new GwtPanelFactoryMock();
	}

	@Provides
	public ConnectionView getConnectionView(){
		return mock(ConnectionView.class);
	}

	@Provides
	@Singleton
	public NativeStyleHelper getNativeStyleHelper() {
		return mock(NativeStyleHelper.class);
	}

	@Provides
	@Singleton
	public StyleSocket getStyleSocket(){
		return mock(StyleSocket.class);
	}

	@Provides
	@Singleton
	public XMLParser getXmlParser(){
		XMLParser parser = mock(XMLParser.class);
		when(parser.parse(Mockito.anyString())).then(new Answer<Document>() {
			@Override
			public Document answer(InvocationOnMock invocation) throws Throwable {
				return eu.ydp.gwtutil.xml.XMLParser.parse((String) invocation.getArguments()[0]);
			}
		});
		return parser;
	}

	@Provides
	@Singleton
	public TouchRecognitionFactory getTouchRecognitionFactory(){
		TouchRecognitionFactory factory = mock(TouchRecognitionFactory.class);
		when(factory.getTouchRecognition(Mockito.any(Widget.class))).thenReturn(spy(new HasTouchHandlersMock()));
		return factory;
	}

	@Provides
	@Singleton
	public PositionHelper getPositionHelper(){
		PositionHelper helper = mock(PositionHelper.class);
		when(helper.getPositionX(Mockito.any(NativeEvent.class), Mockito.any(Element.class))).thenReturn(20);
		when(helper.getPositionY(Mockito.any(NativeEvent.class), Mockito.any(Element.class))).thenReturn(20);
		return helper;
	}











}
