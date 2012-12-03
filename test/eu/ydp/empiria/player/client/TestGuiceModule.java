package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import eu.ydp.empiria.player.client.BindDescriptor.BindType;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackParserFactory;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackParserFactoryMock;
import eu.ydp.empiria.player.client.controller.feedback.TextFeedbackPresenterMock;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistry;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistryFactory;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactoryMock;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListPresenterMock;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListViewMock;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParser;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParserMock;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParserMock;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.SchedulerImpl;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
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
	private class HasInitMethod extends AbstractMatcher<TypeLiteral<?>> {
		@Override
		public boolean matches(TypeLiteral<?> tpe) {
			try {
				Method[] methods = tpe.getRawType().getMethods();
				for(Method method:methods){
					if (method.isAnnotationPresent(PostConstruct.class)) {
						return true;
					}
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
	}

	private class InitInvoker implements InjectionListener {
		@Override
		public void afterInjection(Object injectee) {
			try {
				Method[] methods = injectee.getClass().getMethods();
				for(Method method:methods){
					if (method.isAnnotationPresent(PostConstruct.class)) {
						method.invoke(injectee);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
		bindDescriptors.add(new BindDescriptor<ConnectionModuleFactory>().bind(ConnectionModuleFactory.class).to(ConnectionModuleFactoryMock.class)
				.in(Singleton.class));
	//	bindDescriptors.add(new BindDescriptor<VideoFullScreenHelper>().bind(VideoFullScreenHelper.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<PanelCache>().bind(PanelCache.class));
		bindDescriptors.add(new BindDescriptor<FeedbackParserFactory>().bind(FeedbackParserFactory.class).to(FeedbackParserFactoryMock.class));
	}

	@Override
	public void configure() {
		HasInitMethod hasInit = new HasInitMethod();
		final InitInvoker initInvoker = new InitInvoker();
		bindListener(hasInit, new TypeListener() {
			@Override
			public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
				encounter.register(initInvoker);
			}
		});
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

		bind(SourceListJAXBParser.class).toInstance(spy(new SourceListJAXBParserMock()));
		bind(SourceListView.class).to(SourceListViewMock.class);
		bind(SourceListPresenter.class).to(SourceListPresenterMock.class);
		bind(MatcherRegistry.class).in(Singleton.class);
		bind(OverlayTypesParser.class).toInstance(mock(OverlayTypesParserMock.class));
		bind(TextFeedback.class).toInstance(mock(TextFeedbackPresenterMock.class));
		bind(ImageFeedback.class).toInstance(mock(ImageFeedbackPresenterMock.class));
		
		install(new FactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
		install(new FactoryModuleBuilder().build(PageScopeFactory.class));
		install(new FactoryModuleBuilder().build(TextTrackFactory.class));
		install(new FactoryModuleBuilder().build(MatcherRegistryFactory.class));

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
	public GWTPanelFactory gwtPanelFactory() {
		return new GwtPanelFactoryMock();
	}

	@Provides
	public ConnectionView getConnectionView() {
		return mock(ConnectionView.class);
	}

	@Provides
	@Singleton
	public NativeStyleHelper getNativeStyleHelper() {
		return mock(NativeStyleHelper.class);
	}

	@Provides
	@Singleton
	public StyleSocket getStyleSocket() {
		return mock(StyleSocket.class);
	}

	@Provides
	@Singleton
	public XMLParser getXmlParser() {
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
	public TouchRecognitionFactory getTouchRecognitionFactory() {
		TouchRecognitionFactory factory = mock(TouchRecognitionFactory.class);
		when(factory.getTouchRecognition(Mockito.any(Widget.class), Mockito.anyBoolean())).thenReturn(spy(new HasTouchHandlersMock()));
		return factory;
	}

	@Provides
	@Singleton
	public PositionHelper getPositionHelper() {
		PositionHelper helper = mock(PositionHelper.class);
		when(helper.getPositionX(Mockito.any(NativeEvent.class), Mockito.any(Element.class))).thenReturn(20);
		when(helper.getPositionY(Mockito.any(NativeEvent.class), Mockito.any(Element.class))).thenReturn(20);
		return helper;
	}

	@Provides
	ModuleFactory getModuleFactory(SourceListModule sourceListModule) {
		ModuleFactory factory = mock(ModuleFactory.class);
		when(factory.getSourceListModule()).thenReturn(sourceListModule);
		return factory;
	}

	@Provides
	DragDropHelper getDragDropHelper() {
		return mock(DragDropHelper.class);
	}

}
