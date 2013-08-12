package eu.ydp.empiria.player.client.controller.multiview.swipe;

import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SwipeTypeProvider implements Provider<SwipeType> {
	@Inject private StyleSocket styleSocket;
	@Inject XMLParser xmlParser;
	private SwipeType swipeType = null;

	@Override
	public SwipeType get() {
		if (swipeType == null) {
			swipeType = getSwipeType();
		}
		return swipeType;
	}

	private SwipeType getSwipeType() {
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
