package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropType;
import eu.ydp.empiria.player.client.util.dom.drag.emulate.EmulatedDragDrop;
import eu.ydp.empiria.player.client.util.dom.drag.html5.HTML5DragDrop;

public interface DragDropObjectFactory {
	public HTML5DragDrop<Widget> getHTML5DragDrop(@Assisted("widget") Widget widget,@Assisted("imodule") IModule imodule, @Assisted("type") DragDropType type,
			@Assisted("disableAutoBehavior") boolean disableAutoBehavior);

	public EmulatedDragDrop<Widget> getEmulatedDragDrop(@Assisted("widget") Widget widget,@Assisted("imodule") IModule imodule, @Assisted("type") DragDropType type,
			@Assisted("disableAutoBehavior") boolean disableAutoBehavior);
}
