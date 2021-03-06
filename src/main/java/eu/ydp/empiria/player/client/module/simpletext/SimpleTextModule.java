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

package eu.ydp.empiria.player.client.module.simpletext;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.TextStyleNameConstants;
import eu.ydp.gwtutil.client.geom.Rectangle;

public class SimpleTextModule extends SimpleModuleBase implements IBookmarkable {

    protected Widget contents;
    private final BookmarkingHelper bookmarkingHelper;

    @Inject
    public SimpleTextModule(TextStyleNameConstants styleNames) {
        contents = new ElementWrapperWidget(Document.get().createPElement());
        contents.setStyleName(styleNames.QP_SIMPLETEXT());
        bookmarkingHelper = new BookmarkingHelper(contents);
    }

    @Override
    public void initModule(Element element) {
        getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element, contents.getElement());
    }

    @Override
    public Widget getView() {
        return contents;
    }

    @Override
    public void setBookmarkingStyleName(String styleName) {
        bookmarkingHelper.setBookmarkingStyleName(styleName);
    }

    @Override
    public void removeBookmarkingStyleName() {
        bookmarkingHelper.removeBookmarkingStyleName();
    }

    @Override
    public void setClickCommand(final Command command) {
        bookmarkingHelper.setClickCommand(command);
    }

    @Override
    public String getBookmarkHtmlBody() {
        return contents.getElement().getInnerHTML().trim();
    }

    @Override
    public Rectangle getViewArea() {
        return bookmarkingHelper.getViewArea();
    }

    @Override
    public String getDefaultBookmarkTitle() {
        return BookmarkingHelper.getDefaultBookmarkTitle(getView().getElement().getInnerText());
    }
}
