package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;

import java.util.List;

public class TutorPopupPresenterImpl implements TutorPopupPresenter {

    private final TutorService tutorService;
    private final TutorPopupView view;
    private String tutorId;
    private final PersonaToViewDtoConverter personaViewDtoConverter;

    @Inject
    public TutorPopupPresenterImpl(TutorService tutorService, TutorPopupView tutorPopupView, PersonaToViewDtoConverter personaViewDtoConverter) {
        this.tutorService = tutorService;
        this.view = tutorPopupView;
        this.personaViewDtoConverter = personaViewDtoConverter;
    }

    @Override
    public void init(String tutorId) {
        this.tutorId = tutorId;
        TutorConfig tutorConfig = tutorService.getTutorConfig(tutorId);
        List<TutorPersonaProperties> personas = tutorConfig.getPersonas();
        List<PersonaViewDto> viewDtos = personaViewDtoConverter.convert(personas);
        initilizeView(viewDtos);
    }

    @Override
    public void show() {
        PersonaService tutorPersonaService = tutorService.getTutorPersonaService(tutorId);
        int currentPersonaIndex = tutorPersonaService.getCurrentPersonaIndex();
        view.setSelected(currentPersonaIndex);
        view.show();
    }

    @Override
    public void clicked(PersonaViewDto personaDto) {
        PersonaService tutorPersonaService = tutorService.getTutorPersonaService(tutorId);
        tutorPersonaService.setCurrentPersonaIndex(personaDto.getPersonaIndex());
    }

    private void initilizeView(List<PersonaViewDto> viewDtos) {
        for (final PersonaViewDto personaViewDto : viewDtos) {
            view.addPersona(personaViewDto);
            PopupClickCommand popupClickCommand = new PopupClickCommand(personaViewDto, this);
            view.addClickHandlerToPersona(popupClickCommand, personaViewDto.getPersonaIndex());
        }
    }
}
