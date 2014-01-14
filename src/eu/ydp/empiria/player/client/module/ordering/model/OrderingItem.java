package eu.ydp.empiria.player.client.module.ordering.model;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class OrderingItem {

	private final String itemId;
	private final String answerValue;
	private boolean selected = false;
	private UserAnswerType answerType = UserAnswerType.DEFAULT;
	private boolean locked = false;

	public OrderingItem(String itemId, String answerValue) {
		this.itemId = itemId;
		this.answerValue = answerValue;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public UserAnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(UserAnswerType answerType) {
		this.answerType = answerType;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getId() {
		return itemId;
	}

	public String getAnswerValue() {
		return answerValue;
	}
}
