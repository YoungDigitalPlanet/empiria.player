package eu.ydp.empiria.player.client.module.expression;

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
    private ExpressionReplacer expressionReplacer;

    private InputEventListener listener = new InputEventListener() {

        @Override
        public void onInput() {
            replace();
        }
    };

    public <T extends IsWidget & HasValue<String>> void init(Wrapper<T> textBox, ExpressionReplacer expressionReplacer) {
        this.textBox = textBox.getInstance();
        this.expressionReplacer = expressionReplacer;
        eventRegistrar.registerInputHandler(textBox.getInstance(), listener);
    }

    private void replace() {
        String text = textBox.getValue();
        if (expressionReplacer.isEligibleForReplacement(text)) {
            String replaced = expressionReplacer.replace(text);
            textBox.setValue(replaced, true);
        }
    }

}
