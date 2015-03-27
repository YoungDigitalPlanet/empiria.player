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