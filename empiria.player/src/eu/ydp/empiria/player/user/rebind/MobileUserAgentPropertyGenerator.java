package eu.ydp.empiria.player.user.rebind;

import java.util.SortedSet;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.PropertyProviderGenerator;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.StringSourceWriter;
import com.google.gwt.user.rebind.UserAgentGenerator;
import com.google.gwt.user.rebind.UserAgentPropertyGeneratorPredicate;

import eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent;

/**
 * Generator which writes out the JavaScript for determining the value of the
 * <code>user.agent</code> selection property.
 */

public class MobileUserAgentPropertyGenerator implements PropertyProviderGenerator {
	/**
	 * List of predicates to identify user agent. The order of evaluation is
	 * from top to bottom, i.e., the first matching predicate will have the
	 * associated ua token returned. ua is defined in an outer scope and is
	 * therefore visible in the predicate javascript fragment.
	 */
	private static UserAgentPropertyGeneratorPredicate[] predicates = new UserAgentPropertyGeneratorPredicate[MobileUserAgent.values().length-1];
	static {
		int x = 0;
		for (MobileUserAgent agent : MobileUserAgent.values()) {
			if(agent != MobileUserAgent.UNKNOWN){
				predicates[x++] = new UserAgentPropertyGeneratorPredicate(agent.tagName())
				.getPredicateBlock()
				.println("return /" + agent.getRegexPattern() + "/.test(ua);")
				.returns("'" + agent.tagName() + "'");
			}
		}
	}

	/**
	 * Writes out the JavaScript function body for determining the value of the
	 * <code>user.agent</code> selection property. This method is used to create
	 * the selection script and by {@link UserAgentGenerator} to assert at
	 * runtime that the correct user agent permutation is executing. The list of
	 * <code>user.agent</code> values listed here should be kept in sync with
	 * {@link #VALID_VALUES} and <code>UserAgent.gwt.xml</code>.
	 */
	static void writeUserAgentPropertyJavaScript(TreeLogger logger, SourceWriter body, SortedSet<String> possibleValues) {
		if (MobileUserAgent.values().length != 8) {
			logger.log(TreeLogger.ERROR, "Niepoprawna ilosc wartosci w MobileUserAgent sprawdz plik " + MobileUserAgent.class.getName() + ", module.gwt.xml oraz "
					+ MobileUserAgentPropertyGenerator.class.getName() + " Wszystkie musza byc zsynchronizowane");
		}
		// write preamble
		body.println("var ua = navigator.userAgent.toLowerCase();");
		// write only selected user agents
		for (int i = 0; i < predicates.length; i++) {
			if (possibleValues.contains(predicates[i].getUserAgent())) {
				body.println("if ((function() { ");
				body.indent();
				body.print(predicates[i].toString());
				body.outdent();
				body.println("})()) return " + predicates[i].getReturnValue() + ";");
			}
		}

		// default return;
		body.println("return 'unknown';");
	}

	public String generate(TreeLogger logger, SortedSet<String> possibleValues, String fallback, SortedSet<ConfigurationProperty> configProperties) {
		StringSourceWriter body = new StringSourceWriter();
		body.println("{");
		body.indent();
		writeUserAgentPropertyJavaScript(logger, body, possibleValues);
		body.outdent();
		body.println("}");
		return body.toString();
	}
}
