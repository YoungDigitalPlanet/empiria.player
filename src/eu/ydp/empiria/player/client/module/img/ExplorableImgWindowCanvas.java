package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.canvasadapter.client.CanvasAdapter;
import eu.ydp.canvasadapter.client.Context2dAdapter;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.components.PanelWithScrollbars;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.util.events.dom.emulate.coordinates.EventsCoordinates;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.ITouchHandlerInitializer;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnEndHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnMoveHandler;
import eu.ydp.empiria.player.client.util.events.dom.emulate.handlers.touchon.TouchOnStartHandler;

public class ExplorableImgWindowCanvas extends AbstractExplorableImgWindowBase {

	private static ExplorableCanvasImgContentUiBinder uiBinder = GWT.create(ExplorableCanvasImgContentUiBinder.class);

	interface ExplorableCanvasImgContentUiBinder extends UiBinder<Widget, ExplorableImgWindowCanvas> {
	}

	@UiField
	protected FlowPanel imagePanel;
	@UiField
	protected CanvasAdapter imageCanvas;
	@UiField
	protected PanelWithScrollbars scrollbarsPanel;

	private final Context2dAdapter context2d;
	private static final int REDRAW_INTERVAL_MIN = 50;
	private double imgX = 0;
	private double imgY = 0;

	private double prevX, prevY;
	private boolean moving = false;
	private double prevDistance = -1;
	private long lastRedrawTime = -1;
	private boolean imageLoaded = false;

	private Image tempImage;

	private final TouchController touchController;

	private final ITouchHandlerInitializer<EventsCoordinates> touchHandlerInitializer;

	public ExplorableImgWindowCanvas() {
		initWidget(uiBinder.createAndBindUi(this));
		context2d = imageCanvas.getContext2d();
		touchController = PlayerGinjectorFactory.getPlayerGinjector().getTouchController();
		touchHandlerInitializer = PlayerGinjectorFactory.getPlayerGinjector().getTouchHandlerProvider().getTouchHandlersInitializer();
	}

	@Override
	public void init(int wndWidth, int wndHeight, String imageUrl, double initialScale, double scaleStep, double zoomMax, String title) {
		setWindowWidth(wndWidth);
		setWindowHeight(wndHeight);
		setScale(initialScale);
		setScaleStep(scaleStep);
		setZoomMax(zoomMax);

		createAndInitializeTempImage(imageUrl);
		setUpImageCanvasProperties(title);

		scrollbarsPanel.setSize(getWindowWidth() + "px", getWindowHeight() + "px");
		FocusWidget focusCanvas = (FocusWidget) imageCanvas.asWidget();
		addHandlersToCanvas(focusCanvas);
	}

	private void setUpImageCanvasProperties(String title) {
		imageCanvas.setCoordinateSpaceWidth(getWindowWidth());
		imageCanvas.setCoordinateSpaceHeight(getWindowHeight());
		imageCanvas.setWidth(getWindowWidth() + "px");
		imageCanvas.setHeight(getWindowHeight() + "px");
		imageCanvas.setTitle(title);
	}

	private void createAndInitializeTempImage(String imageUrl) {
		tempImage = new Image(imageUrl);
		RootPanel.get().add(tempImage);
		// TODO: try to put img on a div with visibility:hidden
		// see
		// http://gwt-image-loader.googlecode.com/svn/trunk/src/com/reveregroup/gwt/imagepreloader/ImagePreloader.java
		if (!Navigator.getUserAgent().contains("MSIE")) {
			tempImage.setVisible(false);
		}

		tempImage.addLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				imageLoaded = true;

				setOriginalImageWidth(tempImage.getWidth());
				setOriginalImageHeight(tempImage.getHeight());

				findScaleMinAndOriginalAspectRatio();

				centerImage();
				redraw(false);

				RootPanel.get().remove(tempImage);
			}
		});

		tempImage.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				RootPanel.get().remove(tempImage);
			}
		});
	}

	private void addHandlersToCanvas(FocusWidget focusCanvas) {
		touchHandlerInitializer.addTouchStartHandler(createTouchStartHandler(), focusCanvas);
		touchHandlerInitializer.addTouchMoveHandler(createTouchMoveHandler(), focusCanvas);
		touchHandlerInitializer.addTouchEndHandler(createTouchEndHandler(), focusCanvas);

		addMouseDownHandler(focusCanvas);
		addMouseMoveHandler(focusCanvas);
		addMouseUpHandler(focusCanvas);
		addMouseOutHandler(focusCanvas);
	}

	private TouchOnMoveHandler<EventsCoordinates> createTouchMoveHandler() {
		return new TouchOnMoveHandler<EventsCoordinates>() {

			@Override
			public void onMove(EventsCoordinates eventCoordinates) {
				if (eventCoordinates.getLength() == 1) {
					onMoveMove(eventCoordinates.getEvent(0).getX(), eventCoordinates.getEvent(0).getY());
				} else if (eventCoordinates.getLength() == 2) {
					onMoveScale(eventCoordinates.getEvent(0).getX(), eventCoordinates.getEvent(0).getY(), eventCoordinates.getEvent(1).getX(),
							eventCoordinates.getEvent(1).getY());
				}
			}
		};
	}

	private TouchOnEndHandler<EventsCoordinates> createTouchEndHandler() {
		return new TouchOnEndHandler<EventsCoordinates>() {

			@Override
			public void onEnd(EventsCoordinates eventCoordinates) {
				onMoveEnd();
			}
		};
	}

	private TouchOnStartHandler<EventsCoordinates> createTouchStartHandler() {
		return new TouchOnStartHandler<EventsCoordinates>() {

			@Override
			public void onStart(EventsCoordinates eventCoordinates) {
				onMoveStart(eventCoordinates.getEvent(0).getX(), eventCoordinates.getEvent(0).getY());
			}
		};
	}

	// private void addPointerDownHandler(FocusWidget focusCanvas) {
	// focusCanvas.addDomHandler(new PointerDownHandler() {
	//
	// @Override
	// public void onPointerDown(PointerDownEvent event) {
	// event.getTouchesManager().addEvent(event);
	// onMoveStart(event.getTouchesManager().getEvent(0).getX(),
	// event.getTouchesManager().getEvent(0).getY());
	// event.preventDefault();
	// }
	// }, PointerDownEvent.getType());
	// }
	//
	// private void addPointerUpHandler(FocusWidget focusCanvas) {
	// focusCanvas.addDomHandler(new PointerUpHandler() {
	//
	// @Override
	// public void onPointerUp(PointerUpEvent event) {
	// onMoveEnd();
	// event.preventDefault();
	// }
	// }, PointerUpEvent.getType());
	//
	// }
	//
	// private void addPointerMoveHandler(FocusWidget focusCanvas) {
	// focusCanvas.addDomHandler(new PointerMoveHandler() {
	//
	// @Override
	// public void onPointerMove(PointerMoveEvent event) {
	// event.getTouchesManager().addEvent(event);
	// if (event.getTouchesManager().getLength() == 1) {
	// onMoveMove(event.getTouchesManager().getEvent(0).getX(),
	// event.getTouchesManager().getEvent(0).getY());
	// } else if (event.getTouchesManager().getLength() == 2) {
	// onMoveScale(event.getTouchesManager().getEvent(0).getX(),
	// event.getTouchesManager().getEvent(0).getY(),
	// event.getTouchesManager().getEvent(1).getX(),
	// event.getTouchesManager().getEvent(1).getY());
	// }
	// event.preventDefault();
	//
	// }
	// }, PointerMoveEvent.getType());
	// }

	private void addMouseOutHandler(FocusWidget focusCanvas) {
		focusCanvas.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				onMoveEnd();
			}
		});
	}

	private void addMouseUpHandler(FocusWidget focusCanvas) {
		focusCanvas.addMouseUpHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				onMoveEnd();
			}
		});
	}

	private void addMouseMoveHandler(FocusWidget focusCanvas) {
		focusCanvas.addMouseMoveHandler(new MouseMoveHandler() {

			@Override
			public void onMouseMove(MouseMoveEvent event) {
				onMoveMove(event.getClientX(), event.getClientY());
			}
		});
	}

	private void addMouseDownHandler(FocusWidget focusCanvas) {
		focusCanvas.addMouseDownHandler(new MouseDownHandler() {

			@Override
			public void onMouseDown(MouseDownEvent event) {
				onMoveStart(event.getClientX(), event.getClientY());
			}
		});
	}

	// private void addTouchEndHandler(FocusWidget focusCanvas) {
	// focusCanvas.addTouchEndHandler(new TouchEndHandler() {
	//
	// @Override
	// public void onTouchEnd(TouchEndEvent event) {
	// onMoveEnd();
	// event.preventDefault();
	// }
	// });
	// }
	//
	// private void addTouchMoveHandler(FocusWidget focusCanvas) {
	// focusCanvas.addTouchMoveHandler(new TouchMoveHandler() {
	//
	// @Override
	// public void onTouchMove(TouchMoveEvent event) {
	// if (event.getTouches().length() == 1) {
	// onMoveMove(event.getTouches().get(0).getClientX(),
	// event.getTouches().get(0).getClientY());
	// } else if (event.getTouches().length() == 2) {
	// onMoveScale(event.getTouches().get(0).getClientX(),
	// event.getTouches().get(0).getClientY(),
	// event.getTouches().get(1).getClientX(),
	// event.getTouches().get(1).getClientY());
	// }
	// event.preventDefault();
	// }
	// });
	// }
	//
	// private void addTouchStartHandler(FocusWidget focusCanvas) {
	// focusCanvas.addTouchStartHandler(new TouchStartHandler() {
	//
	// @Override
	// public void onTouchStart(TouchStartEvent event) {
	// Touch firstTouch = event.getTouches().get(0);
	// onMoveStart(firstTouch.getClientX(), firstTouch.getClientY());
	// event.preventDefault();
	// }
	// });
	// }

	private void onMoveStart(int x, int y) {// NOPMD
		disableSwype();
		moving = true;
		prevX = x;
		prevY = y;
	}

	private void onMoveScale(int x1, int y1, int x2, int y2) {// NOPMD
		double currDistance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		if (prevDistance != -1) {
			scaleBy(currDistance / prevDistance);
			redraw(true);
		}

		prevDistance = currDistance;

	}

	private boolean isZoomed() {
		return getScale() > getScaleMin();
	}

	private void disableSwype() {
		touchController.setTouchReservation(isZoomed());
	}

	@SuppressWarnings("PMD")
	private void onMoveMove(int x, int y) {
		disableSwype();

		if (moving) {

			double dx = x - prevX;
			double dy = y - prevY;

			double zoom = getZoom();

			imgX -= dx / zoom;
			imgY -= dy / zoom;

			redraw(true);

			prevX = x;
			prevY = y;

		}
	}

	private void onMoveEnd() {
		moving = false;
		prevDistance = -1;
	}

	private void redraw(boolean showScrollbars) {

		checkImageCoords();

		double scaleNormalized = getScale();

		double sourceX = imgX;
		double sourceY = imgY;
		double sourceWidth = getOriginalImageWidth() / scaleNormalized;
		double sourceHeight = (getOriginalImageWidth() * getWindowHeight() / getWindowWidth()) / scaleNormalized;
		double destWidth = getWindowWidth();
		double destHeight = getWindowHeight();

		if (sourceX + sourceWidth > getOriginalImageWidth()) {
			sourceWidth = getOriginalImageWidth() - sourceX;
			double zoom = getZoom();
			destWidth = sourceWidth * zoom;
		}
		if (sourceY + sourceHeight > getOriginalImageHeight()) {
			sourceHeight = getOriginalImageHeight() - sourceY;
			double zoom = getZoom();
			destHeight = sourceHeight * zoom;
		}

		if (System.currentTimeMillis() - lastRedrawTime > REDRAW_INTERVAL_MIN) {
			if (imageLoaded) {
				double destX = 0;
				double destY = 0;
				clearContext();
				context2d.drawImage(ImageElement.as(tempImage.getElement()), sourceX, sourceY, sourceWidth, sourceHeight, destX, destY, destWidth, destHeight);
			}
			lastRedrawTime = System.currentTimeMillis();
			updateScrollbars(showScrollbars);
		}
	}

	private void clearContext() {
		context2d.clearRect(0, 0, getWindowWidth(), getWindowHeight());
	}

	private void updateScrollbars(boolean showScrollbars) {
		double posX = imgX * getZoom();
		double posY = imgY * getZoom();
		scrollbarsPanel.setHorizontalPosition(posX, getWindowWidth(), getOriginalImageWidth() * getZoom(), showScrollbars);
		scrollbarsPanel.setVerticalPosition(posY, getWindowHeight(), getOriginalImageHeight() * getZoom(), showScrollbars);
	}

	private void scaleBy(double dScale) {
		double newScale;
		if (getZoom() * dScale > getZoomMax()) {
			newScale = getOriginalImageWidth() / getWindowWidth() * (getZoomMax());
		} else if (getScale() * dScale > getScaleMin()) {
			newScale = getScale() * dScale;
		} else {
			newScale = getScaleMin();
		}

		double lastCenterX = imgX * getZoom() + getWindowWidth() / 2;
		double lastCenterY = imgY * getZoom() + getWindowHeight() / 2;

		double newCenterX = lastCenterX * newScale / getScale();
		double newCenterY = lastCenterY * newScale / getScale();

		int newImgX = (int) (newCenterX - getWindowWidth() / 2);
		int newImgY = (int) (newCenterY - getWindowHeight() / 2);

		imgX = newImgX / getZoom(newScale);
		imgY = newImgY / getZoom(newScale);

		setScale(newScale);

	}

	private void checkImageCoords() {

		if (imgX + getOriginalImageWidth() / getScale() > getOriginalImageWidth()) {
			imgX = getOriginalImageWidth() - (int) (getOriginalImageWidth() / getScale()) - 1;
		}

		double height = (getOriginalImageWidth() * getWindowHeight() / getWindowWidth());

		if (imgY + height / getScale() > getOriginalImageHeight()) {
			imgY = getOriginalImageHeight() - (int) (height / getScale()) - 1;
		}

		if (imgX < 0) {
			imgX = 0;
		}

		if (imgY < 0) {
			imgY = 0;
		}
	}

	private void centerImage() {

		imgX = (getOriginalImageWidth() - getWindowWidth() * getScale()) / 2 / getScale();
		imgY = (getOriginalImageHeight() - getWindowHeight() * getScale()) / 2 / getScale();
	}

	@Override
	public void zoomIn() {
		scaleBy(getScaleStep());
		redraw(true);
	}

	@Override
	public void zoomOut() {
		scaleBy(1.0d / getScaleStep());
		redraw(true);
	}
}
