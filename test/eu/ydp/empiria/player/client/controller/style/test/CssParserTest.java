package eu.ydp.empiria.player.client.controller.style.test;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.data.CssParser;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.style.StyleDocument;

public class CssParserTest extends GWTTestCase {

	private String css1 = "customselector { width: 100px; customProperty: abc; } .customClass { height: 100px; xyz: 200; }";
	private String css2 = "h2 { font-size: 5em; } customselector { width: 200px; customproperty: xyz; color: #666; } .customClass { width: 10px; } orderInteraction { module-layout: vertical; }";
	private String css3 = "h1 { font-weight: bold; }";
	
	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}
	
	private static native String parseCSS() /*-{
		var css = "customselector { width: 100px; customProperty: abc } .customClass { height: 100px; xyz: 200 }";
		var parser = new $wnd.CSSParser();
		var sheet = parser.parse(css, false, true);
		return sheet.cssText();
	}-*/;
	
	private static native JavaScriptObject getParser() /*-{
		return new $wnd.CSSParser();
	}-*/;

	public void testIfParserIsPresent() {
		String parsed = parseCSS();
		System.out.println(parsed);
		Assert.assertEquals("sample css was parsed", 0, parsed.indexOf("customselector"));
	}

	public void testStyleDataSourceManager() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		
		Document doc = XMLParser.parse("<orderInteraction responseIdentifier=\"RESPONSE\" shuffle=\"true\" />");
		Element e = doc.getDocumentElement();
		
		Assert.assertNotNull( sdsm );
		Assert.assertEquals( "no styles were added yet", 0, sdsm.getStyleProperties(e).size() );


		doc = XMLParser.parse("<customselector />");
		e = doc.getDocumentElement();

		sdsm.addAssessmentStyle(new StyleDocument(CssParser.parseCss(css1), "") );
		sdsm.addItemStyle(0, new StyleDocument(CssParser.parseCss(css2), "") );
		sdsm.setCurrentPages(new PageReference(PageType.TEST, new int[]{0}, new FlowOptions(), new DisplayOptions()));
		Map<String, String> styles = sdsm.getStyleProperties( e );
		Set<String> keys = styles.keySet();
		
		Assert.assertEquals("customProperty has properties only from assessement style",3,keys.size());
		Assert.assertFalse("StyleDataSourceManager returns correct style values", styles.containsKey("nonExistingStyle") );
		Assert.assertTrue("style names are converted to lower case", styles.containsKey("customproperty") );
		Assert.assertFalse("style names are converted to lower case", styles.containsKey("customProperty") );
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{0}, null, null));
		styles = sdsm.getStyleProperties( e );
		keys = styles.keySet();
		Assert.assertEquals("when page is set customProperty has properties from assessement and pages style",3,keys.size());
		Assert.assertEquals("assessment style is overriden by page style", "200px", styles.get("width"));
		
		Assert.assertEquals("styles added later override existing values", "xyz", styles.get("customproperty") );
	}
	
	public void testEmptyStyleManager() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		
		Document doc = XMLParser.parse("<customselector />");
		Element e = doc.getDocumentElement();
		
		Map<String, String> styles = sdsm.getStyleProperties(e);
		Assert.assertNotNull( "if styles are not initialized getStyleProperties still returns JSOModel", styles );
		Assert.assertEquals( "returned JSOModel is empty", 0, styles.size());
	}
	
	public void testStylesForDifferentPages() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		sdsm.addAssessmentStyle(new StyleDocument( CssParser.parseCss(css1), "") );
		sdsm.addItemStyle(0, new StyleDocument( CssParser.parseCss(css2), ""));
		sdsm.addItemStyle(1, new StyleDocument( CssParser.parseCss(css3), ""));
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{0}, null , null) );
		Document doc = XMLParser.parse("<h2 />");
		Element e = doc.getDocumentElement();
		Map<String, String> styles = sdsm.getStyleProperties(e);
		Assert.assertTrue( styles.containsKey("font-size") );
		Assert.assertFalse( styles.containsKey("font-weight") );
		
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{1}, null, null ) );
		doc = XMLParser.parse("<h1 />");
		e = doc.getDocumentElement();
		styles = sdsm.getStyleProperties(e);
		Assert.assertFalse( styles.containsKey("font-size") );
		Assert.assertTrue( styles.containsKey("font-weight") );
		

	}

}
