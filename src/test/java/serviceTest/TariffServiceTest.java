package serviceTest;

import metro.ticketing.app.model.*;
import metro.ticketing.app.repository.ActionRepository;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.repository.RoleRepository;
import metro.ticketing.app.repository.TariffRepository;
import metro.ticketing.app.repositoryImpl.ActionRepositoryImpl;
import metro.ticketing.app.repositoryImpl.RequestRepositoryImpl;
import metro.ticketing.app.repositoryImpl.RoleRepositoryImpl;
import metro.ticketing.app.repositoryImpl.TariffRepositoryImpl;
import metro.ticketing.app.service.TariffService;
import metro.ticketing.app.utils.DataBaseUtils;
import modelTest.RequestTest;
import modelTest.RoleTest;
import modelTest.TariffTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static metro.ticketing.app.model.RequestStatus.ACCEPTED;

public class TariffServiceTest {

    private TariffService tariffService;
    private TariffRepository tariffRepository;
    private RoleRepository roleRepository;
    private RequestRepository requestRepository;
    private ActionRepository actionRepository;

//    @Before
//    public void init() {
//        tariffRepository = new TariffRepositoryTestImpl();
//        requestRepository = new RequestRepositoryTestImpl();
//        tariffService = new TariffService(tariffRepository, requestRepository);
//        roleRepository = new RoleRepositoryTestImpl();
//    }

    @Before
    public void init_() {
        roleRepository = new RoleRepositoryImpl(DataBaseUtils.getDataSource());
        tariffRepository = new TariffRepositoryImpl(DataBaseUtils.getDataSource());
        requestRepository = new RequestRepositoryImpl(DataBaseUtils.getDataSource(), roleRepository);
        actionRepository = new ActionRepositoryImpl(DataBaseUtils.getDataSource());
        tariffService = new TariffService(tariffRepository, requestRepository);
    }


    @Test
    public void createTariffTest() {
        Role contextRole = RoleTest.generateRole();
        Action action = actionRepository.create(new Action("CREATE_TARIFF", ""));
        contextRole.addAction(action);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Tariff tariff = TariffTest.generateTariff(TariffType.TRIP);

        tariff = tariffService.createTariff(contextRequest.getId(), tariff);

        Assert.assertTrue(tariff.equals(tariffRepository.get(tariff.getId())));
    }

    @Test
    public void deleteTariffTest() {
        Role contextRole = RoleTest.generateRole();
        Action action1 = actionRepository.create(new Action("CREATE_TARIFF", ""));
        Action action2 = actionRepository.create(new Action("DELETE_TARIFF", ""));
        contextRole.addAction(action1);
        contextRole.addAction(action2);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Tariff tariff = TariffTest.generateTariff(TariffType.TIME);

        tariff = tariffService.createTariff(contextRequest.getId(), tariff);
        tariffService.deleteTariff(contextRequest.getId(), tariff.getId());

        Assert.assertEquals(tariffRepository.get(tariff.getId()), null);
    }


}
