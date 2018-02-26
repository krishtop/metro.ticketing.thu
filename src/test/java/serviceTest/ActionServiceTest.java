package serviceTest;


import metro.ticketing.app.exception.NotFoundException;
import metro.ticketing.app.model.Action;
import metro.ticketing.app.repository.ActionRepository;
import metro.ticketing.app.repositoryImpl.ActionRepositoryImpl;
import metro.ticketing.app.service.ActionService;
import metro.ticketing.app.utils.DataBaseUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ActionServiceTest {

    private ActionService actionService;
    private ActionRepository actionRepository;

//    @Before
//    public void init() {
//        actionRepository = new ActionRepositoryTestImpl();
//        actionService = new ActionService(actionRepository);
//    }

    @Before
    public void init_() {
        actionRepository = new ActionRepositoryImpl(DataBaseUtils.getDataSource());
        actionService = new ActionService(actionRepository);
    }

    @Test
    public void createActionTest() {

        Action action = new Action("test name", "test descr");
        action = actionService.createAction(action.getName(), action.getDescription());

        Assert.assertEquals(action, actionRepository.get(action.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createActionInvalid() {

        Action action = new Action(null, null);
        actionService.createAction(action.getName(), action.getDescription());
    }


    @Test
    public void deleteActionTest() {
        Action action = new Action("test name 2", "test descr 2");
        action = actionService.createAction(action.getName(), action.getDescription());
        actionService.deleteAction(action.getId());

        Assert.assertEquals(null, actionRepository.get(action.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteActionInvalidParam() {
        Action action = new Action("test name 2", "test descr2");
        action = actionService.createAction(action.getName(), action.getDescription());
        actionService.deleteAction(99L);

        Assert.assertEquals(null, actionRepository.get(action.getId()));
    }

}


