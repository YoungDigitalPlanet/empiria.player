package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

/**
 * Created by ldomzalski on 2015-06-24.
 */
public enum SyncLoadingScripts implements ScriptUrl {

    JQUERY("jquery/jquery-1.11.3.min.js"),
    CSS_PARSER("jscss/cssparser.js"),
    JQUERY_UI("jquery/jquery-ui.min.js"),
    JQUERY_UI_TOUCH("jquery/jquery.ui.touch-punch.min.js");

    private final String url;

    SyncLoadingScripts(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }


}
