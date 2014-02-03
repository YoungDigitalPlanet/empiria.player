package eu.ydp.empiria.player.client.module.binding;

public interface Bindable {

	public BindingValue getBindingValue(BindingType bindingType);

	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType);
}
