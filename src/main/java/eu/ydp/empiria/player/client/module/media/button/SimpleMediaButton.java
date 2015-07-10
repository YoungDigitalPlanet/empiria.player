package eu.ydp.empiria.player.client.module.media.button;

public class SimpleMediaButton extends AbstractMediaButton {
    private final String styleName;
    private final boolean singleClick;

    public SimpleMediaButton(String styleName, boolean singleClick) {
        super(styleName, singleClick);
        this.styleName = styleName;
        this.singleClick = singleClick;
    }

    @Override
    public void init() {

    }

    @Override
    protected void onClick() {
        setActive(!isActive());
        changeStyleForClick();
    }

    @Override
    public boolean isSupported() {
        return true;
    }

}
