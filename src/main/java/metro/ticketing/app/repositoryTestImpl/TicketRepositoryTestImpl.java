package metro.ticketing.app.repositoryTestImpl;

import metro.ticketing.app.model.Ticket;
import metro.ticketing.app.repository.TicketRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TicketRepositoryTestImpl implements TicketRepository {

    private AtomicLong idSequence = new AtomicLong(0);
    static HashMap<Long, Ticket> storage = new HashMap<>();

    @Override
    public Ticket create(final Ticket ticket) {
        Long key = idSequence.getAndIncrement();

        Ticket newTicket = ticket.clone();
        newTicket.setId(key);
        storage.put(key, newTicket);

        return newTicket;
    }

    @Override
    public Ticket update(final Long oldTicketId, final Ticket ticket) {
        Ticket oldTicket = storage.get(oldTicketId);
        if (oldTicket == null) {
            return null;
        }
        Ticket newTicket = ticket.clone();
        newTicket.setId(oldTicketId);
        storage.put(oldTicketId, newTicket);

        return newTicket;
    }

    @Override
    public void delete(final Long id) {
        storage.remove(id);
    }

    @Override
    public Collection<Ticket> getAll() {
        return storage.values();
    }

    @Override
    public Ticket get(final Long id) {
        return storage.get(id);
    }

}
