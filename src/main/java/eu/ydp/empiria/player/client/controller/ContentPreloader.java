/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.preloader.view.ProgressBundle;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;

@Singleton
public class ContentPreloader {

    private static final String MAIN_PRELOADER_ID = "mainPreloader";
    private final RootPanelDelegate rootPanelDelegate;

    private final WindowDelegate windowDelegate;
    private final ProgressBundle progressBundle;

    private Image mainPreloader;

    @Inject
    public ContentPreloader(ProgressBundle progressBundle, WindowDelegate windowDelegate, RootPanelDelegate rootPanelDelegate) {
        this.progressBundle = progressBundle;
        this.windowDelegate = windowDelegate;
        this.rootPanelDelegate = rootPanelDelegate;
    }

    public void setPreloader() {
        RootPanel preloaderWidget = rootPanelDelegate.getRootPanel(MAIN_PRELOADER_ID);
        if (preloaderWidget == null) {
            ImageResource progressImage = progressBundle.getProgressImage();
            mainPreloader = new Image(progressImage);
        } else {
            mainPreloader = Image.wrap(preloaderWidget.getElement());
        }
        RootPanel rootPanel = rootPanelDelegate.getRootPanel();
        rootPanel.add(mainPreloader);

        int halfWidth = windowDelegate.getClientWidth() / 2;
        int halfHeight = windowDelegate.getClientHeight() / 2;
        centerMainPreloader(halfWidth, halfHeight, mainPreloader.getElement());
    }

    private void centerMainPreloader(int x, int y, Element preloaderElement) {
        preloaderElement.setId(MAIN_PRELOADER_ID);
        preloaderElement.getStyle().setPosition(Style.Position.ABSOLUTE);
        int leftOffset = x - preloaderElement.getOffsetWidth() / 2;
        preloaderElement.getStyle().setLeft(leftOffset, Style.Unit.PX);
        int topOffset = y - preloaderElement.getOffsetHeight() / 2;
        preloaderElement.getStyle().setTop(topOffset, Style.Unit.PX);
    }

    public void removePreloader() {
        if (mainPreloader != null && mainPreloader.isAttached()) {
            mainPreloader.removeFromParent();
        }
    }
}
