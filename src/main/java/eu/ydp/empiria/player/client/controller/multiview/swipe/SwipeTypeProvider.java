package eu.ydp.empiria.player.client.controller.multiview.swipe;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;

import java.util.Map;

@Singleton
public class SwipeTypeProvider implements Provider<SwipeType> {
    @Inject
    private StyleSocket styleSocket;
    @Inject
    XMLParser xmlParser;

    @Override
    public SwipeType get() {
        String xml = "<root><swipeoptions class=\"qp-swipe-options\"/></root>";
        Element firstChild = (Element) xmlParser.parse(xml).getDocumentElement().getFirstChild();
        Map<String, String> styles = styleSocket.getStyles(firstChild);
        if (styles.get(EmpiriaStyleNameConstants.EMPIRIA_SWIPE_DISABLE_ANIMATION) != null) {
            return SwipeType.DISABLED;
        } else {
            return SwipeType.DEFAULT;
        }
    }
}
