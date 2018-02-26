package metro.ticketing.app.repository;


import metro.ticketing.app.model.Tariff;

import java.util.Collection;

public interface TariffRepository {

    Tariff create(final Tariff tariff);

    void delete(final Long id);

    Tariff get(final Long id);

    Collection<Tariff> getAll();
}
