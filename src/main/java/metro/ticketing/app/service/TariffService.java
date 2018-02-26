package metro.ticketing.app.service;


import metro.ticketing.app.exception.AccessDeniedException;
import metro.ticketing.app.exception.NotFoundException;
import metro.ticketing.app.model.Action;
import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.Tariff;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.repository.TariffRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class TariffService {

    private TariffRepository tariffRepository;
    private RequestRepository requestRepository;

    public TariffService(final TariffRepository tariffRepository, final RequestRepository requestRepository) {
        this.tariffRepository = tariffRepository;
        this.requestRepository = requestRepository;
    }

    public Tariff createTariff(final Long contextRequestId, final Tariff tariff) {
        Action action = new Action("CREATE_TARIFF", "");

        if (contextRequestId == null
                || tariff == null
                || requestRepository.get(contextRequestId) == null) {
            throw new IllegalArgumentException("");
        }


        Request contextRequest = requestRepository.get(contextRequestId);
        if (!contextRequest.getRole().checkPermission(action)) {
            throw new AccessDeniedException("Action: " + action.getName());
        }

        return tariffRepository.create(tariff);
    }

    public void deleteTariff(final Long contextRequestId, final Long id) {
        Action action = new Action("DELETE_TARIFF", "");

        if (id == null
                || contextRequestId == null
                || requestRepository.get(contextRequestId) == null) {
            throw new IllegalArgumentException("");
        }

        if (tariffRepository.get(id) == null) {
            throw new NotFoundException("");
        }

        Request contextRequest = requestRepository.get(contextRequestId);

        if (!contextRequest.getRole().checkPermission(action)) {
            throw new AccessDeniedException("Action: " + action.getName());
        }

        if (tariffRepository.get(id) == null) {
            throw new NotFoundException("Tariff with id " + id + " not found");
        }
        tariffRepository.delete(id);
    }

    public Collection<Tariff> getTariffs() {
        return tariffRepository.getAll();
    }

}
