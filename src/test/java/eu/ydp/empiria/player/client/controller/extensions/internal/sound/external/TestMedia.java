package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;

import java.util.Collection;

public interface TestMedia {

    BaseMediaConfiguration getMediaConfiguration();

    Collection<String> getSources();

}
