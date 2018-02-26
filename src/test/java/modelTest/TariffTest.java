package modelTest;

import metro.ticketing.app.model.Tariff;
import metro.ticketing.app.model.TariffType;
import org.junit.Assert;
import org.junit.Test;

public class TariffTest {

    public static TariffType TARIFF_TYPE_TIME = TariffType.TIME;
    public static TariffType TARIFF_TYPE_TRIP = TariffType.TRIP;

    public static Integer DAY_COUNT = 10;
    public static Integer TRIPS_COUNT = 100;

    @Test
    public void init1() {
        Tariff tariff = new Tariff(TARIFF_TYPE_TIME, DAY_COUNT, null);

        Assert.assertEquals(TARIFF_TYPE_TIME, tariff.getTariffType());
        Assert.assertEquals(DAY_COUNT, tariff.getDayCount());
        Assert.assertNull(tariff.getTripsCount());
    }

    @Test
    public void init2() {
        Tariff tariff = new Tariff(TARIFF_TYPE_TRIP, null, TRIPS_COUNT);

        Assert.assertEquals(TARIFF_TYPE_TRIP, tariff.getTariffType());
        Assert.assertEquals(TRIPS_COUNT, tariff.getTripsCount());
        Assert.assertNull(tariff.getDayCount());
    }

    public static Tariff generateTariff(final TariffType tariffType) {
        if (tariffType == null) {
            throw new IllegalArgumentException("Can't be null");
        }

        Tariff tariff;

        if (tariffType.equals(TariffType.TRIP)) {
            tariff = new Tariff(TariffType.TRIP, null, 10);
        } else {
            tariff = new Tariff(TariffType.TIME, 10, null);
        }

        return tariff;
    }
}
