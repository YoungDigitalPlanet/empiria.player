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

import eu.ydp.gwtutil.client.animation.AnimationEndHandler;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.util.events.animation.AnimationEndEvent;
import eu.ydp.gwtutil.client.util.geom.Size;

public class TutorViewImpl implements TutorView {

	private static final String DIMENSIONS_UNIT = "px";

	private static TutorViewUiBinder uiBinder = GWT.create(TutorViewUiBinder.class);
	@UiTemplate("TutorViewImpl.ui.xml")
	interface TutorViewUiBinder extends UiBinder<Widget, TutorViewImpl> {}

	@UiField
	FlowPanel container;

	@UiField
	FlowPanel content;

	private final UserInteractionHandlerFactory userInteractionHandlerFactory;

	@Inject
	public TutorViewImpl(UserInteractionHandlerFactory userInteractionHandlerFactory) {
		this.userInteractionHandlerFactory = userInteractionHandlerFactory;
	}

	@Override
	public void setAnimationLeft(int left) {
		Style style = content.getElement().getStyle();
		style.setProperty("backgroundPosition", left+DIMENSIONS_UNIT);
	}

	@Override
	public void setAnimationStyleName(String styleName, Size size) {
		clearAllStyles();
		content.setStyleName(styleName);
		setSizeOfContent(size);
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
	public void setBackgroundImage(String src, Size size) {
		Style style = content.getElement().getStyle();
		String srcWithUrlInside = "url("+src+")";
		style.setBackgroundImage(srcWithUrlInside);
		style.setProperty("backgroundPosition", 0+DIMENSIONS_UNIT);

		setSizeOfContent(size);
	}

	@Override
	public void removeAnimationStyleName(String styleName) {
		content.removeStyleName(styleName);
	}

	@Override
	public HandlerRegistration addAnimationEndHandler(final AnimationEndHandler animationEndHandler) {
		return content.addDomHandler(new eu.ydp.gwtutil.client.util.events.animation.AnimationEndHandler() {

			@Override
			public void onAnimationEnd(AnimationEndEvent event) {
				animationEndHandler.onEnd();
			}
		}, AnimationEndEvent.getType());
	}

	private void setSizeOfContent(Size size) {
		String width = size.getWidth()+DIMENSIONS_UNIT;
		String height = size.getHeight()+DIMENSIONS_UNIT;
		content.setWidth(width);
		content.setHeight(height);
	}

	@Override
	public void addClickHandler(Command command) {
		userInteractionHandlerFactory.applyUserClickHandler(command, container);
	}
}
