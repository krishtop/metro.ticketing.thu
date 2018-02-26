package metro.ticketing.app.repositoryTestImpl;

import metro.ticketing.app.model.Role;
import metro.ticketing.app.repository.RoleRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RoleRepositoryTestImpl implements RoleRepository {

    private static final AtomicLong idSequence = new AtomicLong(0);

    private static final HashMap<Long, Role> storage = new HashMap<>();

    @Override
    public Role create(final Role role) {
        Long key = idSequence.incrementAndGet();

        Role newRole = new Role(key, role.getName(), role.getActions());
        storage.put(key, newRole);

        return newRole;
    }

    @Override
    public void delete(final Long id) {
        storage.remove(id);
    }

    @Override
    public Role get(final Long id) {
        return storage.get(id);
    }

    @Override
    public Collection<Role> getAll() {
        return storage.values();
    }
}
