package metro.ticketing.app.model;

public class Tariff {

    private Long id;
    private TariffType tariffType;
    private Integer dayCount;
    private Integer tripsCount;


    public Tariff(final TariffType tariffType, final Integer dayCount, final Integer tripsCount) {
        this.tariffType = tariffType;
        this.dayCount = dayCount;
        this.tripsCount = tripsCount;
    }

    public Tariff(final Long id, final TariffType tariffType, final Integer dayCount, final Integer tripsCount) {
        this.id = id;
        this.tariffType = tariffType;
        this.dayCount = dayCount;
        this.tripsCount = tripsCount;
    }


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(final TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(final Integer dayCount) {
        this.dayCount = dayCount;
    }

    public Integer getTripsCount() {
        return tripsCount;
    }

    public void setTripsCount(final Integer tripsCount) {
        this.tripsCount = tripsCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;

        Tariff tariff = (Tariff) o;

        if (getId() != null ? !getId().equals(tariff.getId()) : tariff.getId() != null) return false;
        if (getTariffType() != tariff.getTariffType()) return false;
        if (getDayCount() != null ? !getDayCount().equals(tariff.getDayCount()) : tariff.getDayCount() != null)
            return false;
        return getTripsCount() != null ? getTripsCount().equals(tariff.getTripsCount()) : tariff.getTripsCount() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTariffType() != null ? getTariffType().hashCode() : 0);
        result = 31 * result + (getDayCount() != null ? getDayCount().hashCode() : 0);
        result = 31 * result + (getTripsCount() != null ? getTripsCount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "tariffType=" + tariffType +
                ", dayCount=" + dayCount +
                ", tripsCount=" + tripsCount +
                '}';
    }
}
