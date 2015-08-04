package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class ExplorableImgWindowProvider implements Provider<ExplorableImgWindow>{

    @Inject
    private UserAgentUtil userAgent;
    @Inject
    private Provider<ExplorableImgWindowImg> explorableImgWindowImgProvider;
    @Inject
    private Provider<ExplorableImgWindowCanvas> explorableImgWindowCanvasProvider;

    @Override
    public ExplorableImgWindow get() {
        boolean isIE8 = userAgent.isUserAgent(UserAgentChecker.UserAgent.IE8);
        if(isIE8){
            return explorableImgWindowImgProvider.get();
        }
        return explorableImgWindowCanvasProvider.get();
    }
}
