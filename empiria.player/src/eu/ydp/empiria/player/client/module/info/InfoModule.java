package eu.ydp.empiria.player.client.module.info;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
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
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	protected Panel mainPanel;
	protected Panel contentPanel;
	protected int refItemIndex;
	protected Element mainElement;
	protected String contentString;
	private final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();

	public InfoModule(DataSourceDataSupplier dsds, SessionDataSupplier sds, FlowDataSupplier fds) {
		dataSourceDataSupplier = dsds;
		sessionDataSupplier = sds;
		flowDataSupplier = fds;
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
		Map<String, String> styles = getModuleSocket().getStyles(mainElement);
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT)) {
			contentString = styles.get(EmpiriaStyleNameConstants.EMPIRIA_INFO_CONTENT);
		}
		update();
	}

	protected String replaceTemplates(String content) {// NOPMD
		if (content.contains("$[item.title]")) {
			content = content.replaceAll("\\$\\[item.title]", dataSourceDataSupplier.getItemTitle(refItemIndex));
		}

		VariableProviderSocket itemVps = sessionDataSupplier.getItemSessionDataSocket(refItemIndex).getVariableProviderSocket();
		if (content.contains("$[item.index]")) {
			content = content.replaceAll("\\$\\[item.index]", String.valueOf(refItemIndex + 1));
		}

		if (content.contains("$[item.page_num]")) {
			content = content.replaceAll("\\$\\[item.page_num]", String.valueOf(refItemIndex + 1));
		}
		if (content.contains("$[item.page_count]")) {
			content = content.replaceAll("\\$\\[item.page_count]", String.valueOf(dataSourceDataSupplier.getItemsCount()));
		}
		if (content.contains("$[item.todo]")) {
			content = content.replaceAll("\\$\\[item.todo]", getVariableValue(itemVps, "TODO", "0"));
		}
		if (content.contains("$[item.done]")) {
			content = content.replaceAll("\\$\\[item.done]", getVariableValue(itemVps, "DONE", "0"));
		}
		if (content.contains("$[item.checks]")) {
			content = content.replaceAll("\\$\\[item.checks]", getVariableValue(itemVps, "CHECKS", "0"));
		}
		if (content.contains("$[item.mistakes]")) {
			content = content.replaceAll("\\$\\[item.mistakes]", getVariableValue(itemVps, "MISTAKES", "0"));
		}
		if (content.contains("$[item.show_answers]")) {
			content = content.replaceAll("\\$\\[item.show_answers]", getVariableValue(itemVps, "SHOW_ANSWERS", "0"));
		}
		if (content.contains("$[item.reset]")) {
			content = content.replaceAll("\\$\\[item.reset]", getVariableValue(itemVps, "RESET", "0"));
		}
		if (content.contains("$[item.result]")) {
			int done = NumberUtils.tryParseInt(getVariableValue(itemVps, "DONE", "0"));
			int todo = NumberUtils.tryParseInt(getVariableValue(itemVps, "TODO", "0"));
			int result = 0;
			if (todo != 0) {
				result = done * 100 / todo;
			}
			content = content.replaceAll("\\$\\[item.result]", String.valueOf(result));
		}

		if (content.contains("$[test.title]")) {
			content = content.replaceAll("\\$\\[test.title]", dataSourceDataSupplier.getAssessmentTitle());
		}

		VariableProviderSocket assessmentVps = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
		if (content.contains("$[test.todo]")) {
			content = content.replaceAll("\\$\\[test.todo]", getVariableValue(assessmentVps, "TODO", "0"));
		}
		if (content.contains("$[test.done]")) {
			content = content.replaceAll("\\$\\[test.done]", getVariableValue(assessmentVps, "DONE", "0"));
		}
		if (content.contains("$[test.checks]")) {
			content = content.replaceAll("\\$\\[test.checks]", getVariableValue(assessmentVps, "CHECKS", "0"));
		}
		if (content.contains("$[test.mistakes]")) {
			content = content.replaceAll("\\$\\[test.mistakes]", getVariableValue(assessmentVps, "MISTAKES", "0"));
		}
		if (content.contains("$[test.show_answers]")) {
			content = content.replaceAll("\\$\\[test.show_answers]", getVariableValue(assessmentVps, "SHOW_ANSWERS", "0"));
		}
		if (content.contains("$[test.reset]")) {
			content = content.replaceAll("\\$\\[test.reset]", getVariableValue(assessmentVps, "RESET", "0"));
		}
		if (content.contains("$[test.result]")) {
			int done = NumberUtils.tryParseInt(getVariableValue(assessmentVps, "DONE", "0"));
			int todo = NumberUtils.tryParseInt(getVariableValue(assessmentVps, "TODO", "0"));
			int result = 0;
			if (todo != 0) {
				result = done * 100 / todo;
			}
			content = content.replaceAll("\\$\\[test.result]", String.valueOf(result));
		}

		return content;
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
			refItemIndex = -1;
			if (mainElement.hasAttribute("itemIndex")) {
				refItemIndex = NumberUtils.tryParseInt(mainElement.getAttribute("itemIndex"), -1);
			}
			if (refItemIndex == -1) {
				refItemIndex = flowDataSupplier.getCurrentPageIndex();
			}

			String output = replaceTemplates(contentString);
			InlineHTML html = new InlineHTML(output);
			html.setStyleName(styleNames.QP_INFO_TEXT());
			contentPanel.clear();
			contentPanel.add(html);
		}
	}

}
