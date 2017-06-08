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

package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.HashMap;
import java.util.Map;

public class TutorPopupProvider {

    private final Provider<TutorPopupPresenter> provider;
    private final Map<String, TutorPopupPresenter> idToPresenter = new HashMap<String, TutorPopupPresenter>();

    @Inject
    public TutorPopupProvider(Provider<TutorPopupPresenter> provider) {
        this.provider = provider;
    }

    public TutorPopupPresenter get(String tutorId) {
        TutorPopupPresenter tutorPopupPresenter = idToPresenter.get(tutorId);

        if (tutorPopupPresenter == null) {
            tutorPopupPresenter = createNewTutorPopupPresenter(tutorId);
        }

        return tutorPopupPresenter;
    }

    private TutorPopupPresenter createNewTutorPopupPresenter(String tutorId) {
        TutorPopupPresenter tutorPopupPresenter;
        tutorPopupPresenter = provider.get();
        idToPresenter.put(tutorId, tutorPopupPresenter);
        tutorPopupPresenter.init(tutorId);
        return tutorPopupPresenter;
    }
}
