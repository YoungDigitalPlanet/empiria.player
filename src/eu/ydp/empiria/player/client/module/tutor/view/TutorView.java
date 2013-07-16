package eu.ydp.empiria.player.client.module.tutor.view;

import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;

public interface TutorView extends AnimationHolder, IsWidget {
	void bindUi();
	void setBackgroundImage(String src);
}
