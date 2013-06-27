package eu.ydp.empiria.player.client.module.dragdrop;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;

public class SourcelistManagerHelper {

	final Predicate<IModule> isSourcelist = new Predicate<IModule>() {

		@Override
		public boolean apply(@Nullable IModule module) {
			return module instanceof Sourcelist;
		}
	};

	public Optional<Sourcelist> findSourcelist(SourcelistClient client) {
		return searchForSourcelist(client.getParentModule());
	}

	private Optional<Sourcelist> searchForSourcelist(HasChildren module) {
		if (module == null) {
			return Optional.absent();
		}
		Optional<IModule> sourcelistModule = checkChildren(module);
		if (sourcelistModule.isPresent()) {
			Sourcelist sourcelist = (Sourcelist) sourcelistModule.get();
			return Optional.of(sourcelist);
		}
		return searchForSourcelist(module.getParentModule());
	}

	private Optional<IModule> checkChildren(HasChildren module) {
		List<IModule> children = module.getChildrenModules();
		Optional<IModule> sourcelist = Iterables
				.tryFind(children, isSourcelist);
		return sourcelist;
	}

	public List<SourcelistClient> findClients(Sourcelist sourcelist) {
		List<SourcelistClient> clients = Lists.newArrayList();
		searchForClients(clients, sourcelist.getParentModule());
		return clients;

	}

	private void searchForClients(List<SourcelistClient> clients,
			HasChildren parent) {
		for (IModule child : parent.getChildrenModules()) {
			if (child instanceof SourcelistClient) {
				SourcelistClient client = (SourcelistClient) child;
				clients.add(client);
			} else if (child instanceof HasChildren) {
				HasChildren conteiner = (HasChildren) child;
				searchForClients(clients, conteiner);
			}
		}
	}
}
