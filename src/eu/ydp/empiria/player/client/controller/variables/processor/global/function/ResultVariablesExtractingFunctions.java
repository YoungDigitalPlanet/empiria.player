package eu.ydp.empiria.player.client.controller.variables.processor.global.function;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;

public class ResultVariablesExtractingFunctions {

	private final Function<ResultVariables, Integer> extractTodoFunction = new Function<ResultVariables, Integer>() {
		@Override
		public Integer apply(ResultVariables processingResult) {
			return processingResult.getTodo();
		}
	};

	private final Function<ResultVariables, Integer> extractMistakesFunction = new Function<ResultVariables, Integer>() {
		@Override
		public Integer apply(ResultVariables processingResult) {
			return processingResult.getMistakes();
		}
	};

	private final Function<ResultVariables, Integer> extractErrorsFunction = new Function<ResultVariables, Integer>() {
		@Override
		public Integer apply(ResultVariables processingResult) {
			return processingResult.getErrors();
		}
	};

	private final Function<ResultVariables, Integer> extractDoneFunction = new Function<ResultVariables, Integer>() {
		@Override
		public Integer apply(ResultVariables processingResult) {
			return processingResult.getDone();
		}
	};

	private final Function<ResultVariables, Boolean> extractLastMistakenFunction = new Function<ResultVariables, Boolean>() {
		@Override
		public Boolean apply(ResultVariables processingResult) {
			return processingResult.isLastMistaken();
		}
	};

	public Function<ResultVariables, Integer> getExtractTodoFunction() {
		return extractTodoFunction;
	}

	public Function<ResultVariables, Integer> getExtractMistakesFunction() {
		return extractMistakesFunction;
	}

	public Function<ResultVariables, Integer> getExtractErrorsFunction() {
		return extractErrorsFunction;
	}

	public Function<ResultVariables, Integer> getExtractDoneFunction() {
		return extractDoneFunction;
	}

	public Function<ResultVariables, Boolean> getExtractLastMistakenFunction() {
		return extractLastMistakenFunction;
	}
}
