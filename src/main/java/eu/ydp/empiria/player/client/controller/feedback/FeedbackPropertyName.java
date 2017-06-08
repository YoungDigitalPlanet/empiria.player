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

package eu.ydp.empiria.player.client.controller.feedback;

public enum FeedbackPropertyName {
    RESULT("result"), OK("ok"), WRONG("wrong"), ALL_OK("allOk"), TEXT("text"), ANSWER_TEXT("answerText"), DEMAND("demand"), SELECTED("selected"), UNSELECT(
            "unselect"), DEFAULT("default"), SEND_ONCE("sendOnce"), SOURCE_ID("sourceId"), MISTAKES("mistakes"), HINTS("hints"), TODO("todo"), DONE("done"), ERRORS(
            "errors"), LAST_CHANGE("lastChange");


    private String name;

    private FeedbackPropertyName(String name) {
        this.name = name;
    }

    /**
     * Searches for FeedbackPropertyName by given string name
     *
     * @param value string name for which FeedbackPropertyName will be searched
     * @return retruns instance of FeedbackPropertyName which string name is equal to geven in 'value' paremeter. If it is not found then
     * FeedbackPropertyName.DEFAULT is returned.
     */
    public static FeedbackPropertyName getPropertyName(String value) {
        FeedbackPropertyName searchedName = DEFAULT;

        for (FeedbackPropertyName feedbackPropertyName : values()) {
            if (feedbackPropertyName.name.equals(value)) {
                searchedName = feedbackPropertyName;
                break;
            }
        }

        return searchedName;
    }

    public static boolean exists(FeedbackPropertyName name) {
        return !DEFAULT.equals(name);
    }

    public static boolean exists(String value) {
        return !DEFAULT.equals(getPropertyName(value));
    }

    public String getName() {
        return name;
    }
}
