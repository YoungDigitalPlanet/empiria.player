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

import com.google.gwt.dom.client.NativeEvent;

import javax.annotation.PostConstruct;

public class MediaProgressBarAndroid extends MediaProgressBarImpl {

    @Override
    @PostConstruct
    public void postConstruct() {
        super.postConstruct();
    }

    @Override
    protected void setPosition(NativeEvent event) {
        if (isAttached()) {
            event.preventDefault();
            int positionX = getPositionX(event);
            moveScroll(positionX > 0 ? positionX : 0, true);
            if (!isPressed()) {// robimy seeka tylko gdy zakonczono dotyk
                seekInMedia(positionX > 0 ? positionX : 0);
            }
        }
    }
}
