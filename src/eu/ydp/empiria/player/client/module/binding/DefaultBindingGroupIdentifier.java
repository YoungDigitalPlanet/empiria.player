package eu.ydp.empiria.player.client.module.binding;


public final class DefaultBindingGroupIdentifier implements BindingGroupIdentifier {

	private String groupName;

	public DefaultBindingGroupIdentifier(String groupName){
		this.groupName = groupName;
	}
	
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof DefaultBindingGroupIdentifier))
			return false;
		DefaultBindingGroupIdentifier id2 = (DefaultBindingGroupIdentifier)obj;
		if (groupName == null  ||  id2.groupName == null)
			return false;
		return groupName.equals(id2.groupName);
	}

	@Override
	public boolean isEmptyGroupIdentifier() {
		return groupName == null  ||  "".equals(groupName);
	}
}
