package eu.ydp.empiria.player.client.module.media.button;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.util.UserAgentChecker;
import eu.ydp.empiria.player.client.util.UserAgentChecker.MobileUserAgent;

/**
 * Kontroler mediow
 *
 * @param <T>
 */
public abstract class MediaController<T> extends Composite implements Factory<T>, SupportedAction<T> {
	private Set<MobileUserAgent> supportedMobileAgents = new HashSet<MobileUserAgent>();
	protected final static String clickSuffix = "-click";
	protected final static String hoverSuffx = "-hover";
	protected final static String unsupportedSuffx = "-unsupported";

	/**
	 * przekazuje obiekt multimediow na jakim ma pracowac kontrolka
	 *
	 * @param media
	 */
	public abstract void setMedia(MediaBase media);

	/**
	 * inicjalizacja kontrolki
	 */
	public abstract void init();

	@Override
	public boolean isSupported() {
		//tylko mobilne
		if (supportedMobileAgents.size() > 0 && UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN) {
			return supportedMobileAgents.contains(UserAgentChecker.getMobileUserAgent());
		}
		return true;
	}

	public void setSupportedMobileAgents(MobileUserAgent... userAgents) {
		if (userAgents != null && userAgents.length > 0) {
			supportedMobileAgents.addAll(Arrays.asList(userAgents));
		}
	}
}
