package eu.ydp.empiria.player.client.controller.variables.processor.global.function;

import com.google.common.base.Function;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;

public class ModuleProcessingResultExtractingFunctions {

	private final Function<DtoModuleProcessingResult, Integer> extractTodoFunction = new Function<DtoModuleProcessingResult, Integer>() {
		@Override
		public Integer apply(DtoModuleProcessingResult processingResult) {
			return processingResult.getConstantVariables().getTodo();
		}
	};

	private final Function<DtoModuleProcessingResult, Integer> extractMistakesFunction = new Function<DtoModuleProcessingResult, Integer>() {
		@Override
		public Integer apply(DtoModuleProcessingResult processingResult) {
			return processingResult.getUserInteractionVariables().getMistakes();
		}
	};
	
	private final Function<DtoModuleProcessingResult, Integer> extractErrorsFunction = new Function<DtoModuleProcessingResult, Integer>() {
		@Override
		public Integer apply(DtoModuleProcessingResult processingResult) {
			return processingResult.getGeneralVariables().getErrors();
		}
	};
	
	private final Function<DtoModuleProcessingResult, Integer> extractDoneFunction = new Function<DtoModuleProcessingResult, Integer>() {
		@Override
		public Integer apply(DtoModuleProcessingResult processingResult) {
			return processingResult.getGeneralVariables().getDone();
		}
	};
	
	private final Function<DtoModuleProcessingResult, Boolean> extractLastMistakenFunction = new Function<DtoModuleProcessingResult, Boolean>() {
		@Override
		public Boolean apply(DtoModuleProcessingResult processingResult) {
			return processingResult.getUserInteractionVariables().isLastmistaken();
		}
	};

	public Function<DtoModuleProcessingResult, Integer> getExtractTodoFunction() {
		return extractTodoFunction;
	}

	public Function<DtoModuleProcessingResult, Integer> getExtractMistakesFunction() {
		return extractMistakesFunction;
	}

	public Function<DtoModuleProcessingResult, Integer> getExtractErrorsFunction() {
		return extractErrorsFunction;
	}

	public Function<DtoModuleProcessingResult, Integer> getExtractDoneFunction() {
		return extractDoneFunction;
	}

	public Function<DtoModuleProcessingResult, Boolean> getExtractLastMistakenFunction() {
		return extractLastMistakenFunction;
	}
	
}
