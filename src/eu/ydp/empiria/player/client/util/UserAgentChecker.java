package eu.ydp.empiria.player.client.util;

import eu.ydp.empiria.player.user.rebind.MobileUserAgentPropertyGenerator;

/**
 * Klasa pomocnicza do sprawdzania userAgenta
 *
 */
public class UserAgentChecker {
	protected static String userAgent = UserAgentChecker.getUserAgentStrting().toLowerCase();
	protected static MobileUserAgent mobileUserAgent = null;

	/**
	 * Konfiguracja mobilnego user agenta. Wartosci musza byc zsynchronizowane z
	 * module.gwt.xml oraz {@link MobileUserAgentPropertyGenerator}
	 *
	 */
	public enum MobileUserAgent {
		CHROME("chrome", "mozilla.*android.*chrome\\/[1-9]{1}[0-9]{1}.*"), FIREFOX("firefox", "mozilla.*android.*firefox\\/[1-9]{1}[0-9]{1}.*"), ANDROID23("android23",
				"android[ ]*2.3[.0-9a-z -]*"), ANDROID321("android321", "android[ ]*3.2.1[.0-9a-z -]*"), ANDROID3("android3", "android[ ]*3[.0-9a-z -]*"), ANDROID4("android4",
				"android[ ]*4[.0-9a-z -]*"), UNKNOWN("unknown", ".*");
		private String tagName, regexPattern;

		private MobileUserAgent(String name, String regex) {
			this.tagName = name;
			this.regexPattern = regex;
		}

		/**
		 * regex wykonywany przy sprawdzaniu useragenta. kod wykonywany natywnie
		 *
		 * @return
		 */
		public String getRegexPattern() {
			return regexPattern;
		}

		/**
		 * nazwa useragenta
		 */
		public String tagName() {
			return tagName;
		}
	}

	/**
	 * W przyszlosci do zsynchronizwania z UserAgent dostarczanym przez gwt
	 *
	 */
	public enum UserAgent {
		GECKO1_8("gecko1_8", ".*gecko.*"), ALL("all", ".*");
		private String tagName, regexPattern;

		private UserAgent(String name, String regex) {
			this.tagName = name;
			this.regexPattern = regex;
		}

		/**
		 * regex wykonywany przy sprawdzaniu useragenta. kod wykonywany natywnie
		 *
		 * @return
		 */
		public String getRegexPattern() {
			return regexPattern;
		}

		/**
		 * nazwa useragenta
		 */
		public String tagName() {
			return tagName;
		}
	}

	/**
	 * Sprawdza czy podany parametr odpowiada userAgent w przegladarce
	 *
	 * @param userAgent
	 * @return
	 */
	public static boolean isMobileUserAgent(MobileUserAgent userAgent) {
		return isUserAgentNative(userAgent.regexPattern, UserAgentChecker.userAgent);
	}

	private native static boolean isUserAgentNative(String regex, String userAgent)/*-{
		var reg = eval("/" + regex + "/");
		return reg.test(userAgent);
	}-*/;

	public static boolean isUserAgent(UserAgent userAgent) {
		return isUserAgentNative(userAgent.regexPattern, UserAgentChecker.userAgent);
	}

	/**
	 * zwraca navigator.userAgent
	 *
	 * @return
	 */
	public native static String getUserAgentStrting()/*-{
		return navigator.userAgent;
	}-*/;

	/**
	 * zwraca userAgenta przegladarki
	 */
	public static MobileUserAgent getMobileUserAgent() {
		if (mobileUserAgent == null) {
			mobileUserAgent = MobileUserAgent.UNKNOWN;
			for (MobileUserAgent ua : MobileUserAgent.values()) {
				if (isMobileUserAgent(ua)) {
					mobileUserAgent = ua;
					break;
				}
			}
		}
		return mobileUserAgent;
	}
}
