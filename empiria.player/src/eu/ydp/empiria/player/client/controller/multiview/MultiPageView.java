package eu.ydp.empiria.player.client.controller.multiview;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class MultiPageView extends FlowPanel implements PlayerEventHandler, FlowRequestSocketUserExtension, Extension, ResizeHandler {

	private class ResizeTimer extends Timer {
		protected int lastSize = 0;

		protected MultiPageView pageView;

		public ResizeTimer(MultiPageView pageview) {
			this.pageView = pageview;
		}

		@Override
		public void run() {
			int height = pageView.getHeightForPage(currentVisiblePage);
			if (height > 0) {
				pageView.setHeight(height);
				pageView.hideProgressBarForPage(currentVisiblePage);
				if (lastSize == 0 || lastSize != height) {
					schedule(250);
				} else {
					measuredPanels.add(currentVisiblePage);
				}
				lastSize = height;
			} else {
				schedule(250);
			}
		}

	};

	private final static int ACTIVE_PAGE_COUNT = 3;
	private int currentVisiblePage = -1;
	private final Map<Integer, KeyValue<FlowPanel, FlowPanel>> panels = new TreeMap<Integer, KeyValue<FlowPanel, FlowPanel>>(Collections.reverseOrder());
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected final StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	private int start;
	private int lastEnd = 0;
	private int end = 0;
	private int startY = 0, endY = 0;
	private int swipeLength = 220;
	private final static float WIDTH = 100;
	private final static int DEFAULT_ANIMATION_TIME = 500;
	private final static int QUICK_ANIMATION_TIME = 150;
	private final int TOUCH_START_TIME;
	private float currentPosition;
	private FlowRequestInvoker flowRequestInvoker;
	private boolean touchReservation = false;
	private boolean swipeStarted = false;
	private boolean swipeRight = false;
	private final FlowPanel mainPanel = new FlowPanel();
	private final Set<Integer> measuredPanels = new HashSet<Integer>();
	private final Set<Integer> detachedPages = new HashSet<Integer>();
	/**
	 * ktore strony zostaly zaladowane
	 */
	private final Set<Integer> loadedPages = new HashSet<Integer>();
	private final Scheduler scheduler = Scheduler.get();
	private final static int VISBLE_PAGE_COUNT = 3;
	private int pageProgressBar = -1;
	private final ResizeTimer resizeTimer;
	private final Page page = PlayerGinjector.INSTANCE.getPage();
	private final Timer timer = new Timer() {

		@Override
		public void run() {
			if (Math.abs(startY - endY) < Math.abs(start - end)) {
				swipeStarted = true;
				setStylesForPages(swipeStarted);
			}
		}
	};

	public MultiPageView() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.ASSESSMENT_STARTED), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TOUCH_EVENT_RESERVATION), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_VIEW_LOADED), this);
		if (UserAgentChecker.isStackAndroidBrowser()) {
			TOUCH_START_TIME = 250;
		}else{
			TOUCH_START_TIME = 100;
		}
		resizeTimer = new ResizeTimer(this);
		add(mainPanel);
		new PageEvents(this);
		Window.addResizeHandler(this);
	}

	private void setStylesForPages(boolean swipeStarted) {
		if (swipeStarted) {
			Panel selectedPanel = panels.get(currentVisiblePage).getKey();
			selectedPanel.setStyleName(styleNames.QP_PAGE_SELECTED());
			for (Map.Entry<Integer, KeyValue<FlowPanel, FlowPanel>> panel : panels.entrySet()) {
				if (panel.getKey() != currentVisiblePage) {
					panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_UNSELECTED());
					if (panel.getKey() == currentVisiblePage - 1) {
						panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_PREV());
					} else if (panel.getKey() == currentVisiblePage + 1) {
						panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_NEXT());
					}
				}
			}
		} else {
			for (Map.Entry<Integer, KeyValue<FlowPanel, FlowPanel>> panel : panels.entrySet()) {
				FlowPanel flowPanel = panel.getValue().getKey();
				flowPanel.removeStyleName(styleNames.QP_PAGE_UNSELECTED());
				flowPanel.removeStyleName(styleNames.QP_PAGE_SELECTED());
				flowPanel.removeStyleName(styleNames.QP_PAGE_PREV());
				flowPanel.removeStyleName(styleNames.QP_PAGE_NEXT());
			}
		}
	}

	private int getHeightForPage(int pageNumber) {
		FlowPanel flowPanel = (FlowPanel) getViewForPage(pageNumber).getParent();
		return flowPanel == null ? 0 : flowPanel.getOffsetHeight();
	}

	private void setHeight(int height) {
		this.setHeight(height + "px");
	}

	@Override
	protected void onAttach() {
		Style style = mainPanel.getElement().getStyle();
		style.setWidth(WIDTH, Unit.PCT);
		style.setPosition(Position.ABSOLUTE);
		style.setTop(0, Unit.PX);
		style.setLeft(0, Unit.PX);
		this.getElement().getStyle().setPosition(Position.RELATIVE);
		setSwipeLength();
		super.onAttach();
	};

	private void setSwipeLength() {
		swipeLength = RootPanel.get().getOffsetWidth() / 5;
	}

	@Override
	public void onResize(ResizeEvent event) {
		setSwipeLength();
	}

	protected void setVisiblePanels(int pageNumber) {
		showProgressBarForPage(pageNumber);
		if (!measuredPanels.contains(pageNumber)) {
			resizeTimer.schedule(350);
		}
		if (currentVisiblePage != pageNumber && pageNumber >= 0) {
			this.currentVisiblePage = pageNumber;
			if (ACTIVE_PAGE_COUNT > 1) {
				if (measuredPanels.contains(pageNumber)) {
					setHeight(getHeightForPage(pageNumber));
				}
				if (this.equals(getViewForPage(pageNumber).getParent())) {
					animatePageSwitch(getPositionLeft(), -pageNumber * WIDTH, null, DEFAULT_ANIMATION_TIME, false);
				} else {
					animatePageSwitch(getPositionLeft(), -pageNumber * WIDTH, null, DEFAULT_ANIMATION_TIME, false);
				}
			}
		}else{
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
		hideProgressBarForPage(pageNumber);

	}

	/**
	 * zwraca Panel bdacy kontenerem dla widgetow ze strony pageNumber
	 *
	 * @param pageNumber
	 * @return
	 */
	public FlowPanel getViewForPage(Integer pageNumber) {
		KeyValue<FlowPanel, FlowPanel> panel = null;
		if ((panel = panels.get(pageNumber)) == null) {// NOPMD
			if (panels.isEmpty()) {
				for (int index = 0; index <= pageNumber; ++index) {
					panel = createAndPutToCache(index);
				}
			} else {
				Integer maxIndex = panels.keySet().iterator().next();
				if (pageNumber > maxIndex) {
					for (int index = maxIndex + 1; index <= pageNumber; ++index) {
						panel = createAndPutToCache(index);
					}
				}
			}
			panel.getKey().addStyleName(styleNames.QP_PAGE_UNSELECTED());
			panel.getKey().addStyleName(pageNumber > currentVisiblePage ? styleNames.QP_PAGE_NEXT() : styleNames.QP_PAGE_PREV());
			showProgressBarForPage(pageNumber);
		}
		loadedPages.add(pageNumber);
		return panel.getValue();
	}

	private KeyValue<FlowPanel, FlowPanel> createAndPutToCache(int index) {
		KeyValue<FlowPanel, FlowPanel> panel = createPanel(index);
		panels.put(index, panel);
		mainPanel.add(panel.getKey());
		return panel;
	}

	private KeyValue<FlowPanel, FlowPanel> createPanel(int index) {
		FlowPanel parent = new FlowPanel();
		Style style = parent.getElement().getStyle();
		parent.getElement().setId(styleNames.QP_PAGE() + index);
		style.setPosition(Position.ABSOLUTE);
		style.setTop(0, Unit.PX);
		style.setLeft(WIDTH * index, Unit.PCT);
		style.setWidth(WIDTH, Unit.PCT);
		FlowPanel childPanel = new FlowPanel();
		childPanel.setHeight("100%");
		childPanel.setWidth("100%");
		parent.add(childPanel);
		return new KeyValue<FlowPanel, FlowPanel>(parent, childPanel);
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case TOUCH_EVENT_RESERVATION:
			this.touchReservation = true;
			break;
		case LOAD_PAGE_VIEW:
		case ASSESSMENT_STARTED:
			setVisiblePanels((Integer) (event.getValue() == null ? 0 : event.getValue()));
			break;
		case PAGE_VIEW_LOADED:
			detachAttachPanels();
			break;
		default:
			break;
		}
	}

	private void detachAttachPanels() {
		Set<Integer> activePanels = new HashSet<Integer>(panels.keySet());
		activePanels.removeAll(detachedPages);
		int min = (int) Math.ceil(VISBLE_PAGE_COUNT / 2);
		int max = (int) Math.floor(VISBLE_PAGE_COUNT / 2);
		for (int page = currentVisiblePage - min; page <= currentVisiblePage + max; ++page) {
			activePanels.remove(page);
		}

		for (final Integer page : activePanels) {
			scheduler.scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					panels.get(page).getValue().removeFromParent();
				}
			});
			detachedPages.add(page);
		}

		activePanels.clear();
		for (int page = currentVisiblePage - min; page <= currentVisiblePage + max; ++page) {
			activePanels.add(page);
		}
		for (Integer page : activePanels) {
			final int pageNumber = page;
			if (panels.containsKey(page) && detachedPages.contains(page)) {
				final KeyValue<FlowPanel, FlowPanel> pair = panels.get(page);
				scheduler.scheduleDeferred(new ScheduledCommand() {
					@Override
					public void execute() {
						pair.getKey().add(pair.getValue());
						if(pageNumber == currentVisiblePage){
							setHeight(getHeightForPage(currentVisiblePage));
						}
					}
				});
				detachedPages.remove(page);
			}
		}
	}

	/**
	 * animacja
	 *
	 * @param from
	 *            absolutna pozycja elementu
	 * @param to
	 *            absolutna pozycja elementu cel
	 * @param direction
	 */
	private void animatePageSwitch(float from, final float to, final NavigationButtonDirection direction, int duration, final boolean onlyPositionReset) {// NOPMD
		if (Math.abs(from - to) > 1) {
			if (!onlyPositionReset) {
				Window.scrollTo(0, 0);
			}
			PageSwitchAnimation animation = new PageSwitchAnimation(mainPanel, from, to) {
				@Override
				protected void onComplete() {
					super.onComplete();
					// ominiecia laga nakoncu animacji
					scheduler.scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							setStylesForPages(false);
							if (direction != null) {
								flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(direction));
							}
							if (!onlyPositionReset) {
								eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
							}
							currentPosition = to;
						}
					});

				}
			};

			animation.run(duration);
		} else if (!onlyPositionReset) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
	}

	protected float getPositionLeft() {
		Style style = mainPanel.getElement().getStyle();
		return NumberUtils.tryParseFloat(style.getLeft().replaceAll("[a-z%]+$", ""));

	}

	private void move(boolean swipeRight, float length) {
		if (swipeRight) {
			showProgressBarForPage(currentVisiblePage + 1);
		} else {
			showProgressBarForPage(currentVisiblePage - 1);
		}
		Style style = mainPanel.getElement().getStyle();
		float position = getPositionLeft();
		if (swipeRight) {
			style.setLeft(position - length, Unit.PCT);
		} else {
			style.setLeft(position + length, Unit.PCT);
		}

	}

	private void showProgressBarForPage(int pageIndex) {
		if (!loadedPages.contains(pageIndex) && pageProgressBar != pageIndex && pageIndex < page.getPageCount() - 1 && pageIndex >= 0) {
			Panel panel = getViewForPage(Integer.valueOf(pageIndex));
			panel.add(new ProgressPanel());
			pageProgressBar = pageIndex;
		}
	}

	private void hideProgressBarForPage(int pageIndex) {
		if (pageIndex < page.getPageCount() - 1) {
			FlowPanel panel = getViewForPage(Integer.valueOf(pageIndex));
			for (int x = 0; x < panel.getWidgetCount(); ++x) {
				if (panel.getWidget(x) instanceof ProgressPanel) {
					panel.getWidget(x).removeFromParent();
					break;
				}
			}
		}
	}

	private void reset() {
		timer.cancel();
		touchReservation = false;
		swipeStarted = false;
		swipeRight = false;
		setStylesForPages(swipeStarted);

	}

	public void onEvent(NativeEvent event) {
		switch (Event.getTypeInt(event.getType())) {
		case Event.ONMOUSEDOWN:
		case Event.ONTOUCHSTART:
			onTouchStart(event);
			break;
		case Event.ONMOUSEUP:
		case Event.ONTOUCHEND:
		case Event.ONTOUCHCANCEL:
		case Event.ONMOUSEOUT:
			timer.cancel();
			if (swipeStarted) {
				event.preventDefault();
			}
			onTouchEnd(event);
			break;
		case Event.ONMOUSEMOVE:
		case Event.ONTOUCHMOVE:
			if (swipeStarted) {
				event.preventDefault();
			}
			onTouchMove(event);
			break;
		default:
			break;
		}
	}

	public void onTouchMove(NativeEvent event) {
		if (!touchReservation && swipeStarted) {
			JsArray<Touch> touches = event.getTouches();
			if (touches == null) {
				end = event.getScreenX();
			} else {
				for (int x = 0; x < touches.length();) {
					Touch touch = touches.get(x);
					end = touch.getPageX();
					endY = touch.getScreenY();
					break;
				}
			}
			if (lastEnd != end) {
				swipeRight = (lastEnd > end);
				move(swipeRight, ((float) Math.abs(lastEnd - end) / RootPanel.get().getOffsetWidth()) * 100);
				lastEnd = end;
			}

		}
	}

	public void onTouchEnd(NativeEvent event) {
		if (!touchReservation && swipeStarted) {
			reset();
			JsArray<Touch> touches = event.getChangedTouches();
			if (touches == null) {
				end = event.getScreenX();
			} else {
				for (int x = 0; x < touches.length();) {
					Touch touch = touches.get(x);
					end = touch.getPageX();
					endY = touch.getScreenY();
					break;
				}
			}
			if (end > 0 && (Window.getClientHeight() / 2.5) > (startY > endY ? startY - endY : endY - startY)) {
				switchPage(swipeLength);
			}
		} else {
			reset();
		}
	}

	public void onTouchStart(NativeEvent event) {
		JsArray<Touch> touches = event.getTouches();
		boolean multiTouch = false;
		if (touches == null) {
			lastEnd = start = event.getScreenX();
			multiTouch = false;
		} else {
			for (int x = 0; x < touches.length();) {
				Touch touch = touches.get(x);
				lastEnd = start = touch.getPageX();
				startY = touch.getScreenY();
				end = -1;
				break;
			}
			multiTouch = touches.length() > 1;
		}
		// Czekamy zanima rozpoczniemy przesuwanie
		if (!multiTouch) {
			timer.schedule(TOUCH_START_TIME);
		}
	}

	public NavigationButtonDirection getDirection() {
		NavigationButtonDirection direction = null;
		if (end > start) {
			direction = NavigationButtonDirection.PREVIOUS;
		} else if (start > end) {
			direction = NavigationButtonDirection.NEXT;
		}
		return direction;

	}

	private void switchPage(int minSwipeLength) {
		if (touchReservation) {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			return;
		}
		int swipeLength = Math.abs(start - end);

		if (swipeLength >= minSwipeLength) {
			NavigationButtonDirection direction = getDirection();
			float percent = (float) swipeLength / RootPanel.get().getOffsetWidth() * 100;
			if (direction != null) {
				if (direction == NavigationButtonDirection.PREVIOUS && page.getCurrentPageNumber() > 0) {
					animatePageSwitch(getPositionLeft(), getPositionLeft() + (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				} else if (direction == NavigationButtonDirection.NEXT && page.getCurrentPageNumber() < page.getPageCount() - 1) {
					animatePageSwitch(getPositionLeft(), getPositionLeft() - (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				} else {
					animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
				}
			}
		} else {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
		}
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;

	}

	@Override
	public void init() {// NOPMD

	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

}
