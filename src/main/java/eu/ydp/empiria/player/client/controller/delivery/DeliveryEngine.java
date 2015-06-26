package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.AssessmentController;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.controller.data.DataSourceManagerMode;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryExtension;
import eu.ydp.empiria.player.client.controller.data.library.LibraryExternalExtension;
import eu.ydp.empiria.player.client.controller.data.library.LibraryInternalExtension;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsHub;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionsManager;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionsProvider;
import eu.ydp.empiria.player.client.controller.extensions.internal.SoundProcessorManagerExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusService;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.empiria.player.client.controller.extensions.types.*;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.processing.DefaultFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.session.times.SessionTimeUpdater;
import eu.ydp.empiria.player.client.controller.style.StyleLinkManager;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeState;
import eu.ydp.empiria.player.client.gin.factory.AssessmentFactory;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistry;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.page.PageEvent;
import eu.ydp.empiria.player.client.util.events.internal.page.PageEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.page.PageEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.player.PlayerViewCarrier;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import java.util.List;

public class DeliveryEngine implements DataLoaderEventListener, FlowProcessingEventsListener, DeliveryEngineSocket, PageEventHandler, PlayerEventHandler {

	Integer initialItemIndex;

	private final DataSourceManager dataManager;
	private final PlayerViewSocket playerViewSocket;
	private final StyleSocket styleSocket;
	private final ModuleHandlerManager moduleHandlerManager;
	private final SessionDataManager sessionDataManager;
	private final EventsBus eventsBus;
	private final ModulesRegistry modulesRegistry;

	private final StyleLinkManager styleManager;
	private ExtensionsManager extensionsManager;
	private final FlowManager flowManager;
	private final DeliveryEventsHub deliveryEventsHub;
	private AssessmentController assessmentController;
	private SoundProcessorManagerExtension soundProcessorManager;
	private final TutorService tutorService;
	private final BonusService bonusService;
	private final ProgressBonusService progressBonusService;
	private final UserAgentUtil userAgentUtil;
	private final PlayerWorkModeState playerWorkModeState;
	private final FlowRequestFactory flowRequestFactory;
	private ExtensionsProvider extensionsProvider;

	private JavaScriptObject playerJsObject;
	private String stateAsync;

	private final AssessmentFactory assessmentFactory;

	@Inject
	public DeliveryEngine(PlayerViewSocket playerViewSocket, DataSourceManager dataManager, StyleSocket styleSocket, SessionDataManager sessionDataManager,
			EventsBus eventsBus, ModuleHandlerManager moduleHandlerManager, SessionTimeUpdater sessionTimeUpdater,
			ModulesRegistry modulesRegistry, TutorService tutorService, BonusService bonusService, FlowManager flowManager,
			ProgressBonusService progressBonusService, DeliveryEventsHub deliveryEventsHub, StyleLinkManager styleManager, UserAgentUtil userAgentUtil,
			AssessmentFactory assessmentFactory, PlayerWorkModeState playerWorkModeState,
			FlowRequestFactory flowRequestFactory, SoundProcessorManagerExtension soundProcessorManager, ExtensionsProvider extensionsProvider) {
		this.playerViewSocket = playerViewSocket;
		this.dataManager = dataManager;
		this.sessionDataManager = sessionDataManager;
		this.eventsBus = eventsBus;
		this.styleManager = styleManager;
		this.moduleHandlerManager = moduleHandlerManager;
		this.modulesRegistry = modulesRegistry;
		this.tutorService = tutorService;
		this.bonusService = bonusService;
		this.flowManager = flowManager;
		this.deliveryEventsHub = deliveryEventsHub;
		this.progressBonusService = progressBonusService;
		this.userAgentUtil = userAgentUtil;
		this.assessmentFactory = assessmentFactory;
		this.playerWorkModeState = playerWorkModeState;
		this.flowRequestFactory = flowRequestFactory;
		this.soundProcessorManager = soundProcessorManager;
		this.extensionsProvider = extensionsProvider;
		dataManager.setDataLoaderEventListener(this);
		this.styleSocket = styleSocket;

		eventsBus.addHandler(PageEvent.getTypes(PageEventTypes.values()), this);
		eventsBus.addHandler(PlayerEvent.getTypes(PlayerEventTypes.values()), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), sessionTimeUpdater);
	}

	public void init(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
		extensionsManager = PlayerGinjectorFactory.getPlayerGinjector().getExtensionsManager();
		flowManager.addCommandProcessor(new DefaultFlowRequestProcessor(flowManager.getFlowCommandsExecutor()));

		assessmentController = assessmentFactory.createAssessmentController(
				playerViewSocket.getAssessmentViewSocket(),
				flowManager.getFlowSocket(),
				deliveryEventsHub.getInteractionSocket()
		);
		playerViewSocket.setPlayerViewCarrier(new PlayerViewCarrier());
		loadPredefinedExtensions();
	}

	public void load(String url) {
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADING));
		dataManager.loadMainDocument(url);
	}

	public void load(XmlData assessmentData, XmlData[] itemsData) {
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADING));
		dataManager.loadData(assessmentData, itemsData);
	}

	@Override
	public void onAssessmentLoaded() {
		updateAssessmentStyle();
	}

	@Override
	public void onDataReady() {
		AssessmentData assessmentData = dataManager.getAssessmentData();
		DisplayOptions displayOptions = flowManager.getDisplayOptions();

		if (assessmentData != null) {
			displayOptions.useSkin(assessmentData.useSkin());
		}
		flowManager.setDisplayOptions(displayOptions);

		loadLibraryExtensions();
		sessionDataManager.init(dataManager.getItemsCount(), dataManager.getInitialData());
		initExtensions();

		flowManager.init(dataManager.getItemsCount());
		assessmentController.init(assessmentData, displayOptions);

		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADED));
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.ASSESSMENT_LOADED));
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_STARTING));
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.ASSESSMENT_STARTING));
		updateAssessmentStyle();
		initFlow();
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_STARTED));
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.ASSESSMENT_STARTED));
		updatePageStyle();
	}

	private void initFlow() {
		JSONArray state = null;
		if (stateAsync != null) {
			state = (JSONArray) JSONParser.parseLenient(stateAsync);
			assessmentController.reset();
			sessionDataManager.setState((JSONArray) state.get(1));
			extensionsManager.setState(state.get(2).isArray());
			if (state.size() > 2) {
				playerWorkModeState.setState(state.get(3).isArray());
			}
			flowManager.deinitFlow();
		}
		IFlowRequest flowRequest = flowRequestFactory.create(state, initialItemIndex);
		if (flowRequest != null) {
			flowManager.invokeFlowRequest(flowRequest);
		}
		flowManager.initFlow();
	}

	private void loadPredefinedExtensions() {
		for (Extension extension : extensionsProvider.get()) {
			loadExtension(extension);
		}
	}

	private void loadLibraryExtensions() {
		List<LibraryExtension> extCreators = dataManager.getExtensionCreators();
		for (LibraryExtension ext : extCreators) {
			if (ext instanceof LibraryExternalExtension) {
				JavaScriptObject extInstance = ((LibraryExternalExtension) ext).getExtensionInstance();
				if (extInstance != null) {
					loadExtension(extInstance);
				}
			} else if (ext instanceof LibraryInternalExtension) {
				loadExtension(((LibraryInternalExtension) ext).getName());
			}
		}
	}

	public void loadExtension(JavaScriptObject extension) {
		List<Extension> currExtensions = extensionsManager.addExtension(extension);
		for (Extension currExtension : currExtensions) {
			integrateExtension(currExtension);
		}
	}

	public void loadExtension(String extensionName) {
		Extension currExtension = extensionsManager.getInternaleExtensionByName(extensionName);
		if (currExtension != null) {
			loadExtension(currExtension);
		}
	}

	public void loadExtension(Extension extension) {
		extensionsManager.addExtension(extension);
		integrateExtension(extension);
	}

	private void integrateExtension(Extension extension) {
		if (extension != null) {
			if (extension instanceof DeliveryEventsListenerExtension) {
				deliveryEventsHub.addDeliveryEventsListener(((DeliveryEventsListener) extension));
			}
			if (extension instanceof FlowCommandsSocketUserExtension) {
				((FlowCommandsSocketUserExtension) extension).setFlowCommandsExecutor(flowManager.getFlowCommandsExecutor());
			}
			if (extension instanceof FlowRequestSocketUserExtension) {
				((FlowRequestSocketUserExtension) extension).setFlowRequestsInvoker(flowManager.getFlowRequestInvoker());
			}
			if (extension instanceof PageInterferenceSocketUserExtension) {
				((PageInterferenceSocketUserExtension) extension).setPageInterferenceSocket(assessmentController.getPageControllerSocket());
			}
			if (extension instanceof DataSourceDataSocketUserExtension) {
				((DataSourceDataSocketUserExtension) extension).setDataSourceDataSupplier(dataManager);
			}
			if (extension instanceof SessionDataSocketUserExtension) {
				((SessionDataSocketUserExtension) extension).setSessionDataSupplier(sessionDataManager);
			}
			if (extension instanceof FlowDataSocketUserExtension) {
				((FlowDataSocketUserExtension) extension).setFlowDataSupplier(flowManager.getFlowDataSupplier());
			}
			if (extension instanceof DeliveryEngineSocketUserExtension) {
				((DeliveryEngineSocketUserExtension) extension).setDeliveryEngineSocket(this);
			}
			if (extension instanceof InteractionEventSocketUserExtension) {
				((InteractionEventSocketUserExtension) extension).setInteractionEventsListener(deliveryEventsHub);
			}
			if (extension instanceof PlayerJsObjectModifierExtension) {
				((PlayerJsObjectModifierExtension) extension).setPlayerJsObject(playerJsObject);
			}
			if (extension instanceof MediaProcessorExtension) {
				soundProcessorManager.setSoundProcessorExtension((MediaProcessorExtension) extension);
			}
			if (extension instanceof ModuleConnectorExtension) {
				modulesRegistry.registerModuleCreator(((ModuleConnectorExtension) extension).getModuleNodeName(),
						((ModuleConnectorExtension) extension).getModuleCreator());
			}
			if (extension instanceof ModuleHandlerExtension) {
				moduleHandlerManager.addModuleHandler((ModuleHandlerExtension) extension);
			}
			if (extension instanceof TutorExtension) {
				TutorExtension tutorExtension = (TutorExtension) extension;
				tutorService.registerTutor(tutorExtension.getTutorId(), tutorExtension.getTutorConfig());
			}
			if (extension instanceof BonusExtension) {
				BonusExtension bonusExtension = (BonusExtension) extension;
				bonusService.registerBonus(bonusExtension.getBonusId(), bonusExtension.getBonusConfig());
			}
			if (extension instanceof ProgressBonusExtension) {
				ProgressBonusExtension progressBonusExtension = (ProgressBonusExtension) extension;
				progressBonusService.register(progressBonusExtension.getProgressBonusId(), progressBonusExtension.getProgressBonusConfig());
			}
		}
	}

	private void initExtensions() {
		extensionsManager.init();
		employExtensions();
	}

	private void employExtensions() {
		for (Extension currExtension : extensionsManager.getExtensions()) {
			employExtension(currExtension);
		}
	}

	private void employExtension(Extension extension) {
		if (extension instanceof FlowRequestProcessorExtension) {
			flowManager.addCommandProcessor(((FlowRequestProcessorExtension) extension));
		}
		if (extension instanceof AssessmentHeaderViewExtension) {
			assessmentController.setHeaderViewSocket(((AssessmentHeaderViewExtension) extension).getAssessmentHeaderViewSocket());
		}
		if (extension instanceof AssessmentFooterViewExtension) {
			assessmentController.setFooterViewSocket(((AssessmentFooterViewExtension) extension).getAssessmentFooterViewSocket());
		}

	}

	@Override
	public void onFlowProcessingEvent(FlowProcessingEvent event) {
		if (event.getType() == FlowProcessingEventType.PAGE_LOADED) {
			PageReference pageReferance = flowManager.getPageReference();
			PageData pageData = dataManager.generatePageData(pageReferance);
			getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.PAGE_UNLOADING));

			assessmentController.closePage();

			getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.PAGE_UNLOADED));

			getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.PAGE_LOADING));

			// TODO style provider should listen directly to navigation events
			// via HandlerManager or other event bus
			styleSocket.setCurrentPages(pageReferance);

			if (pageData.type == PageType.SUMMARY) {
				((PageDataSummary) pageData).setAssessmentSessionData(sessionDataManager.getAssessmentSessionDataSocket());
			}
			assessmentController.initPage(pageData);
			if (pageData.type == PageType.SUMMARY) {
				getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.SUMMARY_PAGE_LOADED));
			}
			if (pageData.type == PageType.TOC) {
				getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.TOC_PAGE_LOADED));
			}
			if (pageData.type == PageType.TEST) {
				getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.TEST_PAGE_LOADED));
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TEST_PAGE_LOADED), new CurrentPageScope());
			}
			updatePageStyle();
		}
		if (event.getType() == FlowProcessingEventType.PAGE_CHANGING || event.getType() == FlowProcessingEventType.CHECK
				|| event.getType() == FlowProcessingEventType.SHOW_ANSWERS) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.BEFORE_FLOW));
		}
		deliveryEventsHub.onFlowProcessingEvent(event);
	}

	@Override
	public void onPageEvent(PageEvent event) {
		if (event.getValue() instanceof FlowProcessingEvent) {
			onFlowProcessingEvent((FlowProcessingEvent) event.getValue());
		}
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getValue() instanceof FlowProcessingEvent) {
			onFlowProcessingEvent((FlowProcessingEvent) event.getValue());
		}
	}

	public DeliveryEventsListener getDeliveryEventsListener() {
		return deliveryEventsHub;
	}

	@Override
	public String getStateString() {
		assessmentController.closePage();
		JSONArray jsonState = prepareJSONState();
		return jsonState.toString();
	}

	private JSONArray prepareJSONState() {
		JSONArray state = new JSONArray();
		state.set(0, flowManager.getState());
		state.set(1, sessionDataManager.getState());
		state.set(2, extensionsManager.getState());
		state.set(3, playerWorkModeState.getState());
		return state;
	}

	@Override
	public void setStateString(String state) {
		stateAsync = state;
	}

	@Override
	public String getEngineMode() {
		if (dataManager.getMode() == DataSourceManagerMode.NONE) {
			return EngineMode.NONE.toString();
		} else if (dataManager.getMode() == DataSourceManagerMode.LOADING_ASSESSMENT) {
			return EngineMode.ASSESSMENT_LOADING.toString();
		} else if (dataManager.getMode() == DataSourceManagerMode.LOADING_ITEMS) {
			return EngineMode.ITEM_LOADING.toString();
		}

		return EngineMode.RUNNING.toString();
	}

	@Override
	public void setFlowOptions(FlowOptions o) {
		flowManager.setFlowOptions(o);
	}

	@Override
	public void setDisplayOptions(DisplayOptions o) {
		flowManager.setDisplayOptions(o);
	}

	public void updateAssessmentStyle() {
		String userAgent = userAgentUtil.getUserAgentString();
		List<String> links = dataManager.getAssessmentStyleLinksForUserAgent(userAgent);
		styleManager.registerAssessmentStyles(links);
	}

	public void updatePageStyle() {
		String userAgent = userAgentUtil.getUserAgentString();
		List<String> links = dataManager.getPageStyleLinksForUserAgent(flowManager.getPageReference(), userAgent);
		styleManager.registerItemStyles(links);
	}

	@Override
	public void setInitialItemIndex(Integer num) {
		initialItemIndex = num;
	}
}
