package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

public class DefaultVariableProcessor extends VariableProcessor {

	private Map<String, List<ResponseValue>> groupsCorrectAnswers;
	private Map<String, List<Boolean>> groupsAnswersUsed;
	private final Map<String, List<Boolean>> responsesAnswersEvaluation = new HashMap<String, List<Boolean>>();

	public static final String MISTAKES = "MISTAKES";
	public static final String LASTMISTAKEN = "LASTMISTAKEN";
	public static final String LASTCHANGE = "LASTCHANGE";
	public static final String PREVIOUS = "PREVIOUS";
	public static final String DONECHANGES = "DONECHANGES";
	public static final String DONEHISTORY = "DONEHISTORY";
	public static final String TODO = "TODO";
	public static final String DONE = "DONE";
	public static final String RESET = "RESET";
	public static final String SHOW_ANSWERS = "SHOW_ANSWERS";
	public static final String CHECKS = "CHECKS";
	public static final String ERRORS = "ERRORS";

	@Override
	public void processFlowActivityVariables(Map<String, Outcome> outcomes, FlowActivityEvent event) {

		if (event != null) {
			if (event.getType() == FlowActivityEventType.CHECK) {
				if (outcomes.containsKey(CHECKS)) {
					Integer value = 0;
					if (outcomes.get(CHECKS).values.size() > 0) {
						value = Integer.parseInt(outcomes.get(CHECKS).values.get(0));
					}
					value++;
					outcomes.get(CHECKS).values.clear();
					outcomes.get(CHECKS).values.add(value.toString());
				}
			}
			if (event.getType() == FlowActivityEventType.SHOW_ANSWERS) {
				if (outcomes.containsKey(SHOW_ANSWERS)) {
					Integer value = 0;
					if (outcomes.get(SHOW_ANSWERS).values.size() > 0) {
						value = Integer.parseInt(outcomes.get(SHOW_ANSWERS).values.get(0));
					}
					value++;
					outcomes.get(SHOW_ANSWERS).values.clear();
					outcomes.get(SHOW_ANSWERS).values.add(value.toString());
				}
			}
			if (event.getType() == FlowActivityEventType.RESET) {
				if (outcomes.containsKey(RESET)) {
					Integer value = 0;
					if (outcomes.get(RESET).values.size() > 0) {
						value = Integer.parseInt(outcomes.get(RESET).values.get(0));
					}
					value++;
					outcomes.get(RESET).values.clear();
					outcomes.get(RESET).values.add(value.toString());
				}
			}
		}
	}

	@Override
	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, boolean userInteract) {

		responsesAnswersEvaluation.clear();

		if (groupsCorrectAnswers == null) {
			prepareGroupsCorrectAnswers(responses);
		}

		prepareGroupsAnswersUsedMap();

		Integer points = 0;
		Integer errors = 0;
		Integer todoCount = 0;
		String currKey;

		Iterator<String> iter = responses.keySet().iterator();
		boolean passed;
		while (iter.hasNext()) {
			currKey = iter.next();

			if (!responses.get(currKey).isModuleAdded()) {
				continue;
			}

			passed = processSingleResponse(responses.get(currKey));

			if (passed) {
				points++;
			}

			// MAKRO PROCESSING
			String currKeyPrefix = currKey + "-";

			String doneKey = currKeyPrefix + DONE;
			if (outcomes.containsKey(doneKey)) {
				outcomes.get(doneKey).values.clear();
				if (passed) {
					outcomes.get(doneKey).values.add("1");
				} else {
					outcomes.get(doneKey).values.add("0");
				}
			}

			String todoKey = currKeyPrefix + TODO;
			if (outcomes.containsKey(todoKey)) {

				outcomes.get(todoKey).values.clear();
				if (responses.get(currKey).correctAnswers.getResponseValuesCount() > 0) {
					outcomes.get(todoKey).values.add("1");
					todoCount++;
				} else {
					outcomes.get(todoKey).values.add("0");
				}
			}
			String doneHistoryKey = currKeyPrefix + DONEHISTORY;
			if (outcomes.containsKey(doneHistoryKey)) {
				if (userInteract) {
					if (passed) {
						outcomes.get(doneHistoryKey).values.add("1");
					} else {
						outcomes.get(doneHistoryKey).values.add("0");
					}
				}
			}
			String doneChangesKey = currKeyPrefix + DONECHANGES;
			if (outcomes.containsKey(doneChangesKey) && outcomes.containsKey(doneHistoryKey)) {
				if (userInteract) {
					if (outcomes.get(doneHistoryKey).values.size() == 1) {
						outcomes.get(doneChangesKey).values.add(outcomes.get(doneHistoryKey).values.get(0));
					} else {
						int currModuleScore = Integer.parseInt(outcomes.get(doneHistoryKey).values.get(outcomes.get(doneHistoryKey).values.size() - 1));
						int prevModuleScore = Integer.parseInt(outcomes.get(doneHistoryKey).values.get(outcomes.get(doneHistoryKey).values.size() - 2));
						outcomes.get(doneChangesKey).values.add(String.valueOf(currModuleScore - prevModuleScore));
					}
				}
			}
			String previousKey = currKeyPrefix + PREVIOUS;
			String lastChangeKey = currKeyPrefix + LASTCHANGE;
			if (outcomes.containsKey(previousKey) && outcomes.containsKey(lastChangeKey)) {
				if (userInteract) {
					outcomes.get(lastChangeKey).values = DefaultVariableProcessorHelper.getDifference(responses.get(currKey), outcomes.get(previousKey));
				}
			}
			if (outcomes.containsKey(previousKey)) {
				if (userInteract) {
					outcomes.get(previousKey).values.clear();
					for (int a = 0; a < responses.get(currKey).values.size(); a++) {
						outcomes.get(previousKey).values.add(responses.get(currKey).values.get(a));
					}
				}
			}
			String lastMistakenKey = currKeyPrefix + LASTMISTAKEN;
			String mistakesKey = currKeyPrefix + MISTAKES;
			if (outcomes.containsKey(lastChangeKey) && outcomes.containsKey(lastMistakenKey)) {
				if (userInteract) {
					int lastMistakes = processCheckMistakes(responses.get(currKey), outcomes.get(lastChangeKey));
					outcomes.get(lastMistakenKey).values.set(0, String.valueOf(lastMistakes));
					if (outcomes.containsKey(mistakesKey)) {
						if (outcomes.get(mistakesKey).values.size() == 0) {
							outcomes.get(mistakesKey).values.add("0");
						}
						Integer mistakes = Integer.parseInt(outcomes.get(mistakesKey).values.get(0));
						mistakes += Integer.parseInt(outcomes.get(lastMistakenKey).values.get(0));
						outcomes.get(mistakesKey).values.set(0, mistakes.toString());
					}
				}
			}
			String errorsKey = currKeyPrefix + ERRORS;
			if (outcomes.containsKey(errorsKey)) {
				outcomes.get(errorsKey).values.clear();
				boolean userHasInteracted = (outcomes.containsKey(doneHistoryKey) && outcomes.get(doneHistoryKey).values.size() > 0);
				Integer currErrors = findSingleResponseErrors(responses.get(currKey), passed, userHasInteracted);
				outcomes.get(errorsKey).values.add(currErrors.toString());
				errors += currErrors;
			}
		}

		if (outcomes.containsKey(DONE)) {
			outcomes.get(DONE).values.clear();
			outcomes.get(DONE).values.add(points.toString());
		}

		if (outcomes.containsKey(TODO)) {
			outcomes.get(TODO).values.clear();
			outcomes.get(TODO).values.add(String.valueOf(todoCount));
		}

		if (outcomes.containsKey(ERRORS)) {
			outcomes.get(ERRORS).values.clear();
			outcomes.get(ERRORS).values.add(errors.toString());
		}

		if (outcomes.containsKey(DONEHISTORY)) {
			if (userInteract) {
				outcomes.get(DONEHISTORY).values.add(points.toString());
			}
		}
		if (outcomes.containsKey(DONEHISTORY) && outcomes.containsKey(DONECHANGES)) {
			if (userInteract) {
				if (outcomes.get(DONEHISTORY).values.size() == 1) {
					outcomes.get(DONECHANGES).values.add(outcomes.get(DONEHISTORY).values.get(0));
				} else {
					int currModuleScore = Integer.parseInt(outcomes.get(DONEHISTORY).values.get(outcomes.get(DONEHISTORY).values.size() - 1));
					int prevModuleScore = Integer.parseInt(outcomes.get(DONEHISTORY).values.get(outcomes.get(DONEHISTORY).values.size() - 2));
					outcomes.get(DONECHANGES).values.add(String.valueOf(currModuleScore - prevModuleScore));
				}
			}
		}
		if (outcomes.containsKey(LASTMISTAKEN)) {
			if (userInteract) {
				Integer lastMistakes = 0;
				Iterator<String> keys = responses.keySet().iterator();
				while (keys.hasNext()) {
					String currKey2 = keys.next();
					String outcomesLastMistakenKey = currKey2 + "-" + LASTMISTAKEN;
					if (outcomes.containsKey(outcomesLastMistakenKey)) {
						lastMistakes += Integer.parseInt(outcomes.get(outcomesLastMistakenKey).values.get(0));
					}
				}
				outcomes.get(LASTMISTAKEN).values.set(0, lastMistakes.toString());

				if (outcomes.containsKey(MISTAKES)) {
					if (outcomes.get(MISTAKES).values.size() == 0) {
						outcomes.get(MISTAKES).values.add("0");
					}
					Integer mistakes = Integer.parseInt(outcomes.get(MISTAKES).values.get(0));
					mistakes += Integer.parseInt(outcomes.get(LASTMISTAKEN).values.get(0));
					outcomes.get(MISTAKES).values.set(0, mistakes.toString());
				}
			}
		}
	}

	private void prepareGroupsCorrectAnswers(Map<String, Response> responses) {
		groupsCorrectAnswers = new TreeMap<String, List<ResponseValue>>();

		for (Response currResponse : responses.values()) {
			for (String currResponseGroupName : currResponse.groups.keySet()) {
				if (!groupsCorrectAnswers.containsKey(currResponseGroupName)) {
					groupsCorrectAnswers.put(currResponseGroupName, new ArrayList<ResponseValue>());
				}
				for (Integer currResponseAnswerIndex : currResponse.groups.get(currResponseGroupName)) {
					groupsCorrectAnswers.get(currResponseGroupName).add(currResponse.correctAnswers.getResponseValue(currResponseAnswerIndex));
				}
			}
		}
	}

	private void prepareGroupsAnswersUsedMap() {
		groupsAnswersUsed = new TreeMap<String, List<Boolean>>();

		for (String currGroupName : groupsCorrectAnswers.keySet()) {
			ArrayList<Boolean> currUsed = new ArrayList<Boolean>();
			for (int i = 0; i < groupsCorrectAnswers.get(currGroupName).size(); i++) {
				currUsed.add(false);
			}
			groupsAnswersUsed.put(currGroupName, currUsed);
		}
	}

	int findSingleResponseErrors(Response response, boolean isCorrect, boolean userHasInteracted) {
		if (!isCorrect) {
			if (response.cardinality == Cardinality.SINGLE && (response.values.size() == 0 || response.values.get(0).equals(""))) {
				return 0;
			}
			if ((response.cardinality == Cardinality.COMMUTATIVE || response.cardinality == Cardinality.MULTIPLE)
					&& (response.values.size() == 0 || CollectionsUtil.indexOfNot(response.values, "") == -1)) {
				return 0;
			}
			if (response.cardinality == Cardinality.ORDERED
					&& (!userHasInteracted || response.values.size() == 0 || CollectionsUtil.indexOfNot(response.values, "") == -1)) {
				return 0;
			}
			return 1;
		}
		return 0;
	}

	protected boolean processSingleResponse(Response response) {

		CorrectAnswers correctAnswers = response.correctAnswers;

		Vector<String> userAnswers = response.values;

		ArrayList<Boolean> answersEvaluation = new ArrayList<Boolean>();

		boolean answerFound;
		boolean passed = true;

		if (response.cardinality == Cardinality.COMMUTATIVE || response.cardinality == Cardinality.ORDERED
				|| (response.cardinality == Cardinality.SINGLE && response.groups.size() > 0)) {

			if (Evaluate.CORRECT.equals(response.evaluate)) {
				throw new UnsupportedOperationException();
			} else {
				// DEFAULT is a case Evaluate.USER

				if (correctAnswers.getResponseValuesCount() != userAnswers.size()) {
					passed = false;
				} else {

					for (int correct = 0; correct < correctAnswers.getResponseValuesCount(); correct++) {

						String groupName = null;
						for (String currGroupName : response.groups.keySet()) {
							if (response.groups.get(currGroupName).contains(correct)) {
								groupName = currGroupName;
								break;
							}
						}

						String currUserAnswer = userAnswers.get(correct);

						if (groupName == null) {
							if (!correctAnswers.getResponseValue(correct).getAnswers().contains(currUserAnswer)) {
								passed = false;
								answersEvaluation.add(false);
							} else {
								answersEvaluation.add(true);
							}
						} else {
							List<ResponseValue> currGroupCorrectAnswers = groupsCorrectAnswers.get(groupName);
							List<Boolean> currGroupAnswersUsed = groupsAnswersUsed.get(groupName);

							answerFound = false;
							for (int a = 0; a < currGroupCorrectAnswers.size(); a++) {
								if (currGroupAnswersUsed.get(a)) {
									continue;
								}
								if (currGroupCorrectAnswers.get(a).getAnswers().contains(currUserAnswer)) {
									currGroupAnswersUsed.set(a, true);
									answerFound = true;
									break;
								}
							}
							if (!answerFound && passed) {
								passed = false;
							}
							answersEvaluation.add(answerFound);
						}
					}
				}
			}
		} else if (response.cardinality == Cardinality.SINGLE) {
			passed = processSingleResponseCardinalitySingle(correctAnswers, userAnswers, answersEvaluation);
		} else if (response.cardinality == Cardinality.MULTIPLE) {
			passed = processSingleResponseCardinalityMultiple(response, correctAnswers, userAnswers, answersEvaluation);
		}

		responsesAnswersEvaluation.put(response.identifier, answersEvaluation);

		return passed;

	}

	protected boolean processSingleResponseCardinalitySingle(CorrectAnswers correctAnswers, Vector<String> userAnswers, ArrayList<Boolean> answersEvaluation) {
		boolean passed = ((userAnswers.size() == 0) || !correctAnswers.containsAnswer(userAnswers.get(0))) ? false : true;
		answersEvaluation.add(passed);
		return passed;
	}

	protected boolean processSingleResponseCardinalityMultiple(Response response, CorrectAnswers correctAnswers, Vector<String> userAnswers,
			ArrayList<Boolean> answersEvaluation) {

		boolean passed = correctAnswers.getResponseValuesCount() > 0;

		if (passed) {
			boolean answerFound;
			if (Evaluate.USER.equals(response.evaluate)) {
				for (String userAnswer : userAnswers) {
					answerFound = false;
					for (int correct = 0; correct < correctAnswers.getResponseValuesCount(); correct++) {
						if (correctAnswers.getResponseValue(correct).getAnswers().contains(userAnswer)) {
							answerFound = true;
							break;
						}
					}

					if (!answerFound) {
						passed = false;
					}

					answersEvaluation.add(answerFound);
				}
			} else {
				// case Evaluate.CORRECT
				for (int correct = 0; correct < correctAnswers.getResponseValuesCount(); correct++) {

					answerFound = false;

					loop: for (int user = 0; user < userAnswers.size(); user++) {
						for (String currCorrectAnswerValue : correctAnswers.getResponseValue(correct).getAnswers()) {
							if (currCorrectAnswerValue.equals(userAnswers.get(user))) {
								answerFound = true;
								break loop;
							}
						}
					}

					if (!answerFound) {
						passed = false;
					}

					answersEvaluation.add(answerFound);
				}
			}
		}
		return passed;
	}

	protected int processCheckMistakes(Response response, Outcome moduleLastChange) {
		int mistakesCounter = 0;

		if (response.cardinality == Cardinality.SINGLE || response.cardinality == Cardinality.MULTIPLE) {
			for (int v = 0; v < moduleLastChange.values.size(); v++) {
				String currVal = moduleLastChange.values.get(v);

				if (currVal.startsWith("+")) {
					currVal = currVal.substring(1);
				} else {
					continue;
				}

				boolean answerFound = false;

				if (response.correctAnswers.containsAnswer(currVal)) {
					answerFound = true;
				}

				if (!answerFound) {
					mistakesCounter++;
				}
			}
		} else if (response.cardinality == Cardinality.ORDERED) {
			for (int v = 0; v < response.correctAnswers.getResponseValuesCount() && v < moduleLastChange.values.size(); v++) {
				String[] changeSplited = moduleLastChange.values.get(v).split("->");

				for (String currAnswer : response.correctAnswers.getResponseValue(v).getAnswers()) {
					if (changeSplited.length != 2) {
						continue;
					}

					if (!changeSplited[1].equals(currAnswer)) {
						mistakesCounter++;
						break;
					}
				}

				if (mistakesCounter > 0) {
					break;
				}
			}
		}

		return mistakesCounter;
	}

	@Override
	public List<Boolean> evaluateAnswer(Response currResponse) {
		return responsesAnswersEvaluation.get(currResponse.identifier);
	}

	@Override
	public void ensureVariables(Map<String, Response> responses, Map<String, Outcome> outcomes) {

		ensureVariable(outcomes, new Outcome(DONE, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(TODO, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(ERRORS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(DONEHISTORY, Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome(DONECHANGES, Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome(LASTMISTAKEN, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(CHECKS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(SHOW_ANSWERS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(RESET, Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome(MISTAKES, Cardinality.SINGLE, BaseType.INTEGER, "0"));

		if (responses.keySet().size() > 0) {

			Iterator<String> responseKeys = responses.keySet().iterator();

			while (responseKeys.hasNext()) {
				Response currResp = responses.get(responseKeys.next());
				String cri = currResp.identifier;
				String prefix = cri + "-";

				ensureVariable(outcomes, new Outcome(prefix + DONE, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + TODO, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + ERRORS, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + LASTCHANGE, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + PREVIOUS, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + LASTMISTAKEN, Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(prefix + DONEHISTORY, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + DONECHANGES, Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(prefix + MISTAKES, Cardinality.SINGLE, BaseType.INTEGER, "0"));

			}
		}

	}

	private void ensureVariable(Map<String, Outcome> outcomes, Outcome variable) {
		if (!outcomes.containsKey(variable.identifier)) {
			outcomes.put(variable.identifier, variable);
		} else if (variable.values.size() > 0 && outcomes.get(variable.identifier).values.size() == 0) {
			outcomes.get(variable.identifier).values.addAll(variable.values);
		}
	}

}
