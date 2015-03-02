package org.mockito.configuration;

public class MockitoConfiguration extends DefaultMockitoConfiguration {

	private static boolean enabledClassCache = true;

	public static void setenableClassCache(final boolean enabled) {
		enabledClassCache = enabled;
	}

	@Override
	public boolean enableClassCache() {
		return enabledClassCache;
	}
}
