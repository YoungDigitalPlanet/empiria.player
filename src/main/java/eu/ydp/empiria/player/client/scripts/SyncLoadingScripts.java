package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

public enum SyncLoadingScripts implements ScriptUrl {

    JQUERY("jquery/jquery-1.11.3.min.js"),
    CSS_PARSER("jscss/cssparser.js"),
    JQUERY_UI("jquery/jquery-ui.min.js"),
    JQUERY_UI_TOUCH("jquery/jquery.ui.touch-punch.min.js"),
    MATH_JAX("mathjax/MathJax.js?config=yJax&locale=en");

    private final String url;

    SyncLoadingScripts(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

}
