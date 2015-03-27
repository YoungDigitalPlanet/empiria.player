package eu.ydp.empiria.player.client.module.selection.model;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.components.choicebutton.Identifiable;

public class SelectionAnswerDto implements Identifiable {

	private String id;
	private boolean selected;
	private boolean locked;
	private UserAnswerType selectionAnswerType;
	private boolean stateChanged;

	public SelectionAnswerDto() {
	}

	@Inject
	public SelectionAnswerDto(@Assisted String id) {
		this.id = id;
		this.selected = false;
		this.locked = false;
		this.selectionAnswerType = UserAnswerType.DEFAULT;
		this.stateChanged = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			stateChanged = true;
		}

		this.selected = selected;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		if (this.locked != locked) {
			stateChanged = true;
		}

		this.locked = locked;
	}

	public UserAnswerType getSelectionAnswerType() {
		return selectionAnswerType;
	}

	public void setSelectionAnswerType(UserAnswerType selectionAnswerType) {
		if (this.selectionAnswerType != selectionAnswerType) {
			stateChanged = true;
		}

		this.selectionAnswerType = selectionAnswerType;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isStateChanged() {
		return stateChanged;
	}

	public void setStateChanged(boolean stateChanged) {
		this.stateChanged = stateChanged;
	}
}
