package eu.ydp.empiria.player.client.module.dragdrop;

public class SourcelistItemValue {

	private final SourcelistItemType type;
	private final String value;
	private final String itemId;

	public SourcelistItemValue(SourcelistItemType type, String value, String itemId) {
		super();
		this.type = type;
		this.value = value;
		this.itemId = itemId;
	}

	public SourcelistItemType getType() {
		return type;
	}

	public String getContent() {
		return value;
	}

	public String getItemId() {
		return itemId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SourcelistItemValue other = (SourcelistItemValue) obj;
		if (itemId == null) {
			if (other.itemId != null) {
				return false;
			}
		} else if (!itemId.equals(other.itemId)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
