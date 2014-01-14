package eu.ydp.empiria.player.client.module;

import java.util.Collection;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

/**
 * Klasa abstrakcyjna, model odpowiedzi
 * @author MKaldonek
 * @param <T> typ odpowiedzi dostarczanych przez model
 */
public abstract class AbstractResponseModel<T> implements IStateful{

	protected Response response;
	protected ResponseModelChangeListener responseModelChange;

	public AbstractResponseModel(Response response, ResponseModelChangeListener responseModelChange){
		this.response = response;
		this.responseModelChange = responseModelChange;
	}
	
	public AbstractResponseModel(Response response){
		this.response = response;
	}
	
	protected abstract List<T> parseResponse(Collection<String> values);

	public boolean isCorrectAnswer(String answer){
		List<String> correctAnswers = response.correctAnswers.getAllAnswers();
		return correctAnswers.contains(answer);
	}

	public boolean isUserAnswer(String answer){
		List<String> userAnswers = response.values;
		return userAnswers.contains(answer);
	}

	public List<T> getCorrectAnswers() {
		return parseResponse(response.correctAnswers.getAllAnswers());
	}

	public List<T> getCurrentAnswers() {
		return parseResponse(response.values);
	}

	@Override
	public void setState(JSONArray newState) {
		for (int i = 0; i < newState.size(); i++) {
			String responseValue = newState.get(i).isString().stringValue();
			response.add(responseValue);
		}
	}

	@Override
	public JSONArray getState() {
		JSONArray state = new JSONArray();

		for (String responseValue : response.values) {
			state.set(state.size(), createJSONString(responseValue));
		}

		return state;
	}

	public void reset(){
		response.reset();
	}

	public void clearAnswers() {
		reset();
		onModelChange();
	}
	
	public void addAnswer(String answerIdentifier) {
		response.add(answerIdentifier);
		onModelChange();
	}

	public void removeAnswer(String answerIdentifier) {
		response.remove(answerIdentifier);
		onModelChange();
	}

	protected void onModelChange(){
		responseModelChange.onResponseModelChange();
	}

	private JSONString createJSONString(String value) {
		return new JSONString(value);
	}
}
