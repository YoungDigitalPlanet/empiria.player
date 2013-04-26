package eu.ydp.empiria.player.client.controller.feedback.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;

@SuppressWarnings("PMD")
public class FeedbackSoundPlayerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private FeedbackSoundPlayer instance;
    private EventsBus eventsBus;
    private MediaWrapper<?> mediaWrapper;
    private MediaEvent mediaEvent;

    @Before
    public void before() {
        setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class });
        instance = spy(injector.getInstance(FeedbackSoundPlayer.class));
        doReturn(mock(MediaObjectToDocumentInsertManager.class)).when(instance).getMediaObjectContainer();
        eventsBus = injector.getInstance(EventsBus.class);
    }

    @Test
    public void stopAndPlaySoundTest(){
        SingleFeedbackSoundPlayer player = mock(SingleFeedbackSoundPlayer.class);
        String soundSrc = "test";
        doReturn(player).when(instance).addSingleFeedbackSoundPlayerIfNotPresent(Mockito.eq(soundSrc));
        instance.stopAndPlaySound(soundSrc);
        verify(player).play();
    }

    @Test
    public void createAndStoreSingleFeedbackSoundPlayerTest(){
        String soundSrc = "test";
        instance.feedbackPlayers = spy(instance.feedbackPlayers);
        instance.wrappers.put(soundSrc, mock(MediaWrapper.class));
        assertNotNull(instance.createAndStoreSingleFeedbackSoundPlayer(soundSrc));
        verify(instance.feedbackPlayers).put(Mockito.eq(soundSrc), Mockito.any(SingleFeedbackSoundPlayer.class));

    }

    @Test
    public void testPlayListOfString() {
        ArgumentCaptor<Map> argumentCaptor = ArgumentCaptor.forClass(Map.class);
        doNothing().when(instance).play(Mockito.any(String.class), argumentCaptor.capture());
        doReturn("test").when(instance).getMimeType(Mockito.anyString());

        List<String> sources = Arrays.asList(new String[] { "ogg", "mp3" });
        instance.play(sources);

        Map argumentCaptorValue = argumentCaptor.getValue();
        verify(instance).play(Mockito.anyString(), Mockito.<Map<String, String>> eq(argumentCaptorValue));
        assertTrue(sources.containsAll(argumentCaptorValue.keySet()));

        Collection<String> values = Arrays.asList(new String[] { "test", "test" });
        assertTrue(values.containsAll(argumentCaptorValue.values()));
    }

    @Test
    public void testPlayMapOfStringString() {
        doNothing().when(instance).play(Mockito.any(String.class), Mockito.anyMap());
        Map<String, String> params = new TreeMap<String, String>();
        params.put("1", "1");
        params.put("2", "2");

        instance.play(params);
        verify(instance).play(Mockito.eq("1,2"), Mockito.eq(params));
    }

    @Test
    public void testPlayStringMapOfStringString() {
        doNothing().when(instance).stopAndPlaySound(Mockito.anyString());
        doNothing().when(instance).createMediaWrapperAndStopAndPlaySound(Mockito.anyString(), Mockito.anyMap());
        instance.wrappers = new HashMap<String, MediaWrapper<?>>();

        Map<String, String> params = new HashMap<String, String>();
        instance.play("test", params);

        verify(instance).createMediaWrapperAndStopAndPlaySound(Mockito.eq("test"), Mockito.eq(params));

        instance.wrappers.put("test", mock(MediaWrapper.class));
        instance.play("test", null);
        verify(instance).stopAndPlaySound(Mockito.anyString());

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
    public void addSingleFeedbackSoundPlayerIfNotPresentTest(){
        //prepare
    	String soundSrc = "test";
        SingleFeedbackSoundPlayer player = mock(SingleFeedbackSoundPlayer.class);
        doReturn(player).when(instance).createAndStoreSingleFeedbackSoundPlayer(Mockito.eq(soundSrc));

        //test
        SingleFeedbackSoundPlayer returnedPlayer = instance.addSingleFeedbackSoundPlayerIfNotPresent(soundSrc);

        //verify
        assertEquals(player, returnedPlayer);
        verify(instance).createAndStoreSingleFeedbackSoundPlayer(Mockito.eq(soundSrc));

        //prepare
        instance.feedbackPlayers.put(soundSrc, player);
        //test
        returnedPlayer = instance.addSingleFeedbackSoundPlayerIfNotPresent(soundSrc);

        //verify
        assertEquals(player, returnedPlayer);
        verify(instance).createAndStoreSingleFeedbackSoundPlayer(Mockito.eq(soundSrc));


    }

}
