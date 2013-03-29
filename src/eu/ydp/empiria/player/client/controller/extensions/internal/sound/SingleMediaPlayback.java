package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.common.base.Objects;
import com.google.common.base.Optional;


public class SingleMediaPlayback {

	private Optional<String> currentMediaId = Optional.absent();
	
	public void setCurrentMediaId(String newMediaId){
		currentMediaId = Optional.of(newMediaId);
	}
	
	public String getCurrentMediaId(){
		return currentMediaId.get();
	}
	
	public boolean isPlaybackPresent(){
		return currentMediaId.isPresent();
	}
	
	public void clearCurrentMediaIdIfEqual(String refId){
		if (currentMediaId.isPresent()  &&  Objects.equal(refId, currentMediaId.get())){
			currentMediaId = Optional.absent();
		}
	}
	
	public boolean isCurrentPlayback(String id){
		return currentMediaId.isPresent()  &&  currentMediaId.get().equals(id);
	}

	
}
