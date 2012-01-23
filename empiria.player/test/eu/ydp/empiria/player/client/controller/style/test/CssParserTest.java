package eu.ydp.empiria.player.client.controller.style.test;

import junit.framework.Assert;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.util.js.JSOModel;

public class CssParserTest extends GWTTestCase {

	private String css1 = "customSelector { width: 100px; customProperty: abc; } .customClass { height: 100px; xyz: 200; }";
	private String css2 = "h2 { font-size: 5em; } customSelector { width: 200px; customproperty: xyz; color: #666; } .customClass { width: 10px; } orderInteraction { module-layout: vertical; }";
	private String css3 = "h1 { font-weight: bold; }";
	
	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}
	
	private static native String parseCSS() /*-{
		var css = "customSelector { width: 100px; customProperty: abc } .customClass { height: 100px; xyz: 200 }";
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
		Assert.assertEquals("sample css was parsed", 0, parsed.indexOf("customSelector"));
	}

	public void testStyleDataSourceManager() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		
		Document doc = XMLParser.parse("<orderInteraction responseIdentifier=\"RESPONSE\" shuffle=\"true\" />");
		Element e = doc.getDocumentElement();
		
		Assert.assertNotNull( sdsm );
		Assert.assertEquals( "no styles were added yet", 0, sdsm.getStyleProperties(e).keys().length() );


		doc = XMLParser.parse("<customSelector />");
		e = doc.getDocumentElement();

		sdsm.addAssessmentStyle(css1, "");
		sdsm.addItemStyle(0, css2, "");
		JSOModel styles = sdsm.getStyleProperties( e );
		JsArrayString keys = styles.keys();
		
		Assert.assertEquals("customProperty has properties only from assessement style",2,keys.length());
		Assert.assertFalse("StyleDataSourceManager returns correct style values", styles.hasKey("nonExistingStyle") );
		Assert.assertTrue("style names are converted to lower case", styles.hasKey("customproperty") );
		Assert.assertFalse("style names are converted to lower case", styles.hasKey("customProperty") );
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{0}, null, null));
		styles = sdsm.getStyleProperties( e );
		keys = styles.keys();
		Assert.assertEquals("when page is set customProperty has properties from assessement and pages style",3,keys.length());
		Assert.assertEquals("assessment style is overriden by page style", "200px", styles.get("width"));
		
		Assert.assertEquals("styles added later override existing values", "xyz", styles.get("customproperty") );
	}
	
	public void testEmptyStyleManager() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		
		Document doc = XMLParser.parse("<customSelector />");
		Element e = doc.getDocumentElement();
		
		JSOModel styles = sdsm.getStyleProperties(e);
		Assert.assertNotNull( "if styles are not initialized getStyleProperties still returns JSOModel", styles );
		Assert.assertEquals( "returned JSOModel is empty", 0, styles.keys().length());
	}
	
	public void testStylesForDifferentPages() {
		StyleDataSourceManager sdsm = new StyleDataSourceManager();
		sdsm.addAssessmentStyle(css1, "");
		sdsm.addItemStyle(0, css2, "");
		sdsm.addItemStyle(1, css3, "");
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{0}, null , null) );
		Document doc = XMLParser.parse("<h2 />");
		Element e = doc.getDocumentElement();
		JSOModel styles = sdsm.getStyleProperties(e);
		Assert.assertTrue( styles.hasKey("font-size") );
		Assert.assertFalse( styles.hasKey("font-weight") );
		
		
		sdsm.setCurrentPages( new PageReference(PageType.TEST, new int[]{1}, null, null ) );
		doc = XMLParser.parse("<h1 />");
		e = doc.getDocumentElement();
		styles = sdsm.getStyleProperties(e);
		Assert.assertFalse( styles.hasKey("font-size") );
		Assert.assertTrue( styles.hasKey("font-weight") );
		

	}

}
