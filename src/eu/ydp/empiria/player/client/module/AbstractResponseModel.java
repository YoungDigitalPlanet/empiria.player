package eu.ydp.empiria.player.client.module;

import java.util.ArrayList;
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
	
	public AbstractResponseModel(Response response){
		this.response = response;
	}
	
	public List<T> getCorrectAnswers() {
		return parseResponse(response.correctAnswers.getAllAnswers());
	}

	public List<T> getCurrentAnswers() {
		return parseResponse(response.values);
	}
	
	/**
	 * Konwertuje odpowiedz z postaci String do typu H. Domyslnie przepakowuje kolekcje dla bardziej zlozonych typow
	 * powinna powstac implementacja na poziomie modulu i przeciazyc ta.
	 *
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<T> parseResponse(Collection<String> values) {
		return new ArrayList(values);
	}
	
	@Override
	public void setState(JSONArray newState) {
		for (int i = 0; i < newState.size(); i++) {
			String choiceIdentifier = newState.get(i).isString().stringValue();
			response.add(choiceIdentifier);
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
	
	private JSONString createJSONString(String value) {
		return new JSONString(value);
	}	
}
