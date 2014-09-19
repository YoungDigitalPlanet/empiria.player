package eu.ydp.empiria.player.client.controller.delivery;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.*;
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
import eu.ydp.empiria.player.client.controller.extensions.internal.SoundProcessorManagerExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusService;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;
import eu.ydp.empiria.player.client.controller.extensions.types.*;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.processing.DefaultFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.session.times.SessionTimeUpdater;
import eu.ydp.empiria.player.client.controller.style.StyleLinkManager;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleModuleInstanceProvider;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistry;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.page.PageEvent;
import eu.ydp.empiria.player.client.util.events.page.PageEventHandler;
import eu.ydp.empiria.player.client.util.events.page.PageEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
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
	private final ModuleFactory extensionFactory;
	private final ModuleProviderFactory moduleProviderFactory;
	private final SingleModuleInstanceProvider singleModuleInstanceProvider;
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

	private JavaScriptObject playerJsObject;
	private String stateAsync;

	@Inject
	public DeliveryEngine(PlayerViewSocket playerViewSocket, DataSourceManager dataManager, StyleSocket styleSocket, SessionDataManager sessionDataManager,
	                      EventsBus eventsBus, ModuleFactory extensionFactory, ModuleProviderFactory moduleProviderFactory,
	                      SingleModuleInstanceProvider singleModuleInstanceProvider, ModuleHandlerManager moduleHandlerManager, SessionTimeUpdater sessionTimeUpdater,
	                      ModulesRegistry modulesRegistry, TutorService tutorService, BonusService bonusService, FlowManager flowManager,
	                      ProgressBonusService progressBonusService, DeliveryEventsHub deliveryEventsHub, StyleLinkManager styleManager, UserAgentUtil userAgentUtil) {
		this.playerViewSocket = playerViewSocket;
		this.dataManager = dataManager;
		this.sessionDataManager = sessionDataManager;
		this.eventsBus = eventsBus;
		this.styleManager = styleManager;
		this.extensionFactory = extensionFactory;
		this.moduleProviderFactory = moduleProviderFactory;
		this.singleModuleInstanceProvider = singleModuleInstanceProvider;
		this.moduleHandlerManager = moduleHandlerManager;
		this.modulesRegistry = modulesRegistry;
		this.tutorService = tutorService;
		this.bonusService = bonusService;
		this.flowManager = flowManager;
		this.deliveryEventsHub = deliveryEventsHub;
		this.progressBonusService = progressBonusService;
		this.userAgentUtil = userAgentUtil;
		dataManager.setDataLoaderEventListener(this);
		this.styleSocket = styleSocket;

		eventsBus.addHandler(PageEvent.getTypes(PageEventTypes.values()), this);
		eventsBus.addHandler(PlayerEvent.getTypes(PlayerEventTypes.values()), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), sessionTimeUpdater);
	}

	public void init(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;

		extensionsManager = PlayerGinjectorFactory.getPlayerGinjector().getExtensionsManager();

		soundProcessorManager = new SoundProcessorManagerExtension();

		flowManager.addCommandProcessor(new DefaultFlowRequestProcessor(flowManager.getFlowCommandsExecutor()));

		assessmentController = new AssessmentController(playerViewSocket.getAssessmentViewSocket(), flowManager.getFlowSocket(),
				deliveryEventsHub.getInteractionSocket(), sessionDataManager, modulesRegistry);

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
	public void onAssessmentLoadingError() {

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

	protected void initFlow() {

		JSONArray deState = null;

		if (stateAsync != null) {

			deState = (JSONArray) JSONParser.parseLenient(stateAsync);

			assessmentController.reset();

			sessionDataManager.setState((JSONArray) deState.get(1));

			extensionsManager.setState(deState.get(2).isArray());

			flowManager.deinitFlow();

		}

		IFlowRequest flowRequest = parseFlowRequest(deState);
		if (flowRequest != null) {
			flowManager.invokeFlowRequest(flowRequest);
		}

		flowManager.initFlow();
	}

	protected IFlowRequest parseFlowRequest(JSONArray deState) {
		IFlowRequest flowRequest = null;

		if (deState != null) {
			if (initialItemIndex != null) {
				flowRequest = new FlowRequest.NavigateGotoItem(initialItemIndex);
			} else if (deState.get(0).isNumber() != null) {
				flowRequest = new FlowRequest.NavigateGotoItem((int) deState.get(0).isNumber().doubleValue());
			} else if (deState.get(0).isString() != null) {
				if (deState.get(0).isString().stringValue().equals(PageType.TOC.toString())) {
					flowRequest = new FlowRequest.NavigateToc();
				} else if (deState.get(0).isString().stringValue().equals(PageType.SUMMARY.toString())) {
					flowRequest = new FlowRequest.NavigateSummary();
				}
			}
		} else if (initialItemIndex != null) {
			flowRequest = new FlowRequest.NavigateGotoItem(initialItemIndex);
		}

		return flowRequest;
	}

	protected void loadPredefinedExtensions() {
		loadExtension(extensionFactory.getPlayerCoreApiExtension());
		loadExtension(extensionFactory.getScormSupportExtension());
		loadExtension(extensionFactory.getAssessmentJsonReportExtension());
		loadExtension(soundProcessorManager);
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getDivModule(), ModuleTagName.DIV));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getGroupModule(), ModuleTagName.GROUP));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSpanModule(), ModuleTagName.SPAN));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTextInteractionModule(), ModuleTagName.TEXT_INTERACTION));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getImgModule(), ModuleTagName.IMG, false, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getChoiceModule(), ModuleTagName.CHOICE_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSelectionModule(), ModuleTagName.SELECTION_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getIdentificationModule(), ModuleTagName.IDENTYFICATION_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTextEntryGapModule(), ModuleTagName.TEXT_ENTRY_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTextEntryMathGapModule(), ModuleTagName.MATH_GAP_TEXT_ENTRY_TYPE, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getDragGapModule(), ModuleTagName.DRAG_GAP, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getInlineChoiceMathGapModule(), ModuleTagName.MATH_GAP_INLINE_CHOICE_TYPE, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getInlineChoiceModule(), ModuleTagName.INLINE_CHOICE_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSimpleTextModule(), ModuleTagName.SIMPLE_TEXT));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getObjectModule(), ModuleTagName.AUDIO_PLAYER, false, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getInlineContainerModule(), ModuleTagName.INLINE_CONTAINER_STYLE_STRONG, false, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getMathTextModule(), ModuleTagName.MATH_TEXT, false, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getMathModule(), ModuleTagName.MATH_INTERACTION));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getObjectModule(), ModuleTagName.OBJECT, false, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSlideshowPlayerModule(), ModuleTagName.SLIDESHOW_PLAYER));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getFlashModule(), ModuleTagName.FLASH));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSimulationModule(), ModuleTagName.SIMULATION_PLAYER));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSourceListModule(), ModuleTagName.SOURCE_LIST));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getLabellingModule(), ModuleTagName.LABELLING_INTERACTION));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getOrderInteractionModule(), ModuleTagName.ORDER_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getColorfillInteractionModule(), ModuleTagName.COLORFILL_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTutorModule(), ModuleTagName.TUTOR, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getButtonModule(), ModuleTagName.BUTTON, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getBonusModule(), ModuleTagName.BONUS, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getProgressBonusModule(), ModuleTagName.PROGRESS_BONUS, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getVideoModule(), ModuleTagName.VIDEO, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getDictionaryModule(), ModuleTagName.DICTIONARY, false));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTextEditorModule(), ModuleTagName.OPEN_QUESTION, false));
		loadExtension(singleModuleInstanceProvider.getInfoModuleConnectorExtension());
		loadExtension(new ReportModuleConnectorExtension());
		loadExtension(singleModuleInstanceProvider.getLinkModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getPromptModule(), ModuleTagName.PROMPT));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTableModule(), ModuleTagName.TABLE));
		loadExtension(new NextPageButtonModuleConnectorExtension());
		loadExtension(new PrevPageButtonModuleConnectorExtension());
		loadExtension(new PageSwitchModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getPageInPageModule(), ModuleTagName.PAGE_IN_PAGE));
		loadExtension(moduleProviderFactory.getCheckButtonModuleConnectorExtension().get());
		loadExtension(moduleProviderFactory.getShowAnswersButtonModuleConnectorExtension().get());
		loadExtension(moduleProviderFactory.getResetButtonModuleConnectorExtension().get());
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getShapeModule(), ModuleTagName.SHAPE));
		loadExtension(moduleProviderFactory.getAudioMuteButtonModuleConnectorExtension().get());
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSubHtmlContainerModule(), ModuleTagName.SUB));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getSupHtmlContainerModule(), ModuleTagName.SUP));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getConnectionModule(), ModuleTagName.MATCH_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getTextActionProcessor(), ModuleTagName.TEXT_FEEDBACK));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getImageActionProcessor(), ModuleTagName.IMAGE_FEEDBACK));
		loadExtension(new SimpleConnectorExtension(moduleProviderFactory.getDrawingModule(), ModuleTagName.DRAWING));
		loadExtension(moduleProviderFactory.getMediaProcessor().get());
		loadExtension(PlayerGinjectorFactory.getPlayerGinjector().getMultiPage());
		loadExtension(PlayerGinjectorFactory.getPlayerGinjector().getPage());
		loadExtension(PlayerGinjectorFactory.getPlayerGinjector().getBookmarkProcessorExtension());
		loadExtension(PlayerGinjectorFactory.getPlayerGinjector().getStickiesProcessorExtension());
		loadExtension(moduleProviderFactory.getTutorApiExtension().get());
	}

	protected void loadLibraryExtensions() {
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

	protected void integrateExtension(Extension extension) {
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

	protected void initExtensions() {
		extensionsManager.init();
		employExtensions();
	}

	protected void employExtensions() {
		for (Extension currExtension : extensionsManager.getExtensions()) {
			employExtension(currExtension);
		}
	}

	protected void employExtension(Extension extension) {
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
		JSONArray deState = new JSONArray();
		if (flowManager.getCurrentPageType() == PageType.TEST) {
			deState.set(0, new JSONNumber(flowManager.getCurrentPageIndex()));
		} else if (flowManager.getCurrentPageType() == PageType.TOC || flowManager.getCurrentPageType() == PageType.SUMMARY) {
			deState.set(0, new JSONString(flowManager.getCurrentPageType().toString()));
		} else {
			deState.set(0, JSONNull.getInstance());
		}
		deState.set(1, sessionDataManager.getState());
		deState.set(2, extensionsManager.getState());
		return deState;
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
