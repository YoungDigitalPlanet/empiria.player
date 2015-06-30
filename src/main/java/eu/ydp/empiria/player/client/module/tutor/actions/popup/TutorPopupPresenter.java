package eu.ydp.empiria.player.client.module.tutor.actions.popup;

public interface TutorPopupPresenter {

    void init(String tutorId);

    void show();

    void clicked(PersonaViewDto personaDto);

}
