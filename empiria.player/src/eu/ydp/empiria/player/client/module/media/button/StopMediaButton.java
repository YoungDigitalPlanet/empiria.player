package eu.ydp.empiria.player.client.module.media.button;

/**
 *
 * przycisk stop
 *
 * @author plelakowski
 *
 */
public class StopMediaButton extends AbstractMediaButton<StopMediaButton> {
	public StopMediaButton() {
		super("qp-media-stop", false);
	}

	@Override
	protected void onClick() {
		super.onClick();
		getMedia().pause();
		getMedia().setCurrentTime(0);
	}

	@Override
	public StopMediaButton getNewInstance() {
		return new StopMediaButton();
	}

	@Override
	public void init() {
	}
}
