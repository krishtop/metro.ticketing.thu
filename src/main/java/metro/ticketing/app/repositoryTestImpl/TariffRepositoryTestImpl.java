package metro.ticketing.app.repositoryTestImpl;

import metro.ticketing.app.model.Tariff;
import metro.ticketing.app.repository.TariffRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TariffRepositoryTestImpl implements TariffRepository {

    private final static AtomicLong idSequence = new AtomicLong(0);
    private final static HashMap<Long, Tariff> storage = new HashMap<>();

    @Override
    public Tariff create(final Tariff tariff) {
        Long key = idSequence.getAndIncrement();

        Tariff newTariff = new Tariff(key, tariff.getTariffType(), tariff.getDayCount(), tariff.getTripsCount());
        storage.put(key, newTariff);

        return newTariff;
    }


    @Override
    public void delete(final Long id) {
        storage.remove(id);
    }

    @Override
    public Tariff get(final Long id) {
        return storage.get(id);
    }

    @Override
    public Collection<Tariff> getAll() {
        return storage.values();
    }


}
