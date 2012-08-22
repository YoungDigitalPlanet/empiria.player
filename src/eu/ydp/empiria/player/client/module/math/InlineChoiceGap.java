package eu.ydp.empiria.player.client.module.math;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.ExListBox;

public class InlineChoiceGap implements MathGap {

	private static final String WRONG = "wrong";
	private static final String CORRECT = "correct";
	private static final String NONE = "none";
	private final ExListBox listBox;
	private final List<String> options;
	private final Panel container;

	public InlineChoiceGap(ExListBox listBox, List<String> options){
		this.listBox = listBox;
		this.options = options;

		container = new FlowPanel();
		container.setStyleName("qp-mathinteraction-inlinechoicegap");
		container.add(listBox);
	}

	@Override
	public String getValue() {
		int value = listBox.getSelectedIndex();
		if (value >= 0) {
			return options.get(value);
		}
		return "";
	}

	@Override
	public void setValue(String value) {
		listBox.setSelectedIndex(options.indexOf(value));
	}

	@Override
	public void setEnabled(boolean enabled){
		listBox.setEnabled(enabled);
		if (enabled) {
			container.setStyleDependentName("disabled", !enabled);
		}
	}

	@Override
	public void reset() {
		listBox.setSelectedIndex(-1);
	}

	@Override
	public void mark(boolean correct, boolean wrong){
		if (!correct && !wrong){
			container.setStyleDependentName(NONE, true);
		} else  if (correct){
			container.setStyleDependentName(CORRECT, true);
		} else  if (wrong){
			container.setStyleDependentName(WRONG, true);
		}
	}

	@Override
	public void unmark() {
		container.setStyleDependentName(NONE, false);
		container.setStyleDependentName(CORRECT, false);
		container.setStyleDependentName(WRONG, false);
	}

	@Override
	public Widget getContainer() {
		return container;
	}

	public ExListBox getListBox(){
		return listBox;
	}

}
