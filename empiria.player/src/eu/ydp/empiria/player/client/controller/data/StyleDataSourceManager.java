package eu.ydp.empiria.player.client.controller.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.js.JSOModel;

/**
 * Requires that jscssp.js script is added to module xml file
 * 
 * @see http://glazman.org/JSCSSP/
 * @author Micha³ Samuj³o msamujlo@ydp.com.pl
 */
public class StyleDataSourceManager implements StyleSocket {

	// style declarations for assessment
	List<StyleDocument> assessmentStyle;

	// style declarations for all items
	// TODO consider using WeakHashMap to avoid problems with vector size at lines 50, 96
	Vector<List<StyleDocument>> itemStyle;

	/**
	 * Style declarations that should be searched for styles. When player
	 * changes displayed page activeItemStyles should be rebuild.
	 */
	Vector<List<StyleDocument>> activeItemStyles;

	public StyleDataSourceManager() {
		assessmentStyle = new ArrayList<StyleDocument>();

		itemStyle = new Vector<List<StyleDocument>>();
		activeItemStyles = new Vector<List<StyleDocument>>();
	}
	
	public void addAssessmentStyle(String css, String basePath) {
		assessmentStyle.add(new StyleDocument( parseCss(css) , basePath ) );
	}

	public void addItemStyle(int i, String css, String basePath) {
		if (i >= itemStyle.size()) {
			itemStyle.setSize(i + 1);
		}
		List<StyleDocument> styles = itemStyle.get(i);
		if (styles == null) {
			styles = new ArrayList<StyleDocument>();
			itemStyle.set(i, styles);
		}
		styles.add( new StyleDocument( parseCss(css) , basePath ) );
	}

	public JSOModel getStyleProperties(Element element) {
		List<String> selectors = getElementSelectors(element);
		JSOModel result = JavaScriptObject.createObject().cast();
		for (StyleDocument sheet : assessmentStyle) {
			result = sheet.getDeclarationsForSelectors(selectors, result);
			//JsCssModel cssModel = sheet.cast();
			//cssModel.getDeclarationsForSelector(selector, result);
		}
		for (List<StyleDocument> styles : activeItemStyles) {
			if (styles == null) {
				continue;
			}
			for (StyleDocument sheet : styles) {
				result = sheet.getDeclarationsForSelectors(selectors, result);
				//JsCssModel cssModel = sheet.cast();
				//result = cssModel.getDeclarationsForSelector(selector, result);
			}
		}
		return result;
	}

	protected List<String> getElementSelectors(Element element){
		String name = element.getNodeName().toLowerCase();
		String[] classes = null;
		String id = null;
		if (element.hasAttribute("class")  &&  !"".equals(element.getAttribute("class")) ){
			classes = element.getAttribute("class").split(" ");
		}
		if (element.hasAttribute("id")  &&  !"".equals(element.getAttribute("id")) ){
			id = element.getAttribute("id");
		}
		
		return buildSelectors(name, classes, id);
	}
	
	protected List<String> buildSelectors(String name, String[] classes, String id){
		List<String> selectors = new ArrayList<String>();
		
		selectors.add(name);
		if (classes != null){
			for (int i = 0 ; i < classes.length ; i ++){
				selectors.add("."+classes[i]);
			}
			for (int i = 0 ; i < classes.length ; i ++){
				selectors.add(name+"."+classes[i]);
			}
		}
		if (id != null){
			selectors.add("#"+id);
			selectors.add(name+"#"+id);
		}
		
		return selectors;
	}
 	
	@Override
	public Map<String, String> getStyles(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		JSOModel result = getStyleProperties(element);
		JsArrayString keys = result.keys();
		for (int i = 0; i < keys.length(); i++) {
			String key = keys.get(i);
			map.put(key, result.get(key));
		}
		return map;
	}
	
	@Override
	public void setCurrentPages(PageReference pr) {
		activeItemStyles = new Vector<List<StyleDocument>>( pr.pageIndices.length );
		for (int pageIndex : pr.pageIndices) {
			if (pageIndex < itemStyle.size())
				activeItemStyles.add( itemStyle.get(pageIndex) );
		}
	}

	private native JavaScriptObject parseCss(String css) /*-{
		var parser = new $wnd.CSSParser();
		return parser.parse(css, false, true);
	}-*/;

}
