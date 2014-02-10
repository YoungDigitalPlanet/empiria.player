package eu.ydp.empiria.player.client.rebind;

public interface InterfaceFactory {
	InjectInterface getSimpleInject();

	InjectInterface getSimpleInject(String module);
}
