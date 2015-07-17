package eu.ydp.empiria.player.client.scripts;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.scripts.ScriptUrl;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SynchronousScriptsLoaderTest {

    public static final String FIRST_SCRIPT_URL = "firstScript";
    public static final String SECOND_SCRIPT_URL = "secondScript";
    public static final String RESPONSE_TEXT = "response text";

    @InjectMocks
    private SynchronousScriptsLoader testObj;
    @Mock
    private ScriptInjectorWrapper scriptInjectorWrapper;
    @Mock
    private UrlConverter urlConverter;
    @Mock
    private RequestWrapper requestWrapper;
    @Mock
    private ScriptUrl firstScript;
    @Mock
    private ScriptUrl secondScript;
    @Mock
    private SynchronousScriptsCallback synchronousScriptsCallback;
    @Captor
    private ArgumentCaptor<RequestCallback> requestCallbackCaptor;

    @Before
    public void init() {
        when(urlConverter.getModuleRelativeUrl(firstScript)).thenReturn(FIRST_SCRIPT_URL);
        when(urlConverter.getModuleRelativeUrl(secondScript)).thenReturn(SECOND_SCRIPT_URL);
    }

    @Test
    public void shouldFireOnLoad_afterLastScriptLoaded() {
        // given
        ScriptUrl[] scripts = new ScriptUrl[]{firstScript, secondScript};
        Request request = mock(Request.class);
        Response response = mock(Response.class);
        when(response.getText()).thenReturn(RESPONSE_TEXT);

        // when
        testObj.injectScripts(scripts, synchronousScriptsCallback);
        verify(requestWrapper).get(eq(FIRST_SCRIPT_URL), requestCallbackCaptor.capture());
        RequestCallback requestCallback = requestCallbackCaptor.getValue();
        requestCallback.onResponseReceived(request, response);

        // then
        verify(scriptInjectorWrapper).fromString(RESPONSE_TEXT);

        verify(requestWrapper).get(SECOND_SCRIPT_URL, requestCallback);
        requestCallback.onResponseReceived(request, response);
        verify(synchronousScriptsCallback).onLoad();
    }
}