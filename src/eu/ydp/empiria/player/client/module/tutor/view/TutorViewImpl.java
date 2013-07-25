package eu.ydp.empiria.player.client.module.tutor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
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

	private final TutorConfig tutorConfig;
	
	@Inject
	public TutorViewImpl(@ModuleScoped TutorConfig tutorConfig) {
		this.tutorConfig = tutorConfig;
	}

	@Override
	public void setAnimationImage(String src) {
		setBackgroundImage(src);
	}

	@Override
	public void setAnimationLeft(int left) {
		Style style = content.getElement().getStyle();
		style.setProperty("backgroundPosition", left+DIMENSIONS_UNIT);
	}

	@Override
	public void setAnimationStyleName(String styleName) {
		clearAllStyles();
		content.setStyleName(styleName);
		setSizeOfContent(getSize());
	}

	private void clearAllStyles() {
		content.getElement().setAttribute("style", "");
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
		Size size = getSize();
		setBackgroundImageWithSize(src, size);
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
	
	private Size getSize() {
		TutorPersonaProperties personaProperties = tutorConfig.getTutorPersonaProperties(0);
		return personaProperties.getAnimationSize();
	}
	
	private void setBackgroundImageWithSize(String src, Size size) {
		Style style = content.getElement().getStyle();
		String srcWithUrlInside = "url("+src+")";
		style.setBackgroundImage(srcWithUrlInside);
		style.setProperty("backgroundPosition", 0+DIMENSIONS_UNIT);
		
		setSizeOfContent(size);
	}
	
	private void setSizeOfContent(Size size) {
		String width = size.getWidth()+DIMENSIONS_UNIT;
		String height = size.getHeight()+DIMENSIONS_UNIT;
		content.setWidth(width);
		content.setHeight(height);
	}
}
