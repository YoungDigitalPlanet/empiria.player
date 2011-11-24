package eu.ydp.empiria.player.client.model;

import java.util.Date;

public class AssessmentItemStatistics {
	
	public AssessmentItemStatistics(){
		timeStarted = 0;
		timeTotal = 0;
		counterCheck = 0;
		counterMistakes = 0;
	}

	public void onBeginItemSession(){
		timeStarted = (long) ((new Date()).getTime() * 0.001);
	}

	public void onEndItemSession(){
		long now = (long) ((new Date()).getTime() * 0.001);
		timeTotal += new Long(now - timeStarted).intValue();
		timeStarted = now;
	}
	public void addCheckIncident(){
		counterCheck++;
	}

	public void addMistakesCount(int count){
		counterMistakes += count;
	}
	
	public int getTimeTotal(){
		return timeTotal;
	}

	public int getCheckCount(){
		return counterCheck;
	}

	public int getMistakesCount(){
		return counterMistakes;
	}


	private long timeStarted;
	private int timeTotal;
	public int counterCheck;
	public int counterMistakes;
}
