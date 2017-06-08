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

package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

import java.util.Date;

public class BookmarkProperties {

    /**
     * color index
     */
    int bookmarkIndex;
    String bookmarkTitle;
    long timestamp;

    public BookmarkProperties(int bookmarkIndex, String bookmarkTitle) {
        super();
        this.bookmarkIndex = bookmarkIndex;
        this.bookmarkTitle = bookmarkTitle;
        this.timestamp = new Date().getTime();
    }

    public BookmarkProperties(int bookmarkIndex, String bookmarkTitle, long timestamp) {
        super();
        this.bookmarkIndex = bookmarkIndex;
        this.bookmarkTitle = bookmarkTitle;
        this.timestamp = timestamp;
    }

    public int getBookmarkIndex() {
        return bookmarkIndex;
    }

    public void setBookmarkIndex(int bookmarkIndex) {
        this.bookmarkIndex = bookmarkIndex;
    }

    public String getBookmarkTitle() {
        return bookmarkTitle;
    }

    public void setBookmarkTitle(String bookmarkTitle) {
        this.bookmarkTitle = bookmarkTitle;
    }

    public void touch() {
        timestamp = new Date().getTime();
    }

    public JSONValue toJSON() {
        JSONArray arr = new JSONArray();
        arr.set(0, new JSONNumber(bookmarkIndex));
        arr.set(1, new JSONString(bookmarkTitle));
        arr.set(2, new JSONNumber(timestamp));
        return arr;
    }

    public static BookmarkProperties fromJSON(JSONValue json) {
        int index = (int) json.isArray().get(0).isNumber().doubleValue();
        String title = json.isArray().get(1).isString().stringValue();
        long timeStamp = (long) json.isArray().get(2).isNumber().doubleValue();
        return new BookmarkProperties(index, title, timeStamp);
    }

}
