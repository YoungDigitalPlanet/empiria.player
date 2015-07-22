package eu.ydp.empiria.player.client.controller.style.test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.data.CssParser;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.gin.module.ScriptInjectorGinModule;
import eu.ydp.empiria.player.client.scripts.ScriptsLoader;
import eu.ydp.empiria.player.client.scripts.SynchronousScriptsCallback;
import eu.ydp.empiria.player.client.style.StyleDocument;

import java.util.Map;
import java.util.Set;

public class StyleDataSourceManagerGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private StyleDataSourceManager testObj = new StyleDataSourceManager();
    private final String css1 = "customselector { width: 100px; customProperty: abc; } .customClass { height: 100px; xyz: 200; }";
    private final String css2 = "h2 { font-size: 5em; } customselector { width: 200px; customproperty: xyz; color: #666; } .customClass { width: 10px; } orderInteraction { module-layout: vertical; }";
    private final String css3 = "h1 { font-weight: bold; }";
    private ScriptsLoader scriptsLoader;

    @GinModules(ScriptInjectorGinModule.class)
    interface TestGinjector extends Ginjector {
        ScriptsLoader getScriptsLoader();
    }

    @Override
    protected void gwtSetUp() throws Exception {
        TestGinjector ginjector = GWT.create(TestGinjector.class);
        scriptsLoader = ginjector.getScriptsLoader();
    }

    public void testStyleDataSourceManager() {
        delayTestFinish(1000);
        scriptsLoader.inject(new SynchronousScriptsCallback() {
            @Override
            public void onLoad() {
                Document doc = XMLParser.parse("<orderInteraction responseIdentifier=\"RESPONSE\" shuffle=\"true\" />");
                Element e = doc.getDocumentElement();

                assertNotNull(testObj);
                assertEquals("no styles were added yet", 0, testObj.getStyleProperties(e, true).size());

                doc = XMLParser.parse("<customselector />");
                e = doc.getDocumentElement();

                testObj.addAssessmentStyle(new StyleDocument(CssParser.parseCss(css1), ""));
                testObj.addItemStyle(0, new StyleDocument(CssParser.parseCss(css2), ""));
                testObj.setCurrentPages(new PageReference(PageType.TEST, new int[]{0}, new FlowOptions(), new DisplayOptions()));
                Map<String, String> styles = testObj.getStyleProperties(e, true);
                Set<String> keys = styles.keySet();

                assertEquals("customProperty has properties only from assessement style", 3, keys.size());
                assertFalse("StyleDataSourceManager returns correct style values", styles.containsKey("nonExistingStyle"));
                assertTrue("style names are converted to lower case", styles.containsKey("customproperty"));
                assertFalse("style names are converted to lower case", styles.containsKey("customProperty"));

                testObj.setCurrentPages(new PageReference(PageType.TEST, new int[]{0}, null, null));
                styles = testObj.getStyleProperties(e, true);
                keys = styles.keySet();

                assertEquals("when page is set customProperty has properties from assessement and pages style", 3, keys.size());
                assertEquals("assessment style is overriden by page style", "200px", styles.get("width"));
                assertEquals("styles added later override existing values", "xyz", styles.get("customproperty"));
                finishTest();
            }
        });
    }

    public void testEmptyStyleManager() {
        scriptsLoader.inject(new SynchronousScriptsCallback() {
            @Override
            public void onLoad() {

                Document doc = XMLParser.parse("<customselector />");
                Element e = doc.getDocumentElement();

                Map<String, String> styles = testObj.getStyleProperties(e, true);
                assertNotNull("if styles are not initialized getStyleProperties still returns JSOModel", styles);
                assertEquals("returned JSOModel is empty", 0, styles.size());
                finishTest();
            }
        });
    }

    public void testStylesForDifferentPages() {
        scriptsLoader.inject(new SynchronousScriptsCallback() {
            @Override
            public void onLoad() {

                testObj.addAssessmentStyle(new StyleDocument(CssParser.parseCss(css1), ""));
                testObj.addItemStyle(0, new StyleDocument(CssParser.parseCss(css2), ""));
                testObj.addItemStyle(1, new StyleDocument(CssParser.parseCss(css3), ""));
                testObj.setCurrentPages(new PageReference(PageType.TEST, new int[]{0}, null, null));

                Document doc = XMLParser.parse("<h2 />");
                Element e = doc.getDocumentElement();
                Map<String, String> styles = testObj.getStyleProperties(e, true);
                assertTrue(styles.containsKey("font-size"));
                assertFalse(styles.containsKey("font-weight"));

                testObj.setCurrentPages(new PageReference(PageType.TEST, new int[]{1}, null, null));
                doc = XMLParser.parse("<h1 />");
                e = doc.getDocumentElement();
                styles = testObj.getStyleProperties(e, true);
                assertFalse(styles.containsKey("font-size"));
                assertTrue(styles.containsKey("font-weight"));
                finishTest();
            }
        });
    }
}
