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

import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EmpiriaStateVerifier {

    private final LessonIdentifierProvider lessonIdentifierProvider;

    @Inject
    public EmpiriaStateVerifier(LessonIdentifierProvider lessonIdentifierProvider) {
        this.lessonIdentifierProvider = lessonIdentifierProvider;
    }

    public EmpiriaState verifyState(EmpiriaState empiriaState) {
        String savedIdentifier = empiriaState.getLessonIdentifier();

        if (Strings.isNullOrEmpty(savedIdentifier)) {
            return empiriaState;
        }

        String lessonIdentifier = lessonIdentifierProvider.getLessonIdentifier();
        boolean identifiersMatches = savedIdentifier.equals(lessonIdentifier);
        if (identifiersMatches) {
            return empiriaState;
        }

        return new EmpiriaState(EmpiriaStateType.UNKNOWN, "", lessonIdentifier);
    }
}
