package serviceTest;


import metro.ticketing.app.model.Action;
import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.RequestStatus;
import metro.ticketing.app.model.Role;
import metro.ticketing.app.repository.ActionRepository;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.repository.RoleRepository;
import metro.ticketing.app.repositoryImpl.ActionRepositoryImpl;
import metro.ticketing.app.repositoryImpl.RequestRepositoryImpl;
import metro.ticketing.app.repositoryImpl.RoleRepositoryImpl;
import metro.ticketing.app.service.RequestService;
import metro.ticketing.app.utils.DataBaseUtils;
import metro.ticketing.app.view.RequestView;
import modelTest.RequestTest;
import modelTest.RoleTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static metro.ticketing.app.model.RequestStatus.*;

public class RequestServiceTest {

    private RequestService requestService;
    private RequestRepository requestRepository;
    private RoleRepository roleRepository;
    private ActionRepository actionRepository;

//    @Before
//    public void init() {
//        requestRepository = new RequestRepositoryTestImpl();
//        roleRepository = new RoleRepositoryTestImpl();
//        requestService = new RequestService(requestRepository, roleRepository);
//    }

    @Before
    public void init_() {
        roleRepository = new RoleRepositoryImpl(DataBaseUtils.getDataSource());
        requestRepository = new RequestRepositoryImpl(DataBaseUtils.getDataSource(), roleRepository);
        actionRepository = new ActionRepositoryImpl(DataBaseUtils.getDataSource());
        requestService = new RequestService(requestRepository, roleRepository);
    }

    @Test
    public void userRegistration() {

        Role role = roleRepository.create(RoleTest.generateRole());
        RequestView requestView = new RequestView(RequestStatus.NEW, "name", "123@gmail.com", role.getId());

        Request result = requestService.createRequest(requestView);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(requestView.getName(), result.getName());
        Assert.assertEquals(requestView.getRequestStatus(), result.getRequestStatus());
        Assert.assertEquals(requestView.getRoleId(), result.getRole().getId());

    }

    @Test
    public void changeUserInfo() {
        Role contextRole = RoleTest.generateRole();
        Action action = actionRepository.create(new Action("CHANGE_REQUEST_INFO", ""));
        contextRole.addAction(action);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Role role = roleRepository.create(RoleTest.generateRole());
        RequestView requestView = new RequestView(RequestStatus.NEW, "name", "123@gmail.com", role.getId());

        Request request = requestService.createRequest(requestView);
        Role newRole = RoleTest.generateRole();
        newRole = roleRepository.create(newRole);

        Request newRequest = RequestTest.generateUser(ACCEPTED, newRole);
        RequestView newRequestView = new RequestView(newRequest.getRequestStatus(), newRequest.getName(), newRequest.getEmail(), newRequest.getRole().getId());

        Request result = requestService.changeRequestInfo(contextRequest.getId(), request.getId(), newRequestView);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(newRequestView.getName(), result.getName());
        Assert.assertEquals(newRequestView.getRequestStatus(), result.getRequestStatus());
        Assert.assertEquals(newRequestView.getRoleId(), result.getRole().getId());
    }


    @Test
    public void acceptRequest() {
        Request result = actionOnUser("ACCEPT_REQUEST", ACCEPTED);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(ACCEPTED, result.getRequestStatus());
    }


    @Test
    public void rejectRequest() {
        Request result = actionOnUser("REJECT_REQUEST", REJECTED);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
        Assert.assertEquals(REJECTED, result.getRequestStatus());
    }

    private Request actionOnUser(final String actionName, final RequestStatus requestStatus) {
        Role contextRole = RoleTest.generateRole();
        Action action = actionRepository.create(new Action(actionName, ""));
        contextRole.addAction(action);
        contextRole = roleRepository.create(contextRole);

        Request contextRequest = RequestTest.generateUser(ACCEPTED, contextRole);
        contextRequest = requestRepository.create(contextRequest);

        Role role = roleRepository.create(RoleTest.generateRole());
        RequestView requestView = new RequestView(RequestStatus.NEW, "name", "123@gmail.com", role.getId());

        Request req = requestService.createRequest(requestView);

        Request pickedRequest = requestRepository.get(req.getId());

        if (requestStatus.equals(ACCEPTED)) {
            return requestService.acceptRequest(contextRequest.getId(), pickedRequest.getId());
        } else {
            return requestService.rejectRequest(contextRequest.getId(), pickedRequest.getId());
        }
    }


}
