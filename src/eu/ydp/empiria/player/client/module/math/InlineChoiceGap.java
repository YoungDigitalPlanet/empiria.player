package eu.ydp.empiria.player.client.module.math;

import java.util.List;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.ExListBox;

public class InlineChoiceGap implements MathGap {

	private ExListBox listBox;
	private List<String> options;
	private Panel container;

	public InlineChoiceGap(ExListBox listBox, List<String> options){
		this.listBox = listBox;
		this.options = options;

		container = new FlowPanel();
		container.setStyleName("qp-mathinteraction-inlinechoicegap");
		container.add(listBox);
	}
	
	@Override
	public String getValue() {
		int v = listBox.getSelectedIndex();
		if (v >= 0)
			return options.get(v);
		return "";
	}

	@Override
	public void setValue(String v) {
		int i = options.indexOf(v);
		listBox.setSelectedIndex(i);
	}
	
	@Override
	public void setEnabled(boolean enabled){
		listBox.setEnabled(enabled);
		if (enabled)
			container.setStyleDependentName("disabled", !enabled);
	}

	@Override
	public void reset() {
		listBox.setSelectedIndex(-1);
	}

	@Override
	public void mark(boolean correct, boolean wrong){
		if (!correct && !wrong){
			container.setStyleDependentName("none", true);
		} else  if (correct){
			container.setStyleDependentName("correct", true);
		} else  if (wrong){
			container.setStyleDependentName("wrong", true);
		}
	}

	@Override
	public void unmark() {
		container.setStyleDependentName("none", false);
		container.setStyleDependentName("correct", false);
		container.setStyleDependentName("wrong", false);
	}

	@Override
	public Widget getContainer() {
		return container;
	}
	
	public ExListBox getListBox(){
		return listBox;
	}

}
