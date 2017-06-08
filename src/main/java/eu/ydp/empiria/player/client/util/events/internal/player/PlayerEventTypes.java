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

package eu.ydp.empiria.player.client.util.events.internal.player;

public enum PlayerEventTypes {
    CREATE_MEDIA_WRAPPER,
    TOUCH_EVENT_RESERVATION,
    ASSESSMENT_LOADED,
    ASSESSMENT_STARTING,
    ASSESSMENT_STARTED,
    BEFORE_FLOW,
    LOAD_PAGE_VIEW,
    PAGE_UNLOADED,
    PAGE_LOADED,
    PAGE_CHANGE,
    PAGE_REMOVED,
    PAGE_CHANGING,
    PAGE_VIEW_LOADED,
    PAGE_INITIALIZING,
    PAGE_INITIALIZED,
    PAGE_SWIPE_STARTED,
    PAGE_CONTENT_GROWN,
    PAGE_CONTENT_DECREASED,
    PAGE_CHANGE_STARTED,
    PAGE_CREATED_AND_STARTED,
    SLIDESHOW_STARTED,
    TEST_PAGE_LOADED,
    WINDOW_RESIZED,
    SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED
}
