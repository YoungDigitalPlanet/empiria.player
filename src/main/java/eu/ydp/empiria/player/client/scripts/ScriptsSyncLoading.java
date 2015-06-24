package eu.ydp.empiria.player.client.scripts;

import eu.ydp.gwtutil.client.scripts.ScriptUrl;

/**
 * Created by ldomzalski on 2015-06-24.
 */
public enum ScriptsSyncLoading implements ScriptUrl {

    JQUERY("jquery/jquery-1.10.2.min.js"),
    CSS_PARSER("jscss/cssparser.js"),
    JQUERY_UI("jquery/jquery-ui-1.10.3.custom.min.js"),
    JQUERY_UI_TOUCH("jquery/jquery.ui.touch-punch.min.js");

    private final String url;

    ScriptsSyncLoading(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }


}
