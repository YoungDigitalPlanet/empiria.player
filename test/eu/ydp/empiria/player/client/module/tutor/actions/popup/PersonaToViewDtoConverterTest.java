package eu.ydp.empiria.player.client.module.tutor.actions.popup;


import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.util.geom.Size;

public class PersonaToViewDtoConverterTest {

	private PersonaToViewDtoConverter converter;
	
	@Before
	public void setUp() {
		converter = new PersonaToViewDtoConverter();
	}
	
	@Test
	public void testConvert() throws Exception {
		//given
		int firstIndex = 3;
		String firstAvatarFileName = "fileURL";
		int secondIndex = 3;
		String secondAvatarFileName = "fileURL";
		
		TutorPersonaProperties firstPersonaProperties = createPersonaProperties(firstIndex, firstAvatarFileName);
		TutorPersonaProperties secondPersonaProperties = createPersonaProperties(secondIndex, secondAvatarFileName);
		List<TutorPersonaProperties> personasProperties = Lists.newArrayList(firstPersonaProperties, secondPersonaProperties);
		
		//when
		List<PersonaViewDto> createPersonasDtos = converter.convert(personasProperties);
		
		
		//then
		Assert.assertEquals(createPersonasDtos.get(0).getPersonaIndex(), firstIndex);
		Assert.assertEquals(createPersonasDtos.get(0).getAvatarUrl(), firstAvatarFileName);
		Assert.assertEquals(createPersonasDtos.get(1).getPersonaIndex(), secondIndex);
		Assert.assertEquals(createPersonasDtos.get(1).getAvatarUrl(), secondAvatarFileName);
		
		Assert.assertEquals(2, createPersonasDtos.size());
	}
	
	private TutorPersonaProperties createPersonaProperties(int index, String avatarFileName) {
		return new TutorPersonaProperties(index, new Size(), 60, "name", false, avatarFileName);
	}
}
