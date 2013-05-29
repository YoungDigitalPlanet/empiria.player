package eu.ydp.empiria.player.client.module.expression;

import java.util.Map;

import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.ui.HasValue;

public class ReplacingChangeHandler implements KeyPressHandler{
	
	private HasValue<String> hasValue;
	private Map<String, String> replacements;

	public void init(HasValue<String> hasValue, Map<String, String> replacements) {
		this.hasValue = hasValue;
		this.replacements = replacements;
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		char character = event.getCharCode();
		String text = String.valueOf(character);
		if (hasValue.getValue().isEmpty()  &&  replacements.containsKey(text)){
			hasValue.setValue(replacements.get(text));
			event.preventDefault();
		}		
	}
	
}