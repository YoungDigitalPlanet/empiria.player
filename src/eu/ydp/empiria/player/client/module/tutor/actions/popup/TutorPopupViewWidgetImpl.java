package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class TutorPopupViewWidgetImpl extends Composite implements TutorPopupViewWidget {

	private static TutorPopupViewWidgetUiBinder uiBinder = GWT.create(TutorPopupViewWidgetUiBinder.class);

	interface TutorPopupViewWidgetUiBinder extends UiBinder<Widget, TutorPopupViewWidgetImpl> {
	}

	@UiField protected FlowPanel itemsContainer;

	@PostConstruct
	public void postConstruct() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Widget getWidget(int personaIndex) {
		return itemsContainer.getWidget(personaIndex);
	}

	@Override
	public void addWidget(TutorPopupViewPersonaView personaView) {
		itemsContainer.add(personaView);
	}
	
	@Override
	public List<Widget> getAllWidgets() {
		int widgetCount = itemsContainer.getWidgetCount();
		List<Widget> widgets = new ArrayList<Widget>();
		
		for(int i=0; i<widgetCount; i++) {
			widgets.add(itemsContainer.getWidget(i));
		}
		return widgets;
	}

}
