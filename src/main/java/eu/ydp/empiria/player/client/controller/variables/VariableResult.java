package eu.ydp.empiria.player.client.controller.variables;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;
import eu.ydp.gwtutil.client.NumberUtils;

public class VariableResult {

	private static final String DEFAULT_VALUE_FOR_INT = "0";

	private int todo;

	private int done;

	@Inject
	public VariableResult(@Assisted VariableProviderSocket variableProvider) {
		VariableUtil util = new VariableUtil(variableProvider);
		int done = NumberUtils.tryParseInt(util.getVariableValue(VariableName.DONE.toString(), DEFAULT_VALUE_FOR_INT));
		int todo = NumberUtils.tryParseInt(util.getVariableValue(VariableName.TODO.toString(), DEFAULT_VALUE_FOR_INT));

		initialize(done, todo);
	}

	public VariableResult(int done, int todo) {
		initialize(done, todo);
	}

	protected void initialize(int done, int todo) {
		this.todo = todo;
		this.done = done;
	}

	public int getResult() {
		int result = 0;
		if (todo != 0) {
			result = done * 100 / todo;
		}
		return result;
	}

}
