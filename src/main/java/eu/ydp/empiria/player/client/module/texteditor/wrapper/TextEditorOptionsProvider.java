package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TextEditorOptionsProvider implements Provider<TextEditorOptions> {

    @Inject
    private UserAgentUtil userAgentUtil;
    @Inject
    private TextEditorDesktopOptions desktopOptions;
    @Inject
    private TextEditorMobileOptions mobileOptions;

    @Override
    public TextEditorOptions get() {
        if (userAgentUtil.isMobileUserAgent()) {
            return mobileOptions;

        }
        return desktopOptions;
    }
}
