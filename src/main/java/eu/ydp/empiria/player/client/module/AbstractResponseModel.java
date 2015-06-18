package eu.ydp.empiria.player.client.module;

import com.google.common.base.Optional;
import com.google.gwt.json.client.*;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import java.util.*;

public abstract class AbstractResponseModel<T> implements IStateful {

	private Response response;
	private Optional<ResponseModelChangeListener> responseModelChange;

	public AbstractResponseModel(Response response, ResponseModelChangeListener responseModelChange) {
		this.response = response;
		this.setResponseModelChange(responseModelChange);
	}

	public AbstractResponseModel(Response response) {
		this.response = response;
		responseModelChange = Optional.absent();
	}

	protected abstract List<T> parseResponse(Collection<String> values);

	public boolean isCorrectAnswer(String answer) {
		List<String> correctAnswers = response.correctAnswers.getAllAnswers();
		return correctAnswers.contains(answer);
	}

	public boolean isUserAnswer(String answer) {
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
		response.reset();

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

	public void reset() {
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

	protected void onModelChange() {
		if (responseModelChange.isPresent()) {
			responseModelChange.get().onResponseModelChange();
		}
	}

	private JSONString createJSONString(String value) {
		return new JSONString(value);
	}

	public void setResponseModelChange(ResponseModelChangeListener responseModelChange) {
		this.responseModelChange = Optional.of(responseModelChange);
	}

	public Response getResponse() {
		return response;
	}
}
