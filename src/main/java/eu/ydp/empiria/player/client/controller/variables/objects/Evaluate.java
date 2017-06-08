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

package eu.ydp.empiria.player.client.controller.variables.objects;

/**
 * Used with {@link Cardinality#MULTIPLE} to define the method of processing response (user answers / correct answers / default for the module).
 */
public enum Evaluate {

    CORRECT, USER, DEFAULT;

    public static Evaluate fromString(String key) {

        Evaluate evaluate = DEFAULT;

        if (key != null) {
            if (key.equalsIgnoreCase(USER.toString())) {
                evaluate = USER;
            } else if (key.equalsIgnoreCase(CORRECT.toString())) {
                evaluate = CORRECT;
            }
        }

        return evaluate;
    }

}
