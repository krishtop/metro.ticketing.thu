package modelTest;

import metro.ticketing.app.exception.AccessDeniedException;
import metro.ticketing.app.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class TicketTest {

    public static Integer TRIPS_COUNT = 300;
    public static Integer DAY_COUNT = 100;

    @Test
    public void init() {
        Ticket ticket = new Ticket();
        Assert.assertTrue(ticket.getActivated());
    }

    @Test
    public void addTripTariff() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT));

        Assert.assertNull(ticket.getExpTime());
        Assert.assertEquals(TRIPS_COUNT, ticket.getReminingTrips());
    }

    @Test(expected = AccessDeniedException.class)
    public void addTripTariffExeption() {
        generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT), new Action("", ""));
    }


    @Test
    public void addTimeTariff() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TIME, DAY_COUNT, null));

        Assert.assertNull(ticket.getReminingTrips());
        Assert.assertNotNull(ticket.getExpTime());
        Assert.assertTrue(LocalDate.now().isBefore(ticket.getExpTime()));
    }

    @Test(expected = AccessDeniedException.class)
    public void addTimeTariffExeption() {
        generateTicket(new Tariff(TariffType.TIME, DAY_COUNT, null), new Action("", ""));
    }

    @Test
    public void addTimeTariffInavalid() {
        Role role = RoleTest.generateRole();
        role.addAction(new Action("SET_TARIFF", ""));

        Ticket ticket = new Ticket();
        Assert.assertEquals(null, ticket.addTariff(role, null));
    }

    @Test
    public void changeTripTariff() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT));
        Role role = RoleTest.generateRole();
        role.addAction(new Action("CHANGE_TARIFF", ""));

        ticket.changeTariff(role, new Tariff(TariffType.TIME, DAY_COUNT, null));

        Assert.assertNull(ticket.getReminingTrips());
        Assert.assertNotNull(ticket.getExpTime());
        Assert.assertTrue(LocalDate.now().isBefore(ticket.getExpTime()));
    }

    @Test
    public void changeTripTariffInvalid() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT));
        Role role = RoleTest.generateRole();
        role.addAction(new Action("CHANGE_TARIFF", ""));


        Assert.assertEquals(null, ticket.changeTariff(role, null));
    }

    @Test(expected = AccessDeniedException.class)
    public void changeTripTariffException() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT));
        Role role = RoleTest.generateRole();

        ticket.changeTariff(role, new Tariff(TariffType.TIME, DAY_COUNT, null));
    }

    @Test
    public void changeTimeTariff() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TIME, DAY_COUNT, null));
        Role role = RoleTest.generateRole();
        role.addAction(new Action("CHANGE_TARIFF", ""));

        ticket.changeTariff(role, new Tariff(TariffType.TRIP, null, TRIPS_COUNT));

        Assert.assertNull(ticket.getExpTime());
        Assert.assertEquals(TRIPS_COUNT, ticket.getReminingTrips());
    }

    @Test(expected = AccessDeniedException.class)
    public void changeTimeTariffExeption() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TIME, DAY_COUNT, null));
        Role role = RoleTest.generateRole();

        ticket.changeTariff(role, new Tariff(TariffType.TRIP, null, TRIPS_COUNT));
    }

    @Test
    public void checkExpireWithTrip() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TRIP, null, TRIPS_COUNT));

        ticket.checkExpire();

        Assert.assertEquals(1, TRIPS_COUNT - ticket.getReminingTrips());
        for (int i = 0; i < TRIPS_COUNT - 1; i++) {
            Assert.assertTrue(ticket.checkExpire());
        }
        Assert.assertFalse(ticket.checkExpire());
    }

    @Test
    public void checkExpirWithTime() {
        Ticket ticket = generateTicket(new Tariff(TariffType.TIME, 0, null));

        ticket.checkExpire();

        Assert.assertFalse(ticket.checkExpire());
    }


    public static Ticket generateTicket(final Tariff tariff) {
        Role role = RoleTest.generateRole();
        role.addAction(new Action("SET_TARIFF", ""));

        Ticket ticket = new Ticket();
        ticket.addTariff(role, tariff);
        return ticket;
    }

    public static Ticket generateTicket(final Tariff tariff, final Action action) {
        Role role = RoleTest.generateRole();
        role.addAction(action);

        Ticket ticket = new Ticket();
        ticket.addTariff(role, tariff);
        return ticket;
    }

}
