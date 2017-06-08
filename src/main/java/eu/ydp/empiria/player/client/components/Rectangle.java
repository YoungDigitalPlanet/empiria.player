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

package eu.ydp.empiria.player.client.components;

public class Rectangle {

    public Rectangle(int l, int t, int w, int h) {
        left = l;
        top = t;
        width = w;
        height = h;
    }

    private int left;
    private int top;
    private int width;
    private int height;

    public int getTop() {
        return top;
    }

    public int getLeft() {
        return left;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRight() {
        return left + width;
    }

    public int getBottom() {
        return top + height;
    }

    public int getMiddleHorizontal() {
        return left + width / 2;
    }

    public int getMiddleVertical() {
        return top + height / 2;
    }

    public void move(int newLeft, int newTop) {
        left = newLeft;
        top = newTop;
    }

    public void resize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
    }

    public boolean contains(int x, int y) {
        return (x >= left && y >= top && x <= left + width && y <= top + height);
    }

}
