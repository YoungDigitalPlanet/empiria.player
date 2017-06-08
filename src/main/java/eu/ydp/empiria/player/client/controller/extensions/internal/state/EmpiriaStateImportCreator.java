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

package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.compressor.LzGwtWrapper;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.EmpiriaStateDeserializer;
import eu.ydp.empiria.player.client.controller.extensions.internal.state.json.JsonParserWrapper;

import javax.inject.Singleton;

@Singleton
public class EmpiriaStateImportCreator {

    private static final String EMPTY_STATE = "";

    private final EmpiriaStateDeserializer empiriaStateDeserializer;
    private final LzGwtWrapper lzGwtWrapper;
    private final JsonParserWrapper jsonParser;
    private final EmpiriaStateVerifier empiriaStateVerifier;

    @Inject
    public EmpiriaStateImportCreator(EmpiriaStateDeserializer empiriaStateDeserializer, LzGwtWrapper lzGwtWrapper,
                                     JsonParserWrapper jsonParser, EmpiriaStateVerifier empiriaStateVerifier) {
        this.empiriaStateDeserializer = empiriaStateDeserializer;
        this.lzGwtWrapper = lzGwtWrapper;
        this.jsonParser = jsonParser;
        this.empiriaStateVerifier = empiriaStateVerifier;
    }

    public String createState(String state) {
        JSONValue jsonValue = jsonParser.parse(state);
        EmpiriaState empiriaState = empiriaStateDeserializer.deserialize(jsonValue);
        EmpiriaState verifiedState = empiriaStateVerifier.verifyState(empiriaState);

        if (verifiedState.hasType(EmpiriaStateType.OLD)) {
            return verifiedState.getState();
        } else if (verifiedState.hasType(EmpiriaStateType.UNKNOWN)) {
            return EMPTY_STATE;
        } else {
            String stateString = verifiedState.getState();
            return lzGwtWrapper.decompress(stateString);
        }
    }
}