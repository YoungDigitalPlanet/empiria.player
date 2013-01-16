package eu.ydp.empiria.player.client.controller.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.collections.QueueSet;

/**
 * Requires that jscssp.js script is added to module xml file
 *
 * @see http://glazman.org/JSCSSP/
 * @author Micha� Samuj�o msamujlo@ydp.com.pl
 */
public class StyleDataSourceManager implements StyleSocket {

	// style declarations for assessment
	private final QueueSet<StyleDocument> assessmentStyle;

	// style declarations for all items
	// TODO consider using WeakHashMap to avoid problems with vector size at
	// lines 50, 96
	private final Vector<QueueSet<StyleDocument>> itemStyle;

	/**
	 * Style declarations that should be searched for styles. When player
	 * changes displayed page activeItemStyles should be rebuild.
	 */
	private final QueueSet<StyleDocument> currentStyles;

	public StyleDataSourceManager() {
		assessmentStyle = new QueueSet<StyleDocument>();

		itemStyle = new Vector<QueueSet<StyleDocument>>();
		currentStyles = new QueueSet<StyleDocument>();
	}

	public void addAssessmentStyle(StyleDocument styleDocument) {
		assessmentStyle.append(styleDocument);
		currentStyles.append(styleDocument);
	}

	public void addItemStyle(int index, StyleDocument styleDocument) {
		if (index >= itemStyle.size()) {
			itemStyle.setSize(index + 1);
		}
		QueueSet<StyleDocument> styles = itemStyle.get(index);
		if (styles == null) {
			styles = new QueueSet<StyleDocument>();
			itemStyle.set(index, styles);
		}
		styles.append(styleDocument);
	}

	@Override
	public Map<String, String> getStyles(Element element) {
		return getStyleProperties(element, true);
	}

	@Override
	public Map<String, String> getOrgStyles(Element element) {
		return getStyleProperties(element, false);
	}

	public Map<String, String> getStyleProperties(Element element, boolean lowerCase) {
		List<String> selectors = getElementSelectors(element);
		Map<String, String> result = new HashMap<String, String>();

		for (StyleDocument sheet : currentStyles) {
			Map<String, String> currResult = sheet.getDeclarationsForSelectors(selectors);
			if (lowerCase) {
				for (Map.Entry<String, String> entry : currResult.entrySet()) {
					result.put(entry.getKey().toLowerCase(), entry.getValue());
				}
			} else {
				result.putAll(currResult);
			}
		}
		return result;
	}

	protected List<String> getElementSelectors(Element element) {
		String name = element.getNodeName().toLowerCase();
		String[] classes = null;
		String id = null; // NOPMD
		if (element.hasAttribute("class") && !"".equals(element.getAttribute("class"))) {
			classes = element.getAttribute("class").split(" ");
		}
		if (element.hasAttribute("id") && !"".equals(element.getAttribute("id"))) {
			id = element.getAttribute("id");
		}

		return buildSelectors(name, classes, id);
	}

	protected List<String> buildSelectors(String name, String[] classes, String id) {// NOPMD
		List<String> selectors = new ArrayList<String>();

		selectors.add(name);
		if (classes != null) {
			for (int i = 0; i < classes.length; i++) {
				selectors.add("." + classes[i]);
			}
			for (int i = 0; i < classes.length; i++) {
				selectors.add(name + "." + classes[i]);
			}
		}
		if (id != null) {
			selectors.add("#" + id);
			selectors.add(name + "#" + id);
		}

		return selectors;
	}

	@Override
	public void setCurrentPages(PageReference pageReference) {
		Vector<QueueSet<StyleDocument>> activeItemStyles = new Vector<QueueSet<StyleDocument>>(pageReference.pageIndices.length);
		for (int pageIndex : pageReference.pageIndices) {
			if (pageIndex < itemStyle.size()) {
				activeItemStyles.add(itemStyle.get(pageIndex));
			}
		}
		currentStyles.clear();

		for (StyleDocument sheet : assessmentStyle) {
			currentStyles.append(sheet);
		}
		for (Set<StyleDocument> styles : activeItemStyles) {
			currentStyles.appendAll(styles);
		}
	}

}
