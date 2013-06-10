package eu.ydp.empiria.player.client.module.colorfill.view;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.fill.BlackColorContourDetector;
import eu.ydp.empiria.player.client.module.colorfill.fill.FloodFillScanLine;
import eu.ydp.empiria.player.client.module.colorfill.fill.ICanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class ColorfillCanvasImpl implements ColorfillCanvas {

	@Inject
	private ColorfillCanvasStubView canvasStubView;

	@Inject
	private UserInteractionHandlerFactory interactionHandlerFactory;

	@Inject
	private PositionHelper positionHelper;
	
	@Inject
	private CanvasImageDataProvider canvasImageDataProvider;

	private boolean canvasStubViewLoded = false;

	private final Map<Area, ColorModel> colors = Maps.newHashMap();

	private ColorfillAreaClickListener listener;

	private ICanvasImageData imageData;

	@PostConstruct
	public void postConstruct() {
		canvasStubView.setImageLoadHandler(new LoadHandler() {

			@Override
			public void onLoad(LoadEvent event) {
				canvasStubViewLoded = true;
				bindView();
			}
		});
	}

	void bindView() {
		reloadImageData();
		for (Map.Entry<Area, ColorModel> areaColor : colors.entrySet()) {
			setColorOnCanvas(areaColor.getKey(), areaColor.getValue());
		}
		colors.clear();
		if (listener != null) {
			addAreaClickListener(listener);
		}
		flushImageToCanvas();
	}

	void reloadImageData() {
		imageData = canvasImageDataProvider.getCanvasImageData(canvasStubView);
	}

	@Override
	public void setImage(final Image image) {
		canvasStubView.setImageUrl(image.getSrc(), image.getWidth(), image.getHeight());
	}

	@Override
	public void setColor(final Area area, final ColorModel color) {
		if (canvasStubViewLoded) {
			setColorOnCanvas(area, color);
			flushImageToCanvas();
		} else {
			colors.put(area, color);
		}
	}

	private void setColorOnCanvas(Area area, ColorModel color) {
		FloodFillScanLine floodFiller = new FloodFillScanLine(imageData, new BlackColorContourDetector(), color);
		floodFiller.fillArea(area.getX(), area.getY());
	}

	@Override
	public ColorModel getColor(final Area area) {
		return imageData.getRgbColor(area.getX(), area.getY());
	}

	@Override
	public void setColors(final Map<Area, ColorModel> colors) {
		if (canvasStubViewLoded) {
			for (Map.Entry<Area, ColorModel> areaColor : colors.entrySet()) {
				setColorOnCanvas(areaColor.getKey(), areaColor.getValue());
			}
			flushImageToCanvas();
		} else {
			colors.putAll(colors);
		}
	}

	@Override
	public void setAreaClickListener(final ColorfillAreaClickListener listener) {
		if (canvasStubViewLoded) {
			addAreaClickListener(listener);
		} else {
			this.listener = listener;
		}
	}

	private void addAreaClickListener(final ColorfillAreaClickListener listener) {
		interactionHandlerFactory.applyUserClickHandler(new Command() {

			@Override
			public void execute(NativeEvent event) {
				event.preventDefault();
				CanvasElement canvasElement = canvasStubView.getCanvas().getCanvasElement();
				listener.onAreaClick(new Area(positionHelper.getPositionX(event, canvasElement), positionHelper.getPositionY(event, canvasElement)));
			}
		}, canvasStubView.getCanvas());
	}

	@Override
	public void reset() {
		canvasStubView.reload();
		reloadImageData();
	}

	public void flushImageToCanvas(){
		imageData.flush();
	}

	@Override
	public Widget asWidget() {
		return canvasStubView.asWidget();
	}

}
