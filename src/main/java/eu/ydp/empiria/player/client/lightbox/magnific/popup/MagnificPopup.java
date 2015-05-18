package eu.ydp.empiria.player.client.lightbox.magnific.popup;

import eu.ydp.empiria.player.client.lightbox.FullScreen;

public class MagnificPopup implements FullScreen {

	@Override
	public void openImage(String imageSrc, String title) {
		openImageNative(imageSrc, title);
	}

	private native void openImageNative(String imageSrc, String title) /*-{
		$wnd.$.magnificPopup.open({
			items: {
				src: imageSrc
			},
			type: 'image',

			image: {
				titleSrc: function () {
					return title
				}
			},

			closeOnContentClick: true
		});
	}-*/;
}
