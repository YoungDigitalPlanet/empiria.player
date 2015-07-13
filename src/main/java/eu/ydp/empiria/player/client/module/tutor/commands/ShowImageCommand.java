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
