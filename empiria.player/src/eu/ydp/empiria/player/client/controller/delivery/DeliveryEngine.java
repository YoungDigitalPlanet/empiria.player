package eu.ydp.empiria.player.client.controller.delivery;

import java.util.List;
import java.util.Vector;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.AssessmentController;
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
import eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.AudioPlayerModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ChoiceModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.DivModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.GroupModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.IdentificationModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ImgModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.InfoModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.InlineChoiceModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.LinkModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.MathModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.MathTextModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.NextPageButtonExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ObjectModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PrevPageButtonExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.PromptModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ReportModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SelectionModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SimpleTextModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SlideshowPlayerModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.SpanModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.TableModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.TextEntryModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.TextInteractionModuleConnectorExtension;
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
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StyleSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.processing.DefaultFlowRequestProcessor;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.controller.style.StyleLinkManager;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistry;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;
import eu.ydp.empiria.player.client.view.player.PlayerViewCarrier;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;

/**
 * Responsible for: - loading the content, - managing the content, - delivering
 * content to player, - managing state, results and reports about the
 * assessments.
 * 
 * @author Rafal Rybacki
 * 
 */
public class DeliveryEngine implements DataLoaderEventListener,
		FlowProcessingEventsListener, DeliveryEngineSocket {

	public EngineModeManager mode;

	private StyleLinkManager styleManager;

	private DataSourceManager dataManager;

	private ExtensionsManager extensionsManager;

	private FlowManager flowManager;

	private SessionDataManager sessionDataManager;

	private DeliveryEventsHub deliveryEventsHub;

	private PlayerViewSocket playerViewSocket;

	private AssessmentController assessmentController;

	private StyleSocket styleSocket;
	
	private ModulesRegistry modulesRegistry;

	protected JavaScriptObject playerJsObject;
	
	protected String stateAsync;

	/**
	 * C'tor.
	 */
	@Inject
	public DeliveryEngine(PlayerViewSocket pvs, DataSourceManager dsm,
			StyleSocket ss) {

		playerViewSocket = pvs;
		dataManager = dsm;
		dsm.setDataLoaderEventListener(this);
		styleSocket = ss;
	}

	public void init(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;

		modulesRegistry = new ModulesRegistry();
		mode = new EngineModeManager();
		styleManager = new StyleLinkManager();

		extensionsManager = new ExtensionsManager();

		deliveryEventsHub = new DeliveryEventsHub();

		flowManager = new FlowManager(this);
		flowManager.addCommandProcessor(new DefaultFlowRequestProcessor(
				flowManager.getFlowCommandsExecutor()));
		sessionDataManager = new SessionDataManager(deliveryEventsHub);

		assessmentController = new AssessmentController(
				playerViewSocket.getAssessmentViewSocket(),
				flowManager.getFlowSocket(),
				deliveryEventsHub.getInteractionSocket(), sessionDataManager, modulesRegistry);
		assessmentController.setStyleSocket(styleSocket);

		deliveryEventsHub.addFlowActivityEventsListener(assessmentController);

		playerViewSocket.setPlayerViewCarrier(new PlayerViewCarrier());
		
		loadPredefinedExtensions();
	}

	public void load(String url) {
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADING));
		dataManager.loadMainDocument(url);
	}

	public void load(XMLData assessmentData, XMLData[] itemsData) {
		getDeliveryEventsListener().onDeliveryEvent(new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADING));
		dataManager.loadData(assessmentData, itemsData);
	}

	@Override
	public void onAssessmentLoaded() {
	}

	@Override
	public void onAssessmentLoadingError() {

	}

	@Override
	public void onDataReady() {
		loadLibraryExtensions();
		sessionDataManager.init(dataManager.getItemsCount(), dataManager.getInitialData());
		initExtensions();
		flowManager.init(dataManager.getItemsCount());
		assessmentController.init(dataManager.getAssessmentData());
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADED));
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_STARTING));
		updateAssessmentStyle();
		initFlow();
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_STARTED));
		updatePageStyle();
	}
	
	protected void initFlow(){

		if (stateAsync != null){
			try {
				JSONArray deState = (JSONArray) JSONParser.parse(stateAsync);
	
				assessmentController.reset();
	
				sessionDataManager.setState((JSONArray) deState.get(1));
	
				extensionsManager.setState(deState.get(2).isArray());
	
				flowManager.deinitFlow();
	
				if (deState.get(0).isNumber() != null) {
					flowManager.invokeFlowRequest(new FlowRequest.NavigateGotoItem(
							(int) deState.get(0).isNumber().doubleValue()));
				} else if (deState.get(0).isString() != null) {
					if (deState.get(0).isString().stringValue()
							.equals(PageType.TOC.toString()))
						flowManager
								.invokeFlowRequest(new FlowRequest.NavigateToc());
					else if (deState.get(0).isString().stringValue()
							.equals(PageType.SUMMARY.toString()))
						flowManager
								.invokeFlowRequest(new FlowRequest.NavigateSummary());
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
		loadExtension(new DivModuleConnectorExtension());
		loadExtension(new GroupModuleConnectorExtension());
		loadExtension(new SpanModuleConnectorExtension());
		loadExtension(new TextInteractionModuleConnectorExtension());
		loadExtension(new ImgModuleConnectorExtension());
		loadExtension(new ChoiceModuleConnectorExtension());
		loadExtension(new SelectionModuleConnectorExtension());
		loadExtension(new IdentificationModuleConnectorExtension());
		loadExtension(new TextEntryModuleConnectorExtension());
		loadExtension(new InlineChoiceModuleConnectorExtension());
		loadExtension(new SimpleTextModuleConnectorExtension());
		loadExtension(new AudioPlayerModuleConnectorExtension());
		loadExtension(new MathTextModuleConnectorExtension());
		loadExtension(new MathModuleConnectorExtension());
		loadExtension(new ObjectModuleConnectorExtension());
		loadExtension(new SlideshowPlayerModuleConnectorExtension());
		loadExtension(new InfoModuleConnectorExtension());
		loadExtension(new ReportModuleConnectorExtension());
		loadExtension(new LinkModuleConnectorExtension());
		loadExtension(new PromptModuleConnectorExtension());
		loadExtension(new TableModuleConnectorExtension());
		loadExtension(new NextPageButtonExtension());
		loadExtension(new PrevPageButtonExtension());
	}
	
	protected void loadLibraryExtensions(){
		List<LibraryExtension> extCreators = dataManager.getExtensionCreators();
		for (LibraryExtension ext : extCreators){
			if (ext instanceof LibraryExternalExtension){
				JavaScriptObject extInstance = ((LibraryExternalExtension)ext).getExtensionInstance();
				if (extInstance != null){
					loadExtension(extInstance);
				}
			} else if (ext instanceof LibraryInternalExtension){
				loadExtension(((LibraryInternalExtension)ext).getName());
			}
		}
	}

	public void loadExtension(JavaScriptObject extension) {
		List<Extension> currExtensions = extensionsManager
				.addExtension(extension);
		for (Extension currExtension : currExtensions) {
			integrateExtension(currExtension);
		}
	}

	public void loadExtension(String extensionName) {
		Extension currExtension = extensionsManager
				.getInternaleExtensionByName(extensionName);
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
			if (extension instanceof ModuleConnectorExtension) {
				modulesRegistry.registerModuleCreator(((ModuleConnectorExtension) extension).getModuleNodeName(), ((ModuleConnectorExtension) extension).getModuleCreator());
			}
		}
	}
	
	protected void initExtensions(){
		extensionsManager.init();
		employExtensions();
	}

	protected void employExtensions(){
		for (Extension currExtension : extensionsManager.getExtensions()) {
			employExtension(currExtension);
		}
	}
	
	protected void employExtension(Extension extension){
		if (extension instanceof FlowRequestProcessorExtension) {
			flowManager
					.addCommandProcessor(((FlowRequestProcessorExtension) extension));
		}
		if (extension instanceof AssessmentHeaderViewExtension) {
			assessmentController
					.setHeaderViewSocket(((AssessmentHeaderViewExtension) extension).getAssessmentHeaderViewSocket());
		}
		if (extension instanceof AssessmentFooterViewExtension) {
			assessmentController
					.setFooterViewSocket(((AssessmentFooterViewExtension) extension).getAssessmentFooterViewSocket());
		}
		
	}

	@Override
	public void onFlowExecutionEvent(FlowProcessingEvent event) {
		if (event.getType() == FlowProcessingEventType.PAGE_LOADED) {
			PageReference pr = flowManager.getPageReference();
			PageData pd = dataManager.generatePageData(pr);

			getDeliveryEventsListener().onDeliveryEvent(
					new DeliveryEvent(DeliveryEventType.PAGE_UNLOADING));

			assessmentController.closePage();

			getDeliveryEventsListener().onDeliveryEvent(
					new DeliveryEvent(DeliveryEventType.PAGE_UNLOADED));

			getDeliveryEventsListener().onDeliveryEvent(
					new DeliveryEvent(DeliveryEventType.PAGE_LOADING));

			// TODO style provider should listen directly to navigation events
			// via HandlerManager or other event bus
			styleSocket.setCurrentPages(pr);

			if (pd.type == PageType.SUMMARY)
				((PageDataSummary) pd).setAssessmentSessionData(sessionDataManager
						.getAssessmentSessionDataSocket());
			assessmentController.initPage(pd);
			if (pd.type == PageType.SUMMARY)
				getDeliveryEventsListener()
						.onDeliveryEvent(
								new DeliveryEvent(
										DeliveryEventType.SUMMARY_PAGE_LOADED));
			if (pd.type == PageType.TOC)
				getDeliveryEventsListener().onDeliveryEvent(
						new DeliveryEvent(DeliveryEventType.TOC_PAGE_LOADED));
			if (pd.type == PageType.TEST)
				getDeliveryEventsListener().onDeliveryEvent(
						new DeliveryEvent(DeliveryEventType.TEST_PAGE_LOADED));

			updatePageStyle();
		}
		getFlowExecutionEventsListener().onFlowExecutionEvent(event);
	}

	public DeliveryEventsListener getDeliveryEventsListener() {
		return deliveryEventsHub;
	}

	public FlowProcessingEventsListener getFlowExecutionEventsListener() {
		return deliveryEventsHub;
	}

	public String getStateString() {
		JSONArray deState = new JSONArray();
		if (flowManager.getCurrentPageType() == PageType.TEST)
			deState.set(0, new JSONNumber(flowManager.getCurrentPageIndex()));
		else if (flowManager.getCurrentPageType() == PageType.TOC
				|| flowManager.getCurrentPageType() == PageType.SUMMARY)
			deState.set(0, new JSONString(flowManager.getCurrentPageType()
					.toString()));
		else
			deState.set(0, JSONNull.getInstance());
		deState.set(1, sessionDataManager.getState());
		deState.set(2, extensionsManager.getState());
		return deState.toString();
	}

	public void setStateString(String state) {
		stateAsync = state;
	}

	public String getEngineMode() {
		if (dataManager.getMode() == DataSourceManagerMode.NONE)
			return EngineMode.NONE.toString();
		else if (dataManager.getMode() == DataSourceManagerMode.LOADING_ASSESSMENT)
			return EngineMode.ASSESSMENT_LOADING.toString();
		else if (dataManager.getMode() == DataSourceManagerMode.LOADING_ITEMS)
			return EngineMode.ITEM_LOADING.toString();

		return EngineMode.RUNNING.toString();
	}

	public void setFlowOptions(FlowOptions o) {
		flowManager.setFlowOptions(o);
	}

	public void setDisplayOptions(DisplayOptions o) {
		flowManager.setDisplayOptions(o);
	}

	public void updateAssessmentStyle() {
		String userAgent = styleManager.getUserAgent();
		Vector<String> links = dataManager
				.getAssessmentStyleLinksForUserAgent(userAgent);
		styleManager.registerAssessmentStyles(links);
	}

	public void updatePageStyle() {
		String userAgent = styleManager.getUserAgent();
		Vector<String> links = dataManager.getPageStyleLinksForUserAgent(
				flowManager.getPageReference(), userAgent);
		styleManager.registerItemStyles(links);
	}

}
