package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxPresenter;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxView;

public interface MathJaxModuleFactory {
	MathJaxPresenter getMathJaxPresenter(MathJaxView view);
}
