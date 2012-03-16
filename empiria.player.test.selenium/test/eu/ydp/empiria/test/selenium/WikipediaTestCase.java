package eu.ydp.empiria.test.selenium;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;
import junit.framework.*;

public class WikipediaTestCase extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://pl.wikipedia.org/", "*chrome");
	}
	public void testUntitled() throws Exception {
		selenium.open("/wiki/Wska%C5%BAnik_rozwoju_spo%C5%82ecznego");
		selenium.click("link=English");
		selenium.waitForPageToLoad("30000");
		verifyTrue(selenium.isTextPresent("composite"));
	}
}
