package eu.ydp.empiria.player.client.module.dictionary.external.controller;

public final class Options {

	private Options() {
	}

	public static ViewType getViewType() {
		if (isViewTypeHalf()) {
			return ViewType.HALF;
		}
		return ViewType.FULL;
	}

	private static native boolean isViewTypeHalf()/*-{
													if (typeof $wnd.mskillsDictionaryOptionsViewTypeHalf == 'boolean'){
													return $wnd.mskillsDictionaryOptionsViewTypeHalf;
													}
													return false;
													}-*/;

}