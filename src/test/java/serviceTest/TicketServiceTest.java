package serviceTest;

import metro.ticketing.app.model.*;
import metro.ticketing.app.repository.*;
import metro.ticketing.app.repositoryImpl.*;
import metro.ticketing.app.service.TicketService;
import metro.ticketing.app.utils.DataBaseUtils;
import modelTest.RequestTest;
import modelTest.RoleTest;
import modelTest.TariffTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static metro.ticketing.app.model.RequestStatus.ACCEPTED;

public class TicketServiceTest {

    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private TariffRepository tariffRepository;
    private RequestRepository requestRepository;
    private RoleRepository roleRepository;
    private ActionRepository actionRepository;

//    @Before
//    public void init() {
//        ticketRepository = new TicketRepositoryTestImpl();
//        tariffRepository = new TariffRepositoryTestImpl();
//        ticketService = new TicketService(ticketRepository, tariffRepository);
//        requestRepository = new RequestRepositoryTestImpl();
//        roleRepository = new RoleRepositoryTestImpl();
//    }

    @Before
    public void init_() {
        roleRepository = new RoleRepositoryImpl(DataBaseUtils.getDataSource());
        tariffRepository = new TariffRepositoryImpl(DataBaseUtils.getDataSource());
        requestRepository = new RequestRepositoryImpl(DataBaseUtils.getDataSource(), roleRepository);
        ticketRepository = new TicketRepositoryImpl(DataBaseUtils.getDataSource(), tariffRepository);
        actionRepository = new ActionRepositoryImpl(DataBaseUtils.getDataSource());
        ticketService = new TicketService(ticketRepository, tariffRepository);
    }

    @Test
    public void createTicket() {

        Role contextRole = RoleTest.generateRole();
        Action action1 = actionRepository.create(new Action("SET_TARIFF", ""));
        Action action2 = actionRepository.create(new Action("CHANGE_TARIFF", ""));
        contextRole.addAction(action1);
        contextRole.addAction(action2);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Tariff tariff = TariffTest.generateTariff(TariffType.TRIP);
        tariffRepository.create(tariff);

        Ticket ticket = ticketService.createTicket(contextRequest, tariffRepository.getAll().iterator().next().getId());

        Assert.assertEquals(ticket, ticketRepository.get(ticket.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTicketIllegalArgumentTest() {
        ticketService.createTicket(null, null);
    }

    @Test
    public void updateTicket() {

        Role contextRole = RoleTest.generateRole();

        Action action1 = actionRepository.create(new Action("SET_TARIFF", ""));
        Action action2 = actionRepository.create(new Action("CHANGE_TARIFF", ""));
        contextRole.addAction(action1);
        contextRole.addAction(action2);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Tariff tariff = TariffTest.generateTariff(TariffType.TRIP);
        tariffRepository.create(tariff);

        Ticket ticket = ticketService.createTicket(contextRequest, tariffRepository.getAll().iterator().next().getId());


        Tariff newTariff = TariffTest.generateTariff(TariffType.TIME);
        newTariff = tariffRepository.create(newTariff);

        Ticket updatedTicket = ticketService.updateTicket(contextRequest, ticket.getId(), newTariff.getId());

        Assert.assertNotNull(ticketRepository.get(updatedTicket.getId()));
        Assert.assertEquals(newTariff, ticketRepository.get(updatedTicket.getId()).getTariff());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateTicketIllegalArgumentTest() {
        ticketService.createTicket(null, null);
    }


    @Test
    public void checkTicket() {

        Role contextRole = RoleTest.generateRole();
        Action action1 = actionRepository.create(new Action("SET_TARIFF", ""));
        Action action2 = actionRepository.create(new Action("CHANGE_TARIFF", ""));
        contextRole.addAction(action1);
        contextRole.addAction(action2);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Tariff tariff = TariffTest.generateTariff(TariffType.TRIP);
        tariffRepository.create(tariff);

        Ticket ticket = ticketService.createTicket(contextRequest, tariffRepository.getAll().iterator().next().getId());

        Boolean result = ticketService.checkTicket(ticket.getId());
        Assert.assertTrue(result);
    }


}
