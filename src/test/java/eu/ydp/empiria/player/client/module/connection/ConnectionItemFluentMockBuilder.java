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

package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ConnectionItemFluentMockBuilder {

    private int width;
    private int height;
    private int offsetLeft;
    private int offsetTop;

    private ConnectionItemFluentMockBuilder() {

    }

    public static ConnectionItemFluentMockBuilder newConnectionItem() {
        return new ConnectionItemFluentMockBuilder();
    }

    public ConnectionItemFluentMockBuilder withOffsets(int left, int top) {
        this.offsetLeft = left;
        this.offsetTop = top;
        return this;
    }

    public ConnectionItemFluentMockBuilder withDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public ConnectionItem build() {
        ConnectionItem item = mock(ConnectionItem.class);
        when(item.getOffsetLeft()).thenReturn(offsetLeft);
        when(item.getOffsetTop()).thenReturn(offsetTop);

        when(item.getWidth()).thenReturn(width);
        when(item.getHeight()).thenReturn(height);

        return item;
    }
}
