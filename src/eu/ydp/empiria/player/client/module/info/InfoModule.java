package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Strings;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.gin.binding.FlowManagerDataSupplier;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.NumberUtils;

public class InfoModule extends SimpleModuleBase implements ILifecycleModule, PlayerEventHandler {

	@Inject @FlowManagerDataSupplier protected FlowDataSupplier flowDataSupplier;
	@Inject @ModuleScoped private ModuleStyle moduleStyle;
	@Inject private StyleNameConstants styleNames;
	@Inject private EventsBus eventsBus;
	@Inject private VariableInterpreter variableInterpreter;
	@Inject private InfoModuleProgressStyleName infoModuleProgressStyleName;

	private InfoModuleUnloadListener unloadListener;
	private Panel mainPanel;
	private Panel contentPanel;
	private Element mainElement;
	private String contentString;

	public void setModuleUnloadListener(InfoModuleUnloadListener imul) {
		unloadListener = imul;
	}

	@Override
	public void initModule(Element element) {
		eventsBus.addAsyncHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_REMOVED), this, new CurrentPageScope());
		createContentPanel();
		createMainPanel();
		String cls = element.getAttribute("class");
		if (cls != null) {
			mainPanel.addStyleName(cls);
		}

		mainElement = element;
	}

	private void createMainPanel() {
		mainPanel = new FlowPanel();
		mainPanel.setStyleName(styleNames.QP_INFO());
		mainPanel.add(contentPanel);
	}

	private void createContentPanel() {
		contentPanel = new FlowPanel();
		contentPanel.setStyleName(styleNames.QP_INFO_CONTENT());
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
		if (moduleStyle.containsKey(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT)) {
			contentString = moduleStyle.get(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT);
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
		int refItemIndex = getRefItemIndex();
		if (contentString != null && contentString.length() > 0) {
			String output = replaceTemplates(contentString, refItemIndex);
			replaceContent(output);
		}
		updateProgressStyleName(refItemIndex);
	}

	private void replaceContent(String output) {
		InlineHTML html = new InlineHTML(output);
		html.setStyleName(styleNames.QP_INFO_TEXT());
		contentPanel.clear();
		contentPanel.add(html);
	}

	private int getRefItemIndex() {
		int refItemIndex = -1;
		if (mainElement.hasAttribute("itemIndex")) {
			refItemIndex = NumberUtils.tryParseInt(mainElement.getAttribute("itemIndex"), -1);
		}
		if (refItemIndex == -1) {
			refItemIndex = flowDataSupplier.getCurrentPageIndex();
		}
		return refItemIndex;
	}

	private void updateProgressStyleName(int refItemIndex) {
		String styleName = infoModuleProgressStyleName.getCurrentStyleName(refItemIndex);
		mainPanel.setStyleName(styleNames.QP_INFO());
		if(!Strings.isNullOrEmpty(styleName)){
			mainPanel.addStyleName(styleName);
		}
	}

}
