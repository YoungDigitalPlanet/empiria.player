package eu.ydp.empiria.player.client.module.ordering.drag;

public class SortableNative {

	public native void init(String selector, String dragAxis, SortCallback callback) /*-{
		$wnd
				.jQuery(selector)
				.sortable(
						{
							axis : dragAxis,
							start : function(event, ui) {
								this.from = ui.item.index();
							},
							stop : function(event, ui) {
								var to = ui.item.index();
								if (to != this.from) {
									callback.@eu.ydp.empiria.player.client.module.ordering.drag.SortCallback::sortStoped(II)(this.from, to);
								}
							}
						});
		$wnd.jQuery(selector).disableSelection();
	}-*/;

	public native void enable(String selector) /*-{
		$wnd.jQuery(selector).sortable("enable");
	}-*/;

	public native void disable(String selector) /*-{
		$wnd.jQuery(selector).sortable("disable");
	}-*/;
}