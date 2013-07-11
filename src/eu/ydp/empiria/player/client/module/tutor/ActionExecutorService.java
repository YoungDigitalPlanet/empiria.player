package eu.ydp.empiria.player.client.module.tutor;


public interface ActionExecutorService {

	void execute(ActionType type, EndHandler handler);
	
	void terminate();
}
