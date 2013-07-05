package eu.ydp.empiria.player.client.module.sourcelist;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.UniqueId;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.dragdrop.Sourcelist;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListModuleStructure;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.gwtutil.client.service.json.IJSONService;

public class SourceListModule extends SimpleModuleBase implements Sourcelist {

	@Inject	private SourceListModuleStructure moduleStructure;
	@Inject private SourceListPresenter presenter;
	@Inject private IJSONService ijsonService;
	@Inject @PageScoped private SourcelistManager sourcelistManager;
	@Inject @UniqueId private String moduleUniqueId;

	@Override
	public Widget getView() {
		return presenter.asWidget();
	}

	@Override
	protected void initModule(Element element) {
		moduleStructure.createFromXml(element.toString(), ijsonService.createArray());
		SourceListBean bean = moduleStructure.getBean();
		presenter.setBean(bean);
		presenter.setModuleId(moduleUniqueId);
		presenter.createAndBindUi();
		sourcelistManager.registerSourcelist(this);
	}

	@Override
	public SourcelistItemValue getItemValue(String itemId) {
		return new SourcelistItemValue(SourcelistItemType.TEXT, presenter.getItemValue(itemId),"");  // TODO YPUB-5441 change type
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
		return moduleUniqueId;
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
	public HasDimensions getItemSize() { // TODO YPUB-5441
		// TODO Auto-generated method stub
		return null;
	}

}
