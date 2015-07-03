package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import com.google.gwt.core.client.JsArray;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorActionJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorCommandJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorJs;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static eu.ydp.empiria.player.client.module.tutor.ActionType.DEFAULT;
import static eu.ydp.empiria.player.client.module.tutor.ActionType.ON_PAGE_ALL_OK;
import static eu.ydp.empiria.player.client.module.tutor.CommandType.ANIMATION;
import static eu.ydp.empiria.player.client.module.tutor.CommandType.SOUND;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TutorConfigTest {

    private final TutorConfig tutorConfig = new TutorConfig(createTutorConfigJs());

    @Test
    public void supportsAction_supported() {
        // given
        ActionType type = DEFAULT;

        // when
        boolean supportsAction = tutorConfig.supportsAction(type);

        // then
        assertThat(supportsAction).isTrue();
    }

    @Test
    public void supportsAction_notSupported() {
        // given
        ActionType type = ON_PAGE_ALL_OK;

        // when
        boolean supportsAction = tutorConfig.supportsAction(type);

        // then
        assertThat(supportsAction).isFalse();
    }

    @Test
    public void getCommands() {
        // given
        ActionType type = DEFAULT;

        // when
        Iterable<TutorCommandConfig> commands = tutorConfig.getCommandsForAction(type);

        // then
        assertThat(commands).contains(new TutorCommandConfig("_anim.png", ANIMATION), new TutorCommandConfig("_sound.mp3", SOUND));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCommands_notSupportedAction() {
        // given
        ActionType type = ON_PAGE_ALL_OK;

        // when
        tutorConfig.getCommandsForAction(type);
    }

    @Test
    public void getTutorPersonasCount() {
        // when
        int tutorPersonasCount = tutorConfig.getTutorPersonasCount();

        // then
        assertThat(tutorPersonasCount).isEqualTo(2);
    }

    @Test
    public void getTutorPersonaProperties() {
        // when
        TutorPersonaProperties persona = tutorConfig.getTutorPersonaProperties(1);

        // then
        assertThat(persona.getName()).isEqualTo("persona1");
        assertThat(persona.getAnimationFps()).isEqualTo(20);
        assertThat(persona.getAnimationSize()).isEqualTo(new Size(300, 400));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getTutorPersonaProperties_invalidIndex() {
        // when
        tutorConfig.getTutorPersonaProperties(2);
    }

    @Test
    public void testGetPersonas() throws Exception {
        // when
        List<TutorPersonaProperties> personas = tutorConfig.getPersonas();

        // then
        assertEquals(0, personas.get(0).getIndex());
        assertEquals("avatar0", personas.get(0).getAvatarFilename());
        assertEquals(1, personas.get(1).getIndex());
        assertEquals("avatar1", personas.get(1).getAvatarFilename());

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private TutorConfigJs createTutorConfigJs() {
        TutorConfigJs config = mock(TutorConfigJs.class, RETURNS_DEEP_STUBS);
        JsArray actions = mock(JsArray.class, RETURNS_DEEP_STUBS);
        when(config.getActions()).thenReturn(actions);
        when(actions.length()).thenReturn(1);

        TutorActionJs action0 = mock(TutorActionJs.class, RETURNS_DEEP_STUBS);
        when(actions.get(0)).thenReturn(action0);
        when(action0.getType()).thenReturn("DEFAULT");

        JsArray commands = mock(JsArray.class, RETURNS_DEEP_STUBS);
        when(action0.getCommands()).thenReturn(commands);
        when(commands.length()).thenReturn(2);

        TutorCommandJs command0 = mock(TutorCommandJs.class, RETURNS_DEEP_STUBS);
        when(commands.get(0)).thenReturn(command0);
        when(command0.getType()).thenReturn("ANIMATION");
        when(command0.getAsset()).thenReturn("_anim.png");

        TutorCommandJs command1 = mock(TutorCommandJs.class, RETURNS_DEEP_STUBS);
        when(commands.get(1)).thenReturn(command1);
        when(command1.getType()).thenReturn("SOUND");
        when(command1.getAsset()).thenReturn("_sound.mp3");

        JsArray tutors = mock(JsArray.class, RETURNS_DEEP_STUBS);
        when(config.getTutors()).thenReturn(tutors);
        when(tutors.length()).thenReturn(2);

        TutorJs tutor0 = mock(TutorJs.class, RETURNS_DEEP_STUBS);
        when(tutors.get(0)).thenReturn(tutor0);
        when(tutor0.getName()).thenReturn("persona0");
        when(tutor0.getFps()).thenReturn(25);
        when(tutor0.getWidth()).thenReturn(100);
        when(tutor0.getHeight()).thenReturn(200);
        when(tutor0.getAvatar()).thenReturn("avatar0");

        TutorJs tutor1 = mock(TutorJs.class, RETURNS_DEEP_STUBS);
        when(tutors.get(1)).thenReturn(tutor1);
        when(tutor1.getName()).thenReturn("persona1");
        when(tutor1.getFps()).thenReturn(20);
        when(tutor1.getWidth()).thenReturn(300);
        when(tutor1.getHeight()).thenReturn(400);
        when(tutor1.getAvatar()).thenReturn("avatar1");

        return config;
    }
}
