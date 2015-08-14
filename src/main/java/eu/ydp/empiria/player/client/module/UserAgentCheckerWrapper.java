package eu.ydp.empiria.player.client.module;

import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

@Singleton
public class UserAgentCheckerWrapper {
    public boolean isStackAndroidBrowser() {
        return UserAgentChecker.isStackAndroidBrowser();
    }
}
