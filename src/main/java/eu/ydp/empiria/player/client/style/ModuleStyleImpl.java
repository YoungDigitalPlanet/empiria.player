package eu.ydp.empiria.player.client.style;

import java.util.HashMap;
import java.util.Map;

public class ModuleStyleImpl extends HashMap<String, String> implements ModuleStyle {
    private static final long serialVersionUID = 1L;

    public ModuleStyleImpl(Map<String, String> styles) {
        putAll(styles);
    }
}
