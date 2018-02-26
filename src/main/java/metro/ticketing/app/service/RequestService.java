package metro.ticketing.app.service;


import metro.ticketing.app.exception.AccessDeniedException;
import metro.ticketing.app.exception.NotFoundException;
import metro.ticketing.app.model.Action;
import metro.ticketing.app.model.Request;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.repository.RoleRepository;
import metro.ticketing.app.view.RequestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RoleRepository roleRepository;


    public RequestService(final RequestRepository requestRepository, final RoleRepository roleRepository) {
        this.requestRepository = requestRepository;
        this.roleRepository = roleRepository;
    }

    public Request createRequest(final RequestView requestView) {
        if (requestView == null
                || requestView.getRoleId() == null
                || requestView.getName() == null
                || requestView.getRequestStatus() == null) {
            throw new IllegalArgumentException("");
        }

        if (roleRepository.get(requestView.getRoleId()) == null) {
            throw new NotFoundException("");
        }

        Request request = new Request(requestView.getRequestStatus(), requestView.getName(), requestView.getEmail(), roleRepository.get(requestView.getRoleId()));

        return requestRepository.create(request);
    }

    public Request changeRequestInfo(final Long contextRequestId, final Long requestIdToUpdate, final RequestView requestView) {
        final Action action = new Action("CHANGE_REQUEST_INFO", "");

        if (contextRequestId == null
                || requestIdToUpdate == null
                || requestView == null
                || requestView.getRoleId() == null
                || requestView.getName() == null
                || requestView.getRequestStatus() == null) {
            throw new IllegalArgumentException("");
        }

        Request contextRequest = requestRepository.get(contextRequestId);

        if (contextRequest == null || requestRepository.get(requestIdToUpdate) == null || roleRepository.get(requestView.getRoleId()) == null) {
            throw new NotFoundException("");
        }


        if (!contextRequest.getRole().checkPermission(action)) {
            throw new AccessDeniedException("Action: " + action.getName());
        }

        Request newRequest = new Request(requestView.getRequestStatus(), requestView.getName(), requestView.getEmail(), roleRepository.get(requestView.getRoleId()));

        return requestRepository.update(requestIdToUpdate, newRequest);
    }

    public Request acceptRequest(final Long contextRequestId, final Long userId) {

        if (contextRequestId == null
                || userId == null) {
            throw new IllegalArgumentException("");
        }

        Request contextRequest = requestRepository.get(contextRequestId);
        Request request = requestRepository.get(userId);
        if (contextRequest == null || contextRequest.getRole() == null || request == null) {
            throw new NotFoundException("");
        }
        request.accept(contextRequest.getRole());

        Request result = requestRepository.update(request.getId(), request);

        sendLetter("Accepted", result.getEmail());

        return result;
    }

    public Request rejectRequest(final Long contextRequestId, final Long userId) {

        if (contextRequestId == null
                || userId == null) {
            throw new IllegalArgumentException("");
        }

        Request contextRequest = requestRepository.get(contextRequestId);
        Request request = requestRepository.get(userId);
        if (contextRequest == null || contextRequest.getRole() == null || request == null) {
            throw new NotFoundException("");
        }
        request.reject(contextRequest.getRole());

        Request result = requestRepository.update(request.getId(), request);

        sendLetter("Rejected", result.getEmail());

        return result;
    }


//    public Collection<Request> getUsersByStatus(final RequestStatus requestStatus) {
//        if (requestStatus == null) {
//            throw new IllegalArgumentException("");
//        }
//
//        Collection<Request> requestCollection = new ArrayList<>();
//        requestCollection.addAll(requestRepository.getByStatus(requestStatus));
//
//        return requestCollection;
//    }

    private void sendLetter(final String body, final String email) {
        //
    }


}
