package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonaToViewDtoConverterTest {

    private PersonaToViewDtoConverter converter;
    private EmpiriaPaths empiriaPaths;

    @Before
    public void setUp() {
        empiriaPaths = mock(EmpiriaPaths.class);
        converter = new PersonaToViewDtoConverter(empiriaPaths);
    }

    @Test
    public void testConvert() throws Exception {
        // given
        int firstIndex = 3;
        String firstAvatarSuffix = "firstAcatarFileName";
        String name1 = "name1";

        int secondIndex = 3;
        String secondAvatarFileName = "secondAvatarFileName";
        String name2 = "name2";

        TutorPersonaProperties firstPersonaProperties = createPersonaProperties(firstIndex, firstAvatarSuffix, name1);
        TutorPersonaProperties secondPersonaProperties = createPersonaProperties(secondIndex, secondAvatarFileName, name2);
        List<TutorPersonaProperties> personasProperties = Lists.newArrayList(firstPersonaProperties, secondPersonaProperties);

        String firstAvatarFileFullPath = "firstAvatarFileFullPath";
        when(empiriaPaths.getCommonsFilePath(name1 + firstAvatarSuffix)).thenReturn(firstAvatarFileFullPath);

        String secondAvatarFileFullPath = "secondAvatarFileFullPath";
        when(empiriaPaths.getCommonsFilePath(name2 + secondAvatarFileName)).thenReturn(secondAvatarFileFullPath);

        // when
        List<PersonaViewDto> createPersonasDtos = converter.convert(personasProperties);

        // then
        assertEquals(createPersonasDtos.get(0).getPersonaIndex(), firstIndex);
        assertEquals(createPersonasDtos.get(0).getAvatarUrl(), firstAvatarFileFullPath);
        assertEquals(createPersonasDtos.get(1).getPersonaIndex(), secondIndex);
        assertEquals(createPersonasDtos.get(1).getAvatarUrl(), secondAvatarFileFullPath);

        assertEquals(2, createPersonasDtos.size());
    }

    private TutorPersonaProperties createPersonaProperties(int index, String avatarFileName, String name) {
        return new TutorPersonaProperties(index, new Size(), 60, name, false, avatarFileName);
    }
}
