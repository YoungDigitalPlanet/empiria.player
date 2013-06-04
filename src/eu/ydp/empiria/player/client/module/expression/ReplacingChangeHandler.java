package eu.ydp.empiria.player.client.module.expression;

import java.util.Map;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.components.event.InputEventListener;
import eu.ydp.empiria.player.client.components.event.InputEventRegistrar;
import eu.ydp.gwtutil.client.Wrapper;

public class ReplacingChangeHandler {
	
	@Inject
	private InputEventRegistrar eventRegistrar;
	
	private HasValue<String> textBox;
	private Map<String, String> replacements;
	
	private InputEventListener listener = new InputEventListener() {
		
		@Override
		public void onInput() {
			replace();
		}
	};

	public <T extends IsWidget &  HasValue<String>> void init(Wrapper<T> textBox, Map<String, String> replacements) {
		this.textBox = textBox.getInstance();
		this.replacements = replacements;
		eventRegistrar.registerInputHandler(textBox.getInstance(), listener);
	}

	private void replace() {
		String text = textBox.getValue();
		if (replacements.containsKey(text)){
			textBox.setValue(replacements.get(text));
		}
	}
	
}