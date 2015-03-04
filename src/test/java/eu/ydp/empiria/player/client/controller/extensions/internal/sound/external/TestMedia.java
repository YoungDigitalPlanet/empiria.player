package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import java.util.Collection;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;

public interface TestMedia {

	BaseMediaConfiguration getMediaConfiguration();

	Collection<String> getSources();

}