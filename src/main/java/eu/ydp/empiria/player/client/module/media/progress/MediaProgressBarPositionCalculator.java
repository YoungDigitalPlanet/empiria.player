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

package eu.ydp.empiria.player.client.module.media.progress;

import eu.ydp.empiria.player.client.style.ComputedStyle;

class MediaProgressBarPositionCalculator {
    private static final String POSITION_RIGHT = "right";
    private static final String POSITION_LEFT = "left";
    private final MediaProgressBar mediaProgressBar;
    private final ComputedStyle computedStyle;

    public MediaProgressBarPositionCalculator(MediaProgressBar mediaProgressBar, ComputedStyle computedStyle) {
        this.mediaProgressBar = mediaProgressBar;
        this.computedStyle = computedStyle;
    }

    boolean isRTL() {
        String direction = computedStyle.getDirectionFromBody();
        return "rtl".equalsIgnoreCase(direction);
    }

    public double calculateCurrentPosistion(int positionX) {
        int scrollWidth = mediaProgressBar.getScrollWidth();
        double mediaDuration = mediaProgressBar.getMediaWrapper().getDuration();
        double steep = mediaDuration / scrollWidth;
        positionX = getMirrorOfPositionIfTextIsRTL(positionX);
        double time = steep * positionX;
        double position = Math.min(time, mediaDuration);
        return Math.max(0, position);
    }

    private int getMirrorOfPositionIfTextIsRTL(int positionX) {
        if (isRTL()) {
            return getMirrorOfPosition(positionX);
        }
        return positionX;
    }

    private int getMirrorOfPosition(int positionX) {
        int scrollWidth = mediaProgressBar.getScrollWidth();
        return scrollWidth - positionX;
    }

    public int calculateCurrentPosistionForScroll(int positionX) {
        int scrollWidth = mediaProgressBar.getScrollWidth();
        int position = Math.max(0, Math.min(positionX, scrollWidth));
        return getMirrorOfPositionIfTextIsRTL(position);
    }

    private int getHalfWidthOfProgressBarButton() {
        return mediaProgressBar.getButtonWidth() / 2;
    }

    public int getWidthForAfterProgressElement(int positionX) {
        return getMirrorOfPosition(positionX) + getHalfWidthOfProgressBarButton();
    }

    public String getPositionPropertyForAfterProgressElement() {
        if (isRTL()) {
            return POSITION_LEFT;
        }
        return POSITION_RIGHT;

    }

    public String getPositionPropertyForBeforeProgressElement() {
        if (isRTL()) {
            return POSITION_RIGHT;
        }
        return POSITION_LEFT;

    }

    public double getPercentWidth(int width) {
        double overallWidth = (double) (mediaProgressBar.getScrollWidth() + mediaProgressBar.getButtonWidth());
        return 100 * width / overallWidth;
    }

}
