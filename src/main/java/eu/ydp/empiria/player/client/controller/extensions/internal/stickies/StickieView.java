package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragHandlersManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.WidgetSizeHelper;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.scroll.WindowToStickieScroller;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.*;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.events.animation.*;

public class StickieView extends Composite implements IStickieView {

	private static final int OUT_OF_SCREEN_COORDINATE = -2000;
	private static StickieViewUiBinder uiBinder = GWT.create(StickieViewUiBinder.class);
	private static boolean firstFocusDone = false;

	interface StickieViewUiBinder extends UiBinder<Widget, StickieView> {
	}

	private final HasWidgets parent;
	private final boolean android;
	private final StyleNameConstants styleNames;
	private final EventsBus eventsBus;
	private final StickieDragHandlersManager stickieDragHandlersManager;
	private final WidgetSizeHelper widgetSizeHelper;
	private final WindowToStickieScroller windowToStickieScroller;
	private final IStickiePresenter presenter;

	private HandlerRegistration preventHandlerRegistration;

	private boolean takeOverKeyInput;
	private boolean firstKeyInputAfterClick;

	@UiField
	FlowPanel rootStickiePanel;
	@UiField
	TextArea contentText;
	@UiField
	PushButton deleteButton;
	@UiField
	PushButton minimizeButton;
	@UiField
	FocusPanel headerPanel;
	@UiField
	InlineHTML contentLabel;
	@UiField
	FlowPanel textPanel;
	@UiField
	FocusPanel labelPanel;

	@Inject
	public StickieView(@Assisted HasWidgets parent, @Assisted IStickiePresenter presenter, @Assisted StickieDragHandlersManager stickieDragHandlersManager,
			StyleNameConstants styleNameConstants, EventsBus eventsBus, WidgetSizeHelper widgetSizeHelper, WindowToStickieScroller windowToStickieScroller) {
		this.presenter = presenter;
		this.stickieDragHandlersManager = stickieDragHandlersManager;
		this.styleNames = styleNameConstants;
		this.eventsBus = eventsBus;
		this.widgetSizeHelper = widgetSizeHelper;
		this.windowToStickieScroller = windowToStickieScroller;
		this.android = UserAgentChecker.isStackAndroidBrowser();
		this.parent = parent;
	}

	@PostConstruct
	public void initializeStickie() {
		initWidget(uiBinder.createAndBindUi(this));
		setPosition(OUT_OF_SCREEN_COORDINATE, OUT_OF_SCREEN_COORDINATE);
		addContentTextClickHandler();
		addTransitionEndHandler();
		parent.add(this);
	}

	private void addTransitionEndHandler() {
		rootStickiePanel.addDomHandler(new TransitionEndHandler() {

			@Override
			public void onTransitionEnd(TransitionEndEvent event) {
				presenter.correctStickiePosition();
			}
		}, TransitionEndEvent.getType());
	}

	private void addContentTextClickHandler() {
		contentText.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (isFirstKeyInputAfterClickOnAndroid() && contentText.getCursorPos() != 0) {
					firstKeyInputAfterClick = false;
				}
			}
		});
	}

	@UiHandler("minimizeButton")
	public void minimizeHandler(ClickEvent event) {
		presenter.negateStickieMinimize();
	}

	@UiHandler("deleteButton")
	public void deleteHandler(ClickEvent event) {
		presenter.deleteStickie();
	}

	@UiHandler("headerPanel")
	public void mouseDownHandler(MouseDownEvent event) {
		setEditing(false);
		stickieDragHandlersManager.mouseDown(event);
	}

	@UiHandler("headerPanel")
	public void contentTextTouchStartHandler(TouchStartEvent event) {
		event.stopPropagation();
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
	}

	@UiHandler("headerPanel")
	public void containerPanelTouchStartHandler(TouchStartEvent event) {
		setEditing(false);
		stickieDragHandlersManager.touchStart(event);
	}

	@UiHandler("labelPanel")
	public void contentLabelClickHandler(ClickEvent event) {
		setEditing(true);
	}

	@UiHandler("contentText")
	public void contentTextBlurHandler(BlurEvent event) {
		setEditing(false);
		removePreventHandlerRegistration();
	}

	@UiHandler("contentText")
	public void contentFocusInHandler(FocusEvent event) {
		preventHandlerRegistration = Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				Event nativeEvent = Event.as(event.getNativeEvent());
				EventTarget target = nativeEvent.getEventTarget();
				if (Element.is(target) && nativeEvent.getType().equals("mousedown") && (!getElement().isOrHasChild(Element.as(target)))) {
					contentTextBlurHandler(null);
					minimizeHandler(null);
				}
			}
		});
	}

	@UiHandler("contentText")
	public void contentTextKeyDownHandler(KeyDownEvent event) {
		takeOverKeyInput = (isFirstKeyInputAfterClickOnAndroid() && contentText.getCursorPos() == 0);
	}

	private boolean isFirstKeyInputAfterClickOnAndroid() {
		return android && firstKeyInputAfterClick;
	}

	@UiHandler("contentText")
	public void contentTextKeyPressHandler(KeyPressEvent event) {
		if (takeOverKeyInput) {
			if (firstFocusDone) {
				contentText.setText(contentText.getText() + event.getCharCode());
				event.preventDefault();
				takeOverKeyInput = false;
				firstKeyInputAfterClick = false;
			} else {
				firstFocusDone = true;
			}
		}
	}

	private void updateContentText() {
		updateContentLabel();
		presenter.changeContentText(contentText.getText());
	}

	private void updateContentLabel() {
		String currentContentText = contentText.getText();
		String newContentText = currentContentText.replace("\n", "<br/>");
		contentLabel.setHTML(newContentText);
	}

	private void setEditing(boolean edit) {
		if (edit == labelPanel.isVisible()) {
			if (edit) {
				if (!contentText.isAttached()) {
					textPanel.add(contentText);
				}
				contentText.setVisible(true);
				contentText.setFocus(true);
				if (!android) {
					contentText.setSelectionRange(contentText.getText().length(), 0);
				}
				firstKeyInputAfterClick = true;
				windowToStickieScroller.scrollToStickie(getAbsoluteTop());
			} else {
				updateContentText();
				contentText.removeFromParent();
			}
			labelPanel.setVisible(!edit);
		}
	}

	@Override
	public String getText() {
		return contentText.getText();
	}

	@Override
	public void setText(String text) {
		contentText.setText(text);
		updateContentLabel();
	}

	@Override
	public void setColorIndex(int colorIndex) {
		addStyleName(styleNames.QP_STICKIE_COLOR_() + colorIndex);
	}

	@Override
	public void setMinimized(boolean minimized) {
		setStyleName(styleNames.QP_STICKIE_MINIMIZED(), minimized);
	}

	@Override
	public void remove() {
		this.removeFromParent();
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		removePreventHandlerRegistration();
	}

	@Override
	public final void setPosition(int left, int top) {
		Style elementStyle = getElement().getStyle();
		elementStyle.setLeft(left, Unit.PX);
		elementStyle.setTop(top, Unit.PX);
	}

	@Override
	public ContainerDimensions getStickieDimensions() {
		return widgetSizeHelper.getContainerDimensions(this);
	}

	@Override
	public ContainerDimensions getParentDimensions() {
		return widgetSizeHelper.getContainerDimensions((Widget) parent);
	}

	private void removePreventHandlerRegistration() {
		if (preventHandlerRegistration != null) {
			preventHandlerRegistration.removeHandler();
			preventHandlerRegistration = null;
		}
	}
}
