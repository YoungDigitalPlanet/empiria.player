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

package eu.ydp.empiria.player.client.module.bookmark;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class BookmarkingHelper {

    private Widget view;
    private String bookmarkingStyleName;

    public static int DEFAULT_TITLE_MAX_LENGTH = 30;

    public BookmarkingHelper(Widget view) {
        this.view = view;
    }

    public void setBookmarkingStyleName(String styleName) {
        removeBookmarkingStyleName();
        this.bookmarkingStyleName = styleName;
        if (bookmarkingStyleName != null && !"".equals(bookmarkingStyleName)) {
            view.addStyleName(bookmarkingStyleName);
        }
    }

    public void removeBookmarkingStyleName() {
        if (bookmarkingStyleName != null && !"".equals(bookmarkingStyleName)) {
            view.removeStyleName(bookmarkingStyleName);
        }
    }

    public void setClickCommand(final Command command) {
        view.addDomHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                command.execute();
            }
        }, ClickEvent.getType());
    }

    public Rectangle getViewArea() {
        return new Rectangle(view.getAbsoluteLeft(), view.getAbsoluteTop(), view.getOffsetWidth(), view.getOffsetHeight());
    }

    public static String getDefaultBookmarkTitle(String moduleText) {
        String outputText = null;
        if (moduleText.length() > DEFAULT_TITLE_MAX_LENGTH) {
            int lastSpaceIndex = moduleText.lastIndexOf(' ', DEFAULT_TITLE_MAX_LENGTH - 3);
            if (lastSpaceIndex > 0) {
                outputText = moduleText.substring(0, lastSpaceIndex) + "...";
            } else {
                outputText = moduleText.substring(0, DEFAULT_TITLE_MAX_LENGTH - 3) + "...";
            }
        } else {
            outputText = moduleText;
        }
        return outputText;
    }
}
