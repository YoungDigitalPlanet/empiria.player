package eu.ydp.empiria.player.client.controller.flow;

import eu.ydp.empiria.player.client.controller.flow.processing.commands.ActivityCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.GroupActivityCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.events.ActivityProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventsListener;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public class GroupActivityProcessor implements IActivity  {

	protected GroupIdentifier groupIdentifier;
	protected boolean markingAnswers = false;
	protected boolean showingAnswers = false;
	protected boolean locked = false;
	protected IActivity activityModule;
	
	public GroupActivityProcessor(GroupIdentifier gi, IActivity activityModule){
		this.groupIdentifier = gi;	
		this.activityModule = activityModule;
	}

	@Override
	public void lock(boolean lo) {
		if (lo != locked){
			activityModule.lock(lo);
		}
	}
	
	public void setLock(boolean lo){
		locked = lo;		
	}

	@Override
	public void reset() {
		if (showingAnswers)
			activityModule.showCorrectAnswers(false);
		if (markingAnswers)
			activityModule.markAnswers(false);
		if (locked)
			activityModule.lock(false);
		activityModule.reset();
	}

	@Override
	public void markAnswers(boolean mark) {
		if (markingAnswers != mark){
			if (showingAnswers)
				showCorrectAnswers(false);
			activityModule.markAnswers(mark);
		}
	}
	
	public void setMarkAnswers(boolean mark){
		markingAnswers = mark;
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (showingAnswers != show){
			if (markingAnswers)
				markAnswers(false);
			activityModule.showCorrectAnswers(show);
		}
	}
	
	public void setShowCorrectAnswers(boolean show){
		showingAnswers = show;		
	}
	
}
