package metro.ticketing.app.service;


import metro.ticketing.app.exception.NotFoundException;
import metro.ticketing.app.model.*;
import metro.ticketing.app.repository.TariffRepository;
import metro.ticketing.app.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private TariffRepository tariffRepository;


    public TicketService(final TicketRepository ticketRepository, final TariffRepository tariffRepository) {
        this.ticketRepository = ticketRepository;
        this.tariffRepository = tariffRepository;
    }

    public Ticket createTicket(final Request contextRequest, final Long tariffId) {

        if (contextRequest == null
                || contextRequest.getRole() == null
                || tariffId == null) {
            throw new IllegalArgumentException();
        }

        Tariff tariff = tariffRepository.get(tariffId);
        if (tariff == null) {
            throw new NotFoundException("");
        }

        Ticket newTicket = new Ticket();
        newTicket.addTariff(contextRequest.getRole(), tariff);

        return ticketRepository.create(newTicket);
    }

    public Ticket updateTicket(final Request contextRequest, final Long ticketId, final Long tariffId) {

        if (contextRequest == null
                || contextRequest.getRole() == null
                || tariffId == null
                || ticketId == null) {
            throw new IllegalArgumentException();
        }

        Ticket ticket = ticketRepository.get(ticketId);
        Tariff tariff = tariffRepository.get(tariffId);
        if (ticket == null || tariff == null) {
            throw new NotFoundException("");
        }

        Ticket newTicket = new Ticket();
        newTicket.changeTariff(contextRequest.getRole(), tariff);

        return ticketRepository.update(ticket.getId(), newTicket);
    }

    public Boolean checkTicket(final Long ticketId) {
        if (ticketId == null) {
            throw new IllegalArgumentException("");
        }

        Ticket ticket = ticketRepository.get(ticketId);

        if (ticket == null) {
            throw new NotFoundException("");
        }

        return ticket.checkExpire();
    }

}
