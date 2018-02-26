package modelTest;


import metro.ticketing.app.exception.AccessDeniedException;
import metro.ticketing.app.model.Action;
import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.RequestStatus;
import metro.ticketing.app.model.Role;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;


public class RequestTest {

    @Test
    public void accept() {
        Request request = generateUser(RequestStatus.NEW);
        Role role = RoleTest.generateRole();
        role.addAction(new Action("ACCEPT_REQUEST", ""));

        request.accept(role);

        Assert.assertEquals(RequestStatus.ACCEPTED, request.getRequestStatus());
    }

    @Test
    public void acceptExeption() {
        Request request = generateUser(RequestStatus.NEW);
        Role role = RoleTest.generateRole();

        try {
            request.accept(role);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccessDeniedException);
        }
    }

    @Test
    public void reject() {
        Request request = generateUser(RequestStatus.NEW);
        Role role = RoleTest.generateRole();
        role.addAction(new Action("REJECT_REQUEST", ""));

        request.reject(role);

        Assert.assertEquals(RequestStatus.REJECTED, request.getRequestStatus());
    }

    @Test
    public void rejectExeption() {
        Request request = generateUser(RequestStatus.NEW);
        Role role = RoleTest.generateRole();

        try {
            request.reject(role);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof AccessDeniedException);
        }
    }

    public static Request generateUser(final RequestStatus requestStatus) {
        return new Request(requestStatus, "name" + new Random().nextInt(10000), "1234@mail.ru", RoleTest.generateRole());
    }

    public static Request generateUser(final RequestStatus requestStatus, final Role role) {
        return new Request(requestStatus, "name" + new Random().nextInt(10000), "1234@mail.ru", role);
    }
}
