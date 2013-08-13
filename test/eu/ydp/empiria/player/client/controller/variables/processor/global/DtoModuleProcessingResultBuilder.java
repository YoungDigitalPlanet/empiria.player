package eu.ydp.empiria.player.client.controller.variables.processor.global;

import java.util.List;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class DtoModuleProcessingResultBuilder {

	private int todo = 0;
	private final List<String> answers = Lists.newArrayList();
	private List<Boolean> answersEvaluation = Lists.newArrayList();
	private int errors = 0;
	private int done = 0;
	private final LastAnswersChanges lastAnswerChanges = new LastAnswersChanges();
	private LastMistaken lastmistaken = LastMistaken.NONE;
	private int mistakes = 0;
	
	public DtoModuleProcessingResultBuilder withTodo(int todo){
		this.todo = todo;
		return this;
	}

	public DtoModuleProcessingResultBuilder withErrors(int errors) {
		this.errors = errors;
		return this;
	}
	
	public DtoModuleProcessingResultBuilder withDone(int done) {
		this.done = done;
		return this;
	}
	
	public DtoModuleProcessingResultBuilder withMistakes(int mistakes) {
		this.mistakes = mistakes;
		return this;
	}
	
	public DtoModuleProcessingResultBuilder withLastmistaken(LastMistaken lastmistaken) {
		this.lastmistaken = lastmistaken;
		return this;
	}
	
	public DtoModuleProcessingResultBuilder withAnswerEvaluations(List<Boolean> answerEvaluations) {
		this.answersEvaluation = answerEvaluations;
		return this;
	}
	
	public DtoModuleProcessingResult build(){
		GeneralVariables generalVariables = new GeneralVariables(answers, answersEvaluation, errors, done);
		ConstantVariables constantVariables = new ConstantVariables(todo);
		UserInteractionVariables userInteractionVariables = new UserInteractionVariables(lastAnswerChanges, lastmistaken, mistakes);
		DtoModuleProcessingResult dtoModuleProcessingResult = new DtoModuleProcessingResult(generalVariables, constantVariables, userInteractionVariables);
		return dtoModuleProcessingResult;
	}
}
