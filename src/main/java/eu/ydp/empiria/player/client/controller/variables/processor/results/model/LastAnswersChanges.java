package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import java.util.List;

import com.google.common.collect.Lists;

public class LastAnswersChanges {

	private List<String> addedAnswers;
	private List<String> removedAnswers;

	public LastAnswersChanges() {
		addedAnswers = Lists.newArrayList();
		removedAnswers = Lists.newArrayList();
	}

	public LastAnswersChanges(List<String> addedAnswers, List<String> removedAnswers) {
		this.addedAnswers = addedAnswers;
		this.removedAnswers = removedAnswers;
	}

	public List<String> getAddedAnswers() {
		return addedAnswers;
	}

	public void setAddedAnswers(List<String> addedAnswers) {
		this.addedAnswers = addedAnswers;
	}

	public List<String> getRemovedAnswers() {
		return removedAnswers;
	}

	public void setRemovedAnswers(List<String> removedAnswers) {
		this.removedAnswers = removedAnswers;
	}

	public boolean containChanges() {
		return (!addedAnswers.isEmpty()) || (!removedAnswers.isEmpty());
	}
}
