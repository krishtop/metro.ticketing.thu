package metro.ticketing.app.repositoryTestImpl;

import metro.ticketing.app.model.Action;
import metro.ticketing.app.repository.ActionRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ActionRepositoryTestImpl implements ActionRepository {

    private final AtomicLong idSequence = new AtomicLong(0);

    private static final HashMap<Long, Action> storage = new HashMap<>();


    @Override
    public Action create(final Action action) {
        Long key = idSequence.getAndIncrement();

        Action newAction = new Action(key, action.getName(), action.getDescription());
        storage.put(key, newAction);

        return newAction;
    }

    @Override
    public Action delete(final Long id) {
        return storage.remove(id);
    }

    @Override
    public Action get(final Long id) {
        return storage.get(id);
    }

    @Override
    public Collection<Action> getAll() {
        return storage.values();
    }
}
