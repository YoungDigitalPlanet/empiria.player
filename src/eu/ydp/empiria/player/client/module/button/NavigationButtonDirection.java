package eu.ydp.empiria.player.client.module.button;

import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;

public enum NavigationButtonDirection {
	NEXT {
		@Override
		public FlowRequest getRequest() {
			return new FlowRequest.NavigateNextItem();
		}

		@Override public String getName() {
			return "next";
		}
	},
	PREVIOUS {
		@Override
		public FlowRequest getRequest() {
			return new FlowRequest.NavigatePreviousItem();
		}

		@Override public String getName() {
			return "prev";
		}
	};

	public abstract FlowRequest getRequest();

	public abstract String getName();

}