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
	private final boolean hasEmptyOption;

	public InlineChoiceGap(ExListBox listBox, List<String> options, boolean hasEmptyOption) {
		this.listBox = listBox;
		this.options = options;
		this.hasEmptyOption = hasEmptyOption;

		container = createFlowPanel(); // NOPMD
		container.setStyleName("qp-mathinteraction-inlinechoicegap");
		container.add(listBox);
	}

	@Override
	public String getValue() {
		String value = "";
		int valueIndex = indexViewToInternal();
		if (valueIndex >= 0) {
			value = options.get(valueIndex);
		}
		return value;
	}
	
	@Override
	public void setValue(String valueIdentifier) {
		listBox.setSelectedIndex(indexInternalToView(valueIdentifier));
	}

	private int indexInternalToView(String valueIdentifier) {
		int valueIndex = options.indexOf(valueIdentifier);
		if (hasEmptyOption) {
			valueIndex++;
		}
		return valueIndex;
	} 
	
	private int indexViewToInternal() {
		int valueIndex = listBox.getSelectedIndex();
		if (hasEmptyOption) {
			valueIndex--;
		}	
		return valueIndex;		
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
		listBox.setSelectedIndex((hasEmptyOption) ? 0 : -1);
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

	public FlowPanel createFlowPanel() {
		return new FlowPanel();
	}	
}
