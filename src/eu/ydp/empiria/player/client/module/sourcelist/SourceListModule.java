package eu.ydp.empiria.player.client.module.sourcelist;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.dragdrop.Sourcelist;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class SourceListModule extends SimpleModuleBase implements Sourcelist {

	@Inject	private SourceListModuleStructure moduleStructure;
	@Inject private SourceListPresenter presenter;
	@Inject private IJSONService ijsonService;
	@Inject @PageScoped private SourcelistManager sourcelistManager;
	private String sourcelistId;

	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	@Override
	protected void initModule(Element element) {
		moduleStructure.createFromXml(element.toString(), ijsonService.createArray());
		SourceListBean bean = moduleStructure.getBean();
		sourcelistId = bean.getSourcelistId();
		presenter.setBean(bean);
		presenter.setModuleId(sourcelistId);
		presenter.createAndBindUi();
		sourcelistManager.registerSourcelist(this);
	}

	@Override
	public SourcelistItemValue getItemValue(String itemId) {
		return presenter.getItemValue(itemId);
	}

	@Override
	public void useItem(String itemId) {
		presenter.useItem(itemId);

	}

	@Override
	public void restockItem(String itemId) {
		presenter.restockItem(itemId);

	}

	@Override
	public void useAndRestockItems(List<String> itemsIds) {
		presenter.useAndRestockItems(itemsIds);

	}

	@Override
	public String getIdentifier() {
		return sourcelistId;
	}

	@Override
	public void lockSourceList() {
		presenter.lockSourceList();

	}

	@Override
	public void unlockSourceList() {
		presenter.unlockSourceList();

	}

	@Override
	public HasDimensions getItemSize() {
		return presenter.getMaxItemSize();
	}

}
