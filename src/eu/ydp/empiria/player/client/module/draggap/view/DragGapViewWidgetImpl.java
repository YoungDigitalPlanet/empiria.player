package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DragGapViewWidgetImpl extends Composite {

	private static DragGapViewWidgetImplUiBinder uiBinder = GWT
			.create(DragGapViewWidgetImplUiBinder.class);

	interface DragGapViewWidgetImplUiBinder extends
			UiBinder<Widget, DragGapViewWidgetImpl> {
	}

	public DragGapViewWidgetImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public DragGapViewWidgetImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


}
