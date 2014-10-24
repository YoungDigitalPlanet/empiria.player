package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.common.base.Predicate;


public class SoundInstanceIdPredicate implements Predicate<SoundInstance> {

	private final int idToFind;

	public SoundInstanceIdPredicate(int idToFind) {
		this.idToFind = idToFind;
	}

	@Override
	public boolean apply(SoundInstance input) {
		return idToFind == input.getUniqueId();
	}

}
