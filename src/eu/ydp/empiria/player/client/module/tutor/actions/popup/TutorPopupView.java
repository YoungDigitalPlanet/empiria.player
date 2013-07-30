package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import eu.ydp.gwtutil.client.event.factory.Command;

public interface TutorPopupView {

	void setSelected(int personaIndex);
	void show();
	void hide();
	void addPersona(PersonaViewDto personaViewDto);
	void addClickHandlerToPersona(Command command, int presonaIndex);
}
