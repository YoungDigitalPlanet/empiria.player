package eu.ydp.empiria.player.client.module.tutor.actions.popup;

public interface TutorPopupView {

	void init(Iterable<PersonaViewDto> personas);
	void setSelected(int personaIndex);
	void show();
	void hide();
}
