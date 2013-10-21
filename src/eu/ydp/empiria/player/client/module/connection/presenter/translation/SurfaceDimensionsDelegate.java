package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class SurfaceDimensionsDelegate implements HasDimensions {

	@Inject
	private SurfaceDimensionsFinder finder;
	private HasDimensions view;
	private ConnectionItems items;

	public void init(HasDimensions view, ConnectionItems items){
		this.view = view;
		this.items = items;
	}

	@Override
	public int getHeight() {
		return finder.findHeight(view, items);
	}

	@Override
	public int getWidth() {
		return finder.findWidth(view, items);
	}

}
