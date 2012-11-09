package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.touch.client.Point;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class StickieView extends Composite implements IStickieView {

	private static StickieViewUiBinder uiBinder = GWT.create(StickieViewUiBinder.class);

	interface StickieViewUiBinder extends UiBinder<Widget, StickieView> { }
	
	@Inject IPlayerContainersAccessor accessor;
	@Inject StyleNameConstants styleNames;
	@Inject EventsBus eventsBus;

	public StickieView() {
		initWidget(uiBinder.createAndBindUi(this));
		setPositionRaw(-2000, -2000);
	}

	@UiField TextArea contentText;
	@UiField PushButton deleteButton;
	@UiField PushButton minimizeButton;	
	@UiField FocusPanel headerPanel;
	@UiField InlineHTML contentLabel;
	@UiField FlowPanel textPanel;
	@UiField FocusPanel labelPanel;

	private IPresenter presenter;

	private Widget parent;
	
	private boolean dragging = false;
	private Point dragInitMousePosition;
	private Point dragInitViewPosition;
	private Point position;
	HandlerRegistration upHandlerReg;
		
	@UiHandler("minimizeButton")
	public void minimizeHandler(ClickEvent event){
		presenter.stickieMinimize();
	}
	
	@UiHandler("deleteButton")
	public void deleteHandler(ClickEvent event){
		presenter.stickieDelete();
	}
	
	@UiHandler("headerPanel")
	public void mouseDownHandler(MouseDownEvent event){
		dragStart(event.getScreenX(), event.getScreenY());
		setEditing(false);

		final HandlerRegistration moveHandlerReg = RootPanel.get().addDomHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				dragMove(event.getScreenX(), event.getScreenY());
				event.preventDefault();
			}
		}, MouseMoveEvent.getType());

		upHandlerReg = RootPanel.get().addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				dragEnd();
				moveHandlerReg.removeHandler();
			}
			
		}, MouseUpEvent.getType());
		
		event.preventDefault();
	}

	@UiHandler("containerPanel")
	public void touchStartHandler(TouchStartEvent event){
		setEditing(false);
		dragStart(event.getTouches().get(0).getScreenX(), event.getTouches().get(0).getScreenY());
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.TOUCH_EVENT_RESERVATION));
		
		final HandlerRegistration moveHandlerReg = RootPanel.get().addDomHandler(new TouchMoveHandler() {
			
			@Override
			public void onTouchMove(TouchMoveEvent event) {
				dragMove(event.getTouches().get(0).getScreenX(), event.getTouches().get(0).getScreenY());
				event.preventDefault();
			}
		}, TouchMoveEvent.getType());
		
		upHandlerReg = RootPanel.get().addDomHandler(new TouchEndHandler() {
			
			@Override
			public void onTouchEnd(TouchEndEvent event) {
				dragEnd();
				moveHandlerReg.removeHandler();
			}
		}, TouchEndEvent.getType());
		
		event.preventDefault();
	}
	
	void dragStart(int x, int y){
		dragInitMousePosition = new Point(x, y);
		dragInitViewPosition = position;
		dragging = true;
	}
		
	void dragMove(int x, int y){
		if (dragging){
			int newLeft = (int)(dragInitViewPosition.getX() + x - dragInitMousePosition.getX());
			int newTop = (int)(dragInitViewPosition.getY() + y - dragInitMousePosition.getY());
			setPosition(newLeft, newTop);
		}
	}
	
	void dragEnd(){
		dragging = false;
		upHandlerReg.removeHandler();
	}

	@UiHandler("labelPanel")
	public void contentLabelClickHandler(ClickEvent event){
		setEditing(true);
	}

	@UiHandler("contentText")
	public void contentTextBlurHandler(BlurEvent event){
		setEditing(false);
		updateContentLabel();
		presenter.stickieChange();
	}
	
	private void updateContentLabel() {
		contentLabel.setHTML(contentText.getText().replace("\n", "<br/>"));
	}

	private void setEditing(boolean edit) {
		if (edit == labelPanel.isVisible()){
			if (edit){
				if (!contentText.isAttached()){
					textPanel.add(contentText);
				}
				contentText.setVisible(true);
				contentText.setFocus(true);
				contentText.setSelectionRange(contentText.getText().length(), 0);
				scrollToStickie();
			} else {
				contentText.getElement().blur();
				contentText.removeFromParent();
			}
			labelPanel.setVisible(!edit);
		}
	}
	
	void scrollToStickie(){
		if (UserAgentChecker.isMobileUserAgent()){
			Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {
				
				@Override
				public boolean execute() {
					Window.scrollTo(Window.getScrollLeft(), (int) (getAbsoluteTop() - 20));
					return false;
				}
			}, 500);
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
	public void setPresenter(IPresenter presenter) {
		this.presenter = presenter;
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
	public void setViewParent(HasWidgets parent) {
		this.parent = (Widget)parent;
		parent.add(this);
	}

	@Override
	public void centerView() {
		int x = (((Widget)accessor.getPlayerContainer()).getOffsetWidth() - getOffsetWidth())/2;
		int y = (((Widget)accessor.getPlayerContainer()).getOffsetHeight() - getOffsetHeight())/2 - parent.getAbsoluteTop() + ((Widget)accessor.getPlayerContainer()).getAbsoluteTop();
		setPosition(x, y);
	}

	public final void setPosition(int left, int top){
		int newLeft = left;
		int newTop = top;
		
		if (left < 0){
			newLeft = 0;
		} else if (left > parent.getOffsetWidth() - getOffsetWidth()){
			newLeft = parent.getOffsetWidth() - getOffsetWidth();
		}
		
		if (parent != null){
			
			int topMin = ((Widget)accessor.getPlayerContainer()).getAbsoluteTop() - parent.getAbsoluteTop();
			int topMax = ((Widget)accessor.getPlayerContainer()).getAbsoluteTop() + ((Widget)accessor.getPlayerContainer()).getOffsetHeight() - parent.getAbsoluteTop();
			if (top < topMin){
				newTop = topMin;
			} else if (top > topMax - getOffsetHeight()){
				newTop = topMax - getOffsetHeight();
			}
		}
		
		setPositionRaw(newLeft, newTop);
		presenter.stickieChange();
	}

	@Override
	public void setPositionRaw(int left, int top){
		position = new Point(left, top);
		getElement().getStyle().setLeft(left, Unit.PX);
		getElement().getStyle().setTop(top, Unit.PX);		
	}

	@Override
	public int getX() {
		return (int)position.getX();
	}

	@Override
	public int getY() {
		return (int)position.getY();
	}

}