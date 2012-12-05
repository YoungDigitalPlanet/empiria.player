package eu.ydp.empiria.player.client.module.info;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.gwtutil.client.NumberUtils;

public class VariableInterpreter {

	private SessionDataSupplier sessionDataSupplier;
	
	private DataSourceDataSupplier dataSourceDataSupplier;
	
	@Inject
	public VariableInterpreter(DataSourceDataSupplier dataSourceDataSupplier, SessionDataSupplier sessionDataSupplier) {
		this.dataSourceDataSupplier = dataSourceDataSupplier;
		this.sessionDataSupplier = sessionDataSupplier;
	}
	
	public String replaceTemplates(String content, int refItemIndex) {// NOPMD
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
			content = content.replaceAll("\\$\\[item.result]", String.valueOf(countResult(todo, done)));
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
			
			content = content.replaceAll("\\$\\[test.result]", String.valueOf(countResult(todo, done)));
		}

		return content;
	}
	
	protected int countResult(int done, int todo){
		int result = 0;
		if (todo != 0) {
			result = done * 100 / todo;
		}
		return result;
	}
	
	protected String getVariableValue(VariableProviderSocket vps, String name, String defaultValue) {
		Variable var = vps.getVariableValue(name);
		String value = defaultValue;
		if (var != null) {
			value = var.getValuesShort();
		}
		return value;
	}
	
	
}
