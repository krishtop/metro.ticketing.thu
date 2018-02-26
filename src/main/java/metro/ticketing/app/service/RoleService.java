package metro.ticketing.app.service;

import metro.ticketing.app.model.Role;
import metro.ticketing.app.repository.RoleRepository;

import java.util.Collection;

public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Collection<Role> getRoles() {
        return roleRepository.getAll();
    }

}
