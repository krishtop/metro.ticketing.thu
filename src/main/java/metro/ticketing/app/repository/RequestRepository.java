package metro.ticketing.app.repository;

import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.RequestStatus;

import java.util.Collection;

public interface RequestRepository {

    Request create(final Request request);

    Request update(final Long oldUserId, final Request request);

    void delete(final Long id);

    Request get(final Long id);

    Collection<Request> getByStatus(final RequestStatus requestStatus);

    Collection<Request> getAll();

}
