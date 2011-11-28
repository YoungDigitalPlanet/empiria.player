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
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsHub;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventsListener;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionsManager;
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
import eu.ydp.empiria.player.client.model.ItemVariablesAccessor;
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

	protected JavaScriptObject playerJsObject;

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
				deliveryEventsHub.getInteractionSocket(), sessionDataManager);
		assessmentController.setStyleSocket(styleSocket);

		deliveryEventsHub.addFlowActivityEventsListener(assessmentController);

		playerViewSocket.setPlayerViewCarrier(new PlayerViewCarrier());

		loadPredefinedExtensions();
	}

	public void load(String url) {
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADING));
		dataManager.loadMainDocument(url);
	}

	public void load(XMLData assessmentData, XMLData[] itemsData) {
		dataManager.loadData(assessmentData, itemsData);
	}

	@Override
	public void onAssessmentLoaded() {
		sessionDataManager.init(dataManager.getItemsCount());
	}

	@Override
	public void onAssessmentLoadingError() {

	}

	@Override
	public void onDataReady() {
		initExtensions();
		flowManager.init(dataManager.getItemsCount());
		assessmentController.init(dataManager.getAssessmentData());
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_LOADED));
		flowManager.initFlow();
		updateAssessmentStyle();
		getDeliveryEventsListener().onDeliveryEvent(
				new DeliveryEvent(DeliveryEventType.ASSESSMENT_STARTED));
		updatePageStyle();
	}

	private void loadPredefinedExtensions() {
		
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
				((JsStyleSocketUserExtension) extension)
						.setStyleSocket(styleSocket);
			}
			if (extension instanceof DeliveryEventsListenerExtension) {
				deliveryEventsHub
						.addDeliveryEventsListener(((DeliveryEventsListener) extension));
			}
			if (extension instanceof FlowCommandsSocketUserExtension) {
				((FlowCommandsSocketUserExtension) extension)
						.setFlowCommandsExecutor(flowManager
								.getFlowCommandsExecutor());
			}
			if (extension instanceof FlowRequestSocketUserExtension) {
				((FlowRequestSocketUserExtension) extension)
						.setFlowRequestsInvoker(flowManager
								.getFlowRequestInvoker());
			}
			if (extension instanceof PageInterferenceSocketUserExtension) {
				((PageInterferenceSocketUserExtension) extension)
						.setPageInterferenceSocket(assessmentController
								.getPageControllerSocket());
			}
			if (extension instanceof DataSourceDataSocketUserExtension) {
				((DataSourceDataSocketUserExtension) extension)
						.setDataSourceDataSupplier(dataManager);
			}
			if (extension instanceof SessionDataSocketUserExtension) {
				((SessionDataSocketUserExtension) extension)
						.setSessionDataSupplier(sessionDataManager);
			}
			if (extension instanceof FlowDataSocketUserExtension) {
				((FlowDataSocketUserExtension) extension)
						.setFlowDataSupplier(flowManager.getFlowDataSupplier());
			}
			if (extension instanceof DeliveryEngineSocketUserExtension) {
				((DeliveryEngineSocketUserExtension) extension)
						.setDeliveryEngineSocket(this);
			}
			if (extension instanceof InteractionEventSocketUserExtension) {
				((InteractionEventSocketUserExtension) extension)
						.setInteractionEventsListener(deliveryEventsHub);
			}
			if (extension instanceof PlayerJsObjectModifierExtension) {
				((PlayerJsObjectModifierExtension) extension)
						.setPlayerJsObject(playerJsObject);
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

	@SuppressWarnings("deprecation")
	public void setStateString(String state) {
		try {
			JSONArray deState = (JSONArray) JSONParser.parse(state);

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

	public ItemVariablesAccessor getItemVariablesAccessor() {
		return assessmentController.getItemVariablesAccessor();
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
