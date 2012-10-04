package eu.ydp.empiria.player.client.module.containers.group;


public class DefaultGroupIdentifier implements GroupIdentifier {

	protected String identifier;
	
	public DefaultGroupIdentifier(String identifier){
		this.identifier = identifier;
	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}
	
	@Override
	public boolean equals(Object o){
		if (o instanceof DefaultGroupIdentifier){
			return ((DefaultGroupIdentifier)o).getIdentifier().equals(identifier);
		}
		return false;
	}

}
