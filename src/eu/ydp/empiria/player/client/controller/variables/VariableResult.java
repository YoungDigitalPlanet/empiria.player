package eu.ydp.empiria.player.client.controller.variables;

import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;
import eu.ydp.gwtutil.client.NumberUtils;

public class VariableResult {
	
	private static final String DEFAULT_VALUE_FOR_INT = "0";

	private int todo;
	
	private int done;
	
	public VariableResult(VariableProviderSocket variableProvider){
		VariableUtil util = new VariableUtil(variableProvider);
		int done = NumberUtils.tryParseInt(util.getVariableValue(DONE, DEFAULT_VALUE_FOR_INT));
		int todo = NumberUtils.tryParseInt(util.getVariableValue(TODO, DEFAULT_VALUE_FOR_INT));
		
		initialize(done, todo);
	}
	
	public VariableResult(int done, int todo){
		initialize(done, todo);
	}
	
	protected void initialize(int done, int todo){
		this.todo = todo;
		this.done = done;
	}
	
	public int getResult(){
		int result = 0;
		if (todo != 0) {
			result = done * 100 / todo;
		}
		return result;
	}
	
}
