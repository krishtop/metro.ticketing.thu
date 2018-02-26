package metro.ticketing.app.repository;

import metro.ticketing.app.model.Ticket;

import java.util.Collection;

public interface TicketRepository {

    Ticket create(final Ticket ticket);

    Ticket update(final Long oldTicketId, final Ticket ticket);

    void delete(final Long id);

    Collection<Ticket> getAll();

    Ticket get(final Long id);

}
