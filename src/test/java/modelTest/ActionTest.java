package modelTest;

import metro.ticketing.app.model.Action;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ActionTest {

    @Test
    public void init() {
        Action action = generateAction();

        Assert.assertNotNull(action.getName());
        Assert.assertNotNull(action.getDescription());
    }

    public static Action generateAction() {
        return new Action("Action" + new Random().nextInt(100000), "Description");
    }
}
