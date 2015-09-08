package eu.ydp.empiria.player.client.controller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.preloader.view.ProgressBundle;

@Singleton
public class ContentPreloader {

    private final String MAIN_PRELOADER_ID = "mainPreloader";
    private Image mainPreloader;

    public void setPreloader() {
        InsertPanel.ForIsWidget rootPanel = RootPanel.get();
        RootPanel preloaderWidget = RootPanel.get(MAIN_PRELOADER_ID);
        if (preloaderWidget == null) {
            ProgressBundle progressBundle = GWT.create(ProgressBundle.class);
            ImageResource progressImage = progressBundle.getProgressImage();
            mainPreloader = new Image(progressImage);
        } else {
            RootPanel widget = preloaderWidget;
            mainPreloader = Image.wrap(widget.getElement());
        }
        rootPanel.add(mainPreloader);
        centerMainPreloader(Window.getClientWidth() / 2, Window.getClientHeight() / 2, mainPreloader.getElement());
    }

    private void centerMainPreloader(int x, int y, com.google.gwt.dom.client.Element preloaderElement) {
        preloaderElement.setId(MAIN_PRELOADER_ID);
        preloaderElement.getStyle()
                .setPosition(Style.Position.ABSOLUTE);
        preloaderElement.getStyle()
                .setLeft(x - preloaderElement.getOffsetWidth() / 2, Style.Unit.PX);
        preloaderElement.getStyle()
                .setTop(y - preloaderElement.getOffsetHeight() / 2, Style.Unit.PX);
    }

    public void removePreloader() {
        RootPanel rootPanel = RootPanel.get();
        int preloaderIndex = rootPanel.getWidgetIndex(mainPreloader);

        if (preloaderIndex >= 0) {
            rootPanel.remove(preloaderIndex);
        }
    }
}
