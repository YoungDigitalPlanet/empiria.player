package eu.ydp.empiria.player.client.module.external.object;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

public class ExternalInteractionNullObject implements ExternalInteractionObject {
	@Override
	public void setStateFromEmpiriaOnExternal(JavaScriptObject state) {
	}

	@Override
	public JavaScriptObject getStateFromExternal() {
		return new JSONArray().getJavaScriptObject();
	}

	@Override
	public void showCorrectAnswers() {
	}

	@Override
	public void hideCorrectAnswers() {
	}

	@Override
	public void markCorrectAnswers() {
	}

	@Override
	public void unmarkCorrectAnswers() {
	}

	@Override
	public void markWrongAnswers() {
	}

	@Override
	public void unmarkWrongAnswers() {
	}

	@Override
	public void reset() {
	}

	@Override
	public void lock() {
	}

	@Override
	public void unlock() {
	}
}
