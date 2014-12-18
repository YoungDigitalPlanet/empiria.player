package eu.ydp.empiria.player.client.module.slideshow.view.text;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class TextView extends Composite {

	interface TextWidgetUiBinder extends UiBinder<Widget, TextView> {
	};

	private static TextWidgetUiBinder textWidgetBinder = GWT.create(TextWidgetUiBinder.class);

	@UiField
	public SpanElement innerText;

	public TextView(String text) {
		initWidget(textWidgetBinder.createAndBindUi(this));

		innerText.setInnerHTML(text);
	}
}
