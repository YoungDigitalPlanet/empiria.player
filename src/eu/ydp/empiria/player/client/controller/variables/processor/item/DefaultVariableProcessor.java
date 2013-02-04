package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.RuntimeMobileUserAgent.IOS6;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.RuntimeMobileUserAgent.IOS6_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class DefaultVariableProcessor extends VariableProcessor {

	protected Map<String, List<ResponseValue>> groupsCorrectAnswers;
	protected Map<String, Integer> groupsPoints = new HashMap<String, Integer>();
	protected Map<String, List<Boolean>> groupsAnswersUsed;
	protected final Map<String, List<Boolean>> responsesAnswersEvaluation = new HashMap<String, List<Boolean>>();

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
	public static boolean isIOS6_0_OR_IOS6_1 = false;

	public DefaultVariableProcessor() {
		isIOS6_0_OR_IOS6_1 = UserAgentChecker.isUserAgent(IOS6, IOS6_1);
	}

	@Override
	public void processFlowActivityVariables(Map<String, Outcome> outcomes, FlowActivityEvent event) {
		if (event != null) {
			switch (event.getType()) {
			case CHECK:
				increaseOutcome(outcomes, CHECKS);
				break;
			case SHOW_ANSWERS:
				increaseOutcome(outcomes, SHOW_ANSWERS);
				break;
			case RESET:
				increaseOutcome(outcomes, RESET);
				break;
			default:
				break;
			}
		}
	}

	private void increaseOutcome(Map<String, Outcome> outcomes, String key) {
		Outcome outcome = null;
		if ((outcome = outcomes.get(key)) != null) {
			int value = 0;
			List<String> values = outcome.values;
			if (!values.isEmpty()) {
				value = Integer.parseInt(values.get(0));
			}
			value++;
			values.clear();
			values.add(String.valueOf(value));
		}
	}

	private void clearAndAddToOutcomeValues(Map<String, Outcome> outcomes, String key, String value) {
		Outcome outcome = null;
		if ((outcome = outcomes.get(key)) != null) {
			outcome.values.clear();
			outcome.values.add(value);
		}
	}

	private void addToOutcomeValues(Map<String, Outcome> outcomes, String key, String value) {
		Outcome outcome = null;
		if ((outcome = outcomes.get(key)) != null) {
			outcome.values.add(value);
		}
	}

	private int countCorrectAnswers(Response response) {
		int count = 1;
		if (response.getCountMode() == CountMode.CORRECT_ANSWERS) {
			List<Boolean> evaluations = null;
			count = 0;
			if ((evaluations = responsesAnswersEvaluation.get(response.identifier)) != null) {
				for (Boolean correct : evaluations) {
					if (correct) {
						++count;
					}
				}
			}
		}
		return count;
	}

	public int getTODOCount(Response response) {
		int value = 0;
		if (response.correctAnswers.getResponseValuesCount() > 0) {
			if (response.getCountMode() == CountMode.CORRECT_ANSWERS) {
				value = response.correctAnswers.getResponseValuesCount();
			} else {
				value = 1;
			}
		}
		return value;
	}

	@Override
	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, boolean userInteract, boolean isReset) {

		responsesAnswersEvaluation.clear();

		if (groupsCorrectAnswers == null) {
			prepareGroupsCorrectAnswers(responses);
		}

		prepareGroupsAnswersUsedMap();

		Integer points = 0;
		Integer errors = 0;
		Integer todoCount = 0;
		String currKey;

		String commutativityGroup = StringUtils.EMPTY_STRING;
		Outcome commutativityOutcome = new Outcome();

		boolean passed;
		int correctAnswersCount = 0;
		for (Map.Entry<String, Response> responseEntry : responses.entrySet()) {
			Response response = responseEntry.getValue();
			if (!response.isModuleAdded()) {
				continue;
			}

			currKey = responseEntry.getKey();
			passed = processSingleResponse(response);
			if (passed || response.getCountMode() == CountMode.CORRECT_ANSWERS) {
				correctAnswersCount = countCorrectAnswers(response);
				points += correctAnswersCount;
			}

			// MAKRO PROCESSING
			String currKeyPrefix = currKey + "-";
			String doneKey = currKeyPrefix + DONE;
			clearAndAddToOutcomeValues(outcomes, doneKey, String.valueOf(correctAnswersCount));

			String todoKey = currKeyPrefix + TODO;
			if (outcomes.containsKey(todoKey)) {
				int value = getTODOCount(response);
				clearAndAddToOutcomeValues(outcomes, todoKey, String.valueOf(value));
				todoCount += value;
			}

			String doneHistoryKey = currKeyPrefix + DONEHISTORY;
			if (userInteract) {
				addToOutcomeValues(outcomes, doneHistoryKey, String.valueOf(correctAnswersCount));
			}

			Outcome doneHistory = outcomes.get(doneHistoryKey);
			String doneChangesKey = currKeyPrefix + DONECHANGES;
			if (userInteract && outcomes.containsKey(doneChangesKey) && outcomes.containsKey(doneHistoryKey)) {
				if (doneHistory.values.size() == 1) {
					outcomes.get(doneChangesKey).values.add(doneHistory.values.get(0));
				} else {
					int currModuleScore = Integer.parseInt(doneHistory.values.get(doneHistory.values.size() - 1));
					int prevModuleScore = Integer.parseInt(doneHistory.values.get(doneHistory.values.size() - 2));
					outcomes.get(doneChangesKey).values.add(String.valueOf(currModuleScore - prevModuleScore));
				}
			}

			String previousKey = currKeyPrefix + PREVIOUS;
			String lastChangeKey = currKeyPrefix + LASTCHANGE;
			if (userInteract && outcomes.containsKey(previousKey) && outcomes.containsKey(lastChangeKey)) {
				outcomes.get(lastChangeKey).values = DefaultVariableProcessorHelper.getDifference(response, outcomes.get(previousKey));
			}

			if ((userInteract || isReset) && outcomes.containsKey(previousKey)) {
				List<String> values = outcomes.get(previousKey).values;
				values.clear();
				for (int a = 0; a < response.values.size(); a++) {
					values.add(response.values.get(a));
				}
			}

			String lastMistakenKey = currKeyPrefix + LASTMISTAKEN;
			String mistakesKey = currKeyPrefix + MISTAKES;
			if (userInteract && outcomes.containsKey(lastChangeKey) && outcomes.containsKey(lastMistakenKey)) {
				int lastMistakes = processCheckMistakes(response, outcomes.get(lastChangeKey));

				Outcome lastChanges = outcomes.get(lastChangeKey);
				boolean isInGroup = response.groups.size() > 0;
				if (isInGroup && lastChanges.values.size() > 0) {
					commutativityGroup = response.groups.keySet().iterator().next();
					commutativityOutcome = outcomes.get(lastMistakenKey);
				}

				outcomes.get(lastMistakenKey).values.set(0, String.valueOf(lastMistakes));
				if (outcomes.containsKey(mistakesKey)) {
					Outcome outcome = outcomes.get(mistakesKey);
					if (outcome.values.size() == 0) {
						outcome.values.add("0");
					}
					Integer mistakes = Integer.parseInt(outcome.values.get(0));
					mistakes += Integer.parseInt(outcomes.get(lastMistakenKey).values.get(0));
					outcome.values.set(0, mistakes.toString());
				}
			}
			String errorsKey = currKeyPrefix + ERRORS;
			if (outcomes.containsKey(errorsKey)) {
				outcomes.get(errorsKey).values.clear();
				boolean userHasInteracted = (outcomes.containsKey(doneHistoryKey) && doneHistory.values.size() > 0);
				Integer currErrors = findSingleResponseErrors(response, passed, userHasInteracted);
				outcomes.get(errorsKey).values.add(currErrors.toString());
				errors += currErrors;
			}
		}

		clearAndAddToOutcomeValues(outcomes, DONE, String.valueOf(points));
		clearAndAddToOutcomeValues(outcomes, TODO, String.valueOf(todoCount));
		clearAndAddToOutcomeValues(outcomes, ERRORS, String.valueOf(errors));

		if (userInteract && outcomes.containsKey(DONEHISTORY)) {
			outcomes.get(DONEHISTORY).values.add(points.toString());
		}

		if (userInteract && outcomes.containsKey(DONEHISTORY) && outcomes.containsKey(DONECHANGES)) {
			if (outcomes.get(DONEHISTORY).values.size() == 1) {
				outcomes.get(DONECHANGES).values.add(outcomes.get(DONEHISTORY).values.get(0));
			} else {
				int currModuleScore = Integer.parseInt(outcomes.get(DONEHISTORY).values.get(outcomes.get(DONEHISTORY).values.size() - 1));
				int prevModuleScore = Integer.parseInt(outcomes.get(DONEHISTORY).values.get(outcomes.get(DONEHISTORY).values.size() - 2));
				outcomes.get(DONECHANGES).values.add(String.valueOf(currModuleScore - prevModuleScore));
			}
		}
		if (userInteract && outcomes.containsKey(LASTMISTAKEN)) {
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

		if (!commutativityGroup.equals(StringUtils.EMPTY_STRING)) {
			updateLastMistakeInGroup(commutativityGroup, commutativityOutcome);
		}

		updateGroupsPoints();
	}

	protected void updateGroupsPoints() {
		for (Map.Entry<String, List<Boolean>> group : groupsAnswersUsed.entrySet()) {
			groupsPoints.put(group.getKey(), checkPointsInGroup(group.getKey()));
		}
	}

	protected void updateLastMistakeInGroup(String group, Outcome outcome) {
		Integer currentPoints = checkPointsInGroup(group);
		Integer lastPoints = (groupsPoints.get(group) != null) ? groupsPoints.get(group) : 0;

		if (currentPoints.intValue() > lastPoints.intValue()) {
			outcome.values.set(0, "0");
		} else {
			outcome.values.set(0, "1");
		}

		groupsPoints.put(group, currentPoints);
	}

	protected Integer checkPointsInGroup(String groupId) {
		int points = 0;

		for (boolean isAnswerCorrect : groupsAnswersUsed.get(groupId)) {
			if (isAnswerCorrect) {
				points++;
			}
		}

		return points;
	}

	protected void prepareGroupsCorrectAnswers(Map<String, Response> responses) {
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

	protected void prepareGroupsAnswersUsedMap() {
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

		List<String> userAnswers = response.values;

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

	protected boolean processSingleResponseCardinalitySingle(CorrectAnswers correctAnswers, List<String> userAnswers, ArrayList<Boolean> answersEvaluation) {
		boolean passed = ((userAnswers.isEmpty()) || !correctAnswers.containsAnswer(userAnswers.get(0))) ? false : true;
		answersEvaluation.add(passed);
		return passed;
	}

	protected boolean processSingleResponseCardinalityMultiple(Response response, CorrectAnswers correctAnswers, List<String> userAnswers,
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

				boolean shouldBeCorrect;
				if (currVal.startsWith("+")) {
					shouldBeCorrect = true;
				} else {
					shouldBeCorrect = false;
					// continue;
				}
				currVal = currVal.substring(1);

				boolean answerFound = false;

				if (response.correctAnswers.containsAnswer(currVal)) {
					answerFound = true;
				}

				if (answerFound != shouldBeCorrect) {
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
		Outcome prevValue = null;
		if (isIOS6_0_OR_IOS6_1) {
			for (Map.Entry<String, Outcome> entry : outcomes.entrySet()) {
				if (entry.getKey().equals(variable.identifier)) {
					prevValue = entry.getValue();
					break;
				}
			}
		} else {
			prevValue = outcomes.get(variable.identifier);
		}
		if (prevValue == null) {
			outcomes.put(variable.identifier, variable);
		} else if (variable.values.size() > 0 && prevValue.values.size() == 0) {
			prevValue.values.addAll(variable.values);
		}
	}

}
