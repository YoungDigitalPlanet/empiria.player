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

package eu.ydp.empiria.player.client.controller.events.delivery;

public enum DeliveryEventType {
    RESET, CONTINUE, CHECK, LOCK, UNLOCK, SHOW_ANSWERS, ASSESSMENT_LOADING, ASSESSMENT_LOADED, ASSESSMENT_STARTING, ASSESSMENT_STARTED, PAGE_CHANGING, PAGE_UNLOADING, PAGE_UNLOADED, PAGE_LOADING, TEST_PAGE_LOADED, TOC_PAGE_LOADED, SUMMARY_PAGE_LOADED, STATE_CHANGED, FEEDBACK_SOUND, FEEDBACK_MUTE, MEDIA_SOUND_PLAY;
}
