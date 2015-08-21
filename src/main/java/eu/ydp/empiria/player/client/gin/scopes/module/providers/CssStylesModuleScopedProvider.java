package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.style.IOSModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyleImpl;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import java.util.Map;

@Singleton
public class CssStylesModuleScopedProvider implements Provider<ModuleStyle> {

    @Inject
    StyleSocket styleSocket;
    @Inject
    @ModuleScoped
    Provider<Element> xmlProvider;
    @Inject
    UserAgentUtil agentUtil;

    @Override
    public ModuleStyle get() {
        Map<String, String> styles = styleSocket.getStyles(xmlProvider.get());
        if (agentUtil.isMobileUserAgent(MobileUserAgent.SAFARI)) {
            return new IOSModuleStyle(styles);
        } else {
            return new ModuleStyleImpl(styles);
        }
    }

}
