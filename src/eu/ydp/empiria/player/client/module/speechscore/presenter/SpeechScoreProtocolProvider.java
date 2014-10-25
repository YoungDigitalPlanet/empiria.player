package eu.ydp.empiria.player.client.module.speechscore.presenter;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class SpeechScoreProtocolProvider {

	private static final String HTTP_PROTOCOL_PREFIX = "http://";
	private static final String YDP_PROTOCOL_PREFIX = "ydp://";

	@Inject
	private UserAgentUtil userAgentUtil;

	public String get() {
		return userAgentUtil.isMobileUserAgent() ? YDP_PROTOCOL_PREFIX : HTTP_PROTOCOL_PREFIX;
	}
}
