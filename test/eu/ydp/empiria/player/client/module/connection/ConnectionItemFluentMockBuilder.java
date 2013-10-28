package eu.ydp.empiria.player.client.module.connection;

import static org.mockito.Mockito.*;
import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;

public final class ConnectionItemFluentMockBuilder {

	private int width;
	private int height;
	private int offsetLeft;
	private int offsetTop;

	private ConnectionItemFluentMockBuilder() {
		
	}
	
	public static ConnectionItemFluentMockBuilder newConnectionItem() {
		return new ConnectionItemFluentMockBuilder();
	}
	
	public ConnectionItemFluentMockBuilder withOffsets(int left, int top) {
		this.offsetLeft = left;
		this.offsetTop = top;
		return this;
	}
	
	public ConnectionItemFluentMockBuilder withDimensions(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public ConnectionItem build() {
		ConnectionItem item = mock(ConnectionItem.class);
		when(item.getOffsetLeft()).thenReturn(offsetLeft);
		when(item.getOffsetTop()).thenReturn(offsetTop);
		
		when(item.getWidth()).thenReturn(width);
		when(item.getHeight()).thenReturn(height);
		
		return item;
	}
}
