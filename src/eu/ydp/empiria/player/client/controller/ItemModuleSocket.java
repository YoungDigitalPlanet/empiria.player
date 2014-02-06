package eu.ydp.empiria.player.client.controller;

import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONValue;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGenerator;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.InlineContainerStylesExtractor;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.containers.group.DefaultGroupIdentifier;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.registry.ModulesRegistrySocket;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.js.YJsJsonConverter;
import eu.ydp.gwtutil.client.json.js.YJsJsonFactory;

public class ItemModuleSocket implements ModuleSocket {

	private final Item item;
	private final YJsJsonConverter yJsJsonConverter;
	private final ModulesRegistrySocket modulesRegistrySocket;
	private final InteractionEventsListener interactionEventsListener;

	private JSONArray state;
	private ItemBody itemBody;
	private InlineBodyGenerator inlineBodyGenerator;

	@Inject
	public ItemModuleSocket(@Assisted Item item, YJsJsonConverter yJsJsonConverter, ModulesRegistrySocket modulesRegistrySocket,
			@SuppressWarnings("deprecation") InteractionEventsListener interactionEventsListener) {
		this.item = item;
		this.yJsJsonConverter = yJsJsonConverter;
		this.modulesRegistrySocket = modulesRegistrySocket;
		this.interactionEventsListener = interactionEventsListener;
	}

	public void init(ItemBody itemBody, JSONArray state) {
		this.itemBody = itemBody;
		this.state = state;
	}

	@Override
	public InlineBodyGeneratorSocket getInlineBodyGeneratorSocket() {
		if (inlineBodyGenerator == null) {
			inlineBodyGenerator = new InlineBodyGenerator(modulesRegistrySocket, this, this.item.options, interactionEventsListener, itemBody.getParenthood());
		}
		return inlineBodyGenerator;
	}

	@Override
	public HasChildren getParent(IModule module) {
		return itemBody.getModuleParent(module);
	}

	@Override
	public GroupIdentifier getParentGroupIdentifier(IModule module) {
		IModule currParent = module;

		while (true) {
			currParent = getParent(currParent);
			if (currParent == null || currParent instanceof IGroup) {
				break;
			}
		}
		if (currParent != null) {
			return ((IGroup) currParent).getGroupIdentifier();
		}
		return new DefaultGroupIdentifier("");
	}

	@Override
	public List<IModule> getChildren(IModule parent) {
		return itemBody.getModuleChildren(parent);
	}

	@Override
	public Stack<HasChildren> getParentsHierarchy(IModule module) {
		Stack<HasChildren> hierarchy = new Stack<HasChildren>();
		HasChildren currParent = getParent(module);
		while (currParent != null) {
			hierarchy.push(currParent);
			currParent = getParent(currParent);
		}
		return hierarchy;
	}

	@Override
	public Set<InlineFormattingContainerType> getInlineFormattingTags(IModule module) {
		InlineContainerStylesExtractor inlineContainerHelper = new InlineContainerStylesExtractor();
		return inlineContainerHelper.getInlineStyles(module);
	}

	@Override
	public YJsonArray getStateById(String id) {
		JSONValue object = state.get(0);
		if (object != null) {
			JSONValue stateValue = object.isObject().get(id);
			JSONArray stateArray = stateValue.isArray();
			return yJsJsonConverter.toYJson(stateArray);
		}
		return YJsJsonFactory.createArray();
	}

	public void setState(JSONArray state) {
		this.state = state;
	}
}