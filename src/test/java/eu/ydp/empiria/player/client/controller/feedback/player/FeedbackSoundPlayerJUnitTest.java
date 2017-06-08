/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FeedbackSoundPlayerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private FeedbackSoundPlayer instance;

    @Before
    public void before() {
        setUp(new Class<?>[]{}, new Class<?>[]{}, new Class<?>[]{});
        instance = spy(injector.getInstance(FeedbackSoundPlayer.class));
    }

    @Test
    public void stopAndPlaySoundTest() {
        SingleFeedbackSoundPlayer player = mock(SingleFeedbackSoundPlayer.class);
        String soundSrc = "test";
        doReturn(player).when(instance).addSingleFeedbackSoundPlayerIfNotPresent(Matchers.eq(soundSrc));
        instance.stopAndPlaySound(soundSrc);
        verify(player).play();
    }

    @Test
    public void createAndStoreSingleFeedbackSoundPlayerTest() {
        String soundSrc = "test";
        instance.feedbackPlayers = spy(instance.feedbackPlayers);
        instance.wrappers.put(soundSrc, mock(MediaWrapper.class));
        assertNotNull(instance.createAndStoreSingleFeedbackSoundPlayer(soundSrc));
        verify(instance.feedbackPlayers).put(Matchers.eq(soundSrc), Matchers.any(SingleFeedbackSoundPlayer.class));

    }

    @Test
    public void testPlayListOfString() {
        ArgumentCaptor<Map> argumentCaptor = ArgumentCaptor.forClass(Map.class);
        doNothing().when(instance).play(Matchers.any(String.class), argumentCaptor.capture());
        doReturn("test").when(instance).getMimeType(Matchers.anyString());

        List<String> sources = Arrays.asList(new String[]{"ogg", "mp3"});
        instance.play(sources);

        Map argumentCaptorValue = argumentCaptor.getValue();
        verify(instance).play(Matchers.anyString(), Matchers.<Map<String, String>>eq(argumentCaptorValue));
        assertTrue(sources.containsAll(argumentCaptorValue.keySet()));

        Collection<String> values = Arrays.asList(new String[]{"test", "test"});
        assertTrue(values.containsAll(argumentCaptorValue.values()));
    }

    @Test
    public void testPlayMapOfStringString() {
        doNothing().when(instance).play(Matchers.any(String.class), Matchers.anyMap());
        Map<String, String> params = new TreeMap<String, String>();
        params.put("1", "1");
        params.put("2", "2");

        instance.play(params);
        verify(instance).play(Matchers.eq("1,2"), Matchers.eq(params));
    }

    @Test
    public void testPlayStringMapOfStringString() {
        doNothing().when(instance).stopAndPlaySound(Matchers.anyString());
        doNothing().when(instance).createMediaWrapperAndStopAndPlaySound(Matchers.anyString(), Matchers.anyMap());
        instance.wrappers = new HashMap<String, MediaWrapper<Widget>>();

        Map<String, String> params = new HashMap<String, String>();
        instance.play("test", params);

        verify(instance).createMediaWrapperAndStopAndPlaySound(Matchers.eq("test"), Matchers.eq(params));

        instance.wrappers.put("test", mock(MediaWrapper.class));
        instance.play("test", null);
        verify(instance).stopAndPlaySound(Matchers.anyString());

    }

    @Test
    public void testGetWrappersSourcesKey() {
        List<String> sources = Arrays.asList("1", "2", "3");
        assertEquals("1,2,3", instance.getWrappersSourcesKey(sources));
    }

    @Test
    public void testGetMimeType() {
        String mp4 = "audio/mp4";
        String ogg = "audio/ogg";

        Map<String, String> map = new HashMap<String, String>();
        map.put("dummyurl.mp3", mp4);
        map.put("dummyurl.ogg", ogg);
        map.put("dummyurl.ogv", ogg);
        map.put("dummyurl.rar", "");

        // test
        for (Map.Entry<String, String> entry : map.entrySet()) {
            assertEquals(entry.getValue(), instance.getMimeType(entry.getKey()));
        }

    }

    @Test
    public void addSingleFeedbackSoundPlayerIfNotPresentTest() {
        // prepare
        String soundSrc = "test";
        SingleFeedbackSoundPlayer player = mock(SingleFeedbackSoundPlayer.class);
        doReturn(player).when(instance).createAndStoreSingleFeedbackSoundPlayer(Matchers.eq(soundSrc));

        // test
        SingleFeedbackSoundPlayer returnedPlayer = instance.addSingleFeedbackSoundPlayerIfNotPresent(soundSrc);

        // verify
        assertEquals(player, returnedPlayer);
        verify(instance).createAndStoreSingleFeedbackSoundPlayer(Matchers.eq(soundSrc));

        // prepare
        instance.feedbackPlayers.put(soundSrc, player);
        // test
        returnedPlayer = instance.addSingleFeedbackSoundPlayerIfNotPresent(soundSrc);

        // verify
        assertEquals(player, returnedPlayer);
        verify(instance).createAndStoreSingleFeedbackSoundPlayer(Matchers.eq(soundSrc));

    }

}
