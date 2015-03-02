package eu.ydp.empiria.player.client.module;

public interface ILifecycleModule {

	public void onBodyLoad();

	public void onBodyUnload();

	public void onSetUp();

	public void onStart();

	public void onClose();

}
