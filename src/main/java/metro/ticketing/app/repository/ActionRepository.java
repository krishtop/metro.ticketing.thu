package metro.ticketing.app.repository;

import metro.ticketing.app.model.Action;

import java.util.Collection;

public interface ActionRepository {

    Action get(final Long id);

    Action create(final Action action);

    Action delete(final Long id);

    Collection<Action> getAll();
}
