package serviceTest;

import metro.ticketing.app.model.Role;
import metro.ticketing.app.repository.RoleRepository;
import metro.ticketing.app.repositoryImpl.RoleRepositoryImpl;
import metro.ticketing.app.service.RoleService;
import metro.ticketing.app.utils.DataBaseUtils;
import modelTest.RoleTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.util.Collection;

public class RoleServiceTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

//    @Before
//    public void init() {
//        roleRepository = new RoleRepositoryTestImpl();
//        roleService = new RoleService(roleRepository);
//    }

    @Before
    public void init_() {
        roleRepository = new RoleRepositoryImpl(DataBaseUtils.getDataSource());
        roleService = new RoleService(roleRepository);
    }


    @Test
    public void getRoles() {
        Role role1 = RoleTest.generateRole();
        Role role2 = RoleTest.generateRole();

        role1 = roleRepository.create(role1);
        role2 = roleRepository.create(role2);

        Collection<Role> roleCollection = roleService.getRoles();

        Assert.assertTrue(roleCollection.contains(role1));
        Assert.assertTrue(roleCollection.contains(role2));
    }
}
