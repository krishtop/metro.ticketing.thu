package metro.ticketing.app.repository;

import metro.ticketing.app.model.Role;

import java.util.Collection;

public interface RoleRepository {

    Role create(final Role role);

    void delete(final Long id);

    Role get(final Long id);

    Collection<Role> getAll();
}
