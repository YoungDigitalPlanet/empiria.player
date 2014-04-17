package eu.ydp.empiria.player.client.module.dictionary.external.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;

public class TouchableExtendableList extends FlowPanel {

	protected AbsolutePanel mainPanel;
	protected Panel listPanel;
	protected List<TouchableExtendableListItem> items;
	protected int initialItemsCount = 40;
	protected int extendingItemsCount = 25;
	protected int currentItemsCount = 0;
	protected TouchableExtendableListShowMoreItem showMoreItem;
	protected int touchY;
	protected boolean isTouching;
	protected int marginLeft;
	protected int marginTop;

	public TouchableExtendableList() {
		super();

		mainPanel = new AbsolutePanel();
		mainPanel.setStyleName("tel-main");
		listPanel = new FlowPanel();
		listPanel.setStyleName("tel-list");
		mainPanel.add(listPanel);
		add(mainPanel);
		items = new ArrayList<TouchableExtendableListItem>();
		showMoreItem = new TouchableExtendableListShowMoreItem();
		showMoreItem.addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				showMoreClickHandler(event);
			}
		}, MouseUpEvent.getType());
		isTouching = false;

		addDomHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				onTouchStart(event.getClientX(), event.getClientY());
			}
		}, MouseDownEvent.getType());

		addDomHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				onTouchMove(event.getClientX(), event.getClientY());
			}
		}, MouseMoveEvent.getType());

		addDomHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				onTouchEnd();
			}
		}, MouseUpEvent.getType());

		registerTouchEvents(this.getElement());
	}

	public void addItem(TouchableExtendableListItem item) {
		items.add(item);
	}

	@Override
	public void onLoad() {

		super.onLoad();

		marginLeft = mainPanel.getWidgetLeft(listPanel);
		marginTop = mainPanel.getWidgetTop(listPanel);

		listPanel.clear();

		int i = 0;
		for (; i < initialItemsCount && i < items.size(); i++) {
			listPanel.add(items.get(i));
		}
		currentItemsCount = i + 1;

		listPanel.add(showMoreItem);

	}

	protected void showMoreClickHandler(MouseUpEvent event) {

		listPanel.remove(showMoreItem);

		int previousItemsCount = currentItemsCount;
		int i = previousItemsCount;
		for (; i < previousItemsCount + extendingItemsCount && i < items.size(); i++) {
			listPanel.add(items.get(i));
		}
		currentItemsCount = i + 1;

		listPanel.add(showMoreItem);
	}

	private native void registerTouchEvents(Element el)/*-{
														var instance = this;
														el.ontouchstart = function (e){
														e.preventDefault();
														if(e.touches.length==1){
														var touch=e.touches[0];
														instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.TouchableExtendableList::onTouchStart(II)(touch.pageX, touch.pageY);
														}
														}
														
														el.ontouchmove = function (e){
														e.preventDefault();
														if(e.touches.length==1){
														var touch=e.touches[0];
														instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.TouchableExtendableList::onTouchMove(II)(touch.pageX, touch.pageY);
														}
														}
														
														el.ontouchend = function (e){
														e.preventDefault();
														instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.TouchableExtendableList::onTouchEnd()();
														}
														
														}-*/;

	private void onTouchStart(int x, int y) {
		touchY = y;
		isTouching = true;
	}

	private void onTouchMove(int x, int y) {
		if (isTouching) {
			int listY = mainPanel.getWidgetTop(listPanel);
			int listX = mainPanel.getWidgetLeft(listPanel);
			int delta = y - touchY;
			touchY = y;
			mainPanel.setWidgetPosition(listPanel, listX - marginLeft, listY + delta - marginTop);
		}
	}

	private void onTouchEnd() {
		isTouching = false;
	}
}
