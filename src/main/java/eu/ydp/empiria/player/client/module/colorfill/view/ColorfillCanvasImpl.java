package eu.ydp.empiria.player.client.module.colorfill.view;

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
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

import javax.annotation.PostConstruct;
import java.util.Map;

public class ColorfillCanvasImpl implements ColorfillCanvas {

    @Inject
    private CanvasImageView canvasStubView;
    @Inject
    private UserInteractionHandlerFactory interactionHandlerFactory;
    @Inject
    private PositionHelper positionHelper;
    @Inject
    private CanvasImageDataProvider canvasImageDataProvider;
    @Inject
    private StyleNameConstants styleNameConstants;

    private boolean canvasStubViewLoded = false;

    private final Map<Area, ColorModel> colorsCache = Maps.newHashMap();

    private ColorfillAreaClickListener listener;

    private ICanvasImageData imageData;

    @PostConstruct
    public void postConstruct() {
        canvasStubView.setPanelStyle(styleNameConstants.QP_COLORFILL_IMG());
        canvasStubView.setImageLoadHandler(new LoadHandler() {

            @Override
            public void onLoad(LoadEvent event) {
                canvasStubViewLoded = true;
                bindView();
            }
        });
    }

    private void bindView() {
        reloadImageData();
        for (Map.Entry<Area, ColorModel> areaColor : colorsCache.entrySet()) {
            setColorOnCanvas(areaColor.getKey(), areaColor.getValue());
        }
        colorsCache.clear();
        if (listener != null) {
            addAreaClickListener(listener);
        }
        flushImageToCanvas();
    }

    private void reloadImageData() {
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
            colorsCache.put(area, color);
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
            this.colorsCache.putAll(colors);
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
                Area area = new Area(positionHelper.getXPositionRelativeToTarget(event, canvasElement), positionHelper.getYPositionRelativeToTarget(event,
                        canvasElement));
                if (area.getX() >= 0 && area.getY() >= 0) {
                    listener.onAreaClick(area);
                }
            }
        }, canvasStubView.getCanvas());
    }

    @Override
    public void reset() {
        canvasStubView.reload();
        reloadImageData();
    }

    private void flushImageToCanvas() {
        imageData.flush();
    }

    @Override
    public Widget asWidget() {
        return canvasStubView.asWidget();
    }

}
