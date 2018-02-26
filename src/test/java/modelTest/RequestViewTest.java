package modelTest;

import metro.ticketing.app.model.RequestStatus;
import org.junit.Assert;
import org.junit.Test;
import metro.ticketing.app.view.RequestView;

public class RequestViewTest {

    @Test
    public  void init(){
        RequestStatus requestStatus = RequestStatus.NEW;
        String name = "sdf";
        String email = "fdgfd";
        Long roleId = 1L;

        RequestView requestView = new RequestView(requestStatus, name, email, roleId);
        Assert.assertEquals(requestStatus, requestView.getRequestStatus());
        Assert.assertEquals(name, requestView.getName());
        Assert.assertEquals(email, requestView.getEmail());
        Assert.assertEquals(roleId, requestView.getRoleId());

    }
}
