package eu.ydp.empiria.player.client.debug;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class LoggerMobile extends DialogBox implements Logger {

	private static DebugUiBinder uiBinder = GWT.create(DebugUiBinder.class);
	
	@UiField
	protected TextArea consoleText;

	interface DebugUiBinder extends UiBinder<Widget, LoggerMobile> {
	}

	public LoggerMobile() {
		setWidget(uiBinder.createAndBindUi(this));
		this.setText("Console log");
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		this.show();
		this.setPopupPosition(Window.getClientWidth() - 600, Window.getClientHeight() - 200);
	}
	
	@Override
	public void log(String text) {
		consoleText.setText(consoleText.getText() + "==> " + text + "\n");
	}
	
	@UiHandler("clearButton")
	void clearHandler(ClickEvent event) {
		consoleText.setText("");
	}
	
	@UiHandler("closeButton")
	void closeHandler(ClickEvent event) {
		this.hide();
	}
	
}

