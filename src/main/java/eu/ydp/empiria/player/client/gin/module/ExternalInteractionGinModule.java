/*
 * Created by IntelliJ IDEA.
 * User: jjackowiak
 * Date: 2015-04-29
 * Time: 14:13
 */
package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.external.view.*;

public class ExternalInteractionGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(ExternalInteractionView.class).to(ExternalInteractionViewImpl.class);
	}
}
