package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> {

	private final Instance<IosAudioPlayHack> iosPlayHack;
	private final UserAgentUtil userAgentUtil;

	@Inject
	public HTML5AudioMediaExecutor(Instance<IosAudioPlayHack> iosPlayHack, UserAgentUtil userAgentUtil) {
		this.iosPlayHack = iosPlayHack;
		this.userAgentUtil = userAgentUtil;
		applyIosHackIfNeeded(userAgentUtil);
	}
	
	@Override
	public void initExecutor() {
		//
	}
	
	@Override
	public void setMedia(Audio media) {
		super.setMedia(media);
		applyIosHackIfNeeded(userAgentUtil);
	}

	private void applyIosHackIfNeeded(UserAgentUtil userAgentUtil) {
		if (isPlayOnTouchHackNeeded(userAgentUtil)) {
			addIosAudioHack();
		}
	}

	private boolean isPlayOnTouchHackNeeded(UserAgentUtil userAgentUtil) {
		return userAgentUtil.isInsideIframe() && isMobileSafari(userAgentUtil);
	}


	private boolean isMobileSafari(UserAgentUtil userAgentUtil) {
		return userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI) || userAgentUtil.isMobileUserAgent(MobileUserAgent.SAFARI_WEBVIEW);
	}
	
	protected void addIosAudioHack() {
		iosPlayHack.get().applyHack(this);
	}

}
