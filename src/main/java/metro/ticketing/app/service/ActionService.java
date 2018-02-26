package metro.ticketing.app.service;


import metro.ticketing.app.exception.NotFoundException;
import metro.ticketing.app.model.Action;
import metro.ticketing.app.repository.ActionRepository;

public class ActionService {

    private ActionRepository actionRepository;

    public ActionService(final ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    public Action createAction(final String name, final String description) {
        if (name == null ||
                description == null) {
            throw new IllegalArgumentException("");
        }
        Action action = new Action(name, description);

        return actionRepository.create(action);
    }

    public void deleteAction(final Long actionId) {
        if (actionId == null) {
            throw new IllegalArgumentException("");
        }

        if (actionRepository.get(actionId) == null) {
            throw new NotFoundException("Action with id " + actionId + " not found");
        }
        actionRepository.delete(actionId);
    }
}
