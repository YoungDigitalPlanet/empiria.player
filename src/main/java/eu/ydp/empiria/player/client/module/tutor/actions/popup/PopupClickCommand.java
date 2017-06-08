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

import com.google.gwt.dom.client.NativeEvent;
import eu.ydp.gwtutil.client.event.factory.Command;

public class PopupClickCommand implements Command {

    private final PersonaViewDto personaViewDto;
    private final TutorPopupPresenterImpl tutorPopupPresenterImpl;

    public PopupClickCommand(PersonaViewDto personaViewDto, TutorPopupPresenterImpl tutorPopupPresenterImpl) {
        this.personaViewDto = personaViewDto;
        this.tutorPopupPresenterImpl = tutorPopupPresenterImpl;
    }

    @Override
    public void execute(NativeEvent event) {
        tutorPopupPresenterImpl.clicked(personaViewDto);
    }
}
