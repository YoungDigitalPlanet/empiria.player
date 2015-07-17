package eu.ydp.empiria.player.client.scripts;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import eu.ydp.gwtutil.client.util.paths.UrlConverter;

public class ScriptsLoader {

    private static final String MATH_JAX_URL = "mathjax/MathJax.js?config=yJax&locale=en";

    @Inject
    private SynchronousScriptsLoader synchronousScriptsLoader;
    @Inject
    private ScriptInjectorWrapper scriptInjectorWrapper;
    @Inject
    private UrlConverter urlConverter;

    public void inject(SynchronousScriptsCallback callback) {
        injectMathJax();
        synchronousScriptsLoader.injectScripts(SyncLoadingScripts.values(), callback);
    }

    private void injectMathJax() {
        String correctMathJaxUrl = urlConverter.getModuleRelativeUrl(MATH_JAX_URL);
        scriptInjectorWrapper.fromUrl(correctMathJaxUrl);
    }
}
