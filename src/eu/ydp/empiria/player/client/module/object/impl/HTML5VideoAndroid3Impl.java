package eu.ydp.empiria.player.client.module.object.impl;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;

public class HTML5VideoAndroid3Impl extends HTML5VideoAndroidImpl {
	boolean videoReady = false;
	public HTML5VideoAndroid3Impl() {
		super();
		video.addBitlessDomHandler(new HTML5MediaEventHandler() {
			int stopTime = 300; // zatrzymujemy 300ms przed koncem niestety na galaxytabie ponizej nie zalapie
			// dla androida 3.2x obejscie komunikatu bledu podczas odtwarzania
			@Override
			public void onEvent(HTML5MediaEvent t) {
				//400ms przed koncem filmu
				if (videoReady && video.getCurrentTime() * 1000 > video.getDuration() * 1000 - stopTime) {
					video.pause();
					video.setCurrentTime(0);
				}
			}
		}, HTML5MediaEvent.getType(HTML5MediaEventsType.timeupdate));

		//czekamy na informacje na temat dlugosci utworu
		video.addBitlessDomHandler(new HTML5MediaEventHandler() {
			@Override
			public void onEvent(HTML5MediaEvent event) {
				videoReady = true;
			}
		}, HTML5MediaEvent.getType(HTML5MediaEventsType.durationchange));
	}
}
