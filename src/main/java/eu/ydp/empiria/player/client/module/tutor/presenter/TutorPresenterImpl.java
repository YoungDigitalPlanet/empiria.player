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

package eu.ydp.empiria.player.client.module.tutor.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorId;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupPresenter;
import eu.ydp.empiria.player.client.module.tutor.actions.popup.TutorPopupProvider;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TutorPresenterImpl implements TutorPresenter {

    private final TutorView tutorView;
    private final String tutorId;
    private final TutorPopupProvider tutorPopupProvider;

    @Inject
    public TutorPresenterImpl(@ModuleScoped TutorView tutorView, @TutorId String tutorId, TutorPopupProvider tutorPopupProvider) {
        this.tutorView = tutorView;
        this.tutorId = tutorId;
        this.tutorPopupProvider = tutorPopupProvider;
    }

    @Override
    public void init() {
        tutorView.bindUi();
        tutorView.addClickHandler(new Command() {
            @Override
            public void execute(NativeEvent event) {
                clicked();
            }
        });
    }

    @Override
    public void clicked() {
        TutorPopupPresenter tutorPopupPresenter = tutorPopupProvider.get(tutorId);
        tutorPopupPresenter.show();
    }

}
