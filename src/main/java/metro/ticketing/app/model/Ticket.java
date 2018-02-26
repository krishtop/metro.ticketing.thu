package metro.ticketing.app.model;



import metro.ticketing.app.exception.AccessDeniedException;

import java.time.LocalDate;

    public class Ticket {

    private Long id;
    private Boolean activated;
    private Integer reminingTrips;
    private LocalDate expTime;
    private Tariff tariff;

    public Ticket() {
        activated = true;
    }

    public Ticket(final Long id, final Boolean activated, final Integer reminingTrips, final LocalDate expTime, final Tariff tariff) {
        this.id = id;
        this.activated = activated;
        this.reminingTrips = reminingTrips;
        this.expTime = expTime;
        this.tariff = tariff;
    }

    public Boolean checkExpire() {
        switch (tariff.getTariffType()) {
            case TIME:
                return LocalDate.now().isBefore(expTime);
            case TRIP:
                if (reminingTrips != 0) {
                    --reminingTrips;
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(final Boolean activated) {
        this.activated = activated;
    }

    public Integer getReminingTrips() {
        return reminingTrips;
    }

    public void setReminingTrips(final Integer reminingTrips) {
        this.reminingTrips = reminingTrips;
    }

    public LocalDate getExpTime() {
        return expTime;
    }

    public void setExpTime(final LocalDate expTime) {
        this.expTime = expTime;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(final Tariff tariff) {
        this.tariff = tariff;
    }

    public Tariff addTariff(final Role role, final Tariff tariff) {
        Action action = new Action("SET_TARIFF", "");
        if (!role.checkPermission(action)) {
            throw new AccessDeniedException("");
        }

        if (this.tariff == null && tariff != null) {
            if (Common(tariff)) return tariff;
        }
        this.tariff = null;
        return null;
    }

    public Tariff  changeTariff(final Role role, final Tariff tariff) {
        Action action = new Action("CHANGE_TARIFF", "");
        if (!role.checkPermission(action)) {
            throw new AccessDeniedException("");
        }

        if (tariff != null) {
            if (Common(tariff)) return tariff;
        }
        this.tariff = null;
        return null;
    }

    private boolean Common(final Tariff tariff) {
        switch (tariff.getTariffType()) {
            case TRIP:
                this.reminingTrips = tariff.getTripsCount();
                this.expTime = null;
                this.tariff = tariff;
                return true;
            case TIME:
                this.expTime = LocalDate.now().plusDays(tariff.getDayCount());
                this.tariff = tariff;
                this.reminingTrips = null;
                return true;
        }
        return false;
    }

    @Override
    public Ticket clone() {
        return new Ticket(this.id, this.activated, this.reminingTrips, this.expTime, this.tariff);
    }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Ticket)) return false;

            Ticket ticket = (Ticket) o;

            if (getId() != null ? !getId().equals(ticket.getId()) : ticket.getId() != null) return false;
            if (getActivated() != null ? !getActivated().equals(ticket.getActivated()) : ticket.getActivated() != null)
                return false;
            if (getReminingTrips() != null ? !getReminingTrips().equals(ticket.getReminingTrips()) : ticket.getReminingTrips() != null)
                return false;
            if (getExpTime() != null ? !getExpTime().equals(ticket.getExpTime()) : ticket.getExpTime() != null)
                return false;
            return getTariff() != null ? getTariff().equals(ticket.getTariff()) : ticket.getTariff() == null;
        }

        @Override
        public int hashCode() {
            int result = getId() != null ? getId().hashCode() : 0;
            result = 31 * result + (getActivated() != null ? getActivated().hashCode() : 0);
            result = 31 * result + (getReminingTrips() != null ? getReminingTrips().hashCode() : 0);
            result = 31 * result + (getExpTime() != null ? getExpTime().hashCode() : 0);
            result = 31 * result + (getTariff() != null ? getTariff().hashCode() : 0);
            return result;
        }
    }
