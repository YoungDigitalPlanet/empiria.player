package eu.ydp.empiria.player.client.module.tutor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.util.events.animation.AnimationEndEvent;
import eu.ydp.empiria.player.client.util.geom.Size;

public class TutorViewImpl implements TutorView {

	private static final String DIMENSIONS_UNIT = "px";

	private static TutorViewUiBinder uiBinder = GWT.create(TutorViewUiBinder.class);
	@UiTemplate("TutorViewImpl.ui.xml")
	interface TutorViewUiBinder extends UiBinder<Widget, TutorViewImpl> {}
	
	@UiField
	FlowPanel container;
	
	@UiField
	FlowPanel content;
	
	@Override
	public void setAnimationImage(String src, Size size) {
		Style style = content.getElement().getStyle();
		style.setBackgroundImage(src);
		
		String width = size.getWidth()+DIMENSIONS_UNIT;
		String height = size.getHeight()+DIMENSIONS_UNIT;
		content.setWidth(width);
		content.setHeight(height);
	}

	@Override
	public void setAnimationLeft(int left) {
		Style style = content.getElement().getStyle();
		style.setProperty("backgroundPosition", left+DIMENSIONS_UNIT);
	}

	@Override
	public void setAnimationStyleName(String styleName) {
		content.setStyleName(styleName);
	}

	@Override
	public Widget asWidget() {
		return container;
	}

	@Override
	public void bindUi() {
		uiBinder.createAndBindUi(this);
	}

	@Override
	public void setBackgroundImage(String src) {
		Style style = content.getElement().getStyle();
		style.setBackgroundImage(src);
	}

	@Override
	public void removeAnimationStyleName(String styleName) {
		content.removeStyleName(styleName);
	}

	@Override
	public HandlerRegistration addAnimationEndHandler(final AnimationEndHandler animationEndHandler) {
		return content.addDomHandler(new eu.ydp.empiria.player.client.util.events.animation.AnimationEndHandler() {
			
			@Override
			public void onAnimationEnd(AnimationEndEvent event) {
				animationEndHandler.onEnd();
			}
		}, AnimationEndEvent.getType());
	}

}
