package modelTest;

import metro.ticketing.app.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class RoleTest {

    private final Action ACCEPT_REQUEST = new Action("ACCEPT_USER", "");
    private final Action REJECT_REQUEST = new Action("REJECT_USER", "");
    private final Action CREATE_TICKET = new Action("CREATE_TICKET", "");

    @Test
    public void init() {
        Role role = generateRole();

        Assert.assertNotNull(role.getName());
        Assert.assertNotNull(role.getActions());
    }

    @Test
    public void actionAdd() {
        Role role = generateRole();
        final int actionsSizeBefore = role.getActions().size();
        role.addAction(ACCEPT_REQUEST);
        final int actionsSizeAfter = role.getActions().size();

        Assert.assertEquals(1, actionsSizeAfter - actionsSizeBefore);
    }

    @Test
    public void actionRemove() {
        Role role = generateRole();
        role.addAction(REJECT_REQUEST);
        final int actionsSizeBefore = role.getActions().size();
        role.removeAction(REJECT_REQUEST);
        final int actionsSizeAfter = role.getActions().size();

        Assert.assertEquals(1, actionsSizeBefore - actionsSizeAfter);
    }

    @Test
    public void checkPermission() {
        Role role = generateRole();

        Collection<Action> actions = new HashSet<>();
        actions.add(REJECT_REQUEST);
        actions.add(ACCEPT_REQUEST);

        role.setActions(actions);

        Assert.assertTrue(role.checkPermission(REJECT_REQUEST));
        Assert.assertFalse(role.checkPermission(CREATE_TICKET));
    }

    public static Role generateRole() {
        return new Role("Role" + new Random().nextInt(100000), new ArrayList<>());
    }
}
