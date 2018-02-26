package metro.ticketing.app.repositoryTestImpl;

import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.RequestStatus;
import metro.ticketing.app.repository.RequestRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class RequestRepositoryTestImpl implements RequestRepository {

    private AtomicLong idSequence = new AtomicLong(0);
    static HashMap<Long, Request> storage = new HashMap<>();

    @Override
    public Request create(final Request request) {
        Long key = idSequence.getAndIncrement();

        Request newRequest = new Request(key, request.getRequestStatus(), request.getName(), request.getEmail(), request.getRole());
        storage.put(key, newRequest);

        return newRequest;
    }

    @Override
    public Request update(final Long oldRequestId, final Request request) {
        Request oldRequest = storage.get(oldRequestId);
        if (oldRequest == null) {
            return null;
        }
        Request newRequest = new Request(oldRequestId, request.getRequestStatus(), request.getName(), request.getEmail(), request.getRole());
        storage.put(oldRequestId, newRequest);

        return newRequest;
    }

    @Override
    public void delete(final Long id) {
        storage.remove(id);
    }

    @Override
    public Request get(final Long id) {
        return storage.get(id);
    }

    @Override
    public Collection<Request> getAll() {
        return storage.values();
    }

    @Override
    public Collection<Request> getByStatus(final RequestStatus requestStatus) {
        return storage.values().stream().filter(a -> a.getRequestStatus().equals(requestStatus)).collect(Collectors.toList());
    }
}
