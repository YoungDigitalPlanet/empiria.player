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

package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

public class ShowImageCommand implements TutorCommand {

    private final TutorView view;
    private final ShowImageDTO showImageDTO;
    private final TutorEndHandler handler;
    private boolean finished = false;

    @Inject
    public ShowImageCommand(@Assisted TutorView view, @Assisted ShowImageDTO showImageDTO, @Assisted TutorEndHandler handler) {
        this.view = view;
        this.showImageDTO = showImageDTO;
        this.handler = handler;
    }

    @Override
    public void execute() {
        view.setBackgroundImage(showImageDTO.path, showImageDTO.size);
        finished = true;
        handler.onEnd();
    }

    @Override
    public void terminate() {
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
