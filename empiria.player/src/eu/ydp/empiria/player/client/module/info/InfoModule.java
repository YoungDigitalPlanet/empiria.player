package eu.ydp.empiria.player.client.module.info;

import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.module.ILifecycleModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ISingleViewSimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class InfoModule implements ISingleViewSimpleModule, ILifecycleModule {

	protected DataSourceDataSupplier dataSourceDataSupplier;
	protected SessionDataSupplier sessionDataSupplier;

	protected Panel mainPanel;
	protected Panel contentPanel;
	protected int refItemIndex;
	protected FlowDataSupplier flowDataSupplier;
	protected ModuleSocket moduleSocket;
	protected Element mainElement;

	public InfoModule(DataSourceDataSupplier dsds, SessionDataSupplier sds, FlowDataSupplier fds){
		dataSourceDataSupplier = dsds;
		sessionDataSupplier = sds;
		flowDataSupplier = fds;
	}	
	
	@Override
	public void initModule(Element element, ModuleSocket ms, ModuleInteractionListener mil) {
		contentPanel = new FlowPanel();
		contentPanel.setStyleName("qp-info-content");
		
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-info");
		mainPanel.add(contentPanel);
		
		String cls = element.getAttribute("class");
		if (cls != null)
			mainPanel.addStyleName(cls);
		
		moduleSocket = ms;
		mainElement = element;
		
	}

	@Override
	public Widget getView() {
		return mainPanel;
	}

	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
	}

	@Override
	public void onClose() {
	}

	@Override
	public void onStart() {
		
		refItemIndex = -1;
		if (mainElement.hasAttribute("itemIndex")){
			refItemIndex = IntegerUtils.tryParseInt(mainElement.getAttribute("itemIndex"), -1);
		} 
		if (refItemIndex == -1){
			refItemIndex = flowDataSupplier.getCurrentPageIndex();
		}
		
		Map<String, String> styles = moduleSocket.getStyles(mainElement);
		if (styles.containsKey("-empiria-info-content")){
			String contentString = styles.get("-empiria-info-content");
			String output = replaceTemplates(contentString);
			InlineHTML html = new InlineHTML(output);
			html.setStyleName("qp-info-text");
			contentPanel.add(html);
		}
	}
	
	protected String replaceTemplates(String content){
		if (content.contains("$[item.title]")){
			content = content.replaceAll("\\$\\[item.title]", dataSourceDataSupplier.getItemTitle(refItemIndex));
		}
		
		VariableProviderSocket itemVps = sessionDataSupplier.getItemSessionDataSocket(refItemIndex).getVariableProviderSocket();
		if (content.contains("$[item.index]")){
			content = content.replaceAll("\\$\\[item.index]", String.valueOf(refItemIndex+1) );
		}
		if (content.contains("$[item.todo]")){
			content = content.replaceAll("\\$\\[item.todo]", getVariableValue(itemVps, "TODO", "0") );
		}
		if (content.contains("$[item.done]")){
			content = content.replaceAll("\\$\\[item.done]", getVariableValue(itemVps, "DONE", "0") );
		}
		if (content.contains("$[item.checks]")){
			content = content.replaceAll("\\$\\[item.checks]", getVariableValue(itemVps, "CHECKS", "0") );
		}
		if (content.contains("$[item.mistakes]")){
			content = content.replaceAll("\\$\\[item.mistakes]", getVariableValue(itemVps, "MISTAKES", "0") );
		}
		if (content.contains("$[item.show_answers]")){
			content = content.replaceAll("\\$\\[item.show_answers]", getVariableValue(itemVps, "SHOW_ANSWERS", "0") );
		}
		if (content.contains("$[item.reset]")){
			content = content.replaceAll("\\$\\[item.reset]", getVariableValue(itemVps, "RESET", "0") );
		}
		if (content.contains("$[item.result]")){
			int done = IntegerUtils.tryParseInt( getVariableValue(itemVps, "DONE", "0") );
			int todo = IntegerUtils.tryParseInt( getVariableValue(itemVps, "TODO", "0") );
			int result = 0;
			if (todo != 0){
				result = done*100/todo;
			}
			content = content.replaceAll("\\$\\[item.result]", String.valueOf(result) );
		}

		if (content.contains("$[test.title]")){
			content = content.replaceAll("\\$\\[test.title]", dataSourceDataSupplier.getAssessmentTitle());
		}
		
		VariableProviderSocket assessmentVps = sessionDataSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
		if (content.contains("$[test.todo]")){
			content = content.replaceAll("\\$\\[test.todo]", getVariableValue(assessmentVps, "TODO", "0") );
		}
		if (content.contains("$[test.done]")){
			content = content.replaceAll("\\$\\[test.done]", getVariableValue(assessmentVps, "DONE", "0") );
		}
		if (content.contains("$[test.checks]")){
			content = content.replaceAll("\\$\\[test.checks]", getVariableValue(assessmentVps, "CHECKS", "0") );
		}
		if (content.contains("$[test.mistakes]")){
			content = content.replaceAll("\\$\\[test.mistakes]", getVariableValue(assessmentVps, "MISTAKES", "0") );
		}
		if (content.contains("$[test.show_answers]")){
			content = content.replaceAll("\\$\\[test.show_answers]", getVariableValue(assessmentVps, "SHOW_ANSWERS", "0") );
		}
		if (content.contains("$[test.reset]")){
			content = content.replaceAll("\\$\\[test.reset]", getVariableValue(assessmentVps, "RESET", "0") );
		}	
		if (content.contains("$[test.result]")){
			int done = IntegerUtils.tryParseInt( getVariableValue(assessmentVps, "DONE", "0") );
			int todo = IntegerUtils.tryParseInt( getVariableValue(assessmentVps, "TODO", "0") );
			int result = 0;
			if (todo != 0){
				result = done*100/todo;
			}
			content = content.replaceAll("\\$\\[test.result]", String.valueOf(result) );
		}
		
		return content;
	}
	
	protected String getVariableValue(VariableProviderSocket vps, String name, String defaultValue){
		Variable var = vps.getVariableValue(name);
		String value = defaultValue;
		if (var != null)
			value = var.getValuesShort();
		return value;
	}

}
