package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public class TutorService {

	private final Map<String, TutorConfig> tutors = newHashMap();
	
	public boolean containsTutorConfig(String tutorId){
		return tutors.containsKey(tutorId);
	}
	
	public TutorConfig getTutorConfig(String tutorId){
		return tutors.get(tutorId);
	}
	
	public void registerTutor(String tutorId, TutorConfig tutorConfig){
		tutors.put(tutorId, tutorConfig);
	}
}
