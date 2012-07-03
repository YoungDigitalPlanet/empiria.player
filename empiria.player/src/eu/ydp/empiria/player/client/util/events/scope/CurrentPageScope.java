package eu.ydp.empiria.player.client.util.events.scope;

import eu.ydp.empiria.player.client.controller.Page;

public class CurrentPageScope implements EventScope<CurrentPageScope> {
	int pageIndex = -1;

	public CurrentPageScope() {
		pageIndex = Page.getCurrentPageNumber();
	}

	@Override
	public Scope getScope() {
		return Scope.PAGE;
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

	@Override
	public int compareTo(CurrentPageScope o) {
		if (pageIndex > o.pageIndex)
			return 1;
		if (pageIndex < o.pageIndex)
			return -1;
		return 0;
	}
}
