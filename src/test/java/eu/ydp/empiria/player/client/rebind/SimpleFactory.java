package eu.ydp.empiria.player.client.rebind;

public interface SimpleFactory {
    SimpleInject getSimpleInject();

    SimpleInject getSimpleInject(String module);
}
