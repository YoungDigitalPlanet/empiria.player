package eu.ydp.empiria.player.client.util.events.scope;

import eu.ydp.empiria.player.client.controller.Page;

public class CurrentPageScope implements EventScope<CurrentPageScope> {
	protected int pageIndex = -1;
	protected Scope scope = Scope.PAGE;
	public CurrentPageScope() {
		pageIndex = Page.getCurrentPageNumber();
	}

	@Override
	public Scope getScope() {
		return scope;
	}



	public int getPageIndex() {
		return pageIndex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CurrentPageScope [pageIndex=");
		builder.append(pageIndex);
		builder.append(", scope=");
		builder.append(getScope());
		builder.append("]");
		return builder.toString();
	}

	@SuppressWarnings("PMD")
	@Override
	public int compareTo(CurrentPageScope object) {
		if (pageIndex > object.pageIndex) {
			return 1;
		}
		if (pageIndex < object.pageIndex) {
			return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		return result;
	}

	@SuppressWarnings("PMD")
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
		CurrentPageScope other = (CurrentPageScope) obj;
		if (pageIndex != other.pageIndex) {
			return false;
		}
		if (scope != other.scope) {
			return false;
		}
		return true;
	}

}
