package eu.ydp.empiria.player.client.module.info;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.NumberUtils;

public class InfoModule extends SimpleModuleBase implements ILifecycleModule, PlayerEventHandler {

	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected SessionDataSupplier sessionDataSupplier;
	protected FlowDataSupplier flowDataSupplier;
	protected InfoModuleUnloadListener unloadListener;
	protected final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	protected Panel mainPanel;
	protected Panel contentPanel;
	protected Element mainElement;
	protected String contentString;
	protected VariableInterpreter variableInterpreter;
	private final StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants();
	private final VariableInterpreterFactory interpreterFactory = PlayerGinjectorFactory.getPlayerGinjector().getVariableInterpreterFactory();

	public InfoModule(DataSourceDataSupplier dsds, SessionDataSupplier sds, FlowDataSupplier fds) {
		dataSourceDataSupplier = dsds;
		sessionDataSupplier = sds;
		flowDataSupplier = fds;
		variableInterpreter = interpreterFactory.getInterpreter(dataSourceDataSupplier, sessionDataSupplier);
	}

	public void setModuleUnloadListener(InfoModuleUnloadListener imul) {
		unloadListener = imul;
	}

	@Override
	public void initModule(Element element) {
		eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_REMOVED), this, new CurrentPageScope());
		contentPanel = new FlowPanel();
		contentPanel.setStyleName(styleNames.QP_INFO_CONTENT());

		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_INFO());
		mainPanel.add(contentPanel);

		String cls = element.getAttribute("class");
		if (cls != null) {
			mainPanel.addStyleName(cls);
		}

		mainElement = element;

	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	@Override
	public void onBodyLoad() {// NOPMD
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		if (event.getType() == PlayerEventTypes.PAGE_REMOVED) {
			unloadListener.moduleUnloaded();
		}
	}

	@Override
	public void onBodyUnload() { // NOPMD

	}

	@Override
	public void onSetUp() {// NOPMD
	}

	@Override
	public void onClose() {// NOPMD
	}

	@Override
	public void onStart() {
		PlayerGinjector playerGinjector = PlayerGinjectorFactory.getPlayerGinjector();
		StyleSocket styleSocket = playerGinjector.getStyleSocket();

		Map<String, String> styles = styleSocket.getStyles(mainElement);
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT)) {
			contentString = styles.get(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT);
		}
		update();
	}

	protected String replaceTemplates(String content, int refItemIndex) {// NOPMD
		return variableInterpreter.replaceAllTags(content, refItemIndex);
	}

	protected String getVariableValue(VariableProviderSocket vps, String name, String defaultValue) {
		Variable var = vps.getVariableValue(name);
		String value = defaultValue;
		if (var != null) {
			value = var.getValuesShort();
		}
		return value;
	}

	public void update() {
		if (contentString != null && contentString.length() > 0) {
			int refItemIndex = -1;
			if (mainElement.hasAttribute("itemIndex")) {
				refItemIndex = NumberUtils.tryParseInt(mainElement.getAttribute("itemIndex"), -1);
			}
			if (refItemIndex == -1) {
				refItemIndex = flowDataSupplier.getCurrentPageIndex();
			}

			String output = replaceTemplates(contentString, refItemIndex);
			InlineHTML html = new InlineHTML(output);
			html.setStyleName(styleNames.QP_INFO_TEXT());
			contentPanel.clear();
			contentPanel.add(html);
		}
	}

}
