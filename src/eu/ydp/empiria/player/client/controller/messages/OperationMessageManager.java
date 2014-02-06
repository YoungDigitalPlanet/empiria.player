package eu.ydp.empiria.player.client.controller.messages;

import java.util.Vector;

import com.google.gwt.user.client.Window;

public class OperationMessageManager implements OperationMessageDisplayEventListener {

	public OperationMessageManager() {
		messages = new Vector<OperationMessage>();
	}

	public Vector<OperationMessage> messages;

	public void showMessage(OperationMessage msg) {
		messages.add(msg);
		msg.show(this);
	}

	public void hideMessage(OperationMessage msg) {
		messages.remove(msg);
		msg.hide();
	}

	@Override
	public void onMessageAttaching(OperationMessage msg) {
		msg.setPopupPosition(0, findNextMessageYPosition() - msg.getOffsetHeight());

	}

	@Override
	public void onMessageHided(OperationMessage msg) {
		messages.remove(msg);
	}

	public int findNextMessageYPosition() {
		int y = Window.getClientHeight();
		for (OperationMessage currMsg : messages) {
			if (currMsg.isAttached()) {
				if (currMsg.getAbsoluteTop() < y)
					y = currMsg.getAbsoluteTop();
			}
		}
		return y;
	}

}
