package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;

public class SourcelistManagerHelper {

	public Optional<Sourcelist> findSourcelist(SourcelistClient client) {
		Sourcelist sourcelist = searchForSourcelist(client.getParentModule());
		return Optional.fromNullable(sourcelist);
	}

	private Sourcelist searchForSourcelist(HasChildren module) {
		if (module == null) {
			return null;
		}
		List<IModule> children = module.getChildrenModules();
		for (IModule child : children) {
			if (child instanceof Sourcelist) {
				Sourcelist sourcelist = (Sourcelist) child;
				return sourcelist;
			}
		}
		return searchForSourcelist(module.getParentModule());
	}

	public List<SourcelistClient> findClients(Sourcelist sourcelist) {
		List<SourcelistClient> clients = Lists.newArrayList();
		findClients(clients, sourcelist.getParentModule());
		return clients;

	}

	private void findClients(List<SourcelistClient> clients, HasChildren parent) {
		for (IModule child : parent.getChildrenModules()) {
			if (child instanceof SourcelistClient) {
				SourcelistClient client = (SourcelistClient) child;
				clients.add(client);
			} else if (child instanceof HasChildren) {
				HasChildren conteiner = (HasChildren) child;
				findClients(clients, conteiner);
			}
		}
	}
}
