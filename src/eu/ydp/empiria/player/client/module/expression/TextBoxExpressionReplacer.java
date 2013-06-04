package eu.ydp.empiria.player.client.module.expression;

import java.util.Map;

import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.Wrapper;

public class TextBoxExpressionReplacer {
	
	@Inject
	private Provider<ReplacingChangeHandler> handlerProvider;

	public void makeReplacements(final TextBox textBox, final Map<String, String> replacements){
		ReplacingChangeHandler handler = handlerProvider.get();
		handler.init(Wrapper.of(textBox), replacements);
	}

}
