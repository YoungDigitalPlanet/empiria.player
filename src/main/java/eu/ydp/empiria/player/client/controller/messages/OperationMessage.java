package eu.ydp.empiria.player.client.controller.messages;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

import eu.ydp.empiria.player.client.components.MouseEventPanel;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

public class OperationMessage implements ExtendedPopupPanelDisplayEventListener {

	public OperationMessage(LocaleVariable lv, OperationMessageType type, int _timeout, boolean hideOnClick) {
		this(LocalePublisher.getText(lv), type, _timeout, hideOnClick);
	}

	public OperationMessage(String message, OperationMessageType type, int _timeout, boolean hideOnClick) {
		attached = false;
		timeout = _timeout;

		panel = new ExtendedPopupPanel(this);
		panel.setStyleName("qp-message-box");

		containerPanel = new MouseEventPanel();
		if (type == OperationMessageType.ERROR) {
			containerPanel.setStyleName("qp-message-error");
		} else if (type == OperationMessageType.WARRNING) {
			containerPanel.setStyleName("qp-message-warrning");
		} else if (type == OperationMessageType.INFO) {
			containerPanel.setStyleName("qp-message-info");
		}
		if (hideOnClick) {
			containerPanel.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					hide();
				}
			});
		}

		titlePanel = new FlowPanel();
		titlePanel.setStyleName("qp-message-title");
		containerPanel.add(titlePanel);

		if (type == OperationMessageType.ERROR) {
			title = new Label(LocalePublisher.getText(LocaleVariable.MESSAGE_TITLE_ERROR));
		} else if (type == OperationMessageType.WARRNING) {
			title = new Label(LocalePublisher.getText(LocaleVariable.MESSAGE_TITLE_WARNING));
		} else if (type == OperationMessageType.INFO) {
			title = new Label(LocalePublisher.getText(LocaleVariable.MESSAGE_TITLE_INFO));
		}
		title.setStyleName("qp-message-title-text");
		titlePanel.add(title);

		contentPanel = new FlowPanel();
		contentPanel.setStyleName("qp-message-content");
		containerPanel.add(contentPanel);

		content = new Label(message);
		content.setStyleName("qp-message-content-text");
		contentPanel.add(content);

		panel.setWidget(containerPanel);

		if (timeout > 0) {
			timer = new Timer() {
				@Override
				public void run() {
					hide();
				}
			};
		}
	}

	private PopupPanel panel;
	private MouseEventPanel containerPanel;
	private FlowPanel titlePanel;
	private Label title;
	private FlowPanel contentPanel;
	private Label content;
	private int timeout;

	private Timer timer;

	private OperationMessageDisplayEventListener listener;
	private boolean attached;

	public void show(OperationMessageDisplayEventListener l) {

		listener = l;

		panel.show();

		if (timer != null) {
			timer.schedule(timeout);
		}
	}

	public void hide() {

		if (timer != null) {
			timer.cancel();
		}
		panel.hide();
		listener.onMessageHided(this);
	}

	@Override
	public void onMessageAttaching() {
		if (listener != null) {
			listener.onMessageAttaching(this);
		}
		attached = true;
	}

	@Override
	public void onMessageHided() {
		if (listener != null) {
			listener.onMessageHided(this);
		}
		attached = false;
	}

	public boolean isAttached() {
		return attached;
	}

	public int getAbsoluteTop() {
		return panel.getAbsoluteTop();
	}

	public int getOffsetHeight() {
		return panel.getOffsetHeight();
	}

	public void setPopupPosition(int x, int y) {
		panel.setPopupPosition(x, y);
	}

}
