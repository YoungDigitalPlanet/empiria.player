package eu.ydp.empiria.player.client.module.external.object;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalInteractionObject {

	void setStateFromEmpiriaOnExternal(JavaScriptObject state);

	JavaScriptObject getStateFromExternal();

	void showCorrectAnswers();

	void hideCorrectAnswers();

	void markCorrectAnswers();

	void unmarkCorrectAnswers();

	void markWrongAnswers();

	void unmarkWrongAnswers();

	void reset();

	void lock();

	void unlock();
}
