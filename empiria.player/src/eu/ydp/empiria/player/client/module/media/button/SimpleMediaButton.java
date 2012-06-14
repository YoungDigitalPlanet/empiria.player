package eu.ydp.empiria.player.client.module.media.button;

public class SimpleMediaButton extends AbstractMediaButton<SimpleMediaButton> {
	private String styleName;
	private boolean singleClick;

	public SimpleMediaButton(String styleName, boolean singleClick) {
		super(styleName, singleClick);
		this.styleName = styleName;
		this.singleClick = singleClick;
	}

	@Override
	public SimpleMediaButton getNewInstance() {
		return new SimpleMediaButton(styleName, singleClick);
	}

	@Override
	public void init() {
	}

}
