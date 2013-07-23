package eu.ydp.empiria.player.client.module.tutor.actions.popup;

public class PersonaViewDto {

	private final int personaIndex;
	private final String avatarUrl;
	
	public PersonaViewDto(int personaIndex, String avatarUrl) {
		this.personaIndex = personaIndex;
		this.avatarUrl = avatarUrl;
	}

	public int getPersonaIndex() {
		return personaIndex;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}
	
}
