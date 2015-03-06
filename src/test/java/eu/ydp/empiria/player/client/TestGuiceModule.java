package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.*;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.spi.*;
import eu.ydp.empiria.player.client.BindDescriptor.BindType;
import eu.ydp.empiria.player.client.controller.feedback.*;
import eu.ydp.empiria.player.client.controller.feedback.matcher.*;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeState;
import eu.ydp.empiria.player.client.gin.EmpiriaExListBoxDelay;
import eu.ydp.empiria.player.client.gin.binding.UniqueId;
import eu.ydp.empiria.player.client.gin.factory.*;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.connection.ConnectionSurface;
import eu.ydp.empiria.player.client.module.connection.presenter.view.ConnectionView;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandlerFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.sourcelist.structure.*;
import eu.ydp.empiria.player.client.overlaytypes.*;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.events.bus.*;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlersMock;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxDelays;
import eu.ydp.gwtutil.client.debug.log.*;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;
import eu.ydp.gwtutil.client.scheduler.*;
import eu.ydp.gwtutil.client.service.json.*;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.util.*;
import eu.ydp.gwtutil.client.xml.XMLParser;
import eu.ydp.gwtutil.client.xml.proxy.*;
import eu.ydp.gwtutil.junit.mock.*;
import eu.ydp.gwtutil.xml.XMLProxyWrapper;
import java.util.*;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

@SuppressWarnings("PMD")
public class TestGuiceModule extends ExtendTestGuiceModule {
	private final Set<BindDescriptor<?>> bindDescriptors = new HashSet<BindDescriptor<?>>();
	private final GuiceModuleConfiguration moduleConfiguration;

	public TestGuiceModule() {
		moduleConfiguration = new GuiceModuleConfiguration();
	}

	public TestGuiceModule(Class<?>[] classToOmit, Class<?>[] classToMock, Class<?>[] classToSpy) {
		super(classToOmit);
		moduleConfiguration = new GuiceModuleConfiguration();
		moduleConfiguration.addAllClassToOmit(classToOmit);
		moduleConfiguration.addAllClassToMock(classToMock);
		moduleConfiguration.addAllClassToSpy(classToSpy);
	}

	public TestGuiceModule(GuiceModuleConfiguration moduleConfiguration) {
		super(moduleConfiguration.getClassToOmmit());
		this.moduleConfiguration = moduleConfiguration;
	}

	private void init() {
		bindDescriptors.add(new BindDescriptor<Scheduler>().bind(Scheduler.class)
														   .to(SchedulerMockImpl.class)
														   .in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<EventsBus>().bind(EventsBus.class)
														   .to(PlayerEventsBus.class)
														   .in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<ConnectionModuleFactory>().bind(ConnectionModuleFactory.class)
																		 .to(ConnectionModuleFactoryMock.class)
																		 .in(Singleton.class));
		// bindDescriptors.add(new
		// BindDescriptor<VideoFullScreenHelper>().bind(VideoFullScreenHelper.class).in(Singleton.class));
		bindDescriptors.add(new BindDescriptor<PanelCache>().bind(PanelCache.class));
		bindDescriptors.add(new BindDescriptor<FeedbackParserFactory>().bind(FeedbackParserFactory.class)
																	   .to(FeedbackParserFactoryMock.class));
	}

	@Override
	public void configure() {
		addPostConstructInterceptor(moduleConfiguration);
		init();
		List<Class<?>> classToMock = moduleConfiguration.getClassToMock();
		List<Class<?>> classToSpy = moduleConfiguration.getClassToSpy();
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
		bind(MatcherRegistry.class).in(Singleton.class);
		bind(OverlayTypesParser.class).toInstance(mock(OverlayTypesParserMock.class));
		bind(TextFeedback.class).toInstance(mock(TextFeedbackPresenterMock.class));
		bind(ImageFeedback.class).toInstance(mock(ImageFeedbackPresenterMock.class));
		bind(FeedbackRegistry.class).toInstance(mock(FeedbackRegistry.class));
		bind(XMLProxy.class).to(XMLProxyWrapper.class);
		bind(BrowserNativeInterface.class).toInstance(
				UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS));
		bind(UserAgentUtil.class).toInstance(mock(UserAgentUtil.class));
		bind(SourcelistManager.class).toInstance(mock(SourcelistManager.class));
		bind(OutcomeAccessor.class).toInstance(mock(OutcomeAccessor.class));
		bind(FlowDataSupplier.class).toInstance(mock(FlowDataSupplier.class));
		bind(SessionDataSupplier.class).toInstance(mock(SessionDataSupplier.class));
		binder.bind(IJSONService.class)
			  .to(NativeJSONService.class);
		binder.requestStaticInjection(XMLProxyFactory.class);
		bind(ResponseSocket.class).annotatedWith(PageScoped.class)
								  .toInstance(mock(ResponseSocket.class));
		bind(String.class).annotatedWith(UniqueId.class)
						  .toInstance("id");
		bind(ExListBoxDelays.class).to(EmpiriaExListBoxDelay.class);
		bind(LogAppender.class).to(ConsoleAppender.class);
		bind(PlayerWorkModeState.class).toInstance(mock(PlayerWorkModeState.class));
		install(new FactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
		install(new FactoryModuleBuilder().build(PageScopeFactory.class));
		install(new FactoryModuleBuilder().build(TextTrackFactory.class));
		install(new FactoryModuleBuilder().build(MatcherRegistryFactory.class));
		install(new FactoryModuleBuilder().build(FieldValueHandlerFactory.class));
		install(new FactoryModuleBuilder().build(AssessmentReportFactory.class));
		install(new FactoryModuleBuilder().build(SingleFeedbackSoundPlayerFactory.class));
		install(new FactoryModuleBuilder().build(ResultExtractorsFactory.class));
		install(new FactoryModuleBuilder().build(FeedbackModuleFactory.class));
	}

	private void addPostConstructInterceptor(GuiceModuleConfiguration guiceModuleConfiguration) {
		HasPostConstructMethod hasPostConstruct = new HasPostConstructMethod(guiceModuleConfiguration.getClassWithDisabledPostConstruct());
		final PostConstructInvoker postConstructInvoker = new PostConstructInvoker();
		bindListener(hasPostConstruct, new TypeListener() {
			@Override
			public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
				encounter.register(postConstructInvoker);
			}
		});
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
		return mock(GWTPanelFactory.class);
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
		when(parser.parse(Matchers.anyString())).then(new Answer<Document>() {
			@Override
			public Document answer(InvocationOnMock invocation) throws Throwable {
				return eu.ydp.gwtutil.xml.XMLParser.parse((String) invocation.getArguments()[0]);
			}
		});

		when(parser.createDocument()).then(new Answer<Document>() {
			@Override
			public Document answer(InvocationOnMock invocation) throws Throwable {
				return eu.ydp.gwtutil.xml.XMLParser.createDocument();
			}
		});
		return parser;
	}

	@Provides
	@Singleton
	public TouchRecognitionFactory getTouchRecognitionFactory() {
		TouchRecognitionFactory factory = mock(TouchRecognitionFactory.class);
		when(factory.getTouchRecognition(Matchers.any(Widget.class), Matchers.anyBoolean())).thenReturn(spy(new HasTouchHandlersMock()));
		return factory;
	}

	@Provides
	@Singleton
	public PositionHelper getPositionHelper() {
		PositionHelper helper = mock(PositionHelper.class);
		when(helper.getXPositionRelativeToTarget(Matchers.any(NativeEvent.class), Matchers.any(Element.class))).thenReturn(20);
		when(helper.getYPositionRelativeToTarget(Matchers.any(NativeEvent.class), Matchers.any(Element.class))).thenReturn(20);
		return helper;
	}

	@Provides DragDropHelper getDragDropHelper() {
		return mock(DragDropHelper.class);
	}

	@Provides ConnectionSurface getConnectionSurface() {
		return mock(ConnectionSurface.class);
	}

	@Provides
	@Singleton NativeMethodInvocator getMethodInvocator() {
		NativeMethodInvocator invocator = mock(NativeMethodInvocator.class);
		return invocator;
	}
}
