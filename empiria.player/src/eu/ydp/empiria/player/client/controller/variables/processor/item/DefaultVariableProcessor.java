package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class DefaultVariableProcessor extends VariableProcessor {


	@Override
	public void processFlowActivityVariables(Map<String, Outcome> outcomes, FlowActivityEvent event) {

		if (event != null){
			if (event.getType() == FlowActivityEventType.CHECK){
				if (outcomes.containsKey("CHECKS")){
					Integer value = 0;
					if (outcomes.get("CHECKS").values.size() > 0)
							value = Integer.parseInt(outcomes.get("CHECKS").values.get(0));
					value++;
					outcomes.get("CHECKS").values.clear();
					outcomes.get("CHECKS").values.add(value.toString());
				}
			}
			if (event.getType() == FlowActivityEventType.SHOW_ANSWERS){
				if (outcomes.containsKey("SHOW_ANSWERS")){
					Integer value = 0;
					if (outcomes.get("SHOW_ANSWERS").values.size() > 0)
							value = Integer.parseInt(outcomes.get("SHOW_ANSWERS").values.get(0));
					value++;
					outcomes.get("SHOW_ANSWERS").values.clear();
					outcomes.get("SHOW_ANSWERS").values.add(value.toString());
				}
			}
			if (event.getType() == FlowActivityEventType.RESET){
				if (outcomes.containsKey("RESET")){
					Integer value = 0;
					if (outcomes.get("RESET").values.size() > 0)
							value = Integer.parseInt(outcomes.get("RESET").values.get(0));
					value++;
					outcomes.get("RESET").values.clear();
					outcomes.get("RESET").values.add(value.toString());
				}
			}
		}
		
		
	}
	
	@Override
	public void processResponseVariables(Map<String, Response> responses, Map<String, Outcome> outcomes, boolean userInteract) {


		Integer points = 0;
		String currKey;

		Iterator<String> iter = responses.keySet().iterator();
		boolean passed;
		while (iter.hasNext()){
			currKey = iter.next();
			
			if (!responses.get(currKey).isModuleAdded())
				continue;
			
			passed = processSingleResponse(responses.get(currKey));
				
			if (passed)
				points++;
				
			// MAKRO PROCESSING
			if (outcomes.containsKey(currKey+"-DONE")){
				outcomes.get(currKey+"-DONE").values.clear();
				if (passed)
					outcomes.get(currKey+"-DONE").values.add("1");
				else
					outcomes.get(currKey+"-DONE").values.add("0");
			}
			if (outcomes.containsKey(currKey+"-TODO")){
				outcomes.get(currKey+"-TODO").values.add("1");				
			}
			if (outcomes.containsKey(currKey+"-DONEHISTORY")){
				if (passed)
					outcomes.get(currKey+"-DONEHISTORY").values.add("1");
				else
					outcomes.get(currKey+"-DONEHISTORY").values.add("0");
			}
			if (outcomes.containsKey(currKey+"-DONECHANGES")  &&  outcomes.containsKey(currKey+"-DONEHISTORY")){
				if (outcomes.get(currKey+"-DONEHISTORY").values.size() == 1) {
					outcomes.get(currKey+"-DONECHANGES").values.add(outcomes.get(currKey+"-DONEHISTORY").values.get(0));
				} else {
					int currModuleScore = Integer.parseInt( outcomes.get(currKey+"-DONEHISTORY").values.get(outcomes.get(currKey+"-DONEHISTORY").values.size()-1) );
					int prevModuleScore = Integer.parseInt( outcomes.get(currKey+"-DONEHISTORY").values.get(outcomes.get(currKey+"-DONEHISTORY").values.size()-2) );
					outcomes.get(currKey+"-DONECHANGES").values.add( String.valueOf(currModuleScore - prevModuleScore) );
				}
			}
			if (outcomes.containsKey(currKey+"-PREVIOUS")  &&  outcomes.containsKey(currKey+"-LASTCHANGE")){
				if (userInteract)
					outcomes.get(currKey+"-LASTCHANGE").values = DefaultVariableProcessorHelper.getDifference(responses.get(currKey), outcomes.get(currKey+"-PREVIOUS"));
				else
					outcomes.get(currKey+"-LASTCHANGE").values.clear();
			}
			if (outcomes.containsKey(currKey+"-PREVIOUS")){
				outcomes.get(currKey+"-PREVIOUS").values.clear();
				for (int a = 0 ; a < responses.get(currKey).values.size() ; a ++ ){
					outcomes.get(currKey+"-PREVIOUS").values.add(responses.get(currKey).values.get(a));
				}
			}
			if (outcomes.containsKey(currKey+"-LASTCHANGE")  &&  outcomes.containsKey(currKey+"-LASTMISTAKEN")){
				if (userInteract){
					int lastMistakes = processCheckMistakes( responses.get(currKey), outcomes.get(currKey+"-LASTCHANGE") );
					outcomes.get(currKey+"-LASTMISTAKEN").values.set(0,  String.valueOf(lastMistakes));
					if (outcomes.containsKey(currKey+"-MISTAKES")){
						if (outcomes.get(currKey+"-MISTAKES").values.size() == 0)
							outcomes.get(currKey+"-MISTAKES").values.add("0");
						Integer mistakes = Integer.parseInt( outcomes.get(currKey+"-MISTAKES").values.get(0) );
						mistakes += Integer.parseInt( outcomes.get(currKey+"-LASTMISTAKEN").values.get(0) );
						outcomes.get(currKey+"-MISTAKES").values.set(0, mistakes.toString());
					}
				}
			}
		}

		if (outcomes.containsKey("DONE")){
			outcomes.get("DONE").values.clear();		
			outcomes.get("DONE").values.add(points.toString());
		}

		if (outcomes.containsKey("TODO")){
			outcomes.get("TODO").values.clear();		
			outcomes.get("TODO").values.add( (new Integer(responses.size())).toString() );
		}
		
		if (outcomes.containsKey("DONEHISTORY")){
			outcomes.get("DONEHISTORY").values.add(points.toString());
		}
		if (outcomes.containsKey("DONEHISTORY")  &&  outcomes.containsKey("DONECHANGES")){
			if (outcomes.get("DONEHISTORY").values.size() == 1) {
				outcomes.get("DONECHANGES").values.add(outcomes.get("DONEHISTORY").values.get(0));
			} else {
				int currModuleScore = Integer.parseInt( outcomes.get("DONEHISTORY").values.get(outcomes.get("DONEHISTORY").values.size()-1) );
				int prevModuleScore = Integer.parseInt( outcomes.get("DONEHISTORY").values.get(outcomes.get("DONEHISTORY").values.size()-2) );
				outcomes.get("DONECHANGES").values.add( String.valueOf(currModuleScore - prevModuleScore) );
			}
		}
		if (outcomes.containsKey("LASTMISTAKEN")){
			if (userInteract){
				Integer lastMistakes = 0;
				Iterator<String> keys = responses.keySet().iterator();
				while (keys.hasNext()){
					String currKey2 = keys.next();
					if (outcomes.containsKey(currKey2+"-LASTMISTAKEN")){
						lastMistakes += Integer.parseInt( outcomes.get(currKey2+"-LASTMISTAKEN").values.get(0) );
					}
				}
				outcomes.get("LASTMISTAKEN").values.set(0, lastMistakes.toString());
				
				if (outcomes.containsKey("MISTAKES")){
					if (outcomes.get("MISTAKES").values.size() == 0)
						outcomes.get("MISTAKES").values.add("0");
					Integer mistakes = Integer.parseInt( outcomes.get("MISTAKES").values.get(0) );
					mistakes += Integer.parseInt( outcomes.get("LASTMISTAKEN").values.get(0) );
					outcomes.get("MISTAKES").values.set(0, mistakes.toString());
				}
			}
		}
		
		// MACRO PROCESSING
		
		iter = responses.keySet().iterator();
		while (iter.hasNext()){
			currKey = iter.next();
			
		}
	}
	
	private boolean processSingleResponse(Response response ){
		
		Vector<String> correctAnswers = response.correctAnswers;
		
		Vector<String> userAnswers = response.values;
		
		boolean answerFound;
		boolean passed = true;
		
		if (response.cardinality == Cardinality.ORDERED){
			if (correctAnswers.size() != userAnswers.size()) {
				passed = false;
			} else{
				for (int correct = 0 ; correct < correctAnswers.size() ; correct ++){
					if (correctAnswers.get(correct).compareTo(userAnswers.get(correct)) != 0){
						passed = false;
						break;
					}
				}
			}
			
		} else if (response.cardinality == Cardinality.COMMUTATIVE){
			if (correctAnswers.size() != userAnswers.size()) {
				passed = false;
			} else{

				Vector<Boolean> used = new Vector<Boolean>();
				for (int g = 0 ; g < correctAnswers.size() ; g ++ ){
					used.add(false);
				}
				
				for (int correct = 0 ; correct < correctAnswers.size() ; correct ++){
					
					int groupIndex = -1;
					for (int g = 0 ; g < response.groups.size() ; g ++ ){
						if (response.groups.get(g).contains(correct)){
							groupIndex = g;
							break;
						}
					}
					
					String currUserAnswer = userAnswers.get(correct);
					
					if (groupIndex == -1){
						if (correctAnswers.get(correct).compareTo(currUserAnswer) != 0){
							passed = false;
							break;
						}
					} else {
						answerFound = false;
						for (int a = 0 ; a < response.groups.get(groupIndex).size() ; a ++){
							int answerIndex = response.groups.get(groupIndex).get(a);
							if (correctAnswers.get(answerIndex).compareTo(currUserAnswer) == 0  &&  used.get(answerIndex) == false){
								answerFound = true;
								used.set(answerIndex, true);
								break;
							}
						}
						if (!answerFound){
							passed = false;
							break;
						}
					}
				}
			}
		} else {
			if (correctAnswers.size() != userAnswers.size()){
				passed = false;
			} else {
				for (int correct = 0 ; correct < correctAnswers.size() ; correct ++){
					
					answerFound = false;
					
					for (int user = 0 ; user < userAnswers.size() ; user ++){
						if (correctAnswers.get(correct).compareTo(userAnswers.get(user)) == 0){
							answerFound = true;
							break;
						}
					}
					
					if (!answerFound){
						passed = false;
					}
				}
			}
		}
		
		return passed;
		
	}
	
	private int processCheckMistakes(Response response, Outcome moduleLastChange){
		
		int mistakesCounter = 0;

		if (response.cardinality == Cardinality.SINGLE  ||  response.cardinality == Cardinality.MULTIPLE){
			
			for (int v = 0 ; v < moduleLastChange.values.size() ; v ++){
				String currVal = moduleLastChange.values.get(v);
				if (currVal.startsWith("+"))
					currVal = currVal.substring(1);
				else
					continue;
				
					
					boolean answerFound = false;
					
					for (int correct = 0 ; correct < response.correctAnswers.size() ; correct ++){
						if (response.correctAnswers.get(correct).compareTo(currVal) == 0){
							answerFound = true;
							break;
						}
					}
					
					if (!answerFound){
						mistakesCounter++;
					}
					
			}
		} else if (response.cardinality == Cardinality.ORDERED){
			
			for (int v = 0 ; v < response.correctAnswers.size()  &&  v < moduleLastChange.values.size() ; v ++){
				String currAnswer = response.correctAnswers.get(v);
				String[] changeSplited = moduleLastChange.values.get(v).split("->");
				
				if (changeSplited.length != 2)
					continue;
				
				if (changeSplited[0].compareTo(currAnswer) == 0  &&  
					changeSplited[1].compareTo(currAnswer) != 0){
					mistakesCounter++;
					break;
				}
					
			}
			
		}
		
		return mistakesCounter;
	}

	@Override
	public void ensureVariables(Map<String, Response> responses, Map<String, Outcome> outcomes) {

			
		ensureVariable(outcomes, new Outcome("DONE", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("TODO", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("DONEHISTORY", Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome("DONECHANGES", Cardinality.MULTIPLE, BaseType.INTEGER));
		ensureVariable(outcomes, new Outcome("LASTMISTAKEN", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("CHECKS", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("SHOW_ANSWERS", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("RESET", Cardinality.SINGLE, BaseType.INTEGER, "0"));
		ensureVariable(outcomes, new Outcome("MISTAKES", Cardinality.SINGLE, BaseType.INTEGER, "0"));

		if (responses.keySet().size() > 0){
			
			Iterator<String> responseKeys = responses.keySet().iterator();
			
			while (responseKeys.hasNext()){
				Response currResp = responses.get(responseKeys.next());
				String cri = currResp.identifier;

				ensureVariable(outcomes, new Outcome(cri+"-DONE", Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(cri+"-TODO", Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(cri+"-LASTCHANGE", Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(cri+"-PREVIOUS", Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(cri+"-LASTMISTAKEN", Cardinality.SINGLE, BaseType.INTEGER, "0"));
				ensureVariable(outcomes, new Outcome(cri+"-DONEHISTORY", Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(cri+"-DONECHANGES", Cardinality.MULTIPLE, BaseType.INTEGER));
				ensureVariable(outcomes, new Outcome(cri+"-MISTAKES", Cardinality.SINGLE, BaseType.INTEGER, "0"));
				
			}
		}
		
	}
	
	private void ensureVariable(Map<String, Outcome> outcomes, Outcome variable){
		if (!outcomes.containsKey(variable.identifier)){
			outcomes.put(variable.identifier, variable);
		} else if (variable.values.size() > 0  &&  outcomes.get(variable.identifier).values.size() == 0){
			outcomes.get(variable.identifier).values.addAll(variable.values);
		}
	}

}
