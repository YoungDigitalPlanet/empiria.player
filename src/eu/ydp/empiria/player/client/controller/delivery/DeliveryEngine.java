package eu.ydp.empiria.player.client.controller.delivery;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.AssessmentController;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
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
import eu.ydp.empiria.player.client.controller.extensions.internal.BookmarkProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.ScormSupportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.SoundProcessorManagerExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.AudioMuteButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.CheckButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.InfoModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.LinkModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.NextPageButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PageSwitchModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PrevPageButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ReportModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ResetButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ShowAnswersButtonModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SimpleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStyleSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.AssessmentFooterViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.AssessmentHeaderViewExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowCommandsSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.InteractionEventSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleHandlerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StyleSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.processing.DefaultFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.style.StyleLinkManager;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.containers.DivModule;
import eu.ydp.empiria.player.client.module.containers.HtmlContainerModule;
import eu.ydp.empiria.player.client.module.containers.TextInteractionModule;
import eu.ydp.empiria.player.client.module.containers.group.GroupModule;
import eu.ydp.empiria.player.client.module.flash.FlashModule;
import eu.ydp.empiria.player.client.module.identification.IdentificationModule;
import eu.ydp.empiria.player.client.module.img.ImgModule;
import eu.ydp.empiria.player.client.module.inlinechoice.InlineChoiceModule;
import eu.ydp.empiria.player.client.module.math.MathModule;
import eu.ydp.empiria.player.client.module.mathtext.MathTextModule;
import eu.ydp.empiria.player.client.module.object.ObjectModule;
import eu.ydp.empiria.player.client.module.pageinpage.PageInPageModule;
import eu.ydp.empiria.player.client.module.prompt.PromptModule;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistry;
import eu.ydp.empiria.player.client.module.selection.SelectionModule;
import eu.ydp.empiria.player.client.module.shape.ShapeModule;
import eu.ydp.empiria.player.client.module.simpletext.SimpleTextModule;
import eu.ydp.empiria.player.client.module.simulation.SimulationModule;
import eu.ydp.empiria.player.client.module.slideshow.SlideshowPlayerModule;
import eu.ydp.empiria.player.client.module.span.SpanModule;
import eu.ydp.empiria.player.client.module.table.TableModule;
import eu.ydp.empiria.player.client.module.textentry.TextEntryModule;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.page.PageEvent;
import eu.ydp.empiria.player.client.util.events.page.PageEventHandler;
import eu.ydp.empiria.player.client.util.events.page.PageEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.view.player.PlayerViewCarrier;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;
import eu.ydp.gwtutil.client.collections.QueueSet;

/**
 * Responsible for: - loading the content, - managing the content, - delivering
 * content to player, - managing state, results and reports about the
 * assessments.
 *
 * @author Rafal Rybacki
 *
 */
public class DeliveryEngine implements DataLoaderEventListener, FlowProcessingEventsListener, DeliveryEngineSocket, PageEventHandler, PlayerEventHandler {

	public EngineModeManager mode;

	private StyleLinkManager styleManager;

	private final DataSourceManager dataManager;

	private ExtensionsManager extensionsManager;

	private FlowManager flowManager;

	private SessionDataManager sessionDataManager;

	private DeliveryEventsHub deliveryEventsHub;

	private final PlayerViewSocket playerViewSocket;

	private AssessmentController assessmentController;

	private final StyleSocket styleSocket;

	private ModulesRegistry modulesRegistry;

	private SoundProcessorManagerExtension soundProcessorManager;

	protected JavaScriptObject playerJsObject;

	protected String stateAsync;

	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	protected ModuleFactory moduleFactory = PlayerGinjector.INSTANCE.getModuleFactory();

	private final ModuleHandlerManager moduleHandlerManager = PlayerGinjector.INSTANCE.getModuleHandlerManager();

	/**
	 * C'tor.
	 */
	@Inject
	public DeliveryEngine(PlayerViewSocket pvs, DataSourceManager dsm, StyleSocket styleSocket) {
		playerViewSocket = pvs;
		dataManager = dsm;
		dsm.setDataLoaderEventListener(this);
		this.styleSocket = styleSocket;
		eventsBus.addHandler(PageEvent.getTypes(PageEventTypes.values()), this);
		eventsBus.addHandler(PlayerEvent.getTypes(PlayerEventTypes.values()), this);
	}

	public void init(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;

		modulesRegistry = new ModulesRegistry();
		mode = new EngineModeManager();
		styleManager = new StyleLinkManager();

		extensionsManager = new ExtensionsManager();

		deliveryEventsHub = new DeliveryEventsHub();

		soundProcessorManager = new SoundProcessorManagerExtension();

		flowManager = new FlowManager();
		flowManager.addCommandProcessor(new DefaultFlowRequestProcessor(flowManager.getFlowCommandsExecutor()));
		sessionDataManager = new SessionDataManager();

		assessmentController = new AssessmentController(playerViewSocket.getAssessmentViewSocket(), flowManager.getFlowSocket(), deliveryEventsHub.getInteractionSocket(),
				sessionDataManager, modulesRegistry, moduleHandlerManager);
		assessmentController.setStyleSocket(styleSocket);

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
		// flowManager.gotoPage(4);
	}

	protected void initFlow() {

		if (stateAsync != null) {
			try {
				JSONArray deState = (JSONArray) JSONParser.parseLenient(stateAsync);

				assessmentController.reset();

				sessionDataManager.setState((JSONArray) deState.get(1));

				extensionsManager.setState(deState.get(2).isArray());

				flowManager.deinitFlow();

				if (deState.get(0).isNumber() != null) {
					flowManager.invokeFlowRequest(new FlowRequest.NavigateGotoItem((int) deState.get(0).isNumber().doubleValue()));
				} else if (deState.get(0).isString() != null) {
					if (deState.get(0).isString().stringValue().equals(PageType.TOC.toString())) {
						flowManager.invokeFlowRequest(new FlowRequest.NavigateToc());
					} else if (deState.get(0).isString().stringValue().equals(PageType.SUMMARY.toString())) {
						flowManager.invokeFlowRequest(new FlowRequest.NavigateSummary());
					}
				}

				flowManager.initFlow();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			flowManager.initFlow();
		}
	}

	protected void loadPredefinedExtensions() {
		loadExtension(new PlayerCoreApiExtension());
		loadExtension(new ScormSupportExtension());
		loadExtension(soundProcessorManager);
		loadExtension(new SimpleConnectorExtension(new DivModule(), ModuleTagName.DIV));
		loadExtension(new SimpleConnectorExtension(new GroupModule(), ModuleTagName.GROUP));
		loadExtension(new SimpleConnectorExtension(new SpanModule(), ModuleTagName.SPAN));
		loadExtension(new SimpleConnectorExtension(new TextInteractionModule(), ModuleTagName.TEXT_INTERACTION));
		loadExtension(new SimpleConnectorExtension(new ImgModule(), ModuleTagName.IMG, false, true));
		loadExtension(new SimpleConnectorExtension(moduleFactory.getChoiceModule(), ModuleTagName.CHOICE_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new SelectionModule(), ModuleTagName.SELECTION_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new IdentificationModule(), ModuleTagName.IDENTYFICATION_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new TextEntryModule(), ModuleTagName.TEXT_ENTRY_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new InlineChoiceModule(), ModuleTagName.INLINE_CHOICE_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new SimpleTextModule(), ModuleTagName.SIMPLE_TEXT));
		loadExtension(new SimpleConnectorExtension(new ObjectModule(), ModuleTagName.AUDIO_PLAYER, false));
		loadExtension(new SimpleConnectorExtension(new MathTextModule(), ModuleTagName.MATH_TEXT, false, true));
		loadExtension(new SimpleConnectorExtension(new MathModule(), ModuleTagName.MATH_INTERACTION, true));
		loadExtension(new SimpleConnectorExtension(new ObjectModule(), ModuleTagName.OBJECT));
		loadExtension(new SimpleConnectorExtension(new SlideshowPlayerModule(), ModuleTagName.SLIDESHOW_PLAYER));
		loadExtension(new SimpleConnectorExtension(new FlashModule(), ModuleTagName.FLASH));
		loadExtension(new SimpleConnectorExtension(new SimulationModule(), ModuleTagName.SIMULATION_PLAYER));
		loadExtension(new InfoModuleConnectorExtension());
		loadExtension(new ReportModuleConnectorExtension());
		loadExtension(new LinkModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(new PromptModule(), ModuleTagName.PROMPT));
		loadExtension(new SimpleConnectorExtension(new TableModule(), ModuleTagName.TABLE));
		loadExtension(new NextPageButtonModuleConnectorExtension());
		loadExtension(new PrevPageButtonModuleConnectorExtension());
		loadExtension(new PageSwitchModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(new PageInPageModule(), ModuleTagName.PAGE_IN_PAGE));
		loadExtension(new CheckButtonModuleConnectorExtension());
		loadExtension(new ShowAnswersButtonModuleConnectorExtension());
		loadExtension(new ResetButtonModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(new ShapeModule(), ModuleTagName.SHAPE));
		loadExtension(new AudioMuteButtonModuleConnectorExtension());
		loadExtension(new SimpleConnectorExtension(new HtmlContainerModule(ModuleTagName.SUB.tagName()), ModuleTagName.SUB));
		loadExtension(new SimpleConnectorExtension(new HtmlContainerModule(ModuleTagName.SUP.tagName()), ModuleTagName.SUP));
	//	loadExtension(new SimpleConnectorExtension(moduleFactory.getConnectionModule(), ModuleTagName.CONNECTION));
		loadExtension(PlayerGinjector.INSTANCE.getDefaultMediaExtension());
		loadExtension(PlayerGinjector.INSTANCE.getMultiPage());
		loadExtension(PlayerGinjector.INSTANCE.getPage());
		loadExtension(new BookmarkProcessorExtension());
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
			if (extension instanceof StyleSocketUserExtension) {
				((JsStyleSocketUserExtension) extension).setStyleSocket(styleSocket);
			}
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
			if (extension instanceof SoundProcessorExtension) {
				soundProcessorManager.setSoundProcessorExtension((SoundProcessorExtension) extension);
			}
			if (extension instanceof ModuleConnectorExtension) {
				modulesRegistry.registerModuleCreator(((ModuleConnectorExtension) extension).getModuleNodeName(), ((ModuleConnectorExtension) extension).getModuleCreator());
			}
			if (extension instanceof ModuleHandlerExtension){
				moduleHandlerManager.addModuleHandler((ModuleHandlerExtension)extension);
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
			}
			updatePageStyle();
		}
		if (event.getType() == FlowProcessingEventType.PAGE_CHANGING || event.getType() == FlowProcessingEventType.CHECK ||
				event.getType() == FlowProcessingEventType.SHOW_ANSWERS) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.BEFORE_FLOW));
		}
		deliveryEventsHub.onFlowProcessingEvent(event);
	}
	@Override
	public void onPageEvent(PageEvent event) {
		if(event.getValue() instanceof FlowProcessingEvent) {
			onFlowProcessingEvent((FlowProcessingEvent) event.getValue());
		}
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if(event.getValue() instanceof FlowProcessingEvent){
			onFlowProcessingEvent((FlowProcessingEvent) event.getValue());
		}

	}
	public DeliveryEventsListener getDeliveryEventsListener() {
		return deliveryEventsHub;
	}

	@Override
	public String getStateString() {
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
		return deState.toString();
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
		String userAgent = styleManager.getUserAgent();
		QueueSet<String> links = dataManager.getAssessmentStyleLinksForUserAgent(userAgent);
		styleManager.registerAssessmentStyles(links);
	}

	public void updatePageStyle() {
		String userAgent = styleManager.getUserAgent();
		QueueSet<String> links = dataManager.getPageStyleLinksForUserAgent(flowManager.getPageReference(), userAgent);
		styleManager.registerItemStyles(links);
	}

}
